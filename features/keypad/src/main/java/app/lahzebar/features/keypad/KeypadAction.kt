package app.lahzebar.features.keypad

import app.lahzebar.domain.model.room.Contact
import app.lahzebar.domain.model.room.ContactAndPhone
import app.lahzebar.domain.model.room.PhoneNumber
import core.views.base.BaseAction

sealed class KeypadAction : BaseAction {
    object PrepareReadContact : KeypadAction()
    data class ContactUpdated(
        val contacts: List<Contact>,
        val phones: List<PhoneNumber>,
        val contactAndPhone: List<ContactAndPhone>
    ) : KeypadAction()

    object ReadHistory : KeypadAction()
}
