package com.yousifj.flagsquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.net.HttpURLConnection
import java.net.URL
import java.io.InputStream
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.*
import com.bumptech.glide.Glide
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var text: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //select a random county
        val countryCode = getRandomISO3166()
        //display the flag
        getFlagImage(countryCode)
        //display the name of the country
        text = findViewById(R.id.CountryName)
        text.text = getCountryNameFromISO3166(countryCode)
    }

    //function that will get a flag of a country given the ISO3166 code of that country
    private fun getFlagImage(countryCode: String) {
        imageView = findViewById(R.id.flagImageView)
        //flag API documentations https://flagpedia.net/download/api
        //wavy
        //val flagUrl = "https://flagcdn.com/256x192/$countryCode.png"
        //normal
        val flagUrl = "https://flagcdn.com/h120/$countryCode.png"

        // Use a library like Glide to load the image into an ImageView
        Glide.with(this)
            .load(flagUrl)
            .into(imageView)
    }

    private fun getRandomISO3166(): String {
        val random = Random()
        val countryCode = Locale.getISOCountries()[random.nextInt(Locale.getISOCountries().size)]
        return countryCode.lowercase(Locale.ROOT)
    }
    private fun getCountryNameFromISO3166(countryCode: String): String {
        return Locale("", countryCode).displayCountry
    }

    fun calculateClick(view: View) {
        val countryCode = getRandomISO3166() // Replace with your desired country code
        getFlagImage(countryCode)
        text = findViewById(R.id.CountryName)
        text.text = getCountryNameFromISO3166(countryCode)
    }

}