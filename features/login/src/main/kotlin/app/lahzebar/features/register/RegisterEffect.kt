package app.lahzebar.features.register

import core.views.base.BaseEffect

sealed class RegisterEffect : BaseEffect {
    object Navigate : RegisterEffect()
    data class ShowError(val message: String) : RegisterEffect()
}
