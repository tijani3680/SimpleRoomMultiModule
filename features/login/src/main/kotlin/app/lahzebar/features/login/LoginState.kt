package app.lahzebar.features.login

import core.views.base.BaseState

sealed class LoginState : BaseState<LoginAction, LoginMutation> {
    object Init : LoginState()
}
