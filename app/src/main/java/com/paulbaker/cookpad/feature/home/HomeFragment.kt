package com.paulbaker.cookpad.feature.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulbaker.cookpad.core.extensions.Status
import com.paulbaker.cookpad.core.utils.Utils
import com.paulbaker.cookpad.data.datasource.local.FoodHomeModel
import com.paulbaker.cookpad.data.datasource.local.RegisterUser
import com.paulbaker.cookpad.data.datasource.remote.FoodResponse
import com.paulbaker.cookpad.databinding.FragmentHomeBinding
import com.paulbaker.cookpad.feature.home.adapter.FoodHomeAdapter
import com.paulbaker.cookpad.feature.login.viewmodel.UserViewModel

class HomeFragment : Fragment(), View.OnClickListener,
    FoodHomeAdapter.SetOnItemClick {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var foodHomeAdapter: FoodHomeAdapter? = null
    private val foodHomeData = mutableListOf<FoodHomeModel>()
    private val foodResponseData = mutableListOf<FoodResponse>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        fakeData()
        setupAdapter()
    }

    private fun setupListener() {

    }

    private fun fakeData() {
        for (i in 0..10) {
            foodResponseData.add(
                FoodResponse(
                    urlImageUser = "https://img-global.cpcdn.com/003_users/1921ada0286d29c3/56x56cq50/photo.jpg",
                    nameUser = "Mommy",
                    urlImageFood = "https://img-global.cpcdn.com/003_recipes/8079f3d1d2a4a803/664x470cq70/photo.jpg",
                    nameFood = "Sayur Bayam Jagung Manis",
                    like = "4",
                    dislike = "2"
                )
            )
        }
        for (i in 0..10) {
            if (i == 0) {
                foodHomeData.add(
                    FoodHomeModel(
                        type = FoodHomeAdapter.typeTwoByThree,
                        category = "Gà chiên nước mắm",
                        listItem = foodResponseData
                    )
                )
            } else {
                foodHomeData.add(
                    FoodHomeModel(
                        type = FoodHomeAdapter.typeOneByTwo,
                        category = "Gà chiên nước mắm",
                        listItem = foodResponseData
                    )
                )
            }
        }
    }

    private fun setupAdapter() {
        foodHomeAdapter = FoodHomeAdapter(requireContext(), foodHomeData, this)
        binding.rcvFoodHome.adapter = foodHomeAdapter
        binding.rcvFoodHome.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onItemClick(view: View, item: FoodResponse?) {
        Log.d("TAG", "onItemClick: ${item?.nameFood}")
        Toast.makeText(requireContext(), "Mày vừa nhấn vào cái hình", Toast.LENGTH_SHORT).show()
    }


    override fun onClick(v: View?) {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}