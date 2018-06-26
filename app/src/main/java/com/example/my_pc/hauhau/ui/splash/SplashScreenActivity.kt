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
        val handler = Handler()
        val runnable = Runnable {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
        handler.postAtTime(runnable, System.currentTimeMillis() + 1500)
        handler.postDelayed(runnable, 1500)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

}