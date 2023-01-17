package app.lahzebar.features.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.findNavController
import app.lahzebar.commons.CommonsAnim
import app.lahzebar.commons.CommonsColor
import app.lahzebar.commons.CommonsDrawable
import app.lahzebar.domain.model.room.UserConfig
import app.lahzebar.features.menu.MenuFragmentDirections
import app.lahzebar.features.menu.databinding.FragmentSettingsBinding
import core.views.base.BaseFragmentWithVM
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsFragment :
    BaseFragmentWithVM<FragmentSettingsBinding, SettingsState, SettingsEffect, SettingsViewModel>(
        SettingsViewModel::class.java
    ) {

    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }

    private val initialState
        get() = viewModel.state.value as? SettingsState.LoadTheme

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentSettingsBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)
        handleClickListeners()
    }

    private fun handleClickListeners() {

        binding.apply {

            layoutTvLight.setOnClickListener {

                layoutOverlay.visibility = View.VISIBLE

                if (tvLight.currentTextColor == ResourcesCompat.getColor(resources, CommonsColor.palphone_manatee, null)) {

                    setRotateAnimation(ivThemeIcon)
                }

                coroutineScope.launch {
                    delay(500)
                    withContext(Dispatchers.Main) {

                        viewModel.emitAction(SettingsAction.SaveConfig(theme = UserConfig.Theme.LIGHT))
                    }
                }
            }

            layoutTvDark.setOnClickListener {

                layoutOverlay.visibility = View.VISIBLE

                if (tvDark.currentTextColor == ResourcesCompat.getColor(resources, CommonsColor.palphone_manatee, null)) {

                    setRotateAnimation(ivThemeIcon)
                }

                coroutineScope.launch {
                    delay(500)
                    withContext(Dispatchers.Main) {

                        viewModel.emitAction(SettingsAction.SaveConfig(theme = UserConfig.Theme.DARK))
                    }
                }
            }

            ivThemeIcon.setOnClickListener {

                layoutOverlay.visibility = View.VISIBLE

                setRotateAnimation(ivThemeIcon)

                coroutineScope.launch {
                    delay(500)
                    withContext(Dispatchers.Main) {
                        if (initialState?.theme == UserConfig.Theme.LIGHT)
                            viewModel.emitAction(SettingsAction.SaveConfig(theme = UserConfig.Theme.DARK))
                        else {
                            viewModel.emitAction(SettingsAction.SaveConfig(theme = UserConfig.Theme.LIGHT))
                        }
                    }
                }
            }

            logoutTv.setOnClickListener {

                val action = MenuFragmentDirections.actionGlobalLogoutDialogFragment()
                findNavController().navigate(action)
            }
        }
    }

    private fun setRotateAnimation(viewToAnimate: View) {
        val animation =
            AnimationUtils.loadAnimation(viewToAnimate.context, CommonsAnim.rotate_animation)
        viewToAnimate.startAnimation(animation)
    }

    override fun renderEffect(effect: SettingsEffect) {
        // StopShip
    }

    override fun renderState(state: SettingsState) {

        when (state) {
            is SettingsState.Initial -> {
                viewModel.emitAction(SettingsAction.Initial)
            }
            is SettingsState.LoadTheme -> {
                state.theme.let {
                    loadTheme(it)
                    changeView(it)
                }
            }
        }
    }

    private fun loadTheme(selectedTheme: UserConfig.Theme) {

        when (selectedTheme) {
            UserConfig.Theme.LIGHT -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                requireActivity().window.setBackgroundDrawableResource(CommonsColor.palphone_alice_blue)
            }
            UserConfig.Theme.DARK -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                requireActivity().window.setBackgroundDrawableResource(CommonsColor.palphone_dark_green)
            }
        }
    }

    private fun changeView(theme: UserConfig.Theme) {
        when (theme) {
            UserConfig.Theme.LIGHT -> {

                binding.tvLight.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        CommonsColor.orange,
                        null
                    )
                )
                binding.ivLightArrow.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        CommonsDrawable.double_arrow_right_yellow_selected,
                        null
                    )
                )
                binding.tvDark.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        CommonsColor.palphone_manatee,
                        null
                    )
                )
                binding.ivDarkArrow.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        CommonsDrawable.double_arrow_right_yellow_unselected,
                        null
                    )
                )
            }
            UserConfig.Theme.DARK -> {

                binding.tvDark.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        CommonsColor.orange,
                        null
                    )
                )
                binding.ivDarkArrow.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        CommonsDrawable.double_arrow_right_yellow_selected,
                        null
                    )
                )
                binding.tvLight.setTextColor(
                    ResourcesCompat.getColor(
                        resources,
                        CommonsColor.palphone_manatee,
                        null
                    )
                )
                binding.ivLightArrow.setImageDrawable(
                    ResourcesCompat.getDrawable(
                        resources,
                        CommonsDrawable.double_arrow_right_yellow_unselected,
                        null
                    )
                )
            }
        }
    }
}
