package com.njp.example.data

import android.util.Log
import com.njp.example.data.remote.Result
import com.njp.example.data.remote.github.GithubService
import com.njp.example.data.remote.safeApiCall
import com.njp.example.domain.GithubRepo
import com.njp.example.domain.GithubIssue
import java.util.*

class GithubRepository(private val service: GithubService) {
    private val TAG = GithubRepository::class.java.simpleName

    suspend fun getGithubIssues(userName : String, repositoryName : String) : List<GithubIssue> {
        Log.i(TAG, "getGithubIssues() userName=$userName, repositoryName=$repositoryName")

        return when(val result = safeApiCall { service.getIssues(userName, repositoryName) }) {
            is Result.Success -> {
                Log.d(TAG, "getGithubIssues() Success")
                result.data.map {
                    GithubIssue.from(it) // data level -> domain level
                }
            }
            is Result.Error -> {
                Log.d(TAG, "getGithubIssues() Error")
                Collections.emptyList<GithubIssue>()
            }
        }
    }

    suspend fun getGithubRepos(userName: String) : List<GithubRepo> {
        Log.i(TAG, "getGithubRepos() userName=$userName")

        return when(val result = safeApiCall { service.getRepos(userName) }) {
            is Result.Success -> {
                result.data.map {
                    GithubRepo.from(it) // data level -> domain level
                }
            }
            is Result.Error -> { Collections.emptyList<GithubRepo>() }
        }
    }
}