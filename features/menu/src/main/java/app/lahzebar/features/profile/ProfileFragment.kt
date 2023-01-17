package app.lahzebar.features.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import app.lahzebar.commons.util.Loader
import app.lahzebar.features.menu.databinding.FargmentProfileBinding
import coil.load
import core.views.base.BaseFragmentWithVM
import core.views.util.DebounceClickListener
import core.views.util.instancestate.nullableInstanceState

class ProfileFragment :
    BaseFragmentWithVM<FargmentProfileBinding, ProfileState, ProfileEffect, ProfileViewModel>(
        ProfileViewModel::class.java
    ) {
    private var palId: String? by nullableInstanceState()
    private val args by navArgs<ProfileFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FargmentProfileBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)
        (requireActivity() as? Loader)?.loader(true)
        palId = args.palId

        binding.editIconIvFp.setOnClickListener(
            DebounceClickListener {
                palId ?: run {
                    val action = ProfileFragmentDirections.actionProfileFragmentToEditFragment()
                    findNavController().navigate(action)
                }
            }
        )
        binding.backIconIv.setOnClickListener(
            DebounceClickListener {
                findNavController().navigateUp()
            }
        )
    }

    override fun renderEffect(effect: ProfileEffect) {
    }

    override fun renderState(state: ProfileState) {
        when (state) {
            is ProfileState.Init -> renderInit()
            is ProfileState.DataLoaded -> renderDataLoaded(state)
        }
    }

    private fun renderInit() {
        (requireActivity() as? Loader)?.loader(true)
        palId?.let {
            viewModel.emitAction(ProfileAction.FndOne(it))
            binding.editIconIvFp.visibility = View.GONE
        } ?: viewModel.emitAction(ProfileAction.LoadData)
    }

    private fun renderDataLoaded(state: ProfileState.DataLoaded) {
        (requireActivity() as? Loader)?.loader(false)
        state.data.let { fndMe ->
            binding.apply {
                if (fndMe.nature == true) {
                    personal.visibility = View.VISIBLE
                    banner.visibility = View.VISIBLE
                    contactNameTvFp.text = fndMe.displayName
                    primaryPhoneNumberTvFp.text = fndMe.primaryPhoneNumber
                    sloganTvFp.text = fndMe.description
                    birthDateTvFp.text = extractDate(fndMe.dateOfBirth)
                    profileImageRivFp.load(fndMe.profileImageUrl)
                } else {
                    business.visibility = View.VISIBLE
                    banner.visibility = View.VISIBLE
                    primaryPhoneNumberTvFp.text = fndMe.primaryPhoneNumber
                    companyNameTvFp.text = fndMe.companyName
                    sloganTvFp.text = fndMe.slogan
                }
            }
        }
    }

    private fun extractDate(date: String?): String {
        return date?.indexOf("T")
            ?.let { T -> date.substring(0, T).replace("-", ".") }
            ?: ""
    }
}
