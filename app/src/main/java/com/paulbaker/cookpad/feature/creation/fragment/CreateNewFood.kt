package com.paulbaker.cookpad.feature.creation.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.net.toFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
//import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.gson.Gson
import com.paulbaker.cookpad.HomeScreenActivity
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.core.DATA_USER
import com.paulbaker.cookpad.core.extensions.Status
import com.paulbaker.cookpad.core.utils.Utils
import com.paulbaker.cookpad.data.datasource.local.CreateRecipesModel
import com.paulbaker.cookpad.data.datasource.local.User
import com.paulbaker.cookpad.databinding.CreateDetailBinding
import com.paulbaker.cookpad.feature.creation.adapter.AddMaterialAdapter
import com.paulbaker.cookpad.feature.home.viewmodel.ProductViewModel
import com.paulbaker.library.core.extension.Utils.Companion.toBase64
import com.squareup.picasso.Picasso
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.RecyclerView
import com.paulbaker.cookpad.core.platform.SimpleItemTouchHelperCallback
import com.paulbaker.cookpad.data.datasource.local.StepModel
import com.paulbaker.cookpad.feature.creation.adapter.AddStepAdapter
import java.util.*
import androidx.core.app.ActivityCompat.startActivityForResult





class CreateNewFood : Fragment(), View.OnClickListener, View.OnTouchListener {

    companion object {
        const val TYPE_ADD_MATERIAL = 0
        const val TYPE_ADD_STEP = 1
        const val REQUEST_CODE_IMAGE_REPRESENT = 2021
        const val REQUEST_CODE_IMAGE_STEP = 2022
    }

    private var _binding: CreateDetailBinding? = null
    private val binding get() = _binding!!

    private var uriRepresent: Uri? = null
    private var uriStep: Uri? = null
    private var positionStep: Int? = null
    private var startClickTime = 0L

    private val productViewModel: ProductViewModel by activityViewModels()

    private var addMaterialAdapter: AddMaterialAdapter? = null
    private var addStepAdapter: AddStepAdapter? = null

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
        (activity as? HomeScreenActivity)?.showBottomBar(View.GONE)
        activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setupAddMaterialAdapter()
        setupAddStepAdapter()
    }


    private fun setupAddMaterialAdapter() {
        addMaterialAdapter = AddMaterialAdapter(
            requireContext(),
            mClickItem = object : AddMaterialAdapter.SetOnItemClick {
                override fun onItemClick(view: View?, item: String?, position: Int) {
                    Utils.hideKeyboard(requireContext(), binding.createDetailThemTenMonAn)
                    activity?.window?.decorView?.clearFocus()
                }
            })
        binding.rcvAddMaterial.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvAddMaterial.adapter = addMaterialAdapter
        addItemTouchCallback(binding.rcvAddMaterial, TYPE_ADD_MATERIAL)
    }

    private fun setupAddStepAdapter() {
        addStepAdapter = AddStepAdapter(requireContext(),
            mClickItem = object : AddStepAdapter.SetOnItemClick {
                override fun onItemClick(
                    view: View?,
                    item: CreateRecipesModel.Step?,
                    position: Int
                ) {
                    Utils.hideKeyboard(requireContext(), binding.createDetailThemTenMonAn)
                    activity?.window?.decorView?.clearFocus()
                    loadAndUpdateImage(REQUEST_CODE_IMAGE_STEP)
                    positionStep = position
                }

                override fun forceHideKeyBroad() {
                    Utils.hideKeyboard(requireContext(), binding.createDetailThemTenMonAn)
                    activity?.window?.decorView?.clearFocus()
                }
            })
        binding.rcvCachLam.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rcvCachLam.adapter = addStepAdapter
        addItemTouchCallback(binding.rcvCachLam, TYPE_ADD_STEP)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun setupListener() {
        binding.createDetailButtonBack.setOnClickListener(this)
        binding.createDetailThemAnhMonAn.setOnClickListener(this)
        binding.contentWrapper.setOnTouchListener(this)
        binding.btnPost.setOnClickListener(this)
        binding.btnAddMaterial.setOnClickListener(this)
        binding.btnAddStep.setOnClickListener(this)
    }

    private fun addItemTouchCallback(recyclerView: RecyclerView, type: Int) {
        val callback: ItemTouchHelper.Callback =
            SimpleItemTouchHelperCallback(object :
                SimpleItemTouchHelperCallback.ItemTouchListenner {
                @SuppressLint("NotifyDataSetChanged")
                override fun onMove(oldPosition: Int, newPosition: Int) {
                    when (type) {
                        TYPE_ADD_MATERIAL -> addMaterialAdapter?.onMove(oldPosition, newPosition)
                        else -> {
                            addStepAdapter?.onMove(oldPosition, newPosition)
                        }
                    }
                }

                override fun swipe(position: Int, direction: Int) {
                    when (type) {
                        TYPE_ADD_MATERIAL -> addMaterialAdapter?.swipe(position, direction)
                        else -> addStepAdapter?.swipe(position, direction)
                    }
                }
            })
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }


    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.create_detail_button_back -> {
                v.findNavController().popBackStack()
            }
            R.id.create_detail_ThemAnhMonAn -> {
                loadAndUpdateImage(REQUEST_CODE_IMAGE_REPRESENT)
            }
            R.id.btnPost -> {
                if (isValidData())
                    createNewPost(v)
                else
                    Toast.makeText(requireContext(), "Một số trường ko được để trống!", Toast.LENGTH_SHORT).show()
            }
            R.id.btnAddMaterial -> {
                createNewMaterial()
            }
            R.id.btnAddStep -> {
                createNewStep()
            }
        }
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun createNewStep() {
        addStepAdapter?.data?.add(
            CreateRecipesModel.Step(
                name = "",
                picture = null
            )
        )
        addStepAdapter?.notifyItemInserted(addStepAdapter?.data!!.size - 1)
        addStepAdapter?.notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun createNewMaterial() {
        addMaterialAdapter?.data?.add("")
        addMaterialAdapter?.notifyItemInserted(addMaterialAdapter?.data!!.size - 1)
        addMaterialAdapter?.notifyDataSetChanged()
    }

    private fun createNewPost(view: View) {
        val userId = Gson().fromJson(
            HomeScreenActivity.saveUser?.getString(DATA_USER, Gson().toJson(User())),
            User::class.java
        ).id ?: return

        productViewModel.createRecipe(
            userId, CreateRecipesModel(
                title = binding.createDetailThemTenMonAn.text.toString(),
                description = binding.createDetailThemMoTaMonAn.text.toString(),
                origin = Locale.getDefault().country,
                serves = binding.edtCount.text.toString().toInt(),
                cookTime = binding.edtTime.text.toString().toInt(),
                category = "Món mới nhất",
                ingredients = addMaterialAdapter?.data?.filter { it.isNotEmpty() },
                steps = addStepAdapter?.data?.filter { it.name?.isNotEmpty() == true },
                image = uriRepresent?.toFile()?.toBase64()
            )
        ).observe(viewLifecycleOwner) { resourceResponse ->
            resourceResponse.let { resources ->
                when (resources.status) {
                    Status.LOADING -> {
                        binding.pbLoading.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        if (resources.data?.body()?.success == true) {
                            binding.pbLoading.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Đăng bài thành công",
                                Toast.LENGTH_SHORT
                            ).show()
                            Navigation.findNavController(view).popBackStack()
                        } else {
                            binding.pbLoading.visibility = View.GONE
                            Toast.makeText(
                                requireContext(),
                                "Something went wrong please try again",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                    Status.ERROR -> {
                        binding.pbLoading.visibility = View.GONE
                        Toast.makeText(
                            requireContext(),
                            "Something went wrong please try again",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("TAG", "error message: ${resources.message}")
                    }
                }
            }
        }
    }

    private fun loadAndUpdateImage(requestCode: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), requestCode)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {
                when (requestCode) {
                    REQUEST_CODE_IMAGE_REPRESENT -> {
                        uriRepresent = data?.data!!
                        Picasso.get().load(uriRepresent).into(binding.createDetailThemAnhMonAn)
                    }
                    REQUEST_CODE_IMAGE_STEP -> {
                        uriStep = data?.data!!
                        positionStep?.let {
                            addStepAdapter?.data!![it].picture = uriStep?.toFile()?.toBase64()
                            addStepAdapter?.notifyDataSetChanged()
                        }
                    }
                }
            }
            else -> {
                Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
            }
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            startClickTime = System.currentTimeMillis()
        } else if (event?.action == MotionEvent.ACTION_UP) {
            if (System.currentTimeMillis() - startClickTime < ViewConfiguration.getTapTimeout()) {
                Utils.hideKeyboard(requireContext(), binding.createDetailThemTenMonAn)
                activity?.window?.decorView?.clearFocus()
            }
        }
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        (activity as? HomeScreenActivity)?.showBottomBar(View.VISIBLE)
    }

    private fun isValidData(): Boolean {
        return binding.createDetailThemTenMonAn.text.isNotEmpty() &&
                binding.createDetailThemMoTaMonAn.text.isNotEmpty() &&
                binding.edtCount.text.isNotEmpty() &&
                binding.edtTime.text.isNotEmpty() &&
                addMaterialAdapter?.data!!.any { it.isNotEmpty() } &&
                addStepAdapter?.data!!.any { it.name?.isNotEmpty() == true }
    }
}