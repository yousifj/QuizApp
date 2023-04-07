package com.yousifj.flagsquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class EndScreen : AppCompatActivity() {
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_screen)
        //retrieve score and show it
        val score = intent.getIntExtra("score", 0)
        text = findViewById(R.id.score)
        text.text = getString(R.string.score_text, score)
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