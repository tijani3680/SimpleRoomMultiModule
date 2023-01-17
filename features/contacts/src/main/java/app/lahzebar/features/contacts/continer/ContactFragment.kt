package app.lahzebar.features.contacts.continer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.lahzebar.commons.CommonsString
import app.lahzebar.commons.navigation.DestinationEvent
import app.lahzebar.commons.navigation.Navigator
import app.lahzebar.features.contacts.adapter.ContactViewPagerAdapter
import app.lahzebar.features.contacts.databinding.FragmentContactBinding
import com.google.android.material.tabs.TabLayoutMediator
import core.views.base.BaseFragmentWithVM

class ContactFragment :
    BaseFragmentWithVM<FragmentContactBinding, ContactState, ContactEffect, ContactViewModel>(
        ContactViewModel::class.java
    ) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentContactBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)

        binding.viewPagerContact.adapter = ContactViewPagerAdapter(this)

        TabLayoutMediator(binding.tabLayoutContact, binding.viewPagerContact) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(CommonsString.A_Z)
                1 -> tab.text = resources.getString(CommonsString.FAVORITE)
                2 -> tab.text = resources.getString(CommonsString.FREQUENT)
            }
            binding.viewPagerContact.setCurrentItem(tab.position, true)
        }.attach()

        binding.ivMenuIcon.setOnClickListener {
            (requireActivity() as? Navigator)?.navigateTo(DestinationEvent.Profile())
        }
    }

    override fun renderEffect(effect: ContactEffect) {
        // stopship
    }

    override fun renderState(state: ContactState) {
        // stopship
    }
}
