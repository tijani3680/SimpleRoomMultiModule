package app.lahzebar.features.splash

import core.views.base.BaseState

sealed class SplashState : BaseState<SplashAction, SplashMutation> {
    object Initial : SplashState()
}
