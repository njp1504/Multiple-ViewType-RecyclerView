package com.njp.example.ui.repo.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.njp.example.R
import com.njp.example.domain.GithubRepo


/**
 * ViewType에 책임 부여 : 타입이 자체적으로 특정 ViewHolder 생성
 */
enum class RepoViewType {
    REPO {
        override fun createViewHolder(parent: ViewGroup, viewType: Int) : RepoViewHolder<RepoInfoItem>{
            return RepoInfoViewHolder(viewBind(parent, R.layout.item_repo))
        }
    },
    IMAGE {
        override fun createViewHolder(parent: ViewGroup, viewType: Int) : RepoViewHolder<RepoImageItem>{
            return RepoImageViewHolder(viewBind(parent, R.layout.item_repo_image))
        }
    }
    ;

    abstract fun createViewHolder(parent: ViewGroup, viewType: Int) : RepoViewHolder<out RepoItem>

    companion object {
        private fun <T : ViewDataBinding> viewBind(parent: ViewGroup, layoutRes: Int) : T {
            return DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                layoutRes,
                parent,
                false
            )
        }
    }
}

/**
 * View level Model
 */
sealed class RepoItem(
   open val id : Long,
   open val viewType : RepoViewType
) {
    companion object {

        val DiffCallback = object : DiffUtil.ItemCallback<RepoItem>() {
            override fun areItemsTheSame(oldItem: RepoItem, newItem: RepoItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RepoItem, newItem: RepoItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class RepoInfoItem(
    override val id : Long,
    val name : String,
    val visibility: String,
    val description: String,
    val url : String,
    val createdAt : String,
    val updatedAt : String,
    val pushedAt : String,
    val stargazersCount : Int,
    val watchersCount : Int,
    val forksCount : Int,
    val openIssuesCount : Int
) : RepoItem(id, RepoViewType.REPO) {
    companion object {
        fun from(data : GithubRepo) =
            RepoInfoItem(
                id = data.id.toLong(),
                name = data.name,
                visibility = data.visibility,
                description = data.description,
                url = data.url,
                createdAt = data.createdAt,
                updatedAt = data.updatedAt,
                pushedAt = data.pushedAt,
                stargazersCount = data.stargazersCount,
                watchersCount = data.watchersCount,
                forksCount = data.forksCount,
                openIssuesCount = data.openIssuesCount
            )
    }
}

class RepoImageItem(
    override val id : Long,
    val uri : Uri
) : RepoItem(id, RepoViewType.IMAGE) {
    companion object {
        fun from(imageUri : Uri) =
            RepoImageItem(
                id = System.currentTimeMillis(),
                uri = imageUri
            )
    }
}