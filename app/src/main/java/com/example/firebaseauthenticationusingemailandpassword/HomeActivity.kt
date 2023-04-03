package com.example.firebaseauthenticationusingemailandpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.firebaseauthenticationusingemailandpassword.databinding.ActivityHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {

    lateinit var auth:FirebaseAuth
    lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding =ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.buttonSignOut.setOnClickListener {
            auth.signOut()

            val intent = Intent(this,SignInActivity::class.java)
            startActivity(intent)

            finish()
        }

        binding.buttonUpdatePassword.setOnClickListener {

            val user = auth.currentUser
            val password = binding.editPassword.text.toString()

            if (checkPasswordField()){
                user?.updatePassword(password)?.addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(this,"Password Update Successful",Toast.LENGTH_SHORT).show()

                    }else{
                        Log.e("error :" , it.exception.toString())
                    }
                }
            }

            binding.buttonDeleteAccount.setOnClickListener {
                val user = Firebase.auth.currentUser
                user?.delete()?.addOnCompleteListener{
                    if (it.isSuccessful){
                        Toast.makeText(this,"Account Successfully Deleted",Toast.LENGTH_SHORT).show()
                        val intent = Intent(this,SignInActivity::class.java)
                        startActivity(intent)

                        finish()

                    }else{
                        Log.e("error :" , it.exception.toString())

                    }
                }
            }

        }
    }

    private fun checkPasswordField():Boolean{
        if (binding.editPassword.text.toString() == ""){
            binding.textInputLayoutPassword.error = "This Is Required Field"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }

        if (binding.editPassword.length() <= 6){
            binding.textInputLayoutPassword.error = "Password Should be atleast contain 7 characters"
            binding.textInputLayoutPassword.errorIconDrawable = null
            return false
        }
        return true

    }
}