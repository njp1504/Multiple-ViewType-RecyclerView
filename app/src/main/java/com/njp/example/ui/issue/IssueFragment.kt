package com.njp.example.ui.issue

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.njp.example.data.GithubRepository
import com.njp.example.databinding.FragmentIssueBinding
import com.njp.example.network.github.GithubClient
import com.njp.example.ui.MainViewModel
import com.njp.example.ui.adapter.GithubAdapter
import com.njp.example.ui.adapter.GithubItem

class IssueFragment : Fragment() {
    companion object {
        private val TAG = IssueFragment::class.simpleName
        private const val ARG_DATA = "arg_data"

        @JvmStatic
        fun newInstance(data : Pair<String, String>) =
            IssueFragment().apply {
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

    private lateinit var binding : FragmentIssueBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.i(TAG, "onCreateView()")

        binding = FragmentIssueBinding.inflate(layoutInflater).apply {
            this.lifecycleOwner = this@IssueFragment
            this.viewModel = viewModel
        }

        val adapter = GithubAdapter { view, item ->
            // onClick

            val str = when(item) {
                is GithubItem.GithubIssueItem -> item.title
                is GithubItem.GithubImageItem -> "image"
            }

            Toast.makeText(requireContext(), str, Toast.LENGTH_SHORT).show()

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

        return binding.root
    }

}