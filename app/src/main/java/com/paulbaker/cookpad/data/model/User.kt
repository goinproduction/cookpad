package com.paulbaker.cookpad.data.model


data class User(
    var id: String?=null,
    var name: String ?=null,
    var email: String?=null,
    var url: String ?=null,
    var isLogin:Boolean=false
)