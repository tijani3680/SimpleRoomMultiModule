package app.lahzebar.features.contacts.a_z

import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class AToZViewModel @Inject constructor() :
    BaseVM<AToZAction, AToZMutation, AToZEffect, AToZState,>(AToZState::class.java) {

    override fun initialState(): AToZState = AToZState.Initial

    override fun handle(action: AToZAction): Flow<AToZMutation> = emptyFlow()
}
