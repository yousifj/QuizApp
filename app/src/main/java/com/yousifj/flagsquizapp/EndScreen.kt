package com.yousifj.flagsquizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class EndScreen : AppCompatActivity() {
    private lateinit var text: TextView
    private var wavy = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_end_screen)
        wavy = intent.getBooleanExtra("wavy", false)
        val score = intent.getIntExtra("score", 0)
        text = findViewById(R.id.score)
        text.text = "You score is: ${score.toString()}"
    }
    fun mainMenu(view: View){
        val intent = Intent(this, MainMenu::class.java)
        intent.putExtra("wavy", wavy)
        startActivity(intent)
    }
}