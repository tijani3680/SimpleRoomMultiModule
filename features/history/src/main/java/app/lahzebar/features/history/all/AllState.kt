package app.lahzebar.features.history.all

import core.views.base.BaseState

sealed class AllState : BaseState<AllAction, AllMutation> {
    object Initial : AllState()
}
