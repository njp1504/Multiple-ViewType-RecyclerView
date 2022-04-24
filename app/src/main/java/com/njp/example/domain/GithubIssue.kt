package com.njp.example.domain

import com.njp.example.data.GithubIssueData

/**
 * Domain layer Model
 */
data class GithubIssue(
    val id : Int,
    val number : String,
    val title : String
) {
    companion object {
        fun from(data : GithubIssueData) =
            GithubIssue(
                id = data.id,
                number = data.number,
                title = data.title
            )
    }
}