package me.mehdi.phonebook

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.mehdi.phonebook.databinding.ActivityAddContactBinding

class AddContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityAddContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.save.setOnClickListener {
            val resultIntent = Intent()
            if(binding.fullname.text.isEmpty() || binding.phone.text.isEmpty() || binding.location.text.isEmpty()){
                Toast.makeText(applicationContext, "لطفا موارد خواسته شده را پر کنید", Toast.LENGTH_LONG).show()
            }
            else {
                val fullName = binding.fullname.text.toString()
                val phone = binding.phone.text.toString()
                val location = binding.location.text.toString()
                resultIntent.putExtra("fullname", fullName)
                resultIntent.putExtra("phone", phone)
                resultIntent.putExtra("location", location)
                setResult(RESULT_OK, resultIntent)
                finish()
            }

        }

    }
}