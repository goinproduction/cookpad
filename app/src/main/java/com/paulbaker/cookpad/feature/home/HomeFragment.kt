package com.paulbaker.cookpad.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.databinding.FragmentHomeBinding
import com.paulbaker.cookpad.feature.home.adapter.HomeViewPagerAdapter

class HomeFragment : Fragment(),View.OnClickListener, TabLayout.OnTabSelectedListener {

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var viewPagerAdapter:HomeViewPagerAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupViewPager()
    }

    private fun setupViewPager() {
        viewPagerAdapter= HomeViewPagerAdapter(requireContext(),childFragmentManager)
        binding.viewPager.adapter=viewPagerAdapter
        binding.tabBarHome.setupWithViewPager(binding.viewPager)
        binding.tabBarHome.addOnTabSelectedListener(this)
    }

    private fun setupListener() {

    }


    override fun onClick(v: View?) {

    }

    override fun onTabSelected(tab: TabLayout.Tab?) {

    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {

    }

    override fun onTabReselected(tab: TabLayout.Tab?) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}