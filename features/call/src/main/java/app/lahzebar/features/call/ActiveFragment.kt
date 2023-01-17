package app.lahzebar.features.call

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.lahzebar.features.call.databinding.FragmentActiveBinding
import core.views.base.BaseFragmentWithoutVM

class ActiveFragment : BaseFragmentWithoutVM<FragmentActiveBinding>() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentActiveBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }
}
