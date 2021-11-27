package com.paulbaker.cookpad.data.datasource.local

data class RegisterUser(
    var name:String?=null,
    var username:String?=null,
    var password:String?=null,
    var role:Int = 1
)