package app.lahzebar.features.home.postDetails

import app.lahzebar.domain.model.room.Comment
import app.lahzebar.domain.model.room.Post
import core.views.base.BaseAction

sealed class PostDetailsAction : BaseAction {
    data class FetchData(val postId: Int) : PostDetailsAction()
    data class UpdateLikeCount(val currentPost: Post) : PostDetailsAction()
    data class SaveComment(val comment: Comment) : PostDetailsAction()
}
