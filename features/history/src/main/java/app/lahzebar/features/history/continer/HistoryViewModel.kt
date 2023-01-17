package app.lahzebar.features.history.continer

import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import javax.inject.Inject

class HistoryViewModel @Inject constructor() :
    BaseVM<HistoryAction, HistoryMutation, HistoryEffect, HistoryState>(HistoryState::class.java) {

    override fun initialState(): HistoryState = HistoryState.Initial

    override fun handle(action: HistoryAction): Flow<HistoryMutation> = channelFlow {
    }
}
