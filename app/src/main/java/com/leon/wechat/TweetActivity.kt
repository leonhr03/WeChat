package com.leon.wechat

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class TweetActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mDbRf: DatabaseReference
    private lateinit var tweetList: MutableList<tweetClass>
    private lateinit var recyclerView: RecyclerView
    private lateinit var tweetAdapter: ItemAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tweet)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "WeTweet"

        bottomNavigationView = findViewById(R.id.bnv)

        // RecyclerView RICHTIG initialisieren
        recyclerView = findViewById(R.id.rvTweet)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Adapter mit leerer Liste initialisieren
        tweetList = mutableListOf()
        tweetAdapter = ItemAdapter(tweetList)
        recyclerView.adapter = tweetAdapter

        bottomNavigationView.selectedItemId = R.id.Account
        mDbRf = FirebaseDatabase.getInstance().getReference("tweets")

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Chat -> {
                    startActivity(Intent(this, MainActivity::class.java))
                    true
                }
                R.id.Home -> true
                R.id.Account -> {
                    startActivity(Intent(this, AccountActivity::class.java))
                    true
                }
                else -> false
            }
        }

        fetchUserData()
    }

    private fun fetchUserData() {
        mDbRf.addListenerForSingleValueEvent(MyValueEventListener())
    }

    private inner class MyValueEventListener : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            tweetList.clear()
            snapshot.children.forEach { userSnapshot ->
                val user = userSnapshot.getValue(tweetClass::class.java)
                if (user != null) {
                    tweetList.add(user)
                }
            }
            tweetAdapter.notifyDataSetChanged()
        }

        override fun onCancelled(error: DatabaseError) {
            println("Fehler: ${error.message}")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.tweet_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add) {
            startActivity(Intent(this, AddActivity::class.java))
            return true
        }
        return true
    }
}
