package app.lahzebar.features.home.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import app.lahzebar.domain.model.room.Comment

class CommentsAdapter : ListAdapter<Comment, CommentsViewHolder>(object :
        DiffUtil.ItemCallback<Comment>() {

        override fun areItemsTheSame(
            oldItem: Comment,
            newItem: Comment
        ): Boolean {
            return oldItem.commentId == newItem.commentId
        }

        override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean =
            oldItem == newItem
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = CommentsViewHolder(parent)
    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        holder.bind(currentList[position])
    }
}
