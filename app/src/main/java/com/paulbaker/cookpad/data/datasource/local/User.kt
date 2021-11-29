package com.paulbaker.cookpad.data.datasource.local


data class User(
    var id: String? = null,
    var name: String? = null,
    var username: String? = null,
    var password: String? = null,
    var email: String? = null,
    var url: String? = null,
    var address: String? = null,
    var about: String? = null,
    var cookPadId: String? = null,
    var role: Int? = 0,
    var isLogin: Boolean = false
)