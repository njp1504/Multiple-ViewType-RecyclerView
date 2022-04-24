package com.njp.example.data.remote

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response


sealed class Result<out T: Any> {
    data class Success<out T: Any>(val data: T) : Result<T>()
    data class Error(val exception: String) : Result<Nothing>()
}

suspend fun <T : Any> safeApiCall(call: suspend() -> Response<T>) : Result<T> = withContext(Dispatchers.IO) {
    return@withContext try {
        Log.d("safeApiCall", "invoke()")

        val response = call.invoke()

        Log.d("safeApiCall", "response=$response")

        if(response.isSuccessful) {
            Result.Success(response.body()!!)
        } else {
            Result.Error(response.message() ?: "Something goes wrong")
        }
    } catch (e: Exception) {
        Result.Error(e.message ?: "Error accuser!")
    }
}