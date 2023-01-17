package app.lahzebar.features.mode

import core.views.base.BaseAction

sealed class ModeAction : BaseAction {
    data class Login(
        val type: String,
        val phoneNumber: String,
        val version: String,
        val deviceTitle: String
    ) : ModeAction()
}
