package app.lahzebar.features.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.lahzebar.commons.models.SearchedPhoneNumberItem

class SearchPhoneNumberAdapter(
    private val data: List<SearchedPhoneNumberItem>,
    private val onClickListener: (SearchedPhoneNumberItem) -> Unit
) : RecyclerView.Adapter<SearchPhoneViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchPhoneViewHolder(parent)

    override fun onBindViewHolder(holder: SearchPhoneViewHolder, position: Int) {
        holder.bind(data[position], onClickListener)
    }

    override fun getItemCount() = data.size
}
