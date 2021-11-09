package com.paulbaker.cookpad.data.datasource.local

import com.paulbaker.cookpad.data.datasource.remote.FoodResponse

data class FoodHomeModel(
    val category: String? = null,
    val listItem: MutableList<FoodResponse>? = null,
)