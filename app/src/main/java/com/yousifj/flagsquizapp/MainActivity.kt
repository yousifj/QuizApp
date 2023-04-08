package com.yousifj.flagsquizapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ContextMenu
import android.view.MenuItem
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
    private lateinit var radioGroup: RadioGroup

    //globe variables
    private var checkAnswer = true
    private var wavy = false
    private var countryCode = "us"
    private var questionNum = 0
    private var questionsNum = 10
    private var score = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        // If the wavy exists retrieve the value
        wavy = sharedPref.getBoolean("wavy", false)
        questionsNum = sharedPref.getInt("questionsNum", 10)

        //select a random county
        val countryCode = getRandomISO3166()
        //display the flag
        getFlagImage(countryCode)
        //display the name of the country
        questionNumber = findViewById(R.id.QuestionNumber)
        text = findViewById(R.id.CountryName)
        radioGroup = findViewById(R.id.optionsRadioGroup)
        button = findViewById(R.id.Check_button)
        driver()
        listener()
        //register the context menu
        val myView = findViewById<View>(R.id.activity_main)
        registerForContextMenu(myView)
    }
    /**
     * Shows an alert dialog when the restart button is clicked.
     * @param view The view that triggers the function.
     * @return void
     */
    fun showAlertDialog(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.restartTitle))
        builder.setMessage(getString(R.string.restartWarning))
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            //Restart activity
            val intent = Intent(this, this::class.java)
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            // close the alert dialog and do nothing in not confirmed
            dialog.cancel()
        }
        builder.create().show()

    }
    /**
     * Initializes a context menu and inflates it with menu items.
     * @param menu The context menu.
     * @param v The view for the context menu.
     * @param menuInfo information about the context menu.
     * @return void
     */
    override fun onCreateContextMenu(menu: ContextMenu, v: View, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.my_context_menu, menu)
    }
    /**
     * Handles the selection of a context menu item.
     * @param item The selected context menu item.
     * @return True if the selected context menu item, else false.
     */
    override fun onContextItemSelected(item: MenuItem): Boolean {
        //if flat is clicked make the flag flat
        when (item.itemId) {
            R.id.flat -> {
                val prevValue = wavy
                wavy = false
                getFlagImage(countryCode)
                wavy = prevValue
                return true
            }
            //if wavy is clicked make the flag wavy
            R.id.wavy -> {
                val prevValue = wavy
                wavy = true
                getFlagImage(countryCode)
                wavy = prevValue
                return true
            }
            else -> return super.onContextItemSelected(item)
        }
    }

    /**
     * Function to setup a Listener for the multiple choose
     * it will highlight the selected only
     * @return void
    */
    private fun listener() {
        var prevSelectedRadioButton: RadioButton? = null
        //highlight the selected answer by changing the background color
        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            val checkedRadioButton = group.findViewById<RadioButton>(checkedId)
            if (checkedRadioButton != null) {
                checkedRadioButton.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        R.color.teal_200
                    )
                )
                prevSelectedRadioButton?.setBackgroundColor(
                    ContextCompat.getColor(
                        this,
                        android.R.color.transparent
                    )
                )
                prevSelectedRadioButton = checkedRadioButton
            }
        }
    }

    /**
     * function that will get a flag of a country using API call
     * @param ISO 3166 code of that country
     * @return void
     */
    private fun getFlagImage(countryCode: String) {
        imageView = findViewById(R.id.flagImageView)
        var flagUrl  = ""
        flagUrl = if (wavy){
            //flag API documentations https://flagpedia.net/download/api
            //wavy
            "https://flagcdn.com/256x192/$countryCode.png"
        }else{
            //normal
            "https://flagcdn.com/h120/$countryCode.png"
        }

        // Use a library like Glide to load the image into an ImageView
        Glide.with(this)
            .load(flagUrl)
            .into(imageView)
    }
    /**
     * Generates a random ISO 3166 country code.
     * @return The random ISO 3166 country code in lowercase format.
     */
    private fun getRandomISO3166(): String {
        val random = Random()
        val countryCode = Locale.getISOCountries()[random.nextInt(Locale.getISOCountries().size)]
        return countryCode.lowercase(Locale.ROOT)
    }
    /**
     * Retrieves the country name from the ISO 3166 code.
     * @param countryCode The ISO 3166 country code.
     * @return The country name.
     */
    private fun getCountryNameFromISO3166(countryCode: String): String {
        return Locale("", countryCode).displayCountry
    }
    /**
     * Handles button clicks during a quiz.
     * Checks Answer first then move to next question
     * @param view The view that triggers the function.
     * @return void
     */
    fun buttonClick(view: View) {
        //if the 10 questions have been asked end the quiz by going to a new activity
        if(questionNum >= questionsNum && checkAnswer){
            val intent = Intent(this, EndScreen::class.java)
            intent.putExtra("score", score)
            startActivity(intent)
        }else{
            driver()
            listener()
        }

    }
    /**
     * Function responsible for the logic of quiz activity
     * adjust the check button color the correct answer
     * count questions answered and Score
     * Display correct answer
     * @return void
     */
    private fun driver(){
        // confirm if the answer have been checked
        if (checkAnswer) {
            //give the option to check in the button
            button.text = getString(R.string.Check)
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.check_answer_color))
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
            val radioGroup = radioGroup
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
            button.text = getString(R.string.Next)
            button.setBackgroundColor(ContextCompat.getColor(this, R.color.next_question_color))
            checkAnswer = true
            val radioGroup = radioGroup
            val selectedRadioButtonId = radioGroup.checkedRadioButtonId
            // No radio button is selected
            if (selectedRadioButtonId == -1) {
                val countryName = getCountryNameFromISO3166(countryCode)
                text.text = getString(R.string.no_country_selected, countryName)
            } else {
                val selectedRadioButton: RadioButton = findViewById(selectedRadioButtonId)
                val selectedText = selectedRadioButton.text
                if (selectedText.equals(getCountryNameFromISO3166(countryCode))) {
                    score++
                    selectedRadioButton.setBackgroundColor(ContextCompat.getColor(this, R.color.green))
                } else {
                    selectedRadioButton.setBackgroundColor(ContextCompat.getColor(this, R.color.red))
                }
                text.text = getCountryNameFromISO3166(countryCode)
            }
        }
        //update score
        questionNumber.text = getString(R.string.question_score_text, questionNum, questionsNum, score)
        //restart the radioGroup Listener
        radioGroup.setOnCheckedChangeListener(null)
    }

}