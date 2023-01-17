package app.lahzebar.features.history.missed

import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class MissedViewModel @Inject constructor() :
    BaseVM<MissedAction, MissedMutation, MissedEffect, MissedState>(MissedState::class.java) {

    override fun initialState(): MissedState = MissedState.Initial

    override fun handle(action: MissedAction): Flow<MissedMutation> = channelFlow {
    }
}
