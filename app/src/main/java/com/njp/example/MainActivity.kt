package com.njp.example

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.njp.example.data.GithubRepository
import com.njp.example.databinding.ActivityMainBinding
import com.njp.example.network.github.GithubClient
import com.njp.example.ui.adapter.GithubAdapter
import com.njp.example.ui.adapter.GithubItem

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(GithubRepository(GithubClient.api)) as T
            }
        }
    }

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            lifecycleOwner = this@MainActivity
        }
        binding.viewModel = viewModel

        setContentView(binding.root)


        val adapter = GithubAdapter { view, item ->
            // onClick

            val str = when(item) {
                is GithubItem.GithubIssueItem -> item.title
                is GithubItem.GithubImageItem -> "image"
            }

            Toast.makeText(this, str, Toast.LENGTH_SHORT).show()

        }.apply {
            setHasStableIds(true)
        }

        binding.rvGithub.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
        }


        viewModel.items.observe(this, Observer {
            adapter.submitList(it)
        })

        viewModel.update.observe(this) {}


        // screen update with default data
        viewModel.setOwnerAndRepo(Pair("JakeWharton", "hugo"))

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        receiveIntent(intent)
    }

    private fun receiveIntent(intent: Intent?) {
        intent?:return

        Log.d(TAG, "onNewIntent() intent=$intent")

        // 암시적 인텐트 수신, 데이터 파싱
        if(Intent.ACTION_VIEW == intent.action) {
            viewModel.setOwnerAndRepo(parseUri(intent.data))
        }
    }

    private fun parseUri(uri : Uri?) : Pair<String, String> {
        val userName : String = uri?.host ?: ""

        val paths = uri?.pathSegments
        val repositoryName : String = paths?.get(0) ?: ""

        Log.d(TAG, "parseUri() userName=$userName, repositoryName=$repositoryName")

        return Pair(userName, repositoryName)
    }


}
