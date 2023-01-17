package app.lahzebar.features.verification

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.lahzebar.commons.navigation.DestinationEvent
import app.lahzebar.commons.navigation.Navigator
import app.lahzebar.commons.util.Loader
import app.lahzebar.commons.util.PinEntryEditText2
import app.lahzebar.features.login.R
import app.lahzebar.features.login.databinding.FragmentVerificationBinding
import core.views.base.BaseFragmentWithVM
import core.views.util.instancestate.nullableInstanceState

class VerificationFragment :
    BaseFragmentWithVM<FragmentVerificationBinding, VerificationState, VerificationEffect, VerificationViewModel>(
        VerificationViewModel::class.java
    ) {
    companion object {
        const val CODE = "code"
        const val STATUS_ACTIVE = "Active"
        const val STATUS_REG = "Reg"
    }

    private var verificationCallType: Boolean? by nullableInstanceState()
    private val args by navArgs<VerificationFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentVerificationBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)
        verificationCallType = args.verificationAsCallType

        verificationCallType?.let {
            when (it) {
                true -> {
                    binding.smsGroup.visibility = View.GONE
                    binding.callGroup.visibility = View.VISIBLE
                }
                false -> {
                    binding.callGroup.visibility = View.GONE
                    binding.smsGroup.visibility = View.VISIBLE
                }
            }
        }
        otpAuto()
    }

    override fun renderEffect(effect: VerificationEffect) {
        when (effect) {
            is VerificationEffect.NavigateToKeypad -> {
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.VerificationFragment, true).build()
                (requireActivity() as? Navigator)?.navigateTo(DestinationEvent.Keypad(navOptions))
            }
            is VerificationEffect.NavigateToReg -> {
                val navOptions =
                    NavOptions.Builder().setPopUpTo(R.id.VerificationFragment, true).build()
                findNavController().navigate(
                    VerificationFragmentDirections.actionVerificationFragmentToRegisterFragment(),
                    navOptions
                )
            }
            is VerificationEffect.React -> {
                if (effect.isCorrect) {
                    binding.pinEntryEtFvvs.setSuccess(true)
                    binding.pinEntryEtFvvs.invalidate()
                } else {
                    (requireActivity() as? Loader)?.loader(false)
                    binding.pinEntryEtFvvs.isError = true
                    (requireActivity() as? Loader)?.loader(false)
                    Toast.makeText(requireContext(), effect.error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun renderState(state: VerificationState) {
    }

    private fun otpAuto() {
        showKeyboardAndCursor(binding.pinEntryEtFvvs)
        binding.pinEntryEtFvvs.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (start == 5 && before == 1) {
                    binding.pinEntryEtFvvs.setText("")
                }
            }

            override fun afterTextChanged(s: Editable?) {
                // STOPSHIP
            }
        })
        binding.pinEntryEtFvvs.setOnPinEnteredListener { str ->
            if (str.length == 6) {
                (requireActivity() as? Loader)?.loader(true)
                viewModel.emitAction(VerificationAction.OtpCode(str.toString()))
            }
        }
    }

    private fun showKeyboardAndCursor(edittext: PinEntryEditText2) {
        edittext.requestFocus()
        val imm =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(edittext, InputMethodManager.SHOW_IMPLICIT)
    }
}
