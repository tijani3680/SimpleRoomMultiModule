package app.lahzebar.features.mode

import core.views.base.BaseEffect

sealed class ModeEffect : BaseEffect {
    data class NavigateToVerify(
        val type: Boolean
    ) : ModeEffect()

    data class ShowError(val message: String) : ModeEffect()
}
