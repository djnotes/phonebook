package me.mehdi.phonebook

import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import me.mehdi.phonebook.databinding.ActivityMainBinding
import me.mehdi.phonebook.model.Contact
import me.mehdi.phonebook.model.ContactDbHelper
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    lateinit var db : SQLiteDatabase
    lateinit var binding: ActivityMainBinding
    lateinit var cursor: Cursor
    lateinit var mSwipeRefresh : SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        db = ContactDbHelper(applicationContext).writableDatabase

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        mSwipeRefresh = binding.swipeRefresh

        mSwipeRefresh.setOnRefreshListener {
            val ip = "192.168.1.34";
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://$ip:8080")
                .build()
            val alert = AlertDialog.Builder(this)
                .setMessage("لطفا صبر کنید")
                .create()
            try{
                alert.show()
                client.newCall(request).enqueue(object: Callback{
                    override fun onFailure(call: Call, e: IOException) {
                        runOnUiThread {
                            Toast.makeText(applicationContext, "Some problem occurred: ${e.message}", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onResponse(call: Call, response: Response) {
                        val responseString = response.body?.string().toString()
//                        val jsonRoot = JSONObject(responseString)
                        Log.d("MainActivity", "onResponse: jsonRoot: $responseString")
//                        val embedded = jsonRoot.getJSONObject("_embedded")
                        val contacts = JSONArray(responseString)
                        val serverContacts = mutableListOf<Contact>()
                        for(i in 0 until contacts.length() - 1){
                            val newContact = contacts.get(i) as JSONObject
                            val id = newContact.getLong("contact_id")
                            val fullname = newContact.getString("fullname")
                            val phone = newContact.getString("phone")
                            serverContacts.add(Contact(fullname, phone, id))
                            db.insert("person", null, ContentValues().apply{
                                put("fullname", fullname)
                                put("phone", phone)
                            })
                        }


                        alert.dismiss()
                    }

                })
            } catch(e: Exception){
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(applicationContext, "Some problem occurred: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }

            updateList()


        }

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

                    updateList()
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