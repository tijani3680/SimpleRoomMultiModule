package app.lahzebar.features.contacts.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import app.lahzebar.features.contacts.adapter.ContactAdapter
import app.lahzebar.features.contacts.databinding.FragmentFavoriteBinding
import core.views.base.BaseFragmentWithVM

class FavoriteFragment :
    BaseFragmentWithVM<FragmentFavoriteBinding, FavoriteState, FavoriteEffect, FavoriteViewModel>(
        FavoriteViewModel::class.java
    ) {

    companion object {
        private const val ARG_COUNT = "param1"
        fun newInstance(counter: Int?): FavoriteFragment {
            val fragment = FavoriteFragment()
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
    ): View = FragmentFavoriteBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)

        binding.apply {

            rvFavorite.layoutManager = LinearLayoutManager(requireContext())

            val contactsNameList = ArrayList<String>().apply {
                add("Bibi")
                add("Soozi")
                add("Kourosh")
                add("Negin")
                add("Abolfazl")
                add("Amir Mohammad")
                add("Satin")
                add("Negar")
                add("Hatef")
                add("Hosein")
                add("Diba")
                add("Mahan")
                add("Saghar")
                add("Shima")
                add("AliReza")
                add("Kobra")
                add("Poori")
                add("Nooshin")
                add("Nayyer")
                add("Soghra")
            }

            val adapter = ContactAdapter(contactsNameList)
            rvFavorite.adapter = adapter
        }
    }

    override fun renderEffect(effect: FavoriteEffect) {
        // stopship
    }

    override fun renderState(state: FavoriteState) {
        // stopship
    }
}
