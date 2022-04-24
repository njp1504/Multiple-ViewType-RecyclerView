package com.njp.example.ui.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter

/**
 * 단순 ViewType 판별용으로 사용
 */
enum class IssueViewType {
    ISSUE, IMAGE
}

class IssueAdapter(
    private val onClick : (view: View, item: IssueItem) -> Unit
) : ListAdapter<IssueItem, IssueViewHolder<IssueItem>>(IssueItem.DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssueViewHolder<IssueItem> {
        return IssueViewHolder.getViewHolder(parent, IssueViewType.values()[viewType], onClick)
    }

    override fun onBindViewHolder(holder: IssueViewHolder<IssueItem>, position: Int) {
        holder.onBind(getItem(position))
    }

    override fun submitList(list: List<IssueItem>?) {
        super.submitList(if (list != null) ArrayList(list) else null)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).viewType.ordinal
    }
}





