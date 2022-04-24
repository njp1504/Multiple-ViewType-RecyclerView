package com.njp.example.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.njp.example.R
import com.njp.example.databinding.ItemIssueBinding
import com.njp.example.databinding.ItemImageBinding

sealed class GithubViewHolder<D: GithubItem>(binding: ViewDataBinding)
    : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(item: D)

    class IssueViewHolder(
        private val binding: ItemIssueBinding,
        private val onClick : (view: View, item: GithubItem) -> Unit
    ) : GithubViewHolder<GithubItem.GithubIssueItem>(binding) {

        override fun onBind(item: GithubItem.GithubIssueItem) {
            binding.item = item
            binding.executePendingBindings()

            binding.root.setOnClickListener { onClick(binding.root, item) }
        }

    }

    class ImageViewHolder(
        private val binding: ItemImageBinding,
        private val onClick : (view: View, item: GithubItem) -> Unit
    ) : GithubViewHolder<GithubItem.GithubImageItem>(binding) {

        override fun onBind(item: GithubItem.GithubImageItem) {
            binding.item = item
            binding.executePendingBindings()

            binding.root.setOnClickListener { onClick(binding.root, item) }

            Glide.with(binding.root)
                .load(item.imageUri)
                .into(binding.ivBanner)
        }
    }

    companion object {
        fun getViewHolder(
            parent: ViewGroup,
            viewType: GithubViewType,
            onClick: (view: View, item: GithubItem) -> Unit
        ) : GithubViewHolder<GithubItem> {
            return when(viewType) {
                GithubViewType.ISSUE -> GithubViewHolder.IssueViewHolder(
                    viewBind(parent, R.layout.item_issue),
                    onClick
                )
                GithubViewType.IMAGE -> GithubViewHolder.ImageViewHolder(
                    viewBind(parent, R.layout.item_image),
                    onClick
                )

                /**
                 * Q. enum인데 else 구문 안써도 되는 이유는?
                 *
                 * when은 기본적으로 else 구문이 필수이지만,
                 * 모든 경우를 branch 한 경우, 생략할 수 있다.
                 */

            } as GithubViewHolder<GithubItem>
        }

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