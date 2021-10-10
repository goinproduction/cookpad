package com.paulbaker.cookpad.feature.home.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.feature.home.fragment.InspirationFragment
import com.paulbaker.cookpad.feature.home.fragment.KitchenFriendFragment

class HomeViewPagerAdapter(private val context: Context, fm:FragmentManager):FragmentPagerAdapter(fm) {
    override fun getCount(): Int {
        return 2
    }

    override fun getItem(position: Int): Fragment {
       return when(position){
           0->KitchenFriendFragment()
           1->InspirationFragment()
           else-> throw IllegalStateException("cannot get item")
       }
    }

    override fun getPageTitle(position: Int): CharSequence {
       return when(position){
           0->context.getString(R.string.title_kitchen_friend)
           1->context.getString(R.string.title_inspiration_store)
           else->throw IllegalStateException("cannot get title")
       }
    }
}