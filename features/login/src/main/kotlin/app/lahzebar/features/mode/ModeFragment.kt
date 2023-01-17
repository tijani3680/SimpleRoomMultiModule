package app.lahzebar.features.mode

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.lahzebar.commons.util.Loader
import app.lahzebar.features.login.databinding.FragmentModeBinding
import core.views.base.BaseFragmentWithVM
import core.views.util.instancestate.nullableInstanceState

class ModeFragment : BaseFragmentWithVM<
    FragmentModeBinding, ModeState, ModeEffect, ModeViewModel>(
    ModeViewModel::class.java
) {
    companion object {
        const val SMS_TYPE = "sms"
        const val CALL_TYPE = "call"
        const val DEVICE_OS = "ANDROID"
        const val FIREBASE_TOKEN = "SampleFireBaseTokenLCJhY2Nlc3MiOiJDdXJyZW50Iiwi"
    }
    private var phoneNumber: String? by nullableInstanceState()
    private var hasPass: Boolean? by nullableInstanceState()
    private val args by navArgs<ModeFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentModeBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)
        phoneNumber = args.phoneNumber
        hasPass = args.password
        hasPass?.let {
            if (it) {
                binding.passGroup.visibility = View.VISIBLE
            }
        }
        binding.phoneNumber.text = phoneNumber

        binding.callTypeClick.setOnClickListener {
            phoneNumber?.let {
                (requireActivity() as? Loader)?.loader(true)
                viewModel.emitAction(
                    ModeAction.Login(CALL_TYPE, it, versionName(), buildManufacture())
                )
            }
        }
        binding.smsTypeClick.setOnClickListener {
            phoneNumber?.let {
                (requireActivity() as? Loader)?.loader(true)
                viewModel.emitAction(
                    ModeAction.Login(SMS_TYPE, it, versionName(), buildManufacture())
                )
            }
        }
        binding.wrongNumberTvFlm.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.passwordTypeClick.setOnClickListener {
            findNavController().navigate(
                ModeFragmentDirections.actionModeFragmentToPasswordLoginFragment(
                    phoneNumber.toString(), versionName(), buildManufacture()
                )
            )
        }
    }

    override fun renderEffect(effect: ModeEffect) {
        when (effect) {
            is ModeEffect.NavigateToVerify -> {
                findNavController().navigate(
                    ModeFragmentDirections.actionModeFragmentToVerificationFragment(effect.type)
                )
            }
            is ModeEffect.ShowError -> {
                (requireActivity() as? Loader)?.loader(false)
                Toast.makeText(requireContext(), effect.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun renderState(state: ModeState) {
    }

    private fun versionName(): String {
        return context?.let {
            it.packageManager?.getPackageInfo(it.packageName, 0)?.versionName
        } ?: ""
    }

    private fun buildManufacture(): String {
        val model = Build.MODEL
        val manufacture = Build.MANUFACTURER
        val result: String? = if (model.startsWith(manufacture)) model
        else String.format("%s %s", manufacture, model)
        return result ?: ""
    }
}
