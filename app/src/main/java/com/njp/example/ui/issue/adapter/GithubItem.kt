package com.njp.example.ui.adapter

import android.net.Uri
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.recyclerview.widget.DiffUtil
import com.njp.example.BR

sealed class GithubItem(
    open val id: Long,
    val viewType: GithubViewType
) : BaseObservable() {

    @get:Bindable
    var isSelected: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.selected)
        }

    data class GithubImageItem(
        override val id : Long,
        val imageUri : Uri
    ) : GithubItem(id, GithubViewType.IMAGE)

    data class GithubIssueItem(
        override val id: Long,
        val number : String,
        val title : String
    ) : GithubItem(id, GithubViewType.ISSUE)

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<GithubItem>() {
            override fun areItemsTheSame(oldItem: GithubItem, newItem: GithubItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubItem, newItem: GithubItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}