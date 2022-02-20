package com.njp.example.network.github

import com.njp.example.data.GithubIssue
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApi {

    @GET("repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Path("owner") owner : String,
        @Path("repo") repo : String
    ) : Response<List<GithubIssue>>


}