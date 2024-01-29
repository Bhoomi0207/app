package com.example.instagramclone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.instagramclone.Model.User
import com.example.instagramclone.databinding.ActivityLoginBinding
import com.example.instagramclone.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app

class LoginActivity : AppCompatActivity() {
    val binding by lazy{
        ActivityLoginBinding.inflate(layoutInflater)
    }
    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        binding.LoginButton.setOnClickListener {
            if(binding.email.editText?.text.toString().equals("")or
                    binding.password.editText?.text.toString().equals("")){
                Toast.makeText(this,"please fill all the details",Toast.LENGTH_SHORT).show()

            }else{
                var user= User(binding.email.editText?.text.toString(), binding.password.editText?.text.toString())
                signInWithEmailAndPassword(user.email!!,user.password!!)


            }
        }
    }
    private fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                   Toast.makeText(this,"Login successful",Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomeActivity::class.java)
                    startActivity(intent)
                    finish() // Finish LoginActivity to prevent going back to it using the back button
                } else {

                    Toast.makeText(this,"Authentication failed. Please check your credentials.",Toast.LENGTH_SHORT).show()
                }
            }
    }
}