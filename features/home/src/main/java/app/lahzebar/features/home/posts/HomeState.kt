package app.lahzebar.features.home.posts

import core.views.base.BaseState

sealed class HomeState : BaseState<HomeAction, HomeMutation> {
    object Init : HomeState()
}
