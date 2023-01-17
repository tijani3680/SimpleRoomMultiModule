package app.lahzebar.features.verification

import core.views.base.BaseEffect

sealed class VerificationEffect : BaseEffect {
    data class React(val isCorrect: Boolean, val error: String? = null) : VerificationEffect()
    object NavigateToReg : VerificationEffect()
    object NavigateToKeypad : VerificationEffect()
}
