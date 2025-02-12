package com.leon.wechat

import User
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtPasswort: EditText
    private lateinit var btnSignup: Button
    private lateinit var btnLogin: TextView

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRf: DatabaseReference

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)

        supportActionBar?.hide()

        mAuth = FirebaseAuth.getInstance()


        edtName = findViewById(R.id.edt_name)
        edtEmail = findViewById(R.id.edt_email)
        edtPasswort = findViewById(R.id.edt_passwort)
        btnSignup = findViewById(R.id.btn_signin)
        btnLogin = findViewById(R.id.tvLogin)

        btnSignup.setOnClickListener() {
            val name = edtName.text.toString()
            val email = edtEmail.text.toString()
            val password = edtPasswort.text.toString()

            SignUp(name,email,password)
        }

        btnLogin.setOnClickListener(){
            val intent = Intent(this, LogIn::class.java)
            startActivity(intent)
        }

    }
    private fun SignUp(name: String, email: String,password: String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(name, email, mAuth.uid!!)
                    val intent = Intent(this, MainActivity::class.java)
                    finish()


                    startActivity(intent)

                } else {
                    Toast.makeText(this, "some error ocured", Toast.LENGTH_SHORT).show()

                }
            }

    }
    private fun addUserToDatabase(name: String,email: String,uid: String){
        mDbRf = FirebaseDatabase.getInstance().getReference()

        mDbRf.child("user").child(uid).setValue(User(name, email, uid))
    }

}