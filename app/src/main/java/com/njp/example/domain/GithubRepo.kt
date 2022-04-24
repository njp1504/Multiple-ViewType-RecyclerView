package com.njp.example.domain

import com.njp.example.data.GithubRepoData

/**
 * Domain level Model
 */
data class GithubRepo(
    val id : Int,
    val name : String,
    val visibility: String,
    val description: String,
    val url : String,
    val createdAt : String,
    val updatedAt : String,
    val pushedAt : String,
    val stargazersCount : Int,
    val watchersCount : Int,
    val forksCount : Int,
    val openIssuesCount : Int
) {
    companion object {
        fun from(data : GithubRepoData) =
            GithubRepo(
                id = data.id,
                name = data.name,
                visibility = data.visibility,
                description = data.description,
                url = data.url,
                createdAt = data.createdAt,
                updatedAt = data.updatedAt,
                pushedAt = data.pushedAt,
                stargazersCount = data.stargazersCount,
                watchersCount = data.watchersCount,
                forksCount = data.forksCount,
                openIssuesCount = data.openIssuesCount
            )
    }
}