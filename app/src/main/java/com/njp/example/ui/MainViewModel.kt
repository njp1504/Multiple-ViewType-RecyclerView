package com.njp.example.ui

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.njp.example.data.GithubRepository
import com.njp.example.ui.adapter.GithubItem
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val repository : GithubRepository
) : ViewModel() {
    companion object {
        private val TAG = MainViewModel::class.java.simpleName

        val KEY_OWNER_AND_REPO = "key_owner_and_repo"
    }

    private val _ownerAndRepo = savedStateHandle.getLiveData<Pair<String, String>>(KEY_OWNER_AND_REPO)
    val ownerAndRepo : LiveData<Pair<String, String>> = _ownerAndRepo

    private val image = GithubItem.GithubImageItem(id = System.currentTimeMillis(), Uri.parse("https://static.toss.im/homepage-static/career-share.jpg"))

    val update = Transformations.map(_ownerAndRepo) { params ->
        Log.d(TAG, "Transformations trigger $params")

        if(params.first.isNotEmpty() && params.second.isNotEmpty()) {
            updateIssues(params.first, params.second)
        }
    }

    private val _items = MutableLiveData<List<GithubItem>>()
    val items : LiveData<List<GithubItem>> = _items

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

    private fun updateIssues(userName : String, repositoryName : String) {
        viewModelScope.launch {
            Log.i(TAG, "updateIssues() viewModelScope start")

            val result = repository.getGithubIssues(userName, repositoryName)

            Log.d(TAG, "viewModelScope result.size=${result.size}")

            val issues : List<GithubItem> = result
                .map { GithubItem.GithubIssueItem(it.id.toLong(), it.number, it.title) }
                .toImmutableList()

            _items.value = if(issues.isEmpty())
                                listOf(image)
                            else
                                issues.subList(0, 2) + image + issues.subList(3, issues.size)

        }
    }
}