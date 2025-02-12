package com.leon.wechat

import User
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class AccountActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mDbRf: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_account)

        bottomNavigationView = findViewById(R.id.bnv)
        mDbRf = FirebaseDatabase.getInstance().getReference()


        val tvUser = findViewById<TextView>(R.id.Username)


        bottomNavigationView.selectedItemId = R.id.Account





        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Chat -> {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.Home ->{
                    val intent = Intent(this,TweetActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.Account -> {
                    true

                }

                else -> false
            }
        }

        mDbRf.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val currentUser = postSnapshot.getValue(User::class.java)
                     if (currentUser != null) {
                         tvUser.text = currentUser.name.toString()
                        }

                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })



    }
}