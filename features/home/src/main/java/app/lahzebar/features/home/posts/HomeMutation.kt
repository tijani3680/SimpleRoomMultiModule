package app.lahzebar.features.home.posts

import app.lahzebar.domain.model.room.PostWithUserAndComment
import core.views.base.BaseMutation

sealed class HomeMutation : BaseMutation {
    data class DataLoaded(val posts: List<PostWithUserAndComment>) : HomeMutation()
}
