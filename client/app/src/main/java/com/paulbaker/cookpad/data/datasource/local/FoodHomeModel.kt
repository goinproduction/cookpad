package com.paulbaker.cookpad.data.datasource.local

import com.paulbaker.cookpad.data.datasource.remote.Data

data class FoodHomeModel(
    val type: Int? = null,
    val category: String? = null,
    val item: List<Data>? = null,
)
