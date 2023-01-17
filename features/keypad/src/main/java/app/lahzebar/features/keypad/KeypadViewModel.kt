package app.lahzebar.features.keypad

import app.lahzebar.domain.api.ContactDataSource
import app.lahzebar.domain.api.StoreDataSource
import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class KeypadViewModel @Inject constructor(
    private val storeDataSource: StoreDataSource,
    private val contactDataSource: ContactDataSource
) : BaseVM<KeypadAction, KeypadMutation, KeypadEffect, KeypadState>(KeypadState::class.java) {
    override fun initialState(): KeypadState = KeypadState.Init()
    override fun handle(action: KeypadAction): Flow<KeypadMutation> = channelFlow {
        when (action) {
            is KeypadAction.PrepareReadContact -> {
                storeDataSource.getLastUpdateContact().first().let { lastContactUpdate ->
                    send(KeypadMutation.ReadContact(lastContactUpdate))
                }
            }
            is KeypadAction.ContactUpdated -> {
                contactDataSource.insertLocalContactListReplace(
                    action.contacts,
                    action.phones,
                    action.contactAndPhone
                )
                /*  contactDataSource.insert()*/
            }

            is KeypadAction.ReadHistory -> emitEffect(KeypadEffect.ReadHistory)
        }
    }
}
