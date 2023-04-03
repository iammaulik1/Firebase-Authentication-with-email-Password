package com.example.firebaseauthenticationusingemailandpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import com.example.firebaseauthenticationusingemailandpassword.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var auth:FirebaseAuth
    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonSignUp.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val password = binding.editPassword.text.toString()

            if (checkAllField()){
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                    if (it.isSuccessful){
                        auth.signOut()
                        Toast.makeText(this , "Account Created Successfull" , Toast.LENGTH_SHORT).show()

                        val intent = Intent(this,SignInActivity::class.java)
                        startActivity(intent)
                        finish()

                    }
                    else{
                        Log.e("error :" , it.exception.toString())
                    }

                }
            }

        }
    }

    private fun checkAllField() : Boolean{

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

        if (binding.editConfirmPassword.text.toString() == ""){
            binding.textInputLayoutConfirmPassword.error = "This is Required Field"
            binding.textInputLayoutConfirmPassword.errorIconDrawable = null
            return false
        }
        if (binding.editPassword.text.toString() != binding.editConfirmPassword.text.toString()){
            binding.textInputLayoutPassword.error = " Passwords Didn't Matched"
            return false
        }
        return true
    }
}