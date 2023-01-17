package app.lahzebar.features.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.lahzebar.commons.navigation.DestinationEvent
import app.lahzebar.commons.navigation.Navigator
import app.lahzebar.features.splash.databinding.FragmentSplashBinding
import core.views.base.BaseFragmentWithVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashFragment :
    BaseFragmentWithVM<FragmentSplashBinding, SplashState, SplashEffect, SplashViewModel>(
        SplashViewModel::class.java
    ) {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSplashBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)

        coroutineScope.launch {
            delay(600)
            withContext(Dispatchers.Main) {

                (requireActivity() as? Navigator)?.navigateTo(DestinationEvent.Login())
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineScope.cancel()
    }

    override fun renderEffect(effect: SplashEffect) {
        // STOPSHIP:
    }

    override fun renderState(state: SplashState) {
        when (state) {
            is SplashState.Initial -> {
                viewModel.emitAction(SplashAction.Initial)
            }
        }
    }
}
