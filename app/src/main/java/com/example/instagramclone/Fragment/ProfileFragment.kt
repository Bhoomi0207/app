package com.example.instagramclone.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.instagramclone.Adapters.ViewPageAdapter
import com.example.instagramclone.Model.User
import com.example.instagramclone.R
import com.example.instagramclone.SignupActivity
import com.example.instagramclone.Utils.USER_NODE
import com.example.instagramclone.databinding.FragmentProfileBinding

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {
    private lateinit var binding:FragmentProfileBinding
    private lateinit var viewPAgeAdapter:ViewPageAdapter

override fun onCreate(savedInstanceState:Bundle?) {
    super.onCreate(savedInstanceState)
}

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?,
    ):View? {
        // Inflate the layout for this fragment
       binding=FragmentProfileBinding.inflate(inflater,container,false)

     binding.editprofileButton.setOnClickListener {
         val intent = Intent(activity,SignupActivity::class.java)
         intent.putExtra("MODE",1)
         activity?.startActivity(intent)
     }

       viewPAgeAdapter= ViewPageAdapter(requireActivity().supportFragmentManager)
        viewPAgeAdapter.addFragment(MyPostFragment(),"My Post")
        viewPAgeAdapter.addFragment(MyreelFragment(),"My Reels")
        binding.viewPager.adapter=viewPAgeAdapter
        binding.tablayout.setupWithViewPager(binding.viewPager)

        return binding.root
    }

    companion object {

    }

    override fun onStart() {
        super.onStart()
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        Firebase.firestore.collection(USER_NODE).document(currentUser!!.uid).get()
            .addOnSuccessListener {
                val user:User=it.toObject<User>()!!
                binding.name.text=user.name
                binding.bio.text=user.email
                if(!user.image.isNullOrEmpty()){
                    Picasso.get().load(user.image).into(binding.profile)
                }
            }



        }
    }

