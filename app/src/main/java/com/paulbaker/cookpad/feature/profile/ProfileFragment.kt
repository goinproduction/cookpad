package com.paulbaker.cookpad.feature.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paulbaker.cookpad.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

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