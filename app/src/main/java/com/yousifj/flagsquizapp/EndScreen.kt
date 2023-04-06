package com.yousifj.flagsquizapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class EndScreen : AppCompatActivity() {
    private lateinit var text: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_screen)
        val score = intent.getIntExtra("score", 0)
        text = findViewById(R.id.score)
        text.text = "You score is: ${score.toString()}"
    }
}