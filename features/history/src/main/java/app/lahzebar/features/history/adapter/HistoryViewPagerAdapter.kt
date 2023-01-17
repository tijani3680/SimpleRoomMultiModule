package app.lahzebar.features.history.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.lahzebar.features.history.all.AllFragment
import app.lahzebar.features.history.missed.MissedFragment

class HistoryViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                AllFragment.newInstance(position)
            }
            else -> {
                MissedFragment.newInstance(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    companion object {
        private const val ITEM_COUNT = 2
    }
}
