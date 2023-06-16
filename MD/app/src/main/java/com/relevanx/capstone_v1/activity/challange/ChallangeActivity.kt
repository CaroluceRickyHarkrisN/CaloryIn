package com.relevanx.capstone_v1.activity.challange

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.relevanx.capstone_v1.BottomNavigationHelper
import com.relevanx.capstone_v1.R
import com.relevanx.capstone_v1.activity.camera.CameraActivity
import com.relevanx.capstone_v1.activity.home.HomeActivity
import com.relevanx.capstone_v1.databinding.ActivityChallangeBinding

class ChallangeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChallangeBinding

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityChallangeBinding.inflate(layoutInflater)
                setContentView(binding.root)
                bottom()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun bottom() {
        binding.fabCamera.setOnClickListener {
            BottomNavigationHelper.setupFabCameraClick2(this, binding.fabCamera, binding.fabCameraUp, binding.fabGalleryUp)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.bottom_task
        BottomNavigationHelper.setupBottomNavigation(this)
    }
}