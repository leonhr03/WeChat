package com.leon.wechat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class checkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // FirebaseAuth-Instanz abrufen
        val auth = FirebaseAuth.getInstance()
        val currentUser: FirebaseUser? = auth.currentUser

        if (currentUser != null) {
            // Benutzer ist angemeldet
            val userId = currentUser.uid
            val email = currentUser.email

            // Weiterleitung zur Hauptseite
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // Kein Benutzer angemeldet, weiter zur Login-Seite
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
            finish()
        }
    }
}
