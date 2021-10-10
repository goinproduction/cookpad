package com.paulbaker.cookpad.feature.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.paulbaker.cookpad.data.model.User
import com.paulbaker.cookpad.databinding.FragmentLoginBinding

class LoginFragment : Fragment(), View.OnClickListener {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by activityViewModels()

    //google
    private var gso: GoogleSignInOptions?=null
    private var googleSignInClient: GoogleSignInClient?=null
    private var auth: FirebaseAuth? = null
    private var user:User?=null

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
    }
    
    override fun onClick(v: View?) {
        when (v!!.id) {
           R.id.continueWithGoogleButton->{
               configureGoogle()
           }
        }
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
            ?.addOnCompleteListener { task->
                if(task.isSuccessful){
                    when(option){
                        0->{
                            user = User(
                                auth?.currentUser?.uid,
                                auth?.currentUser?.displayName,
                                auth?.currentUser?.email,
                                auth?.currentUser?.photoUrl.toString(),
                                isLogin = true
                            )
                            homeViewModel.sendDataUser(user!!)
                            HomeScreenActivity.saveUser?.edit()?.putString(DATA_USER, Gson().toJson(user))?.apply()
                            (activity as? HomeScreenActivity)?.supportFragmentManager?.beginTransaction()?.remove(this)?.commit()
                        }
                        1->{

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
}