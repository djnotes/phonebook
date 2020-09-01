package me.mehdi.phonebook

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.mehdi.phonebook.databinding.ActivityMainBinding
import me.mehdi.phonebook.model.AppDb
import me.mehdi.phonebook.model.Contact

class MainActivity : AppCompatActivity(), ListAdapter.ClickListener {

    lateinit var binding: ActivityMainBinding
    lateinit var mSwipeRefresh : SwipeRefreshLayout
    lateinit var mList : RecyclerView
    private var mContacts = mutableListOf<Contact>()
    lateinit var mAdapter : ListAdapter
    lateinit var appDb : AppDb

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)



        appDb = AppDb.getInstance(applicationContext)



        setContentView(binding.root)


        mList = binding.list


        mSwipeRefresh = binding.swipeRefresh
        mSwipeRefresh.setOnRefreshListener {
            Snackbar.make(binding.root, "Swipe Refresh is working!", Snackbar.LENGTH_LONG)
                .show()
            mSwipeRefresh.isRefreshing = false
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
                    val location = data?.getStringExtra("location")


                    val newContact = Contact(fullName, phone, location, null)
                    GlobalScope.launch{
                        appDb.contactDao().addContact(newContact)
                    }
                    updateList()
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun updateList() {

        GlobalScope.launch{
            mContacts = appDb.contactDao().getAll() as MutableList<Contact>
            runOnUiThread{
                mAdapter = ListAdapter(this@MainActivity, mContacts)
                mList.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                mList.adapter = mAdapter
                mAdapter.clickListener = this@MainActivity
            }
        }

    }

    override fun onItemClick(position: Int) {
        Snackbar.make(mList, "Item $position clicked", Snackbar.LENGTH_SHORT).show()
    }


    override fun onResume() {
        super.onResume()
        updateList()
    }
}