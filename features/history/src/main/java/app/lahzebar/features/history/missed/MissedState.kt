package app.lahzebar.features.history.missed

import core.views.base.BaseState

sealed class MissedState : BaseState<MissedAction, MissedMutation> {
    object Initial : MissedState()
}
