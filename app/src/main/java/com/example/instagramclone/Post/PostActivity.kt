package com.example.instagramclone.Post

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.HomeActivity
import com.example.instagramclone.Model.Post
import com.example.instagramclone.Utils.POST
import com.example.instagramclone.Utils.POST_FOLDER
import com.example.instagramclone.Utils.USER_PROFILE_FOLDER
import com.example.instagramclone.Utils.uploadimage

import com.example.instagramclone.databinding.ActivityPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class PostActivity : AppCompatActivity() {
    val binding by lazy{
        ActivityPostBinding.inflate(layoutInflater)
    }
    var imageurl:String?=null

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadimage(uri, POST_FOLDER) {
                url->
                if (it != null) {

                    binding.selectimage.setImageURI(uri)
                     imageurl=url
                }
            }
        }
    }
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.selectimage.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.caption.setOnClickListener {
            startActivity(Intent(this,HomeActivity::class.java))
            finish()
        }

        binding.post.setOnClickListener {
            val post:Post =Post(imageurl!!,binding.caption.editText?.text.toString())
            val auth = FirebaseAuth.getInstance()
            val currentUser = auth.currentUser
            Firebase.firestore.collection(POST).document().set(post).addOnSuccessListener {
                Firebase.firestore.collection(currentUser!!.uid.toString()).document().set(post).addOnSuccessListener {
                    startActivity(Intent(this,HomeActivity::class.java))
                    finish()
                }
            }

        }




    }
}