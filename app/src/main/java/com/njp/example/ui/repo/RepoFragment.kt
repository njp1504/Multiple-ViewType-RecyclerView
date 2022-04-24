package com.njp.example.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.njp.example.data.GithubRepository
import com.njp.example.databinding.FragmentRepoBinding
import com.njp.example.network.github.GithubClient
import com.njp.example.ui.MainViewModel

class RepoFragment : Fragment() {
    companion object {
        private const val ARG_DATA = "arg_data"

        @JvmStatic
        fun newInstance(data : Pair<String, String>) =
            RepoFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_DATA, data)
                }
            }
    }

    private val viewModel by activityViewModels<MainViewModel> {
        object : AbstractSavedStateViewModelFactory(requireActivity(), null) {
            override fun <T : ViewModel?> create(
                key: String,
                modelClass: Class<T>,
                handle: SavedStateHandle
            ): T {
                return MainViewModel(handle, GithubRepository(GithubClient.api)) as T
            }
        }
    }

    private lateinit var binding : FragmentRepoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRepoBinding.inflate(layoutInflater).apply {
            this.lifecycleOwner = this@RepoFragment
            this.viewModel = viewModel
        }

        return super.onCreateView(inflater, container, savedInstanceState)
    }
}