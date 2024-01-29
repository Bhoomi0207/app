package com.example.instagramclone
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.instagramclone.Model.User
import com.example.instagramclone.Utils.USER_NODE
import com.example.instagramclone.Utils.USER_PROFILE_FOLDER
import com.example.instagramclone.Utils.uploadimage
import com.example.instagramclone.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.app
import com.squareup.picasso.Picasso

class SignupActivity : AppCompatActivity() {
    val binding by lazy {
        ActivitySignupBinding.inflate(layoutInflater)
    }
    lateinit var user:User

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            uploadimage(uri, USER_PROFILE_FOLDER) {
                if (it == null) {

                } else {
                    user.image = it
                    binding.profile.setImageURI(uri)

                }
            }


        }
    }


    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        user = User()
        if (intent.hasExtra("MODE")) {
            if (intent.getIntExtra("MODE", -1) == 1) {
                binding.registerButton.text = "Update Profile"
                val auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser
                Firebase.firestore.collection(USER_NODE).document(currentUser!!.uid).get()
                    .addOnSuccessListener {
                        user = it.toObject<User>()!!
                        if (!user.image.isNullOrEmpty()) {
                            Picasso.get().load(user.image).into(binding.profile)
                        }
                        binding.name.editText?.setText(user.name)
                        binding.email.editText?.setText(user.email)
                        binding.password.editText?.setText(user.password)
                    }
            }

        }

        binding.registerButton.setOnClickListener {
            //update profile
            if (intent.getIntExtra("MODE", -1) == 1) {
                val auth = FirebaseAuth.getInstance()
                val currentUser = auth.currentUser
                if (currentUser != null) {
                    val userDocRef = Firebase.firestore
                        .collection(USER_NODE).document(currentUser.uid).set(user)
                        .addOnSuccessListener {
                            // Toast.makeText(this,"Login success",Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this, HomeActivity::class.java))
                            finish()
                        }
                }
            } else {
                if (binding.name.editText?.text.toString().equals("") or
                    binding.email.editText?.text.toString().equals("") or
                    binding.password.editText?.text.toString().equals("")
                ) {
                    Toast.makeText(this, "Please fill the all information", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    val auth = FirebaseAuth.getInstance()
                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                        binding.email.editText?.text.toString(),
                        binding.password.editText?.text.toString()
                    ).addOnCompleteListener { result ->
                        if (result.isSuccessful) {


                            user.name = binding.name.editText?.text.toString()
                            user.email = binding.email.editText?.text.toString()
                            user.password = binding.password.editText?.text.toString()
                            //Firebase.firestore.collection("User")
                            //  .document(Firebase.auth.currentUSer!!.uid).set(user)val auth = FirebaseAuth.getInstance
                            val auth = FirebaseAuth.getInstance()
                            val currentUser = auth.currentUser
                            if (currentUser != null) {

                                val userDocRef = Firebase.firestore
                                    .collection(USER_NODE).document(currentUser.uid).set(user)
                                    .addOnSuccessListener {
                                        // Toast.makeText(this,"Login success",Toast.LENGTH_SHORT).show()
                                        startActivity(Intent(this, HomeActivity::class.java))
                                        finish()
                                    }


                            }


                        } else {
                            Toast.makeText(this,
                                result.exception?.localizedMessage,
                                Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
        binding.plus.setOnClickListener {
            launcher.launch("image/*")
        }
        binding.login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }
    }




}