package com.njp.example.ui

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.njp.example.R
import com.njp.example.data.GithubRepository
import com.njp.example.databinding.ActivityMainBinding
import com.njp.example.network.github.GithubClient
import com.njp.example.ui.issue.IssueFragment
import com.njp.example.ui.repo.RepoFragment
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName

    private val issueFragment: IssueFragment = IssueFragment.newInstance(Pair("JakeWharton", "hugo"))
    private val repoFragment: RepoFragment = RepoFragment.newInstance(Pair("JakeWharton", "hugo"))

    private val viewModel by viewModels<MainViewModel> {
        object : AbstractSavedStateViewModelFactory(this, intent?.extras) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return MainViewModel(handle, GithubRepository(GithubClient.api)) as T
            }
        }
}

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d(TAG, "onCreate() savedInstanceState=${savedInstanceState?.get("test_key")}, ${savedInstanceState?.get("ownerAndRepo_key")}")
        Log.d(TAG, "onCreate() intent=${intent?.extras?.get("test_key")}")

        binding = ActivityMainBinding.inflate(layoutInflater).apply {
            this.lifecycleOwner = this@MainActivity
            this.viewModel = viewModel
        }

        setContentView(binding.root)

        setupBottomNavigationView()

        navigateTo(issueFragment)

        // screen update with default data
        receiveIntent(intent)
    }


    private fun setupBottomNavigationView() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.menu_issue ->
                    navigateTo(issueFragment)
                R.id.menu_repo ->
                    navigateTo(repoFragment)
            }
            true
        }
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.mainContainer.id, fragment)
            .commit()
    }


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        receiveIntent(intent)
    }

    /**
     * Test Way
     * (in Terminal)
     * adb shell am start -a android.intent.action.VIEW -d issues://{owner}/{repo}/issues
     */
    private fun receiveIntent(intent: Intent?) {
        intent?:return

        Log.d(TAG, "onNewIntent() intent=$intent")

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


    override fun onSaveInstanceState(outState: Bundle) {
        val currentTime = System.currentTimeMillis()
        outState.putLong("test_key", currentTime)
        Log.d(TAG, "onSaveInstanceState() currentTime=$currentTime")

        super.onSaveInstanceState(outState)
    }
}
