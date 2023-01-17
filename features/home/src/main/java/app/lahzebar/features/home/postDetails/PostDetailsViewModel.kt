package app.lahzebar.features.home.postDetails

import android.util.Log
import app.lahzebar.domain.api.CommentDataSource
import app.lahzebar.domain.api.PostDataSource
import app.lahzebar.domain.api.UserDataSource
import app.lahzebar.features.home.posts.HomeEffect
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel

class PostDetailsViewModel @Inject constructor(
    private val postDataSource: PostDataSource,
    private val userDataSource: UserDataSource,
    private val commentDataSource: CommentDataSource
) : BaseVM
<PostDetailsAction, PostDetailsMutation, PostDetailsEffect, PostDetailsState>(PostDetailsState::class.java) {
    override fun initialState(): PostDetailsState = PostDetailsState.Init

    override fun handle(action: PostDetailsAction): Flow<PostDetailsMutation> = channelFlow {
        when (action) {
            is PostDetailsAction.FetchData -> {

           userDataSource.getSuggestedById(action.postId).onEach {

               emitEffect(PostDetailsEffect.ShowDetailsPosts(it))
               Log.d("tijanidididiidiieieie","ssssssssssssssssssssssssssssssssssssss")

                }.collect()





                // postDataSource.getPostWithUserAndCommentById(action.postId).onEach {
                //     val comments = commentDataSource.getCommentsById(action.postId).first()
                //     val finalData = it.copy(comments = comments)
                //     emitEffect(PostDetailsEffect.ShowDetailsPosts(finalData))
                // }.collect()
            }

            is PostDetailsAction.UpdateLikeCount -> {
                if (action.currentPost.yourLiked)
                    postDataSource.updateLikePost(
                        likeCount = action.currentPost.like - 1, yourLiked = false,
                        postId = action.currentPost.postId
                    )
                else
                    postDataSource.updateLikePost(
                        likeCount = action.currentPost.like + 1, yourLiked = true,
                        postId = action.currentPost.postId
                    )
            }
            is PostDetailsAction.SaveComment -> {
                commentDataSource.insertComment(action.comment)
            }
        }
    }
}
