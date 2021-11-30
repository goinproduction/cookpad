package com.paulbaker.cookpad.feature.login

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.gson.Gson
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.core.extensions.Status
import com.paulbaker.cookpad.data.datasource.local.RegisterUser
import com.paulbaker.cookpad.data.datasource.local.User
import com.paulbaker.cookpad.data.datasource.remote.RegisterResponse
import com.paulbaker.cookpad.databinding.FragmentRegisterBinding
import com.paulbaker.cookpad.feature.login.viewmodel.UserViewModel
import com.paulbaker.library.core.widget.BottomSheetFullScreen

class BottomSheetRegister(val callback: RegisterCallBack) : BottomSheetFullScreen(),
    View.OnClickListener {

    private val binding get() = _binding!!
    private var _binding: FragmentRegisterBinding? = null

    private val userViewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        binding.btnRegister.setOnClickListener(this)
        binding.btnClose.setOnClickListener(this)
        setupTextWatch()
    }

    private fun setupTextWatch() {
        binding.edtInputName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.btnRegister.isEnabled =
                    binding.edtInputName.text.isNotEmpty() && binding.edtInputAccount.text.isNotEmpty()
                            && binding.edtInputPassword.text.isNotEmpty()
            }
        })
        binding.edtInputAccount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.btnRegister.isEnabled =
                    binding.edtInputName.text.isNotEmpty() && binding.edtInputAccount.text.isNotEmpty()
                            && binding.edtInputPassword.text.isNotEmpty()
            }
        })
        binding.edtInputPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(p0: Editable?) {
                binding.btnRegister.isEnabled =
                    binding.edtInputName.text.isNotEmpty() && binding.edtInputAccount.text.isNotEmpty()
                            && binding.edtInputPassword.text.isNotEmpty()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btnRegister -> {
                registerUser()
            }
            R.id.btnClose -> {
                dismiss()
                callback.onError("Người dùng đã hủy bỏ đăng ký")
            }
        }
    }

    private fun registerUser() {
        userViewModel.registerUser(
            RegisterUser(
                name = binding.edtInputName.text.toString(),
                username = binding.edtInputAccount.text.toString(),
                password = binding.edtInputPassword.text.toString(),
                role = binding.spinnerRole.selectedItemPosition
            )
        ).observe(viewLifecycleOwner) { resourceResponse ->
            resourceResponse.let { resources ->
                when (resources.status) {
                    Status.LOADING -> {
                        binding.rootRegister.isClickable = false
                        binding.pbLoading.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        if (resources.data?.body()?.success == true) {
                            binding.pbLoading.visibility = View.GONE
                            binding.rootRegister.isClickable = true
                            Toast.makeText(activity, "Tạo tài khoản thành công", Toast.LENGTH_LONG)
                                .show()
                            dismiss()
                            callback.onSuccess(
                                User(
                                    name = binding.edtInputName.text.toString(),
                                    username = binding.edtInputAccount.text.toString(),
                                    password = binding.edtInputPassword.text.toString(),
                                    role = binding.spinnerRole.selectedItemPosition
                                )
                            )
                        } else {
                            binding.pbLoading.visibility = View.GONE
                            binding.rootRegister.isClickable = true
                            Toast.makeText(
                                activity,
                                "${
                                    Gson().fromJson(
                                        resources.data?.errorBody()?.string(),
                                        RegisterResponse::class.java
                                    ).message
                                }",
                                Toast.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                    Status.ERROR -> {
                        binding.rootRegister.isClickable = true
                        Toast.makeText(
                            activity,
                            "${resources.data?.errorBody()?.string()}",
                            Toast.LENGTH_LONG
                        ).show()
                        Log.d("TAG", "error message: ${resources.message}")
                    }
                }
            }
        }
    }

    interface RegisterCallBack {
        fun onSuccess(user: User)
        fun onError(message: String?)
    }
}