package app.lahzebar.features.adapter

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.ViewGroup
import app.lahzebar.commons.CommonsDrawable
import app.lahzebar.commons.models.SearchedPhoneNumberItem
import app.lahzebar.features.keypad.databinding.ItemSearchPhoneNumberBinding
import coil.load
import core.views.base.BaseViewHolder

class SearchPhoneViewHolder(parent: ViewGroup) : BaseViewHolder<ItemSearchPhoneNumberBinding>(
    ItemSearchPhoneNumberBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    fun bind(searchedPhoneNumberItem: SearchedPhoneNumberItem, onClickListener: (SearchedPhoneNumberItem) -> Unit) {
        binding.phoneNumberTvLispn.text = searchedPhoneNumberItem.phoneNumber
        binding.contactNameTvLispn.text = searchedPhoneNumberItem.name
        binding.contactImageIvLispn.load(if (!TextUtils.isEmpty(searchedPhoneNumberItem.imageUri)) searchedPhoneNumberItem.imageUri else searchedPhoneNumberItem.imageUrl) {
            crossfade(false)
            scale(coil.size.Scale.FIT)
            placeholder(CommonsDrawable.default_profile_image_light)
            fallback(CommonsDrawable.default_profile_image_light)
            error(CommonsDrawable.default_profile_image_dark)
        }
        binding.root.setOnClickListener {
            onClickListener(searchedPhoneNumberItem)
        }
    }
}
