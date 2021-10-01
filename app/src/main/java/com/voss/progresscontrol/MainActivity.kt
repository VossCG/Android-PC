package com.voss.progresscontrol

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName
    private var currentProgress: Int = 0
    private var maxProgress = 100
    private var repeatTaskTime: Long = 100
    private var taskHandler = Handler()
    private var runnable = object : Runnable {
        override fun run() {
            startIncreaseProgress()
        }
    }
    private var runnableDecrease = object :Runnable{
        override fun run() {
            backProgressView()
        }
    }

    private fun startIncreaseProgress() {
        if (currentProgress >= maxProgress) {
            pauseIncreasingProgress()
        } else {
            increaseProgress(currentProgress)
        }
        Log.d(TAG, "Start Progress, progress:$currentProgress")
        taskHandler.postDelayed(runnable, repeatTaskTime)
    }

    private fun increaseProgress(progress: Int) {
        Log.d(TAG, "Increase Progress, progress:$currentProgress")
        currentProgress += 1
        progressBar.setProgress(currentProgress)
        setProgressTextView()

    }
    private fun backProgressView() {
        if (currentProgress < 1) {
            pauseIncreasingProgress()
        } else {
            decreaseProgress(currentProgress)
        }
        Log.d(TAG, "Start Progress, progress:$currentProgress")
        taskHandler.postDelayed(runnableDecrease, repeatTaskTime)
    }

    private fun decreaseProgress(progress:Int) {
        Log.d(TAG, "Decrease Progress, progress:$currentProgress")
        currentProgress -= 1
        progressBar.setProgress(currentProgress)
        setProgressTextView()

    }

    private fun pauseIncreasingProgress() {
        Log.d(TAG, "Pause Progress, progress:$currentProgress")
        taskHandler.removeCallbacksAndMessages(null)
    }

    private fun resetProgress() {
        Log.d(TAG, "Reset Progress, progress:$currentProgress")
        pauseIncreasingProgress()

        currentProgress = 0
        progressBar.setProgress(currentProgress)

        setProgressTextView()
    }

    private fun setProgressTextView() {
        progressView_Text.setText("$currentProgress%")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupView()
    }

    private fun setupView() {
        setProgress()
        setProgressTextView()
        start_but.setOnClickListener {
            pauseIncreasingProgress()
            startIncreaseProgress() }
        pasue_but.setOnClickListener { pauseIncreasingProgress() }
        restart_but.setOnClickListener { resetProgress() }
        decrease_but.setOnClickListener {
            pauseIncreasingProgress()
            backProgressView()}
    }

    private fun setProgress() {
        progressBar.max = 100
        progressBar.progress = 0
    }


}

