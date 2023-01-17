package app.lahzebar.features.contacts.continer

import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class ContactViewModel @Inject constructor() :
    BaseVM<ContactAction, ContactMutation, ContactEffect, ContactState>(ContactState::class.java) {

    override fun initialState(): ContactState = ContactState.Initial

    override fun handle(action: ContactAction): Flow<ContactMutation> = emptyFlow()
}
