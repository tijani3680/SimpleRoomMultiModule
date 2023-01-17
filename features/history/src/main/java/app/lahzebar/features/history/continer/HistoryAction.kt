package app.lahzebar.features.history.continer

import core.views.base.BaseAction

sealed class HistoryAction : BaseAction {
    object Initial : HistoryAction()
}
