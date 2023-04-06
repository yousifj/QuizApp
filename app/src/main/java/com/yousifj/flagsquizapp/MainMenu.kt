package com.yousifj.flagsquizapp

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

private lateinit var button: Button


class MainMenu : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)

    }
    fun showAlertDialog(view: View) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit Application")
        builder.setMessage("Are you sure you want to exit?")
        builder.setPositiveButton("Yes") { _, _ ->
            // Show the button when the user clicks "Yes"
            //button = findViewById(R.id.return_button)
            //button.visibility = View.VISIBLE
            //exit if yes
            finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            // Does nothing for no
            dialog.cancel()
        }
        builder.create().show()
    }
    fun startQuiz(view: View){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}