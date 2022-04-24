package com.njp.example.ui.repo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.njp.example.data.GithubRepository
import com.njp.example.databinding.FragmentRepoBinding
import com.njp.example.data.remote.github.GithubService
import com.njp.example.ui.MainViewModel
import com.njp.example.ui.repo.adapter.RepoAdapter

class RepoFragment : Fragment() {
    companion object {
        private val TAG = RepoFragment::class.simpleName
        private const val ARG_DATA = "arg_data"

        @JvmStatic
        fun newInstance(data : String) =
            RepoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DATA, data)
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
                return MainViewModel(handle, GithubRepository(GithubService.api)) as T
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
            this.vm = viewModel
        }

        arguments?.getString(ARG_DATA)?.let {
            viewModel.setOwner(it)
        }

        val adapter = RepoAdapter().apply {
            setHasStableIds(true)
        }

        binding.rvRepo.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = null
        }

        viewModel.repos.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        viewModel.isOwnerUpdate.observe(viewLifecycleOwner) {}

        return binding.root
    }
}