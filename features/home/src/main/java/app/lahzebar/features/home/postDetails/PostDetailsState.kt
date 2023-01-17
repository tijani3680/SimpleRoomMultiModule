package app.lahzebar.features.home.postDetails

import core.views.base.BaseState

sealed class PostDetailsState : BaseState<PostDetailsAction, PostDetailsMutation> {
    object Init : PostDetailsState()
}
