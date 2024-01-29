package com.example.instagramclone.Fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.instagramclone.Adapters.MyPostRvAdapter
import com.example.instagramclone.Model.Post
import com.example.instagramclone.R
import com.example.instagramclone.databinding.FragmentAddBinding
import com.example.instagramclone.databinding.FragmentMyPostBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase

class MyPostFragment : Fragment() {
    private lateinit var binding:FragmentMyPostBinding

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater:LayoutInflater, container:ViewGroup?,
        savedInstanceState:Bundle?,
    ):View? {
        binding=FragmentMyPostBinding.inflate(inflater,container,false)
        var postList=ArrayList<Post>()
        var adapter =MyPostRvAdapter(requireContext(),postList)
        binding.RecyclerView.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        binding.RecyclerView.adapter=adapter
        val auth = FirebaseAuth.getInstance()
        val currentUser = auth.currentUser
        Firebase.firestore.collection(currentUser!!.uid).get().addOnSuccessListener {
            var tempList= arrayListOf<Post>()
            for(i in it.documents){
                var post:Post=i.toObject<Post>()!!
                tempList.add(post)

            }
            postList.addAll(tempList)
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {


    }
}