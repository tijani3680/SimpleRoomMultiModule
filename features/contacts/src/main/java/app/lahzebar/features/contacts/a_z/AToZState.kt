package app.lahzebar.features.contacts.a_z

import core.views.base.BaseState

sealed class AToZState : BaseState<AToZAction, AToZMutation> {
    object Initial : AToZState()
}
