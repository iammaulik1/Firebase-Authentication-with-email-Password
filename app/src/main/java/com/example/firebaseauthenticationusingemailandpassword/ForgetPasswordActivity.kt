package com.example.firebaseauthenticationusingemailandpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import com.example.firebaseauthenticationusingemailandpassword.databinding.ActivityForgetPasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgetPasswordActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivityForgetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonForgetPassword.setOnClickListener {
            val email = binding.editEmail.text.toString()
            if (checkEmail()){
                auth.sendPasswordResetEmail(email).addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(this,"Email Sent" , Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,SignInActivity::class.java)
                        startActivity(intent)

                        finish()
                    }
                }
            }
        }
    }

    private fun checkEmail():Boolean{
        val email = binding.editEmail.text.toString()
        if (binding.editEmail.text.toString()== ""){
            binding.textInputLayoutEmail.error ="This is required field"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.textInputLayoutEmail.error = "Check Email Format"
            return false
        }
        return true
    }
}