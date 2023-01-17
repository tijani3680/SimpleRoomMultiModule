package app.lahzebar.features.history.all

import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class AllViewModel @Inject constructor() :
    BaseVM<AllAction, AllMutation, AllEffect, AllState>(AllState::class.java) {

    override fun initialState(): AllState = AllState.Initial

    override fun handle(action: AllAction): Flow<AllMutation> = channelFlow {
    }
}
