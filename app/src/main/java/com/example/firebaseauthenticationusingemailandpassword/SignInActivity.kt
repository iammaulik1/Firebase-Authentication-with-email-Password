package com.example.firebaseauthenticationusingemailandpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.firebaseauthenticationusingemailandpassword.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {

    lateinit var auth:FirebaseAuth
    lateinit var binding: ActivitySignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        auth=Firebase.auth

        binding.buttonSignIn.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()
            if (checkAllFields()){
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(this,"Successfully Signed In",Toast.LENGTH_SHORT).show()

                        val intent = Intent(this,HomeActivity::class.java)
                        startActivity(intent)

                        finish()

                    }else{
                        Log.e("error :" , it.exception.toString())
                    }
                }
            }

        }

        binding.textViewCreateAccount.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.textViewForgetPassword.setOnClickListener {
            val intent = Intent(this,ForgetPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun checkAllFields():Boolean{

        val email = binding.editEmail.text.toString()

        if (binding.editEmail.text.toString() == ""){
            binding.textInputLayoutEmail.error = "This Is Required Field"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.textInputLayoutEmail.error = "Check Email Format"
            return false
        }
        if (binding.editPassword.text.toString() == ""){
            binding.textInputLayoutPassword.error = "This Is Required Field"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }

        if (binding.editPassword.length() <= 6){
            binding.textInputLayoutPassword.error = "Password Should be atleast contain 6 characters"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        return true
    }
}