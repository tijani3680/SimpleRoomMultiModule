package app.lahzebar.features.login

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import app.lahzebar.commons.util.CustomEditText
import app.lahzebar.commons.util.Loader
import app.lahzebar.features.login.databinding.FragmentLoginBinding
import core.views.base.BaseFragmentWithVM
import me.ibrahimsn.lib.PhoneNumberKit

class LoginFragment :
    BaseFragmentWithVM<FragmentLoginBinding, LoginState, LoginEffect, LoginViewModel>(
        LoginViewModel::class.java
    ) {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentLoginBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)

        showKeyboardAndCursor()
        binding.editText.setHandleDismissingKeyboard(object :
                CustomEditText.HandleDismissingKeyboard {
                override fun dismissKeyboard() {}
            })
        countryIsoCodeAndFlag()
        binding.loginButtonFl.setOnClickListener {
            viewModel.emitAction(
                LoginAction
                    .PrimaryPhoneNumber(binding.editText.text.toString())
            )
            (requireActivity() as Loader).loader(true)
        }
    }

    private fun countryIsoCodeAndFlag() {
        val phoneNumberKit = PhoneNumberKit.Builder(requireContext())
            .setIconEnabled(true)
            .build()
        phoneNumberKit.attachToInput(binding.textField, 1)
        phoneNumberKit.setupCountryPicker(
            requireActivity() as AppCompatActivity, R.layout.item_country_picker, true
        )
    }

    override fun renderEffect(effect: LoginEffect) {
        when (effect) {
            is LoginEffect.NavigateToMode -> {
                val action = LoginFragmentDirections.actionLoginFragmentToLoginModeFragment(
                    effect.phoneNumber,
                    effect.hasPass
                )
                findNavController().navigate(action)
            }
            is LoginEffect.ShowError -> Toast.makeText(context, effect.message, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun renderState(state: LoginState) {}

    private fun showKeyboardAndCursor() {
        binding.editText.requestFocus()
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(binding.editText, InputMethodManager.SHOW_IMPLICIT)
    }
}
