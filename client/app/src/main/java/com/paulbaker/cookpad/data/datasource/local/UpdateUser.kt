package com.paulbaker.cookpad.data.datasource.local

data class UpdateUser(
    var name: String? = null,
    var email: String? = null,
    var about: String? = null,
    var cookpadId: String? = null,
    var address: String? = null,
    var avatar :String? = null
)