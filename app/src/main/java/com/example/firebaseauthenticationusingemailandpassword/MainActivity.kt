package com.example.firebaseauthenticationusingemailandpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        auth = Firebase.auth


        Handler(Looper.getMainLooper()).postDelayed({

            val user = auth.currentUser
            if (user!=null){
                val intent = Intent(this,HomeActivity::class.java)
                startActivity(intent)

                finish()

            }else{
                val intent = Intent(this,SignInActivity::class.java)
                startActivity(intent)

                finish()
            }

        },3000)

    }
}