package me.mehdi.phonebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.mehdi.phonebook.databinding.ActivityDetailsBinding

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_details)

        val binding = ActivityDetailsBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val fullName = intent.getStringExtra("fullname")
        val phone = intent.getStringExtra("phone")
        val location = intent.getStringExtra("location")


        binding.fullName.setText(fullName)
        binding.phone.setText(phone)
        binding.location.setText(location)

        binding.location.setOnClickListener {
            val locationParts = location?.split(",")
            val longitude = locationParts?.get(0)?.toDouble()
            val latitude = locationParts?.get(1)?.toDouble()

            val mapIntent = Intent(this, MapActivity::class.java).apply{
                putExtra("longitude", longitude)
                putExtra("latitude", latitude)
            }

            startActivity(mapIntent)
        }
    }
}