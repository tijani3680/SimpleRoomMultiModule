package app.lahzebar.features.home.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import app.lahzebar.domain.model.room.PostWithUserAndComment
import app.lahzebar.domain.model.room.UserWithPostWithComment1

class PostAdapter(
    private val onItemClickListener: (post: UserWithPostWithComment1, currentPosition: Int) -> Unit,
    private val likeClickListener: (post: UserWithPostWithComment1,currentPosition: Int) -> Unit
) : ListAdapter<UserWithPostWithComment1, PostViewHolder>(object :
        DiffUtil.ItemCallback<UserWithPostWithComment1>() {

        override fun areItemsTheSame(
            oldItem: UserWithPostWithComment1,
            newItem: UserWithPostWithComment1
        ): Boolean {
            return oldItem.user.userId == newItem.user.userId
        }

        override fun areContentsTheSame(
            oldItem: UserWithPostWithComment1,
            newItem: UserWithPostWithComment1
        ) =
            oldItem == newItem
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PostViewHolder(parent)
    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(currentList[position], currentList[position].posWithComment, position, onItemClickListener, likeClickListener)
    }
}
