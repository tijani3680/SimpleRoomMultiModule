package app.lahzebar.features.home.postDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import app.lahzebar.commons.CommonsDrawable
import app.lahzebar.domain.model.room.Comment
import app.lahzebar.features.home.adapters.CommentsAdapter
import app.lahzebar.features.home.databinding.FragmentPostDetailsBinding
import coil.load
import core.views.base.BaseFragmentWithVM
import core.views.util.instancestate.instanceState

class PostDetailsFragment :
    BaseFragmentWithVM<FragmentPostDetailsBinding, PostDetailsState, PostDetailsEffect, PostDetailsViewModel>(
        PostDetailsViewModel::class.java
    ) {
    private val args by navArgs<PostDetailsFragmentArgs>()
    private var postId by instanceState(0)

    private lateinit var commentAdapter: CommentsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPostDetailsBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postId = args.postId
    }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)
        commentAdapter = CommentsAdapter()

        binding.commentRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.commentRecycler.adapter = commentAdapter
        binding.imgSendComment.setOnClickListener{
            val comment = binding.edtComment.text.toString()
            if (comment.isNotEmpty()){
                viewModel.emitAction(PostDetailsAction.SaveComment(Comment(31,postId,comment)))

            }
        }
    }

    override fun renderEffect(effect: PostDetailsEffect) {
        when (effect) {
            is PostDetailsEffect.ShowDetailsPosts -> {
                binding.tvUserName.text = effect.post.user.name
                binding.tvLikeCount.text = "Like By ${effect.post.posWithComment[0].post.like} people"
                binding.tvCommentCount.text = "${effect.post.posWithComment[0].comments.size} comment"
                binding.tvCaption.text = effect.post.posWithComment[0].post.caption
                binding.imgPicture.load(effect.post.posWithComment[0].post.image) {
                    placeholder(CommonsDrawable.place_holder)
                    fallback(CommonsDrawable.place_holder)
                    error(CommonsDrawable.place_holder)
                }

                if (effect.post.posWithComment[0].post.yourLiked)
                    binding.imgLike.setImageResource(CommonsDrawable.ic_after_like)
                else
                    binding.imgLike.setImageResource(CommonsDrawable.ic_befor_like)

                binding.imgLike.setOnClickListener {
                    viewModel.emitAction(PostDetailsAction.UpdateLikeCount(effect.post.posWithComment[0].post))
                }

                commentAdapter.submitList(effect.post.posWithComment[0].comments)
            }
        }
    }

    override fun renderState(state: PostDetailsState) {
        when (state) {
            PostDetailsState.Init -> viewModel.emitAction(PostDetailsAction.FetchData(postId))
        }
    }
}
