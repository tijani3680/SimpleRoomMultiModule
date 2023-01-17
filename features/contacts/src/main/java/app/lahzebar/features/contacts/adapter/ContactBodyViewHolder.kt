package app.lahzebar.features.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.lahzebar.features.contacts.R
import app.lahzebar.features.contacts.databinding.ListItemContactsBodyBinding

class ContactBodyViewHolder(
    parent: ViewGroup,
) : RecyclerView.ViewHolder(
    LayoutInflater.from(parent.context).inflate(R.layout.list_item_contacts_body, parent, false)
) {
    private val binding = ListItemContactsBodyBinding.bind(itemView)

    fun bind(name: String) {
        binding.tvContactName.text = name
    }
}
