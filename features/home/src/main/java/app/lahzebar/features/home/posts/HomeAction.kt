package app.lahzebar.features.home.posts

import app.lahzebar.domain.model.room.Post
import core.views.base.BaseAction

sealed class HomeAction : BaseAction {
    object FetchData : HomeAction()
    data class UpdateLikeCount(val currentPost: Post) : HomeAction()
}
