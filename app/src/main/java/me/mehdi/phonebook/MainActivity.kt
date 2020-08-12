package me.mehdi.phonebook

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import me.mehdi.phonebook.databinding.ActivityMainBinding
import me.mehdi.phonebook.model.Contact
import me.mehdi.phonebook.model.ContactDbHelper

class MainActivity : AppCompatActivity() {

    lateinit var db : SQLiteDatabase
    lateinit var binding: ActivityMainBinding
    lateinit var cursor: Cursor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = ContactDbHelper(applicationContext).writableDatabase

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)


        binding.fab.setOnClickListener {
            val addContactIntent = Intent(this, AddContactActivity::class.java)
            startActivityForResult(addContactIntent, 100)

        }

        updateList()


    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            100 -> {
                if(resultCode == Activity.RESULT_OK){
                    val fullName = data?.getStringExtra("fullname")
                    val phone = data?.getStringExtra("phone")

                    val values = ContentValues().apply{
                        put("fullname", fullName)
                        put("phone", phone)
                    }
                    val rowId = db.insert("person", null, values)
                    if(rowId != -1L){
                        Toast.makeText(applicationContext, "ذخیره اطلاعات با موفقیت انجام شد", Toast.LENGTH_LONG).show()
                    }

//                    updateList()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateList() {
        cursor = db.query("person", arrayOf("rowid", "fullname", "phone"), null, null, null, null, null)
        val contactList : ListView = findViewById(R.id.list)
        val contacts = mutableListOf<Contact>()

        while(cursor.moveToNext()){
            val fullName = cursor.getString(cursor.getColumnIndex("fullname"))
            val phone = cursor.getString(cursor.getColumnIndex("phone"))
            val contactId = cursor.getLong(cursor.getColumnIndexOrThrow("rowid"))
            contacts.add(Contact(fullName, phone, id = contactId))
//            val itemBinding = ListItemBinding.inflate(layoutInflater)
//            val layout = layoutInflater.inflate(R.layout.list_item, contactList, false)
//            layout.findViewById<TextView>(R.id.fullName).text = fullName
//            layout.findViewById<TextView>(R.id.phone).text = phone
//            contactList.addView(layout)
        }

        contactList.adapter = ContactAdapter(this, contacts)

        cursor.close()
    }
}