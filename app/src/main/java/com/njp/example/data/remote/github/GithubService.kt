package com.njp.example.data.remote.github

import com.njp.example.data.GithubIssueData
import com.njp.example.data.GithubRepoData
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("repos/{owner}/{repo}/issues")
    suspend fun getIssues(
        @Path("owner") owner : String,
        @Path("repo") repo : String
    ) : Response<List<GithubIssueData>>

    @GET("/users/{owner}/repos")
    suspend fun getRepos(
        @Path("owner") org : String
    ) : Response<List<GithubRepoData>>


    companion object {
        private val BASE_URL = "https://api.github.com"

        private val httpClient = OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .build()

        private val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        private val _api = retrofit.create(GithubService::class.java)

        val api
            get() = _api
    }
}