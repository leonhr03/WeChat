package com.leon.wechat

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatActivity : AppCompatActivity() {

    private lateinit var messageRecyclerView: RecyclerView
    private lateinit var messageBox : EditText
    private lateinit var sendButton : Button
    private lateinit var messageAdapter: messageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRf: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_chat)


        val name = intent.getStringExtra("name")
        val receiveruid = intent.getStringExtra("uid")

        val senderuid = FirebaseAuth.getInstance().currentUser?.uid

        mDbRf = FirebaseDatabase.getInstance().getReference()

        senderRoom = receiveruid + senderuid
        receiverRoom = senderuid + receiveruid

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //backButton = findViewById(R.id.imageView)
        messageRecyclerView = findViewById(R.id.chatRecyclerview)
        messageBox = findViewById(R.id.messagebox)
        sendButton = findViewById(R.id.sent)
        messageList = ArrayList()
        messageAdapter = messageAdapter(this,messageList)

        messageRecyclerView.layoutManager = LinearLayoutManager(this)
        messageRecyclerView.adapter = messageAdapter




        mDbRf.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    messageList.clear()
                    for(postSnapshot in snapshot.children){
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })

        sendButton.setOnClickListener(){
            val message = messageBox.text.toString()
            val messageObject = Message(message, senderuid)

            mDbRf.child("chats").child(senderRoom!!).child("messages").push()
                .setValue(messageObject).addOnSuccessListener{
                    mDbRf.child("chats").child(receiverRoom!!).child("messages").push()
                        .setValue(messageObject)
                }
            messageBox.setText("")
        }

    }
   
}