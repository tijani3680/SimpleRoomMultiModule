package app.lahzebar.features.history.continer

import core.views.base.BaseState

sealed class HistoryState : BaseState<HistoryAction, HistoryMutation> {
    object Initial : HistoryState()
}
