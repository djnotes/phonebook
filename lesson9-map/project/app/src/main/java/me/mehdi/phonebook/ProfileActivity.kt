package me.mehdi.phonebook

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_profile.*
import me.mehdi.phonebook.databinding.ActivityProfileBinding
import java.io.FileNotFoundException

class ProfileActivity : AppCompatActivity() {

    companion object {
        const val REQUEST_STORAGE_CODE = 100
        const val REQUEST_IMAGE_CODE = 200
        const val SHARED_PREFS_FILE = "application_data.xml"
    }

    lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.changePicture.setOnClickListener {
            if(ContextCompat.checkSelfPermission(applicationContext, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                changeProfilePicture()
            }
            else {
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE, android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_STORAGE_CODE)
            }
        }

        val prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)
        if(prefs.contains("image_address")){
            val uri = Uri.parse(prefs.getString("image_address", ""))

            var pFD : ParcelFileDescriptor? = null
            try {
                uri?.also {
                    pFD = contentResolver.openFileDescriptor(it, "r")
//                    contentResolver.takePersistableUriPermission(
//                        it,
//                        Intent.FLAG_GRANT_READ_URI_PERMISSION
//                    )
                }
            } catch(ex: FileNotFoundException){
                Log.e("ProfileActivity", "File not found ${ex.message}")
                return
            }

            val bmp = BitmapFactory.decodeFileDescriptor(pFD?.fileDescriptor)

            binding.profilePicture.setImageBitmap(bmp)

        }

    }

    fun changeProfilePicture(){
        val pickIntent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply{
            type = "image/jpeg"
            addCategory(Intent.CATEGORY_OPENABLE)
        }

        startActivityForResult(pickIntent, REQUEST_IMAGE_CODE)

    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode == REQUEST_STORAGE_CODE){
            if(grantResults[0] == grantResults[1] && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                changeProfilePicture()
            }
            else {
                Toast.makeText(applicationContext, R.string.you_should_give_storage_access, Toast.LENGTH_LONG).show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, returnedIntent: Intent?) {
        when(requestCode){
            REQUEST_IMAGE_CODE -> {
                var pFD : ParcelFileDescriptor? = null
                if(resultCode == Activity.RESULT_OK) {
                    try {
                        returnedIntent!!.data?.also {
                            pFD = contentResolver.openFileDescriptor(it, "r")
                            contentResolver.takePersistableUriPermission(
                                it,
                                Intent.FLAG_GRANT_READ_URI_PERMISSION
                            )
                        }
                    } catch(ex: FileNotFoundException){
                        Log.e("ProfileActivity", "File not found ${ex.message}")
                        return
                    }

                    val bmp = BitmapFactory.decodeFileDescriptor(pFD?.fileDescriptor)

                    binding.profilePicture.setImageBitmap(bmp)

                    val prefs = getSharedPreferences(SHARED_PREFS_FILE, Context.MODE_PRIVATE)
                    prefs.edit().putString("image_address", returnedIntent.data.toString())
                        .apply()
                }

            }

        }
        super.onActivityResult(requestCode, resultCode, returnedIntent)
    }
}