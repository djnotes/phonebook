package me.mehdi.phonebook

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import me.mehdi.phonebook.model.Contact

class ContactAdapter(val context: Context, val contacts: MutableList<Contact>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, root: ViewGroup?): View {
        if(convertView == null) {
            val newView = LayoutInflater.from(context).inflate(R.layout.list_item, root, false)
            val fullName = newView.findViewById<TextView>(R.id.fullName)
            val phone = newView.findViewById<TextView>(R.id.phone)
            val location = newView.findViewById<TextView>(R.id.location)
            fullName.text = contacts[position].fullName
            phone.text = contacts[position].phone
            location.text = contacts[position].location

            return newView
        }
        else {
            val fullName = convertView.findViewById<TextView>(R.id.fullName)
            val phone = convertView.findViewById<TextView>(R.id.phone)
            val location = convertView.findViewById<TextView>(R.id.location)
            fullName.text = contacts[position].fullName
            phone.text = contacts[position].phone
            location.text = contacts[position].location

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
