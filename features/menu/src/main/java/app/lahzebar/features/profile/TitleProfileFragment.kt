package app.lahzebar.features.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import app.lahzebar.features.menu.MenuFragmentDirections
import app.lahzebar.features.menu.databinding.FragmentTitleProfileBinding
import core.views.base.BaseFragmentWithoutVM

class TitleProfileFragment : BaseFragmentWithoutVM<FragmentTitleProfileBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentTitleProfileBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialView()
    }

    private fun initialView() {
        binding.readMoreTvFnb.setOnClickListener {
            val action = MenuFragmentDirections.actionTitleProfileFragmentToProfileFragment(null)
            findNavController().navigate(action)
        }
        binding.readMoreTvFnb.setOnLongClickListener {
            val action = MenuFragmentDirections.actionTitleProfileFragmentToProfileFragment("63567d614df3e4002c3d48cd")
            findNavController().navigate(action)
            return@setOnLongClickListener(true)
        }
    }

    companion object {
        fun newInstance(): TitleProfileFragment {
            return TitleProfileFragment()
        }
    }
}
