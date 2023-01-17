package app.lahzebar.features.settings.logout

import core.views.base.BaseState

sealed class LogoutState : BaseState<LogoutAction, LogoutMutation> {
    object Initial : LogoutState()
}
