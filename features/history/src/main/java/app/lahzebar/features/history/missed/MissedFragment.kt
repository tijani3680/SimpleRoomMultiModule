package app.lahzebar.features.history.missed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.lahzebar.commons.CommonsDimen
import app.lahzebar.commons.CommonsDrawable
import app.lahzebar.commons.CommonsString
import app.lahzebar.commons.util.swipe.SwipeButtonClickListener
import app.lahzebar.commons.util.swipe.SwipeHelper
import app.lahzebar.features.history.adapter.HistoryAdapter
import app.lahzebar.features.history.databinding.FragmentMissedBinding
import core.views.base.BaseFragmentWithVM

class MissedFragment :
    BaseFragmentWithVM<FragmentMissedBinding, MissedState, MissedEffect, MissedViewModel>(
        MissedViewModel::class.java
    ) {

    companion object {
        private const val ARG_COUNT = "param1"
        fun newInstance(counter: Int?): MissedFragment {
            val fragment = MissedFragment()
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
    ): View = FragmentMissedBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)

        binding.apply {

            rvMissed.layoutManager = LinearLayoutManager(requireContext())

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

            val adapter = HistoryAdapter(contactsNameList)
            rvMissed.adapter = adapter

            initializeSwipe(rvMissed)
        }
    }

    private fun initializeSwipe(recyclerView: RecyclerView) {
        object : SwipeHelper(requireContext(), recyclerView, 200) {

            override fun instantiateMyButton(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(
                    MyButton(
                        requireContext(),
                        resources.getString(CommonsString.DELETE),
                        resources.getDimension(CommonsDimen.standard_font_size).toInt(),
                        CommonsDrawable.trash_icon_h,
                        object :
                            SwipeButtonClickListener {
                            override fun onClick(pos: Int) {
                            }
                        }
                    )
                )
            }

            override fun instantiateMyButtonRight(
                viewHolder: RecyclerView.ViewHolder,
                buffer: MutableList<MyButton>
            ) {
                buffer.add(
                    MyButton(
                        requireContext(),
                        resources.getString(CommonsString.DELETE),
                        resources.getDimension(CommonsDimen.standard_font_size).toInt(),
                        CommonsDrawable.trash_icon_h,
                        object :
                            SwipeButtonClickListener {
                            override fun onClick(pos: Int) {
                            }
                        }
                    )
                )
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }
        }
    }

    override fun renderEffect(effect: MissedEffect) {
        // stopship
    }

    override fun renderState(state: MissedState) {
        // stopship
    }
}
