package app.lahzebar.features.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.lahzebar.features.history.R
import app.lahzebar.features.history.databinding.ListItemHistoryBodyBinding

class HistoryBodyViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_item_history_body, parent, false)
) {
    private val binding = ListItemHistoryBodyBinding.bind(itemView)

    fun bind(name: String) {
        binding.tvContactName.text = name
    }
}
