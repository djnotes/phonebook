package me.mehdi.phonebook.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 2)
abstract class AppDb : RoomDatabase() {
    abstract fun contactDao() : ContactDao

    companion object {
        var INSTANCE : AppDb? = null
        fun getInstance(context: Context) : AppDb {
            return INSTANCE ?: synchronized(this){
                val newInstance = Room.databaseBuilder(context, AppDb::class.java, "contacts.db")
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = newInstance
                newInstance
        }

        }
    }



}