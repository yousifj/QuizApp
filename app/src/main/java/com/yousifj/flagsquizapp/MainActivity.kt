package com.yousifj.flagsquizapp

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var text: TextView
    private lateinit var button: Button
    private lateinit var WhichCountry: RadioGroup

    //globe variables
    var checkanswer = true
    var countryCode = "us"
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
        WhichCountry = findViewById(R.id.optionsRadioGroup)
        button = findViewById(R.id.Check_button)
        Choeses()
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

    fun buttonClick(view: View) {
        Choeses()

        //Thread.sleep(2000)
    }
    fun Choeses(){

        if (checkanswer) {
            button.text = "Check"
            checkanswer = false
            //make a list without duplicates
            val numCountries = 4
            val countryCodes = mutableSetOf<String>()
            while (countryCodes.size < numCountries) {
                val countryCode = getRandomISO3166()
                if (!countryCodes.contains(countryCode)) {
                    countryCodes.add(countryCode)
                }
            }
            val countryList = countryCodes.toList()

            val option1CountryName = getCountryNameFromISO3166(countryList[0])
            val option2CountryName = getCountryNameFromISO3166(countryList[1])
            val option3CountryName = getCountryNameFromISO3166(countryList[2])
            val option4CountryName = getCountryNameFromISO3166(countryList[3])
            // randomly chose one of them
            val randomIndex = Random().nextInt(4)
            countryCode = countryList[randomIndex] // Replace with your desired country code
            getFlagImage(countryCode)
            //For testing

            text.text = getCountryNameFromISO3166(countryCode)

            // Get the reference to the RadioGroup in your layout
            val radioGroup = WhichCountry

            // Assign the country names to each RadioButton in the RadioGroup
            radioGroup.getChildAt(0).findViewById<RadioButton>(R.id.option1RadioButton).text =
                option1CountryName
            radioGroup.getChildAt(1).findViewById<RadioButton>(R.id.option2RadioButton).text =
                option2CountryName
            radioGroup.getChildAt(2).findViewById<RadioButton>(R.id.option3RadioButton).text =
                option3CountryName
            radioGroup.getChildAt(3).findViewById<RadioButton>(R.id.option4RadioButton).text =
                option4CountryName
            radioGroup.clearCheck()
        }else {
            button.text = "Next"
            checkanswer = true
            val radioGroup = WhichCountry
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            // No radio button is selected
            if (selectedRadioButtonId == -1) {
                text.text = "Incorrect"
            } else {
                val selectedRadioButton: RadioButton = findViewById(selectedRadioButtonId)
                val selectedText = selectedRadioButton.text
                if (selectedText.equals(getCountryNameFromISO3166(countryCode))) {
                    text.text = "Correct"
                } else {
                    text.text = "Wrong"
                }

            }
        }
    }

}