package app.lahzebar.features.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import app.lahzebar.domain.model.room.Comment
import app.lahzebar.features.home.databinding.LayoutItemRecyclerDetailsPostBinding
import core.views.base.BaseViewHolder

class CommentsViewHolder(parent: ViewGroup) : BaseViewHolder<LayoutItemRecyclerDetailsPostBinding>(
    LayoutItemRecyclerDetailsPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    fun bind(comment: Comment) {
        binding.tvTitle.text = comment.title
    }
}
