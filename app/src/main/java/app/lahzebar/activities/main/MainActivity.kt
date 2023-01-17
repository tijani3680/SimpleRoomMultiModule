package app.lahzebar.activities.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import app.lahzebar.R
import app.lahzebar.commons.navigation.Destination
import app.lahzebar.commons.navigation.DestinationEvent
import app.lahzebar.commons.navigation.Navigator
import app.lahzebar.databinding.ActivityMainBinding
import core.views.base.BaseActivityWithVM

class MainActivity :
    BaseActivityWithVM
    <ActivityMainBinding, MainState, MainEffect, MainViewModel>(
        MainViewModel::class.java
    ),
    Navigator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initialView(savedInstanceState)
    }

    override fun initialView(savedInstanceState: Bundle?) {
        viewModel.emitAction(MainAction.insertPrimaryData)
    }

    override fun renderEffect(effect: MainEffect) {
        // STOPSHIP
    }

    override fun renderState(state: MainState) {
        // STOPSHIP
    }

    override fun navigateTo(destinationEvent: DestinationEvent) {
    }

    override fun getDestinationId(destination: Destination): Int = when (destination) {
        Destination.HOME -> app.lahzebar.features.home.R.id.fragment_home
    }

    override fun clearBackstack(destination: Destination): Boolean =
        getNavController()?.clearBackStack(getDestinationId(destination)) ?: false

    private fun getNavController() =
        (supportFragmentManager.findFragmentById(binding.mainNavContainer.id) as? NavHostFragment)
            ?.navController
}
