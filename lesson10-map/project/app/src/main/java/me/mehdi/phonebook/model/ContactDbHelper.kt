package me.mehdi.phonebook.model

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ContactDbHelper(context: Context) : SQLiteOpenHelper(context, "contacts.db", null, 4) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE person  (fullname TEXT, phone TEXT, location TEXT)")
        db?.execSQL("INSERT INTO person (fullname, phone, location) VALUES ('Hannan Mousavi', '09121231234', '36.7783,119.4179')")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE person")
        onCreate(db)
    }
}