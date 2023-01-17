package app.lahzebar.features.settings.logout

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import app.lahzebar.commons.navigation.DestinationEvent
import app.lahzebar.commons.navigation.Navigator
import app.lahzebar.commons.util.Loader
import app.lahzebar.features.menu.databinding.LayoutLogoutDialogBinding
import core.views.base.BaseDialogFragmentWithVM

class LogoutDialogFragment :
    BaseDialogFragmentWithVM<LayoutLogoutDialogBinding, LogoutState, LogoutEffect, LogoutViewModel>(
        LogoutViewModel::class.java
    ) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = LayoutLogoutDialogBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.buttonOk.setOnClickListener {

            viewModel.emitAction(LogoutAction.Logout)
            (requireActivity() as? Loader)?.loader(true)
        }

        binding.buttonCancel.setOnClickListener {

            dismiss()
        }
    }

    override fun renderEffect(effect: LogoutEffect) {
        when (effect) {
            is LogoutEffect.Error -> {

                (requireActivity() as? Loader)?.loader(false)

                Toast.makeText(requireContext(), "${effect.error}", Toast.LENGTH_SHORT).show()
            }

            is LogoutEffect.Logout -> {
                (requireActivity() as? Navigator)?.navigateTo(DestinationEvent.Login())
            }
        }
    }

    override fun renderState(state: LogoutState) {
        // STOPSHIP
    }
}
