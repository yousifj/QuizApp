package com.yousifj.flagsquizapp

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.AlertDialog
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainMenuActivity : AppCompatActivity() {
    //MediaPlayer player
    private lateinit var mediaPlayer: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        mediaPlayer = MediaPlayer.create(this, R.raw.click)

    }
    /**
     * Starts Button animation and wait for animation to finish playing then start
     * a new quiz by opening the main activity when button is clicked.
     * @param view The view that triggers the function.
     * @return void
     */
    fun startQuiz(view: View) {
        mediaPlayer.start()
        val button = view as Button
        val anim = AnimatorInflater.loadAnimator(this, R.animator.button_animation) as AnimatorSet
        anim.setTarget(button)

        anim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator) {
                // animation started
            }

            override fun onAnimationEnd(animation: Animator) {
                // animation ended, start activity
                nextActivity()
            }

            override fun onAnimationCancel(animation: Animator) {
                // animation canceled
            }

            override fun onAnimationRepeat(animation: Animator) {
                // animation repeated
            }
        })

        anim.start()
    }
    /**
     * Go to the main activity when called.
     * @return void
     */
    fun nextActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    /**
     * Opens the settings activity when button is clicked.
     * @param view The view that triggers the function.
     * @return void
     */
    fun openSettings(view: View) {
        val intent = Intent(this, Settings::class.java)
        startActivity(intent)
    }
    /**
     * Shows an alert dialog when the exit button is clicked.
     * @param view The view that triggers the function.
     * @return void
     */
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