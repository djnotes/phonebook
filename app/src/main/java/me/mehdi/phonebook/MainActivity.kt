package me.mehdi.phonebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import me.mehdi.phonebook.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button : Button = findViewById(R.id.helloWorld)

        val bird : ImageView = findViewById(R.id.imageView)

        val title : TextView = findViewById(R.id.title)

        button.setOnClickListener {
            title.text = "عنوان صفحه"
            bird.setImageResource(R.drawable.newyork)
        }



    }


}