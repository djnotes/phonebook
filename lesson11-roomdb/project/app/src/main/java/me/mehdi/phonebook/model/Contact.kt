package me.mehdi.phonebook.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
data class Contact (@ColumnInfo(name = "full_name") var fullName: String?,
               @ColumnInfo(name = "phone") var phone: String?,
               @ColumnInfo(name="location") var location : String?,
               @PrimaryKey(autoGenerate = true) var id: Long?)
