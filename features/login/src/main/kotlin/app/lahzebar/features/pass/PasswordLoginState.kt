package app.lahzebar.features.pass

import core.views.base.BaseState

sealed class PasswordLoginState : BaseState<PasswordLoginAction, PasswordLoginMutation> {
    object Init : PasswordLoginState()
}
