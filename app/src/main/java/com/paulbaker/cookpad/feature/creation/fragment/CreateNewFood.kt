package com.paulbaker.cookpad.feature.creation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.paulbaker.cookpad.databinding.CreateDetailBinding


class CreateNewFood : Fragment(), View.OnClickListener {
    private var _binding: CreateDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = CreateDetailBinding.inflate(layoutInflater, container, false)
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