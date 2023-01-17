package app.lahzebar.features.pass

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import app.lahzebar.commons.navigation.DestinationEvent
import app.lahzebar.commons.navigation.Navigator
import app.lahzebar.features.login.databinding.FragmentPassBinding
import com.google.android.material.textfield.TextInputEditText
import core.utils.safeLet
import core.views.base.BaseFragmentWithVM
import core.views.util.instancestate.nullableInstanceState

class PasswordLoginFragment :
    BaseFragmentWithVM<FragmentPassBinding, PasswordLoginState, PasswordLoginEffect, PasswordLoginViewModel>(
        PasswordLoginViewModel::class.java
    ) {
    companion object {
        const val DEVICE_OS = "ANDROID"
        const val FIREBASE_TOKEN = "SampleFireBaseTokenLCJhY2Nlc3MiOiJDdXJyZW50Iiwi"
    }

    private val args by navArgs<PasswordLoginFragmentArgs>()
    private var phoneNumber: String? by nullableInstanceState()
    private var version: String? by nullableInstanceState()
    private var deviceTitle: String? by nullableInstanceState()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentPassBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)
        phoneNumber = args.phoneNumber
        version = args.version
        deviceTitle = args.deviceTitle
        showKeyboardAndCursor(binding.passEt)

        binding.continueButtonFv.setOnClickListener {
            if (binding.passEt.text.toString().isNotEmpty()) {
                safeLet(phoneNumber, version, deviceTitle) { s, s1, s2 ->
                    viewModel.emitAction(
                        PasswordLoginAction.Login(
                            binding.passEt.text.toString(), s, s1, s2
                        )
                    )
                }
            }
        }
    }

    override fun renderEffect(effect: PasswordLoginEffect) {
        when (effect) {
            is PasswordLoginEffect.Navigate -> {
                (requireActivity() as? Navigator)?.navigateTo(DestinationEvent.Keypad())
            }
            is PasswordLoginEffect.ShowError ->
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun renderState(state: PasswordLoginState) {
    }

    private fun showKeyboardAndCursor(editText: TextInputEditText) {
        editText.requestFocus()
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }
}
