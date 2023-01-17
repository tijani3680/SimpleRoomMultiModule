package app.lahzebar.features.home.posts

import app.lahzebar.domain.model.room.PostWithUserAndComment
import app.lahzebar.domain.model.room.UserWithPostWithComment1
import core.views.base.BaseEffect

sealed class HomeEffect : BaseEffect {
    data class ShowPosts(val posts: List<UserWithPostWithComment1>) : HomeEffect()
}
