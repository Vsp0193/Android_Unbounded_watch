package com.unbounded.realizingself.data.remote.retrofitapi

sealed class Response<out T>{

    data class Success<T>(val data: T) : Response<T>()
    data class Error(val statusCode: Int, val errorMessage: String) : Response<Nothing>()
    object  Loading : Response<Nothing>()

}
