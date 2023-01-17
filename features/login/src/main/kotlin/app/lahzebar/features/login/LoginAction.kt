package app.lahzebar.features.login

import core.views.base.BaseAction

sealed class LoginAction : BaseAction {
    data class PrimaryPhoneNumber(val number: String) : LoginAction()
}
