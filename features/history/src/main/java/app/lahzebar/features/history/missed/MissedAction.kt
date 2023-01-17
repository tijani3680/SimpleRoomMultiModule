package app.lahzebar.features.history.missed

import core.views.base.BaseAction

sealed class MissedAction : BaseAction {
    object Initial : MissedAction()
}
