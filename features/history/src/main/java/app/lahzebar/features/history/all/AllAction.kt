package app.lahzebar.features.history.all

import core.views.base.BaseAction

sealed class AllAction : BaseAction {
    object Initial : AllAction()
}
