package com.njp.example.data

import com.google.gson.annotations.SerializedName

/**
 * Data layer Model
 */

/**
 * Gson을 사용할 때 @SerializedName 어노테이션을 붙여야 한다.
 * 이 어노테이션의 역할은 Gson이 JSON 키를 Java 필드에 매팽하는 것을 도와주는데,
 * 이를 사용하지 않으면 클래스 멤버 변수와 JSON 키의 이름이 동일해야 매핑이 된다.
 *
 * 하지만 @SerializedName 를 사용하면 JSON 키와 다른 이름의 변수명을 사용할 수 있다.
 *
 * 이름 변경이 없는 경우에도 @SerializedName 어노테이션을 붙이는 것이 좋다.
 * 애플리케이션을 Release 할 때 소스 코드가 난독화 되는 과정에서
 * Java 필드가 변환되고, 이로 인해 Gson 매핑에 오작동이 일어날 수 있기 때문에
 * @SerializedName 은 필수로 사용하는 것이 좋다.
 */
data class GithubIssueData(
    @SerializedName("id") val id : Int,
    @SerializedName("number") val number : String,
    @SerializedName("title") val title : String
)

data class GithubRepoData(
    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("visibility") val visibility: String,
    @SerializedName("description") val description: String,
    @SerializedName("url") val url : String,
    @SerializedName("created_at") val createdAt : String,
    @SerializedName("updated_at") val updatedAt : String,
    @SerializedName("pushed_at") val pushedAt : String,
    @SerializedName("stargazers_count") val stargazersCount : Int,
    @SerializedName("watchers_count") val watchersCount : Int,
    @SerializedName("forks_count") val forksCount : Int,
    @SerializedName("open_issues_count") val openIssuesCount : Int
)