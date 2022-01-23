package com.paulbaker.cookpad

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.paulbaker.cookpad.data.datasource.local.User
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel(){
    val user = MutableLiveData<User>()

    fun sendDataUser(value: User){
        this.user.value=value
    }
}