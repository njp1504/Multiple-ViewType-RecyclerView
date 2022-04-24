package com.njp.example.ui.adapter

import android.net.Uri
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.recyclerview.widget.DiffUtil
import com.njp.example.BR
import com.njp.example.domain.GithubIssue

/**
 * View layer Model
 */
sealed class IssueItem(
    open val id: Long,
    val viewType: IssueViewType
) : BaseObservable() {

    @get:Bindable
    var isSelected: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.selected)
        }

    data class IssueImageItem(
        override val id : Long,
        val imageUri : Uri
    ) : IssueItem(id, IssueViewType.IMAGE) {
        companion object {
            fun from(uri : Uri) =
                IssueImageItem(
                    id = System.currentTimeMillis(),
                    imageUri = uri
                )
        }
    }

    data class IssueInfoItem(
        override val id: Long,
        val number : String,
        val title : String
    ) : IssueItem(id, IssueViewType.ISSUE) {
        companion object {
            fun from(data : GithubIssue) =
                IssueInfoItem(
                    id = data.id.toLong(),
                    number = data.number,
                    title = data.title
                )
        }
    }

    companion object {
        val DiffCallback = object : DiffUtil.ItemCallback<IssueItem>() {
            override fun areItemsTheSame(oldItem: IssueItem, newItem: IssueItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: IssueItem, newItem: IssueItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}