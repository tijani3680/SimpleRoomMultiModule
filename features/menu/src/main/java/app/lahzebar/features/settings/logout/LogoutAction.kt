package app.lahzebar.features.settings.logout

import core.views.base.BaseAction

sealed class LogoutAction : BaseAction {

    object Logout : LogoutAction()
}
