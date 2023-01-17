package app.lahzebar.features.menu

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import app.lahzebar.features.profile.TitleProfileFragment
import app.lahzebar.features.settings.SettingsFragment

class MenuAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        when (position) {
            MenuFragment.PROFILE_TAB -> {
                return TitleProfileFragment.newInstance()
            }
            MenuFragment.SETTING_TAB -> {
                return SettingsFragment.newInstance()
            }
        }
        return TitleProfileFragment.newInstance()
    }
}
