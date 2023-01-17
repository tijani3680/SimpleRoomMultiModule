package app.lahzebar.features.verification

import core.views.base.BaseAction

sealed class VerificationAction : BaseAction {
    data class OtpCode(
        val otpCode: String
    ) : VerificationAction()
}
