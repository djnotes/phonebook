package me.mehdi.phonebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import me.mehdi.phonebook.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding : ActivityProfileBinding = ActivityProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val birds = arrayOf(
            R.drawable.bird1
        , R.drawable.bird2,
        R.drawable.bird3,
        R.drawable.bird4,
        R.drawable.bird5,
        R.drawable.bird6,
        R.drawable.bird7)

        binding.showInfo.setOnClickListener {
            Toast.makeText(applicationContext, "شما دکمه را کلیک کردید", Toast.LENGTH_LONG).show()
        }

        binding.bird1.setOnClickListener { it as ImageView
            it.setImageResource(R.drawable.bird2)
        }






    }
}