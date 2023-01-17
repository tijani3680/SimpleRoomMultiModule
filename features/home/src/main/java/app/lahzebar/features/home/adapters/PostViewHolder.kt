package app.lahzebar.features.home.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import app.lahzebar.commons.CommonsDrawable
import app.lahzebar.domain.model.room.PostWithComment1
import app.lahzebar.domain.model.room.PostWithUserAndComment
import app.lahzebar.domain.model.room.UserWithPostWithComment1
import app.lahzebar.features.home.databinding.LayoutItemRecyclerPostBinding
import coil.load
import core.views.base.BaseViewHolder

class PostViewHolder(parent: ViewGroup) : BaseViewHolder<LayoutItemRecyclerPostBinding>(
    LayoutItemRecyclerPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    fun bind(
        post: UserWithPostWithComment1,
        postWithComment: List<PostWithComment1>,
        currentPosition: Int,
        onItemClickListener: (post: UserWithPostWithComment1, currentPosition: Int) -> Unit,
        likeClickListener: (post: UserWithPostWithComment1,currentPosition: Int) -> Unit
    ) {
        binding.tvUserName.text = post.user.name
        binding.tvLikeCount.text = "Like By ${post.posWithComment[0].post.like} people"
        binding.tvCommentCount.text = "${post.posWithComment[0].comments.size} comment"
        binding.tvCaption.text = post.posWithComment[0].post.caption
        binding.imgPicture.load(post.posWithComment[0].post.image) {
            placeholder(CommonsDrawable.place_holder)
            fallback(CommonsDrawable.place_holder)
            error(CommonsDrawable.place_holder)
        }

        if (post.posWithComment[0].post.yourLiked)
            binding.imgLike.setImageResource(CommonsDrawable.ic_after_like)
        else
            binding.imgLike.setImageResource(CommonsDrawable.ic_befor_like)

        binding.root.setOnClickListener {
            onItemClickListener(post, currentPosition)
        }
        binding.imgLike.setOnClickListener {
            likeClickListener(post,currentPosition)
        }
    }
}
