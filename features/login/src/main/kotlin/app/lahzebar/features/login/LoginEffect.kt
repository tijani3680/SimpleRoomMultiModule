package app.lahzebar.features.login

import core.views.base.BaseEffect

sealed class LoginEffect : BaseEffect {
    data class NavigateToMode(
        val phoneNumber: String,
        val hasPass: Boolean
    ) : LoginEffect()

    data class ShowError(val message: String) : LoginEffect()
}
