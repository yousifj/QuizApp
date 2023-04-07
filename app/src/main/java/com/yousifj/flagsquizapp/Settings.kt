package com.yousifj.flagsquizapp

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Switch
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

private lateinit var switch: Switch
var wavy = false
class Settings : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        //retrieve sharedPref
        val sharedPref = getSharedPreferences("myprefs", Context.MODE_PRIVATE)
        if (sharedPref.contains("wavy")) {
            // If the wavy exists retrieve the value
            val currentWavyValue = sharedPref.getBoolean("wavy", false)
            wavy = currentWavyValue
        }

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
        switch = view.findViewById<Switch>(R.id.Wavyflag)
        switch.isChecked = wavy
        switch.setOnCheckedChangeListener { _, isChecked ->
            wavy = isChecked
            // Update the wavy value in the Shared Preferences
            val sharedPref = requireContext().getSharedPreferences("myprefs", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("wavy", wavy)
            editor.apply()
        }
        return view
    }

}