package com.paulbaker.cookpad

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.paulbaker.cookpad.core.CircleTransform
import com.paulbaker.cookpad.core.DATA_USER
import com.paulbaker.cookpad.core.utils.Utils
import com.paulbaker.cookpad.data.datasource.local.User
import com.paulbaker.cookpad.databinding.ActivityMainBinding
import com.paulbaker.cookpad.feature.login.LoginFragment
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

@AndroidEntryPoint
class HomeScreenActivity : AppCompatActivity() {

    companion object {
        var saveUser: SharedPreferences? = null
    }

    private val homeViewModel: HomeViewModel by viewModels()

    private lateinit var binding: ActivityMainBinding
    private var user: User? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_CookPad)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupBottomNavigation()
        loadUser()
        observerDataUser()
        checkLogin()
    }

    private fun observerDataUser() {
        homeViewModel.user.observe(this) {
            user = it
            saveUser?.edit()?.putString(DATA_USER, Gson().toJson(user))?.apply()
            loadPhotoUser(user?.url)
        }
    }

    private fun loadUser() {
        saveUser = getSharedPreferences(DATA_USER, Context.MODE_PRIVATE)
        loadPhotoUser(
            Gson().fromJson(
                saveUser?.getString(DATA_USER, Gson().toJson(User())),
                User::class.java
            ).url
        )
    }

    private fun loadPhotoUser(url: String?) {
        if (url == null || url.isEmpty() || url.isBlank())
            return
        Picasso.get().load(url).placeholder(R.drawable.ic_user_small)
            .error(R.drawable.ic_user_small).resize(Utils.dpToPx(24), Utils.dpToPx(24))
            .into(object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    binding.bottomBar.menu.getItem(4).icon =
                        BitmapDrawable(resources, CircleTransform.getCroppedBitmap(bitmap!!))

                }

                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {

                }

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

                }
            })
    }


    private fun setupBottomNavigation() {
        val navView: BottomNavigationView = binding.bottomBar
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
    }


    private fun checkLogin() {
        val isLogin = !Gson().fromJson(
            saveUser?.getString(DATA_USER, Gson().toJson(User())), User::class.java
        ).isLogin
        if (FirebaseAuth.getInstance().currentUser == null && isLogin) {
            showFragmentLogin()
        }
    }

    private fun showFragmentLogin() {
        supportFragmentManager.beginTransaction()
            .add(R.id.containerLogin, LoginFragment())
            .commit()
        showBottomBar(View.GONE)
    }

    fun showBottomBar(isShow: Int) {
        binding.bottomBar.visibility = isShow
    }
}