package me.mehdi.phonebook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import me.mehdi.phonebook.model.Contact

class ContactAdapter(val ctx: Context, val contacts: MutableList<Contact>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, root: ViewGroup?): View {
        if(convertView == null) {
            val newView = LayoutInflater.from(ctx).inflate(R.layout.list_item, root, false)
            val fullName = newView.findViewById<TextView>(R.id.fullName)
            val phone = newView.findViewById<TextView>(R.id.phone)
            fullName.text = contacts[position].fullName
            phone.text = contacts[position].phone
            return newView
        }
        else {
            val fullName = convertView.findViewById<TextView>(R.id.fullName)
            val phone = convertView.findViewById<TextView>(R.id.phone)
            fullName.text = contacts[position].fullName
            phone.text = contacts[position].phone
            return convertView
        }

    }

    override fun getItem(position: Int): Any {
        return contacts[position]
    }

    override fun getItemId(position: Int): Long {
            return contacts[position].id
    }

    override fun getCount(): Int {
        return contacts.count()
    }

}
