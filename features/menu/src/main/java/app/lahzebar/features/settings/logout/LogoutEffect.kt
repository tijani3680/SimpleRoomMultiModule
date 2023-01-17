package app.lahzebar.features.settings.logout

import app.lahzebar.domain.model.exception.BaseException
import core.views.base.BaseEffect

sealed class LogoutEffect : BaseEffect {

    object Logout : LogoutEffect()
    data class Error(val error: BaseException) : LogoutEffect()
}
