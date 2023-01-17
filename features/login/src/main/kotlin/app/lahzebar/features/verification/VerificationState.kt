package app.lahzebar.features.verification

import core.views.base.BaseState

sealed class VerificationState : BaseState<VerificationAction, VerificationMutation> {
    object Init : VerificationState()
}
