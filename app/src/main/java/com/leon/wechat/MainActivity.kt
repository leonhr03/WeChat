package com.leon.wechat

import User
import android.content.Intent
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.leon.wechat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var userList: ArrayList<User>
    private lateinit var adapter: UserAdapter
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRf: DatabaseReference
    private lateinit var bottomNavigationView: BottomNavigationView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.title = "WeChat"

        mDbRf = FirebaseDatabase.getInstance().getReference()

        userList = ArrayList()
        adapter = UserAdapter(this, userList)
        mAuth = FirebaseAuth.getInstance()


        userRecyclerView = findViewById(R.id.userRecyclerView)

        userRecyclerView.layoutManager = LinearLayoutManager(this)

        userRecyclerView.adapter = adapter



        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.selectedItemId = R.id.Chat

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Chat -> {
                    // Bleib in MainActivity
                    true
                }

                R.id.Home -> {
                    val intent = Intent(this, TweetActivity::class.java)
                    startActivity(intent)
                    true
                }



                R.id.Account -> {
                    // Wechsle zu SearchActivity
                    val intent = Intent(this, AccountActivity::class.java)
                    startActivity(intent)
                    true

                }

                else -> false
            }
        }








        mDbRf.child("user").addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children){
                    val currentUser = postSnapshot.getValue(User::class.java)

                    if (currentUser != null && mAuth.currentUser?.uid != currentUser.uid) {
                        userList.add(currentUser)
                    }

                }

                adapter.notifyDataSetChanged()


            }

            override fun onCancelled(error: DatabaseError) {

            }

        })




    }

    

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.logout){

            mAuth.signOut()
            val intent = Intent(this,LogIn::class.java)
            finish()
            startActivity(intent)


            return true
        }
        return true
    }


}