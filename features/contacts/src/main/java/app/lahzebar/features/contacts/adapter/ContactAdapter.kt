package app.lahzebar.features.contacts.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import app.lahzebar.features.contacts.databinding.ListItemContactsBodyBinding

class ContactAdapter(private val contactList: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactBodyViewHolder {

        val binding = ListItemContactsBodyBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ContactBodyViewHolder(binding.root)
    }

    override fun getItemCount() = contactList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView
        when (holder) {
            is ContactBodyViewHolder -> {
                holder.bind(contactList[position])
            }
        }
    }
}
