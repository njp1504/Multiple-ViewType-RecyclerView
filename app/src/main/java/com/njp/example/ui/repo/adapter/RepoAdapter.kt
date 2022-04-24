package com.njp.example.ui.repo.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

class RepoAdapter : ListAdapter<RepoItem, RepoViewHolder<RepoItem>>(RepoItem.DiffCallback){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder<RepoItem> {
        return RepoViewType.values()[viewType].createViewHolder(parent, viewType) as RepoViewHolder<RepoItem>
    }

    override fun onBindViewHolder(holder: RepoViewHolder<RepoItem>, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun submitList(list: List<RepoItem>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.ordinal
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }
}