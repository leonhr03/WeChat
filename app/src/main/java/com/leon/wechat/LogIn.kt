package com.leon.wechat

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    private lateinit var edtEmail: EditText
    private lateinit var edtPasswort: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignup: TextView

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_log_in)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()

        edtEmail = findViewById(R.id.edt_email)
        edtPasswort = findViewById(R.id.edt_passwort)
        btnLogin = findViewById(R.id.btn_login)
        btnSignup = findViewById(R.id.btn_signin)


        btnSignup.setOnClickListener(){
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener() {
            val email = edtEmail.text.toString()
            val password = edtPasswort.text.toString()

            login(email,password)
        }


    }

    private fun login(email: String,password: String){

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    finish()

                    startActivity(intent)

                } else {
                    Toast.makeText(this, "login failed", Toast.LENGTH_SHORT).show()
                }
            }


    }


}