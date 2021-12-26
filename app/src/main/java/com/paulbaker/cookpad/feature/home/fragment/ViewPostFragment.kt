package com.paulbaker.cookpad.feature.home.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.core.utils.Utils
import com.paulbaker.cookpad.databinding.FragmentViewPostBinding
import com.paulbaker.cookpad.feature.home.adapter.MaterialViewPostAdapter
import com.paulbaker.cookpad.feature.home.viewmodel.ProductViewModel
import androidx.recyclerview.widget.DividerItemDecoration
import com.paulbaker.cookpad.feature.home.adapter.StepViewPostAdapter


class ViewPostFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentViewPostBinding? = null
    private val binding get() = _binding!!

    private val productViewModel: ProductViewModel by activityViewModels()

    private var viewMaterialAdapter: MaterialViewPostAdapter? = null
    private var viewStepAdapter: StepViewPostAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentViewPostBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
        setupObserverProductItem()
        setupViewMaterialAdapter()
        setupViewStepAdapter()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setupObserverProductItem() {
        productViewModel.productItem.observe(viewLifecycleOwner) {
            binding.imgFood.layoutParams.height = Utils.getDeviceWidth(requireContext())
            binding.imgFood.setImageBitmap(
                com.paulbaker.library.core.extension.Utils.decodeBase64ToBitMap(
                    it.image
                )
            )
            binding.tvNameFood.text = it.title
            binding.tvAuthor.text = it.author?.name
            binding.postAnhNguoiDang.setImageBitmap(
                com.paulbaker.library.core.extension.Utils.decodeBase64ToBitMap(
                    it.author?.avatar
                )
            )
            binding.tvLocale.text = it.origin
            binding.tvDescription.text = it.description
            it.ingredients?.let { materials -> viewMaterialAdapter?.data?.addAll(materials) }
            viewMaterialAdapter?.notifyDataSetChanged()
            it.steps?.let { steps -> viewStepAdapter?.data?.addAll(steps) }
            viewStepAdapter?.notifyDataSetChanged()
        }
    }


    private fun setupViewMaterialAdapter() {
        viewMaterialAdapter = MaterialViewPostAdapter(requireContext())
        binding.rcvNguyenLieu.adapter = viewMaterialAdapter
        binding.rcvNguyenLieu.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvNguyenLieu.addItemDecoration(
            DividerItemDecoration(
                binding.rcvNguyenLieu.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setupViewStepAdapter() {
        viewStepAdapter = StepViewPostAdapter(requireContext())
        binding.rcvStep.adapter = viewStepAdapter
        binding.rcvStep.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvStep.addItemDecoration(
            DividerItemDecoration(
                binding.rcvStep.context,
                DividerItemDecoration.VERTICAL
            )
        )
    }

    private fun setupListener() {
        binding.btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnBack -> v.findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}