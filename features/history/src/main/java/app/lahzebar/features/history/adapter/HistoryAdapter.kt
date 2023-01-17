package app.lahzebar.features.history.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class HistoryAdapter(private val contactList: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val HISTORY_HEADER = 0
        const val HISTORY_BODY = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            HISTORY_BODY -> {
                HistoryBodyViewHolder(parent)
            }
            else -> {
                HistoryHeaderViewHolder(parent)
            }
        }
    }

    override fun getItemCount() = contactList.size

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            HISTORY_HEADER
        else HISTORY_BODY
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView
        when (holder) {
            is HistoryHeaderViewHolder -> {
                holder.bind(contactList[position])
            }
            is HistoryBodyViewHolder -> {
                holder.bind(contactList[position])
            }
        }
    }
}
