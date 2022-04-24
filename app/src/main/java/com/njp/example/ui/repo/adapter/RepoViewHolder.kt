package com.njp.example.ui.repo.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.njp.example.databinding.ItemRepoBinding
import com.njp.example.databinding.ItemRepoImageBinding

abstract class RepoViewHolder<D : RepoItem>(binding: ViewDataBinding)
    : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(item : D)
}


class RepoInfoViewHolder(
    private val binding: ItemRepoBinding
) : RepoViewHolder<RepoInfoItem>(binding) {

    override fun onBind(item: RepoInfoItem) {
        binding.apply {
            this.item = item
        }.executePendingBindings()
    }
}

class RepoImageViewHolder(
    private val binding: ItemRepoImageBinding
) : RepoViewHolder<RepoImageItem>(binding) {

    override fun onBind(item: RepoImageItem) {
        binding.apply {
            this.item = item
        }.executePendingBindings()

        Glide.with(binding.root)
            .load(item.uri)
            .into(binding.ivBanner)
    }
}