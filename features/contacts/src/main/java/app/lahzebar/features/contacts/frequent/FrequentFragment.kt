package app.lahzebar.features.contacts.frequent

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import app.lahzebar.features.contacts.adapter.ContactAdapter
import app.lahzebar.features.contacts.databinding.FragmentFrequentBinding
import core.views.base.BaseFragmentWithVM

class FrequentFragment : BaseFragmentWithVM<FragmentFrequentBinding, FrequentState, FrequentEffect, FrequentViewModel>(
    FrequentViewModel::class.java
) {
    companion object {
        private const val ARG_COUNT = "param1"
        fun newInstance(counter: Int?): FrequentFragment {
            val fragment = FrequentFragment()
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
    ): View = FragmentFrequentBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)

        binding.apply {

            rvFrequent.layoutManager = LinearLayoutManager(requireContext())

            val contactsNameList = ArrayList<String>().apply {
                add("Sadra")
                add("Soosan")
                add("Mina")
                add("Hamid")
                add("Kamran")
                add("Sattar")
                add("Nina")
                add("Aref")
                add("Nikan")
                add("Alfred")
                add("Ernest")
                add("Kian")
                add("Palasht")
                add("Sona")
                add("Sanaz")
                add("Mahnaz")
                add("Dara")
                add("Aghdas")
                add("Nima")
                add("Behrooz")
            }

            val adapter = ContactAdapter(contactsNameList)
            rvFrequent.adapter = adapter
        }
    }

    override fun renderEffect(effect: FrequentEffect) {
        // stopship
    }

    override fun renderState(state: FrequentState) {
        // stopship
    }
}
