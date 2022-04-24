package com.njp.example.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

enum class GithubViewType {
    ISSUE, IMAGE
}

class GithubAdapter(
    private val onClick : (view: View, item: GithubItem) -> Unit
) : ListAdapter<GithubItem, GithubViewHolder<GithubItem>>(GithubItem.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubViewHolder<GithubItem> {
        return GithubViewHolder.getViewHolder(parent, GithubViewType.values()[viewType], onClick)
    }

    override fun onBindViewHolder(holder: GithubViewHolder<GithubItem>, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun submitList(list: List<GithubItem>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.ordinal
    }
}





