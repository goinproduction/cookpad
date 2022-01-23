package com.paulbaker.cookpad.data.datasource.local


data class CreateRecipesModel(
    var category: String? = null,
    var cookTime: Int? = null,
    var description: String? = null,
    var image: String? = null,
    var ingredients: List<String?>? = null,
    var origin: String? = null,
    var serves: Int? = null,
    var steps: List<Step?>? = null,
    var title: String? = null
) {
    data class Step(
        var name: String? = null,
        var picture: String? = null
    )
}