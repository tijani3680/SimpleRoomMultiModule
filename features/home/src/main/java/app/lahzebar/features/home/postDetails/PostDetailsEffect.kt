package app.lahzebar.features.home.postDetails

import app.lahzebar.domain.model.room.PostWithUserAndComment
import app.lahzebar.domain.model.room.UserWithPostWithComment1
import core.views.base.BaseEffect

sealed class PostDetailsEffect : BaseEffect {
    data class ShowDetailsPosts(val post: UserWithPostWithComment1) : PostDetailsEffect()
}
