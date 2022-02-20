package com.njp.example.data

import android.util.Log
import com.njp.example.network.Result
import com.njp.example.network.github.GithubApi
import com.njp.example.network.safeApiCall
import java.util.*

class GithubRepository(private val api: GithubApi) {
    private val TAG = GithubRepository::class.java.simpleName

    suspend fun getGithubIssues(userName : String, repositoryName : String) : List<GithubIssue> {
        Log.i(TAG, "getGithubIssues()")

        return when(val result = safeApiCall { api.getIssues(userName, repositoryName) }) {
            is Result.Success -> {
                Log.d(TAG, "getGithubIssues() Success")
                result.data
            }
            is Result.Error -> {
                Log.d(TAG, "getGithubIssues() Error")
                Collections.emptyList<GithubIssue>()
            }
        }
    }
}