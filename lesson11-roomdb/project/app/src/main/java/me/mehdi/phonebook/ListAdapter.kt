package me.mehdi.phonebook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import me.mehdi.phonebook.model.Contact

class ListAdapter(private val context: Context, private val contacts: List<Contact>) : Adapter<ListAdapter.ViewHolder>() {

    var clickListener: ClickListener? = null

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var root : ViewGroup = view.findViewById(R.id.root)
        val fullName: TextView = view.findViewById(R.id.fullName)
        val phone : TextView = view.findViewById(R.id.phone)
        val location : TextView = view.findViewById(R.id.location)
        val picture : ImageView = view.findViewById(R.id.picture)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.root.setOnClickListener { clickListener?.onItemClick(position)}
        holder.fullName.text = contacts[position].fullName
        holder.phone.text = contacts[position].phone
        holder.location.text = contacts[position].location
        holder.picture.setImageResource(R.drawable.bird7)
    }

    override fun getItemCount(): Int {
        return contacts.count()
    }

    interface ClickListener{
        fun onItemClick(position: Int)
    }
}