package app.lahzebar.features.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import app.lahzebar.commons.CommonsString
import app.lahzebar.commons.util.CustomEditText
import app.lahzebar.features.login.databinding.FragmentRegisterBinding
import core.views.base.BaseFragmentWithVM

class RegisterFragment :
    BaseFragmentWithVM<FragmentRegisterBinding, RegisterState, RegisterEffect, RegisterViewModel>(
        RegisterViewModel::class.java
    ) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentRegisterBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)
        var isBiz = false

        binding.switchFr.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                binding.dialogBoxPersonal.visibility = View.VISIBLE
                binding.dialogBoxBusiness.visibility = View.GONE
                binding.description.setText(CommonsString.REGISTRATION_PERSONAL_MODE_DESCRIPTION)
                binding.switchFr.setText(CommonsString.PERSONAL)
                isBiz = false
            } else {
                binding.dialogBoxPersonal.visibility = View.GONE
                binding.dialogBoxBusiness.visibility = View.VISIBLE
                binding.description.setText(CommonsString.REGISTRATION_BUSINESS_MODE_DESCRIPTION)
                binding.switchFr.setText(CommonsString.BUSINESS)
                isBiz = true
            }
        }
        binding.doneTvFr.setOnClickListener {
            if (isBiz) {
                viewModel.emitAction(
                    RegisterAction.RegBusiness(
                        binding.companyNameEt.text.toString(),
                        binding.sloganEtFep.text.toString()
                    )
                )
            } else {
                val dayOfMonth = binding.birthDatePickerFr.dayOfMonth
                val month = binding.birthDatePickerFr.month + 1
                val year = binding.birthDatePickerFr.year
                val birthDate = "$year-$month-${dayOfMonth}T00:00:00.000Z"
                viewModel.emitAction(
                    RegisterAction.RegPerson(
                        binding.firstNameEt.text.toString(),
                        binding.lastNameEtFr.text.toString(),
                        birthDate
                    )
                )
            }
        }
        handleVisibilityKeyboard(binding.firstNameEt)
        handleVisibilityKeyboard(binding.lastNameEtFr)
        handleVisibilityKeyboard(binding.companyNameEt)
        handleVisibilityKeyboard(binding.sloganEtFep)
    }

    private fun handleVisibilityKeyboard(view: CustomEditText) {
        view.setHandleDismissingKeyboard(object :
                CustomEditText.HandleDismissingKeyboard {
                override fun dismissKeyboard() {
                    hideKeyboard(view)
                }
            }
        )
    }

    override fun renderEffect(effect: RegisterEffect) {
        when (effect) {
            RegisterEffect.Navigate -> {
                findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToWelcomeFragment()
                )
            }
            is RegisterEffect.ShowError ->
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun renderState(state: RegisterState) {
    }
}
