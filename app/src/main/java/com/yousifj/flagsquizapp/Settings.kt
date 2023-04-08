package com.yousifj.flagsquizapp


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.*
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.Fragment

var wavy = false
var questionNum = 10
class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        //retrieve sharedPref
        val sharedPref = getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
        // If the wavy exists retrieve the value
        wavy = sharedPref.getBoolean("wavy", false)
        questionNum = sharedPref.getInt("questionsNum", 10)

        // Create a new instance of the MainFragment
        val mainFragment = SettingsFragment()
        // Use a FragmentTransaction to add the fragment to the activity
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, mainFragment)
            .commit()
    }
    /**
     * Launches the main menu activity when a button is clicked
     * @param view The view that triggers the function.
     * @return void
     */
    fun mainMenu(view: View){
        val intent = Intent(this, MainMenuActivity::class.java)
        startActivity(intent)
    }

}
/**
 * A fragment that displays the app settings, it has a switch to toggle the wavy flag effect on/off.
 * and it updates the wavy value in the Shared Preferences when the switch is toggled.
 */
class SettingsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_settings, container, false)
        val switch = view.findViewById<SwitchCompat>(R.id.Wavyflag)
        switch.isChecked = wavy
        switch.setOnCheckedChangeListener { _, isChecked ->
            wavy = isChecked
            // Update the wavy value in the Shared Preferences
            val sharedPref = requireContext().getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("wavy", wavy)
            editor.apply()
        }
        //get the number if questions
        val integerInput = view.findViewById<EditText>(R.id.integer_input)
        integerInput.setText(questionNum.toString())
        integerInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val inputValue = s.toString().toIntOrNull()
                inputValue?.let {
                    val sharedPref = requireContext().getSharedPreferences("appPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPref.edit()
                    editor.putInt("questionsNum", it)
                    editor.apply()
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        return view
    }

}