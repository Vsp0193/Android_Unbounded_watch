package com.unbounded.realizingself.data.remote.retrofitapi


enum class ApiEnum {
    SUCCESS,
    ERROR,
    LOADING
}
data class ResultState<out T>(val status: ApiEnum, val data: T?, val message: String?) {

    companion object {

        fun <T> success(data: T?): ResultState<T> {
            return ResultState(ApiEnum.SUCCESS, data, null)
        }

        fun <T> error(msg: String): ResultState<T> {
            return ResultState(ApiEnum.ERROR, null, msg)
        }

        fun <T> loading(): ResultState<T> {
            return ResultState(ApiEnum.LOADING, null, null)
        }
    }
}