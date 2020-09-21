package com.egabruskiy.distanceus.models


data class Resource<T>(
    val status: Status,
    val data: T?
)

sealed class Status{
    object Success : Status()
    object Loading : Status()
    data class ShowError(val message: String) : Status()
}