package com.example.instagramclone.Adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class ViewPageAdapter (fm:FragmentManager):FragmentPagerAdapter(fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    val fragmentList= mutableListOf<Fragment>()
    val titlelist= mutableListOf<String>()
    override fun getCount():Int {
       return fragmentList.size
    }

    override fun getItem(position:Int):Fragment {
        return fragmentList.get(position)
    }

    override fun getPageTitle(position:Int):CharSequence? {
        return titlelist.get(position)
    }
    fun addFragment(fragment:Fragment,title:String){
        fragmentList.add(fragment)
        titlelist.add(title)
    }

}