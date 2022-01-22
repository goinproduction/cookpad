package com.paulbaker.cookpad.feature.cart

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.core.DATA_USER
import com.paulbaker.cookpad.core.extensions.Status
import com.paulbaker.cookpad.core.extensions.Strings
import com.paulbaker.cookpad.data.datasource.local.User
import com.paulbaker.cookpad.data.datasource.remote.CartRecipesResponse
import com.paulbaker.cookpad.data.datasource.remote.Data
import com.paulbaker.cookpad.databinding.CartFragmentBinding
import com.paulbaker.cookpad.feature.cart.adapter.CartRecipesAdapter
import com.paulbaker.cookpad.feature.home.viewmodel.ProductViewModel
import com.paulbaker.cookpad.feature.search.adapter.SearchAdapter
import kotlin.math.log

class CartFragment : Fragment(), View.OnClickListener ,CartRecipesAdapter.SetOnItemClick{
    private var _binding: CartFragmentBinding? = null
    private val binding get() = _binding!!

    private val productViewModel: ProductViewModel by activityViewModels()

    private var adapter: CartRecipesAdapter? = null
    private val data: MutableList<CartRecipesResponse.Data.Recipe> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    private fun getIdUser(): String? {
        val userID = requireContext().getSharedPreferences(DATA_USER, Context.MODE_PRIVATE)
        return Gson().fromJson(
            userID.getString(DATA_USER, Gson().toJson(User())),
            User::class.java
        ).id
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = CartFragmentBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupAdapter()
        getCartUser()
    }

    private fun setupAdapter() {
        adapter = CartRecipesAdapter(requireContext(),data, mClickItem = this)
        binding.rcvFoodCart.adapter = adapter
        binding.rcvFoodCart.layoutManager = LinearLayoutManager(requireContext(),
            LinearLayoutManager.VERTICAL,false)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getCartUser() {
        productViewModel.getCartUser(getIdUser() ?: Strings.EMPTY)
            .observe(viewLifecycleOwner) { resourceResponse ->
                resourceResponse.let { resources ->
                    when (resources.status) {
                        Status.LOADING -> {
                            binding.pbLoading.visibility = View.VISIBLE
                            adapter?.setData(listOf())
                        }
                        Status.SUCCESS -> {
                            if (resources.data?.body()?.success == true) {
                                binding.pbLoading.visibility = View.GONE
                                binding.ContainerNoResult.visibility = View.GONE
                                resources.data.body()?.data?.recipes?.let { data ->
                                    this.data.clear()
                                    this.data.addAll(data)
                                    adapter?.notifyDataSetChanged()
                                }
                            } else {
                                binding.ContainerNoResult.visibility = View.VISIBLE
                                binding.pbLoading.visibility = View.GONE
                            }
                        }
                        Status.ERROR -> {
                            binding.ContainerNoResult.visibility = View.VISIBLE
                            binding.pbLoading.visibility = View.GONE
                        }
                    }
                }
            }
    }

    private fun setupListener() {
        binding.btnClose.setOnClickListener(this)
        binding.btnCreatePost.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnClose ->{
                v.findNavController().popBackStack()
            }
            R.id.btnCreatePost ->{
                v.findNavController().navigate(R.id.actionCreation)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(view: View?, item: Data, position: Int) {
        view?.findNavController()?.navigate(R.id.actionViewPost)
        item.let { productViewModel.setProductItem(it) }
    }
}