package app.lahzebar.features.splash

import core.views.base.BaseAction

sealed class SplashAction : BaseAction {
    object Initial : SplashAction()
}
