package com.example.my_pc.hauhau.ui.splash

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.my_pc.hauhau.R
import com.example.my_pc.hauhau.ui.home.HomeActivity


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        splashWaiting()
    }

    private fun splashWaiting() {
        val interval = 1000 // 1 Second
        val handler = Handler()
        val runnable = Runnable {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        handler.postAtTime(runnable, System.currentTimeMillis()+interval)
        handler.postDelayed(runnable, interval.toLong())
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}