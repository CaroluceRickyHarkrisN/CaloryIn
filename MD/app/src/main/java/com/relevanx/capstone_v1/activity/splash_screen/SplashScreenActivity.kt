package com.relevanx.capstone_v1.activity.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.relevanx.capstone_v1.R
import com.relevanx.capstone_v1.activity.login.LoginActivity

class SplashScreenActivity : AppCompatActivity() {

    private val splashTimeOut: Long = 3000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            // Pindah ke aktivitas berikutnya setelah waktu yang ditentukan selesai
            val intent = Intent(this@SplashScreenActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)
    }
}