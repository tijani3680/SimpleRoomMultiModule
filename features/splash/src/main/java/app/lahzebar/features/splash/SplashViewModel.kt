package app.lahzebar.features.splash

import core.views.base.BaseVM
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class SplashViewModel @Inject constructor() :
    BaseVM<SplashAction, SplashMutation, SplashEffect, SplashState>(SplashState::class.java) {
    override fun initialState(): SplashState = SplashState.Initial
    override fun handle(action: SplashAction): Flow<SplashMutation> = emptyFlow()
}
