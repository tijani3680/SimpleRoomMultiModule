package app.lahzebar.features.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.lahzebar.features.history.R
import app.lahzebar.features.history.databinding.ListItemHistoryHeaderBinding

class HistoryHeaderViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_item_history_header, parent, false)
) {
    private val binding = ListItemHistoryHeaderBinding.bind(itemView)

    fun bind(date: String) {
        binding.itemTitle.text = date
    }
}
