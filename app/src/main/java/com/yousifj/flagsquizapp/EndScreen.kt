package com.yousifj.flagsquizapp

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import nl.dionsegijn.konfetti.KonfettiView
import nl.dionsegijn.konfetti.models.Shape
import nl.dionsegijn.konfetti.models.Size

class EndScreen : AppCompatActivity() {
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_screen)
        //retrieve score and show it
        val score = intent.getIntExtra("score", 0)
        text = findViewById(R.id.score)
        text.text = getString(R.string.score_text, score)

        val konfettiView = findViewById<KonfettiView>(R.id.konfetti_view)

        // Configure the confetti
        konfettiView.build()
            .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
            .setDirection(260.0, 359.0)
            .setSpeed(1f, 5f)
            .setFadeOutEnabled(true)
            .setTimeToLive(3000L)
            .addShapes(Shape.Square, Shape.Circle)
            .addSizes(Size(12))
            .setPosition(konfettiView.width / 2f, 0f)
            .streamFor(300, 10000L)
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