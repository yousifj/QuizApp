package com.yousifj.flagsquizapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var text: TextView
    private lateinit var questionNumber: TextView
    private lateinit var button: Button
    private lateinit var WhichCountry: RadioGroup

    //globe variables
    private var checkAnswer = true
    private var countryCode = "us"
    private var questionNum = 0
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //select a random county
        val countryCode = getRandomISO3166()
        //display the flag
        getFlagImage(countryCode)
        //display the name of the country
        questionNumber = findViewById(R.id.QuestionNumber)
        text = findViewById(R.id.CountryName)
        //
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
        //if the 10 questions have been asked end the quiz by going to a new activity
        if(questionNum >= 10){
            val intent = Intent(this, EndScreen::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
        }
        Choeses()
    }
    @SuppressLint("SetTextI18n")
    fun Choeses(){
        // confirm if the answer have been checked
        if (checkAnswer) {
            //give the option to check in the button
            button.text = "Check"
            checkAnswer = false
            questionNum ++
            //make a list of 4 countries without duplicates
            val numCountries = 4
            val countryCodes = mutableSetOf<String>()
            while (countryCodes.size < numCountries) {
                val countryCode = getRandomISO3166()
                if (!countryCodes.contains(countryCode)) {
                    countryCodes.add(countryCode)
                }
            }
            val countryList = countryCodes.toList()

            // randomly chose one of them
            val randomIndex = Random().nextInt(4)
            countryCode = countryList[randomIndex]
            //display the flag
            getFlagImage(countryCode)
            //clear correct answer
            text.text =""

            // Get the reference to the RadioGroup in your layout
            val radioGroup = WhichCountry
            // Assign the country names to each RadioButton in the RadioGroup
            radioGroup.getChildAt(0).findViewById<RadioButton>(R.id.option1RadioButton).text =
                getCountryNameFromISO3166(countryList[0])
            radioGroup.getChildAt(1).findViewById<RadioButton>(R.id.option2RadioButton).text =
                getCountryNameFromISO3166(countryList[1])
            radioGroup.getChildAt(2).findViewById<RadioButton>(R.id.option3RadioButton).text =
                getCountryNameFromISO3166(countryList[2])
            radioGroup.getChildAt(3).findViewById<RadioButton>(R.id.option4RadioButton).text =
                getCountryNameFromISO3166(countryList[3])
            // clear checked
            radioGroup.clearCheck()
            // clear background
            for (i in 0 until radioGroup.childCount) {
                val child = radioGroup.getChildAt(i)
                child.background = null
                // or use child.setBackgroundColor(Color.TRANSPARENT);
            }
        }
        // condition when the answer needs to be checked
        else {
            button.text = "Next"
            checkAnswer = true
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
                    score++
                    selectedRadioButton.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                } else {
                    selectedRadioButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                    text.text = "Wrong"
                }
                text.text = getCountryNameFromISO3166(countryCode)
            }
        }
        //update score
        questionNumber.text = "QuestionNumber: ${questionNum.toString()}/10\t\t\t\t\t\t\t\t Score: ${score.toString()}"
    }

}