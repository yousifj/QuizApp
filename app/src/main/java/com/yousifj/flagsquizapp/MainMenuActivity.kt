package com.yousifj.flagsquizapp

import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlin.math.abs
import kotlin.math.log

class MainMenuActivity : AppCompatActivity() , SensorEventListener{
    //MediaPlayer player
    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private var lastUpdate = 0L
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f
    private val shakeThreshold = 100
    private var shaken = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_menu)
        mediaPlayer = MediaPlayer.create(this, R.raw.click)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

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
    /**
     * Resumes the activity and registers the listener for the accelerometer sensor.
     * @return void
     */
    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
    }
    /**
     * Pauses the activity and unregisters the listener for the accelerometer sensor.
     * @return void
     */
    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }
    /**
     * Called when the accelerometer sensor detects a change in acceleration.
     * If the speed of the change in acceleration is greater than a threshold value,
     * then a shake is detected.
     * @param event The sensor event that triggers the function.
     * @return void
     */
    override fun onSensorChanged(event: SensorEvent?) {
        val curTime = System.currentTimeMillis()
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]
            if (curTime - lastUpdate > 100) {
                val diffTime = curTime - lastUpdate
                lastUpdate = curTime
                val speed = abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000
                if (speed > shakeThreshold) {
                    onShakeDetected()
                }
                lastX = x
                lastY = y
                lastZ = z
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
    /**
     * Executes the code when a shake event is detected.
     * Sets the 'shaken' variable to true and calls 'nextActivity' function.
     * @return void
     */
    private fun onShakeDetected() {
        // Your code to execute when a shake event is detected
        if(!shaken){
            shaken= true
            nextActivity()
        }
    }

}
