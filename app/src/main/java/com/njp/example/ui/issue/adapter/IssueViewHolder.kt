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

/**
 * Multiple-ViewType 구현방법 1
 * - ViewHolder를 sealed class로 구현하여 Enum 타입에 따라 ViewHolder 분기처리
 */
sealed class IssueViewHolder<D: IssueItem>(binding: ViewDataBinding)
    : RecyclerView.ViewHolder(binding.root) {

    abstract fun onBind(item: D)

    class InfoViewHolder(
        private val binding: ItemIssueBinding,
        private val onClick : (view: View, item: IssueItem) -> Unit
    ) : IssueViewHolder<IssueItem.IssueInfoItem>(binding) {

        override fun onBind(item: IssueItem.IssueInfoItem) {
            binding.item = item
            binding.executePendingBindings()

            binding.root.setOnClickListener { onClick(binding.root, item) }
        }

    }

    class ImageViewHolder(
        private val binding: ItemImageBinding,
        private val onClick : (view: View, item: IssueItem) -> Unit
    ) : IssueViewHolder<IssueItem.IssueImageItem>(binding) {

        override fun onBind(item: IssueItem.IssueImageItem) {
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
            viewType: IssueViewType,
            onClick: (view: View, item: IssueItem) -> Unit
        ) : IssueViewHolder<IssueItem> {
            return when(viewType) {
                IssueViewType.ISSUE -> IssueViewHolder.InfoViewHolder(
                    viewBind(parent, R.layout.item_issue),
                    onClick
                )
                IssueViewType.IMAGE -> IssueViewHolder.ImageViewHolder(
                    viewBind(parent, R.layout.item_image),
                    onClick
                )

                /**
                 * Q. enum인데 else 구문 안써도 되는 이유는?
                 *
                 * when은 기본적으로 else 구문이 필수이지만,
                 * 모든 경우를 branch 한 경우, 생략할 수 있다.
                 */

            } as IssueViewHolder<IssueItem>
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