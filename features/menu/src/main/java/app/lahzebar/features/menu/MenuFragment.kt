package app.lahzebar.features.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import app.lahzebar.commons.CommonsDrawable
import app.lahzebar.domain.model.FndMe
import app.lahzebar.features.menu.databinding.FragmentMenuBinding
import coil.load
import core.views.base.BaseFragmentWithVM
import core.views.util.DebounceClickListener

class MenuFragment : BaseFragmentWithVM<FragmentMenuBinding, MenuState, MenuEffect, MenuViewModel>(
    MenuViewModel::class.java
) {

    companion object {
        val SAMPLE_FND_ME = FndMe(
            id = "635cf02003321300482a6cce", accessLvl = "Public",
            gender = "Female", primaryPhoneNumber = "+989198419604",
            modifiedDateTime = "2022-11-01T05:46:11.418Z", dateOfBirth = "2011-10-29T00:00:00.000Z",
            displayName = "amir hey", firstName = "amir", lastName = "hey",
            description = "ما براي وصل كردن آمديم ني براي فصل كردن آمدیم",
            profileImageUrl = "http://185.255.88.17:9000/photo//uploads/bubble/profileImg/2022-11-01/Thumbnail/6360b2a3d0978a01ec296e99.bmp?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Credential=1111111111222222222234%2F20221105%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Date=20221105T091429Z&X-Amz-Expires=604800&X-Amz-SignedHeaders=host&X-Amz-Signature=6ec80ffaae146a6c870e8f50257033502a5b84b0b010f01a8cb6abe2918176c1",
            nature = true,
        )
        const val PROFILE_TAB = 0
        const val SETTING_TAB = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMenuBinding.inflate(inflater, container, false)
        .let { binding = it; binding.root }

    override fun initialView(savedInstanceState: Bundle?) {
        super.initialView(savedInstanceState)
        binding.backIconIvFm.setOnClickListener(
            DebounceClickListener {
                findNavController().navigateUp()
            }
        )
        initViewPager()
    }

    private fun initViewPager() {
        binding.viewPager.apply {
            adapter = MenuAdapter(this@MenuFragment)
            (getChildAt(0) as RecyclerView).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
            setPageTransformer(MarginPageTransformer(10))
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    when (position) {
                        PROFILE_TAB -> {
                            binding.profile.setBackgroundResource(CommonsDrawable.menu_item_background_after_click)
                            binding.settings.setBackgroundResource(CommonsDrawable.bg_transparent)
                        }
                        SETTING_TAB -> {
                            binding.profile.setBackgroundResource(CommonsDrawable.bg_transparent)
                            binding.settings.setBackgroundResource(CommonsDrawable.menu_item_background_after_click)
                        }
                    }
                }
            })
            binding.profile.setOnClickListener {
                if (currentItem != PROFILE_TAB)
                    currentItem = PROFILE_TAB
            }
            binding.settings.setOnClickListener {
                if (currentItem != SETTING_TAB)
                    currentItem = SETTING_TAB
            }
        }
    }

    override fun onDestroyView() {
        binding.viewPager.adapter = null
        super.onDestroyView()
    }

    override fun renderEffect(effect: MenuEffect) {
        // STOPSHIP
    }

    override fun renderState(state: MenuState) {
        when (state) {
            is MenuState.Init -> {
                state.fndMe?.let {
                    binding.contactNameTvFm.text = it.displayName
                    binding.phoneNumberTvFm.text = it.primaryPhoneNumber
                    binding.profileImageIvFm.load(it.profileImageUrl)
                    binding.occupationTitleTvFm.visibility = View.GONE
                } ?: viewModel.emitAction(MenuAction.LoadData)
            }
        }
    }
}
