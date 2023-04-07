package com.yousifj.flagsquizapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainMenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
    }
    fun startQuiz(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun openSettings(view: View) {
        val intent = Intent(this, Settings::class.java)
        startActivity(intent)
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
}