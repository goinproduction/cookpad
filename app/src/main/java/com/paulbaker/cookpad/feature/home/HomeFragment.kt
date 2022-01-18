package com.paulbaker.cookpad.feature.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.core.extensions.Status
import com.paulbaker.cookpad.core.utils.Utils
import com.paulbaker.cookpad.data.datasource.local.Data
import com.paulbaker.cookpad.data.datasource.local.FoodHomeModel
import com.paulbaker.cookpad.data.datasource.local.Payload
import com.paulbaker.cookpad.data.datasource.remote.RecipesResponse
import com.paulbaker.cookpad.databinding.FragmentHomeBinding
import com.paulbaker.cookpad.feature.home.adapter.FoodHomeAdapter
import com.paulbaker.cookpad.feature.home.viewmodel.ProductViewModel

class HomeFragment : Fragment(), View.OnClickListener,
    FoodHomeAdapter.SetOnItemClick,
    SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentHomeBinding? = null

    private val binding get() = _binding!!

    private var foodHomeAdapter: FoodHomeAdapter? = null
    private val foodHomeData = mutableListOf<FoodHomeModel>()

    private val productViewModel: ProductViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getAllRecipes()
    }


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
    }

    private fun getAllRecipes() {
        productViewModel.getAllRecipes().observe(this) { resourceResponse ->
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
    private fun updateFoodHome(foodResponseData: MutableList<Data>?) {
        foodHomeData.clear()
        foodHomeData.addAll(Utils.groupFoodByCategory(foodResponseData))
        foodHomeAdapter?.notifyDataSetChanged()
    }

    private fun setupListener() {
        binding.swipeLayout.setOnRefreshListener(this)
        binding.homepageToolbarButtonCart.setOnClickListener(this)
    }

    private fun setupAdapter() {
        foodHomeAdapter = FoodHomeAdapter(requireContext(), foodHomeData, this)
        binding.rcvFoodHome.adapter = foodHomeAdapter
        binding.rcvFoodHome.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
    }

    override fun onItemClick(view: View, item: Data?) {
        view.findNavController().navigate(R.id.actionViewPost)
        item?.let { productViewModel.setProductItem(it) }
    }


    override fun onClick(v: View?) {
        when(v?.id){
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onRefresh() {
        getAllRecipes()
        Handler(Looper.getMainLooper()).postDelayed({
            _binding?.let {
                binding.swipeLayout.isRefreshing = false
            }
        }, 2000)
    }
}