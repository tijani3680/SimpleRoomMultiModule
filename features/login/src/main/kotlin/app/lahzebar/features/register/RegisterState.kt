package app.lahzebar.features.register

import core.views.base.BaseState

sealed class RegisterState : BaseState<RegisterAction, RegisterMutation> {
    object Init : RegisterState()
}
