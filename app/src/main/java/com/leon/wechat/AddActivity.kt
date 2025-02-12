package com.leon.wechat

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Insets.add
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AddActivity : AppCompatActivity() {
    private lateinit var buAdd : Button
    private lateinit var tvTitle: TextView
    private lateinit var tvTweet: TextView
    private lateinit var mDbRf: DatabaseReference
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_add)

        buAdd = findViewById(R.id.btnAdd)
        tvTitle = findViewById(R.id.edtTitle)
        tvTweet = findViewById(R.id.edttweet)

        mDbRf = FirebaseDatabase.getInstance().getReference()


        buAdd.setOnClickListener{
            val title = tvTitle.text.toString()
            val tweet = tvTweet.text.toString()

            add(title,tweet)

        }




    }
    private fun add(title: String,tweet: String) {
        val intent = Intent(this, TweetActivity::class.java)
        mDbRf.child("tweets").child(title).setValue(tweetClass(title, tweet))
        startActivity(intent)

    }





}