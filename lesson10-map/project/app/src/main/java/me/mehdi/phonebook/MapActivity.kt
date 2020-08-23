package me.mehdi.phonebook

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import me.mehdi.phonebook.databinding.ActivityMapBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView

class MapActivity : AppCompatActivity() {

    lateinit var mMap : MapView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mMap = binding.mapView
        mMap.setTileSource(TileSourceFactory.MAPNIK)



        Configuration.getInstance().load(applicationContext, getSharedPreferences("phonebook_app", MODE_PRIVATE))

        val longitude = intent.getDoubleExtra("longitude", 36.7783)
        val latitude = intent.getDoubleExtra("latitude", 119.4179)


        val controller = mMap.controller

        val mapPoint = GeoPoint(latitude, longitude)

        controller.setZoom(9.5)

        controller.animateTo(mapPoint)


    }
}