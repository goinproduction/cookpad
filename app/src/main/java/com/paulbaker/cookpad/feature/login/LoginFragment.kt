package com.paulbaker.cookpad.feature.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.paulbaker.cookpad.HomeScreenActivity
import com.paulbaker.cookpad.HomeViewModel
import com.paulbaker.cookpad.R
import com.paulbaker.cookpad.core.DATA_USER
import com.paulbaker.cookpad.core.SIGN_IN
import com.paulbaker.cookpad.core.extensions.Status
import com.paulbaker.cookpad.core.utils.Utils
import com.paulbaker.cookpad.data.datasource.local.RegisterUser
import com.paulbaker.cookpad.data.datasource.local.User
import com.paulbaker.cookpad.databinding.FragmentLoginBinding
import com.paulbaker.cookpad.feature.login.viewmodel.UserViewModel

class LoginFragment : Fragment(), View.OnClickListener, BottomSheetRegister.RegisterCallBack {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val userViewModel: UserViewModel by activityViewModels()

    //google
    private var gso: GoogleSignInOptions? = null
    private var googleSignInClient: GoogleSignInClient? = null
    private var auth: FirebaseAuth? = null
    private var user: User? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupListener()
    }

    private fun setupListener() {
        binding.continueWithGoogleButton.setOnClickListener(this)
        binding.btnRegister.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.edtPassword.setOnEditorActionListener { v: TextView?, actionId: Int, event: KeyEvent? ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.btnLogin.performClick()
            }
            true
        }
    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.btnRegister -> {
                val bottomSheetRegister = BottomSheetRegister(this)
                bottomSheetRegister.show(childFragmentManager, BottomSheetRegister::class.java.name)
            }
            R.id.btnLogin -> {
                loginUser()
            }
            R.id.continueWithGoogleButton -> {
                configureGoogle()
            }
        }
    }

    private fun loginUser() {
        if (binding.edtAccount.text.isNotEmpty() && binding.edtPassword.text.isNotEmpty())
            userViewModel.loginUser(
                RegisterUser(
                    username = binding.edtAccount.text.toString(),
                    password = binding.edtPassword.text.toString()
                )
            ).observe(viewLifecycleOwner) { resourceResponse ->
                resourceResponse.let { resources ->
                    when (resources.status) {
                        Status.LOADING -> {
                            binding.rootLogin.isClickable = false
                            binding.pbLoading.visibility = View.VISIBLE
                        }
                        Status.SUCCESS -> {
                            if (resources.data?.body()?.success == true) {
                                binding.pbLoading.visibility = View.GONE
                                binding.rootLogin.isClickable = true
                                (activity as? HomeScreenActivity)?.supportFragmentManager?.beginTransaction()
                                    ?.remove(this)?.commit()
                                Utils.hideKeyboard(requireActivity(), binding.edtPassword)
                                user?.isLogin = true
                                homeViewModel.sendDataUser(user!!)
                                HomeScreenActivity.saveUser?.edit()
                                    ?.putString(DATA_USER, Gson().toJson(user))?.apply()
                            } else {
                                binding.pbLoading.visibility = View.GONE
                                binding.rootLogin.isClickable = true
                                Toast.makeText(
                                    activity,
                                    "Tài khoản hoặc mật khẩu không đúng!",
                                    Toast.LENGTH_LONG
                                )
                                    .show()
                            }
                        }
                        Status.ERROR -> {
                            binding.rootLogin.isClickable = true
                            Toast.makeText(
                                activity,
                                "Có lỗi xảy ra vui lòng thử lại sau!",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            Log.d("TAG", "error message: ${resources.message}")
                        }
                    }
                }
            }
        else
            Toast.makeText(
                activity,
                "Tài khoản hoặc mật khẩu không được để trống!",
                Toast.LENGTH_LONG
            )
                .show()
    }

    private fun configureGoogle() {
        auth = Firebase.auth
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        signIn()
    }

    private fun signIn() {
        val signInIntent = googleSignInClient?.signInIntent
        startActivityForResult(signInIntent, SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                Toast.makeText(
                    requireContext(),
                    "Login failed. Please try again later!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        signInWithCredential(credential, 0)
    }

    private fun signInWithCredential(credential: AuthCredential, option: Int) {
        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    when (option) {
                        0 -> {
                            user = User(
                                id = auth?.currentUser?.uid,
                                name = auth?.currentUser?.displayName,
                                email = auth?.currentUser?.email,
                                url = auth?.currentUser?.photoUrl.toString(),
                                isLogin = true
                            )
                            homeViewModel.sendDataUser(user!!)
                            HomeScreenActivity.saveUser?.edit()
                                ?.putString(DATA_USER, Gson().toJson(user))?.apply()
                            (activity as? HomeScreenActivity)?.supportFragmentManager?.beginTransaction()
                                ?.remove(this)?.commit()
                        }
                        1 -> {

                        }
                    }
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as? HomeScreenActivity)?.showBottomBar(View.VISIBLE)
        _binding = null
    }

    override fun onSuccess(user: User) {
        binding.edtAccount.setText(user.username)
        binding.edtPassword.setText(user.password)
        binding.btnLogin.performClick()
        this.user = User(
            name = user.name,
            email = user.username,
            url = user.password,
            role = user.role,
        )
    }

    override fun onError(message: String?) {
        Toast.makeText(activity, message, Toast.LENGTH_LONG).show()
    }
}