package app.lahzebar.features.history.all

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
import app.lahzebar.features.history.databinding.FragmentAllBinding
import core.views.base.BaseFragmentWithVM

class AllFragment : BaseFragmentWithVM<FragmentAllBinding, AllState, AllEffect, AllViewModel>(
    AllViewModel::class.java
) {
    companion object {
        private const val ARG_COUNT = "param1"
        fun newInstance(counter: Int?): AllFragment {
            val fragment = AllFragment()
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
    ): View = FragmentAllBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)

        binding.apply {

            rvAll.layoutManager = LinearLayoutManager(requireContext())

            val contactsNameList = ArrayList<String>().apply {
                add("Pashmak")
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

            val adapter = HistoryAdapter(contactsNameList)
            rvAll.adapter = adapter

            initializeSwipe(rvAll)
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

    override fun renderEffect(effect: AllEffect) {
        // stopship
    }

    override fun renderState(state: AllState) {
        // stopship
    }
}
