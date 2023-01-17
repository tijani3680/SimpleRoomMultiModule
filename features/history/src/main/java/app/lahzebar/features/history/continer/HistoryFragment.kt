package app.lahzebar.features.history.continer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.lahzebar.commons.CommonsString
import app.lahzebar.commons.navigation.DestinationEvent
import app.lahzebar.commons.navigation.Navigator
import app.lahzebar.features.history.adapter.HistoryViewPagerAdapter
import app.lahzebar.features.history.databinding.FragmentHistoryBinding
import com.google.android.material.tabs.TabLayoutMediator
import core.views.base.BaseFragmentWithVM

class HistoryFragment :
    BaseFragmentWithVM<FragmentHistoryBinding, HistoryState, HistoryEffect, HistoryViewModel>(
        HistoryViewModel::class.java
    ) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentHistoryBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)

        binding.viewPager.adapter = HistoryViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(CommonsString.All)
                1 -> tab.text = resources.getString(CommonsString.MISSED)
            }
            binding.viewPager.setCurrentItem(tab.position, true)
        }.attach()

        binding.ivMenuIcon.setOnClickListener {
            (requireActivity() as? Navigator)?.navigateTo(DestinationEvent.Profile())
        }
    }

    override fun renderEffect(effect: HistoryEffect) {
        // stopship
    }

    override fun renderState(state: HistoryState) {
        // stopship
    }
}
