package com.example.mybooks.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.mybooks.R

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Handler().postDelayed({
            startActivity(
                Intent(this@SplashActivity, HuidigBooksActivity::class.java)
            )
            finish()
        }, 1000)
    }
}
