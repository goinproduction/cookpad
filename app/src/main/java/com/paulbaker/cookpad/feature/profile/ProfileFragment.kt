package com.paulbaker.cookpad.feature.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.paulbaker.cookpad.HomeScreenActivity
import com.paulbaker.cookpad.core.DATA_USER
import com.paulbaker.cookpad.core.extensions.Status
import com.paulbaker.cookpad.core.utils.Utils
import com.paulbaker.cookpad.databinding.FragmentProfileBinding
import com.paulbaker.cookpad.feature.login.viewmodel.UserViewModel

class ProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        getProfile()
    }

    private fun getProfile() {
        userViewModel.getProfile().observe(viewLifecycleOwner) { resourceResponse ->
            resourceResponse.let { resources ->
                when (resources.status) {
                    Status.LOADING -> {
                        binding.rootProfile.isClickable = false
                        binding.pbLoading.visibility = View.VISIBLE
                        binding.imgError.visibility = View.GONE
                    }
                    Status.SUCCESS -> {
                        if (resources.data?.body()?.success == true) {
                            binding.pbLoading.visibility = View.GONE
                            binding.rootProfile.isClickable = true
                        } else {
                            binding.pbLoading.visibility = View.GONE
                            binding.rootProfile.isClickable = true
                            binding.imgError.visibility = View.VISIBLE
                        }
                    }
                    Status.ERROR -> {
                        binding.imgError.visibility = View.VISIBLE
                        binding.rootProfile.isClickable = true
                        Log.d("TAG", "error message: ${resources.message}")
                    }
                }
            }
        }
    }

    private fun setupListener() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}