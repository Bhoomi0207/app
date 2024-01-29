package com.example.instagramclone.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.instagramclone.Model.Post
import com.example.instagramclone.databinding.MypostRvBinding
import com.google.api.Context
import com.squareup.picasso.Picasso

class MyPostRvAdapter(var context:android.content.Context,var postList:ArrayList<Post>) :RecyclerView.Adapter<MyPostRvAdapter.ViewHolder>() {
    inner class ViewHolder(var binding:MypostRvBinding):
         RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent:ViewGroup, viewType:Int):ViewHolder {
        var binding=MypostRvBinding.inflate(LayoutInflater.from(context),parent,false)
         return ViewHolder(binding)

    }

    override fun onBindViewHolder(holder:ViewHolder, position:Int) {
     //   holder.binding.postImg
        Picasso.get().load(postList.get(position).posturl).into(holder.binding.postImg)
    }

    override fun getItemCount():Int {
      return postList.size
    }
}