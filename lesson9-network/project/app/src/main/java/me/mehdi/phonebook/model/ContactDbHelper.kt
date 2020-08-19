package me.mehdi.phonebook.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactDbHelper(context: Context) : SQLiteOpenHelper(context, "contacts.db", null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("CREATE TABLE person  (fullname TEXT, phone TEXT)")
        p0?.execSQL("INSERT INTO person (fullname, phone) VALUES ('Hannan Mousavi', '09121231234')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("DROP TABLE person")
        onCreate(p0)
    }
}