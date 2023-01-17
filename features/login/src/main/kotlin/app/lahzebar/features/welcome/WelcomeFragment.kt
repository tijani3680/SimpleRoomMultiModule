package app.lahzebar.features.welcome

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.lahzebar.features.login.databinding.FragmentWelcomeBinding
import core.views.base.BaseFragmentWithoutVM

class WelcomeFragment : BaseFragmentWithoutVM<FragmentWelcomeBinding>() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentWelcomeBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }
}
