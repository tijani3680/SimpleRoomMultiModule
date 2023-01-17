package app.lahzebar.features.pass

import core.views.base.BaseAction

sealed class PasswordLoginAction : BaseAction {
    data class Login(
        val password: String,
        val phoneNumber: String,
        val version: String,
        val deviceTitle: String
    ) : PasswordLoginAction()
}
