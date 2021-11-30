package com.paulbaker.cookpad.feature.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulbaker.cookpad.core.extensions.Status
import com.paulbaker.cookpad.core.utils.Utils
import com.paulbaker.cookpad.data.datasource.local.FoodHomeModel
import com.paulbaker.cookpad.data.datasource.remote.FoodResponse
import com.paulbaker.cookpad.data.datasource.remote.RecipesResponse
import com.paulbaker.cookpad.databinding.FragmentHomeBinding
import com.paulbaker.cookpad.feature.home.adapter.FoodHomeAdapter
import com.paulbaker.cookpad.feature.home.viewmodel.ProductViewModel

class HomeFragment : Fragment(), View.OnClickListener,
    FoodHomeAdapter.SetOnItemClick {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var foodHomeAdapter: FoodHomeAdapter? = null
    private val foodHomeData = mutableListOf<FoodHomeModel>()

    private val productViewModel: ProductViewModel by activityViewModels()

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
        setupAdapter()
        getAllRecipes()
    }

    private fun getAllRecipes() {
        productViewModel.getAllRecipes().observe(viewLifecycleOwner) { resourceResponse ->
            resourceResponse.let { resources ->
                when (resources.status) {
                    Status.LOADING -> {
                        binding.rootHome.isClickable = false
                        binding.pbLoading.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        if (resources.data?.body()?.success == true) {
                            binding.pbLoading.visibility = View.GONE
                            binding.rootHome.isClickable = true
                            updateFoodHome(resources.data.body()!!.data?.toMutableList())
                        } else {
                            binding.pbLoading.visibility = View.GONE
                            binding.rootHome.isClickable = true
                        }
                    }
                    Status.ERROR -> {
                        binding.rootHome.isClickable = true
                        binding.pbLoading.visibility = View.GONE
                        Log.d("TAG", "error message: ${resources.message}")
                    }
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateFoodHome(foodResponseData: MutableList<RecipesResponse.Data?>?) {
        foodHomeData.clear()
        foodHomeData.addAll(Utils.groupFoodByCategory(foodResponseData))
        foodHomeAdapter?.notifyDataSetChanged()
    }

    private fun setupListener() {

    }

    private fun setupAdapter() {
        foodHomeAdapter = FoodHomeAdapter(requireContext(), foodHomeData, this)
        binding.rcvFoodHome.adapter = foodHomeAdapter
        binding.rcvFoodHome.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onItemClick(view: View, item: RecipesResponse.Data?) {
        Log.d("TAG", "onItemClick: ${item?.id}")
    }


    override fun onClick(v: View?) {

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}