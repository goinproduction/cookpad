package com.paulbaker.library.core.extension

fun String?.isValidValue(): Boolean {
    return this?.isNotEmpty() == true && this.isNotBlank()
}

fun String?.isNotNull(): Boolean {
    return this != null
}