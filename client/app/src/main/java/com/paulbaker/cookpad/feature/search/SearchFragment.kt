package com.paulbaker.cookpad.feature.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.core.extensions.Status
import com.paulbaker.cookpad.core.utils.Utils
import com.paulbaker.cookpad.data.datasource.remote.Data
import com.paulbaker.cookpad.data.datasource.local.Payload
import com.paulbaker.cookpad.databinding.FragmentSearchBinding
import com.paulbaker.cookpad.feature.home.viewmodel.ProductViewModel
import com.paulbaker.cookpad.feature.search.adapter.SearchAdapter
import java.util.*

class SearchFragment : Fragment(), View.OnClickListener, View.OnFocusChangeListener,
    View.OnTouchListener, SearchView.OnQueryTextListener,SearchAdapter.SetOnItemClick {

    companion object{
        const val REQ_CODE_SPEECH_INPUT = 100
    }

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private val productViewModel: ProductViewModel by activityViewModels()

    private var adapter: SearchAdapter? = null
    private val data: MutableList<Data> = mutableListOf()
    private var isShowNoResult = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentSearchBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupNoResultWhenPopBackStack()
        setupListener()
        setupAdapter()
    }

    private fun setupNoResultWhenPopBackStack() {
        if (isShowNoResult) {
            binding.ContainerNoResult.visibility = View.VISIBLE
        }
    }

    private fun setupAdapter() {
        adapter = SearchAdapter(requireContext(),data, mClickItem = this)
        binding.rcvSearch.adapter = adapter
        binding.rcvSearch.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListener() {
        binding.btnBack.setOnClickListener(this)
        binding.searchView.setOnQueryTextFocusChangeListener(this)
        binding.searchView.setOnQueryTextListener(this)
        binding.rootSearch.setOnTouchListener(this)
        binding.btnCreatePost.setOnClickListener(this)
        binding.btnMic.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnBack -> {
                Utils.hideKeyboard(requireContext(), binding.searchView)
                activity?.window?.decorView?.clearFocus()
                v.findNavController().popBackStack()
            }
            R.id.btnCreatePost -> {
                v.findNavController().navigate(R.id.actionCreation)
            }
            R.id.btnMic ->{
                promptSpeechInput()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        if (hasFocus)
            binding.btnMic.visibility = View.GONE
        else
            binding.btnMic.visibility = View.VISIBLE
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if(event?.action == MotionEvent.ACTION_DOWN) {
            Utils.hideKeyboard(requireContext(), binding.searchView)
            activity?.window?.decorView?.clearFocus()
        }
        return true
    }


    override fun onQueryTextSubmit(query: String): Boolean {
        getSearchFood(query)
        activity?.window?.decorView?.clearFocus()
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getSearchFood(query: String) {
        productViewModel.searchFood(Payload(query)).observe(viewLifecycleOwner) { resourceResponse ->
            resourceResponse.let { resources ->
                when (resources.status) {
                    Status.LOADING -> {
                        binding.pbLoading.visibility = View.VISIBLE
                        this.data.clear()
                        adapter?.notifyDataSetChanged()
                    }
                    Status.SUCCESS -> {
                        if (resources.data?.body()?.success == true) {
                            binding.pbLoading.visibility = View.GONE
                            binding.ContainerNoResult.visibility = View.GONE
                            resources.data.body()?.data?.let { data ->
                                this.data.addAll(data)
                                this.adapter?.notifyDataSetChanged()
                            }
                        } else {
                            isShowNoResult = true
                            binding.ContainerNoResult.visibility = View.VISIBLE
                            binding.pbLoading.visibility = View.GONE
                        }
                    }
                    Status.ERROR -> {
                        isShowNoResult = true
                        binding.ContainerNoResult.visibility = View.VISIBLE
                        binding.pbLoading.visibility = View.GONE
                    }
                }
            }
        }
    }

    override fun onItemClick(view: View?, item: Data, position: Int) {
        view?.findNavController()?.navigate(R.id.actionViewPost)
        productViewModel.setProductItem(item)
    }



    private fun promptSpeechInput() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(
            RecognizerIntent.EXTRA_PROMPT,
            getString(R.string.speech_prompt)
        )
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                getString(R.string.speech_not_supported),
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQ_CODE_SPEECH_INPUT -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    result?.get(0).apply {
                        binding.searchView.setQuery(this,true)
                    }
                }
            }
        }
    }
}