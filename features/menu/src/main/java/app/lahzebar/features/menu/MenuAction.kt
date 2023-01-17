package app.lahzebar.features.menu

import core.views.base.BaseAction

sealed class MenuAction : BaseAction {
    object LoadData : MenuAction()
}
