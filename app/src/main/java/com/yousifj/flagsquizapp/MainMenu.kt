package com.yousifj.flagsquizapp

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.Switch
import android.widget.Toast
import androidx.fragment.app.Fragment

private lateinit var switch: Switch

var wavy = false
class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        wavy = intent.getBooleanExtra("wavy", false)

        // Create a new instance of the MainFragment
        val mainFragment = MainFragment()
        // Use a FragmentTransaction to add the fragment to the activity
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, mainFragment)
            .commit()

        // Find the Button element that triggers the openSettings function
        val settingsButton = findViewById<Button>(R.id.settings_button)
        //val view = R.layout.fragment_settings
        //switch = view.findViewById(R.id.Wavyflag)
        // Set an OnClickListener on the Button to call openSettings
        settingsButton.setOnClickListener {
            settingsButton.setVisibility(View.GONE)
            if (mainFragment.isAdded) {
                mainFragment.openSettings(it)
            }
        }

    }
    //show alert when exit button is clicked
    fun showAlertDialog(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.exitTitle))
        builder.setMessage(getString(R.string.exitWarning))
        builder.setPositiveButton(getString(R.string.yes)) { _, _ ->
            //Exit the whole program is yes
            finishAffinity()
        }
        builder.setNegativeButton(getString(R.string.no)) { dialog, _ ->
            // close the alert dialog and do nothing in not confirmed
            dialog.cancel()
        }
        builder.create().show()

    }
    fun startQuiz(view: View){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("wavy", wavy)
        startActivity(intent)
    }

}
class MainFragment : Fragment() {

    fun openSettings(view: View) {
        val settingsFragment = SettingsFragment()
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, settingsFragment)
            .addToBackStack(null)
            .commit()
    }

}
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
        }
        return view
    }

}