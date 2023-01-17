package app.lahzebar.features.pass

import core.views.base.BaseEffect

sealed class PasswordLoginEffect : BaseEffect {
    object Navigate : PasswordLoginEffect()
    data class ShowError(val message: String) : PasswordLoginEffect()
}
