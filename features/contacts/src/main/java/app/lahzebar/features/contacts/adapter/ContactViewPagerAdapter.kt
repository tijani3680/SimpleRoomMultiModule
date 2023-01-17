package app.lahzebar.features.contacts.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.lahzebar.features.contacts.a_z.AToZFragment
import app.lahzebar.features.contacts.favorite.FavoriteFragment
import app.lahzebar.features.contacts.frequent.FrequentFragment

class ContactViewPagerAdapter(fragment: Fragment) :
    FragmentStateAdapter(fragment) {

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AToZFragment.newInstance(position)
            1 -> FavoriteFragment.newInstance(position)
            else -> FrequentFragment.newInstance(position)
        }
    }

    override fun getItemCount(): Int {
        return ITEM_COUNT
    }

    companion object {
        private const val ITEM_COUNT = 3
    }
}
