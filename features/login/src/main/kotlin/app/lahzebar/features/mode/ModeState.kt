package app.lahzebar.features.mode

import core.views.base.BaseState

sealed class ModeState : BaseState<ModeAction, ModeMutation> {
    object Init : ModeState()
}
