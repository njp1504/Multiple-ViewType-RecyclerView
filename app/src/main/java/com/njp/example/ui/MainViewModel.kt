package com.njp.example.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.njp.example.data.GithubRepository
import com.njp.example.ui.adapter.IssueItem
import com.njp.example.ui.repo.adapter.RepoImageItem
import com.njp.example.ui.repo.adapter.RepoInfoItem
import com.njp.example.ui.repo.adapter.RepoItem
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository : GithubRepository
) : ViewModel() {
    companion object {
        private val TAG = MainViewModel::class.java.simpleName
        const val KEY_OWNER_AND_REPO = "key_owner_and_repo"
        const val KEY_OWNER = "key_owner"
    }

    private val _ownerAndRepo = savedStateHandle.getLiveData<Pair<String, String>>(KEY_OWNER_AND_REPO)
    val ownerAndRepo : LiveData<Pair<String, String>> = _ownerAndRepo

    private val _owner = savedStateHandle.getLiveData<String>(KEY_OWNER)
    val owner : LiveData<String> = _owner

    val isIssueUpdate = Transformations.map(_ownerAndRepo) { params ->
        Log.d(TAG, "Transformations trigger : $params")

        if(params.first.isNotEmpty() && params.second.isNotEmpty()) {
            updateIssues(params.first, params.second)
        }
    }

    val isOwnerUpdate = Transformations.map(_owner) {
        Log.d(TAG, "Transformations trigger : owner=$it")

        updateRepos(it)
    }

    private val _issues = MutableLiveData<List<IssueItem>>()
    val issues : LiveData<List<IssueItem>> = _issues

    private val _repos = MutableLiveData<List<RepoItem>>()
    val repos : LiveData<List<RepoItem>> = _repos

    init {
        Log.i(TAG, "init() ${savedStateHandle.keys().size}")

        savedStateHandle.keys().forEach { key ->
            Log.d(TAG, "key=$key, value=${savedStateHandle.get<Any>(key)}")
        }
    }

    fun setOwnerAndRepo(info: Pair<String, String>) {
        Log.d(TAG, "setOwnerAndRepo() $info")

        _ownerAndRepo.value = info

        savedStateHandle.set(KEY_OWNER_AND_REPO, info)
    }

    fun setOwner(user : String) {
        Log.d(TAG, "setOwner() $user")

        _owner.value = user

        savedStateHandle.set(KEY_OWNER, user)
    }

    private fun updateIssues(userName : String, repositoryName : String) {
        viewModelScope.launch {
            Log.i(TAG, "updateIssues() viewModelScope start")

            val result = repository.getGithubIssues(userName, repositoryName)

            Log.d(TAG, "viewModelScope result.size=${result.size}")

            _issues.value = result
                .map { IssueItem.IssueInfoItem.from(it) as IssueItem }
                .toMutableList()
//                .also {
//                    Log.d(TAG, "map to size : ${it.size}")
//                }
                .apply {
                    val image = IssueItem.IssueImageItem.from(Uri.parse("https://static.toss.im/homepage-static/career-share.jpg"))

                    if(isEmpty())
                        listOf(image)
                    else
                        add(2, image)
                }
                .toImmutableList()
//                .also {
//                    Log.d(TAG, "final size : ${it.size}")
//                }

        }
    }

    private fun updateRepos(userName : String) =
        viewModelScope.launch {
            Log.i(TAG, "updateRepos() viewModelScope start")

            val result = repository.getGithubRepos(userName)

            Log.d(TAG, "updateRepos() result.size=${result.size}")

            _repos.value = result
                .map { RepoInfoItem.from(it) as RepoItem }
                .toMutableList()
//                .also {
//                    Log.d(TAG, "map to size : ${it.size}")
//                }
                .apply {
                    val image = RepoImageItem.from(Uri.parse("https://static.toss.im/homepage-static/career-share.jpg"))

                    if(isEmpty())
                        listOf(image)
                    else
                        add(2, image)
                }
                .toImmutableList()
//                .also {
//                    Log.d(TAG, "final size : ${it.size}")
//                }
        }
}