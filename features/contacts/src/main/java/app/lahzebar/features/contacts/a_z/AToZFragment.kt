package app.lahzebar.features.contacts.a_z

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import app.lahzebar.features.contacts.adapter.ContactAdapter
import app.lahzebar.features.contacts.databinding.FragmentAZBinding
import core.views.base.BaseFragmentWithVM

class AToZFragment : BaseFragmentWithVM<FragmentAZBinding, AToZState, AToZEffect, AToZViewModel>(
    AToZViewModel::class.java
) {
    companion object {
        private const val ARG_COUNT = "param1"
        fun newInstance(counter: Int?): AToZFragment {
            val fragment = AToZFragment()
            val args = Bundle()
            args.putInt(ARG_COUNT, counter!!)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentAZBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)

        binding.apply {

            rvAz.layoutManager = LinearLayoutManager(requireContext())

            val contactsNameList = ArrayList<String>().apply {
                add("Tijani")
                add("Ali")
                add("Reza")
                add("Mohammad")
                add("Hamed")
                add("Amir Ali")
                add("Gholam")
                add("Nazi")
                add("Maryam")
                add("Hasan")
                add("Davoud")
                add("Kamal")
                add("Kiarash")
                add("Naser")
                add("Amir")
                add("Goli")
                add("Parvin")
                add("Jamal")
                add("Bagher")
                add("Dariush")
            }

            val adapter = ContactAdapter(contactsNameList)
            rvAz.adapter = adapter
        }
    }

    override fun renderEffect(effect: AToZEffect) {
        // stopship
    }

    override fun renderState(state: AToZState) {
        // stopship
    }
}
