package com.njp.example

import android.net.Uri
import android.util.Log
import androidx.lifecycle.*
import com.njp.example.data.GithubRepository
import com.njp.example.ui.adapter.GithubItem
import kotlinx.coroutines.launch
import okhttp3.internal.toImmutableList

class MainViewModel(
    private val repository : GithubRepository
) : ViewModel() {
    private val TAG = MainViewModel::class.java.simpleName

    private val _ownerAndRepo = MutableLiveData<Pair<String, String>>()
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


    fun setOwnerAndRepo(info: Pair<String, String>) {
        _ownerAndRepo.value = info
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