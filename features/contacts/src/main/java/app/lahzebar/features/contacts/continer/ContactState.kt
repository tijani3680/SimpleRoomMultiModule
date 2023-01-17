package app.lahzebar.features.contacts.continer

import core.views.base.BaseState

sealed class ContactState : BaseState<ContactAction, ContactMutation> {
    object Initial : ContactState()
}
