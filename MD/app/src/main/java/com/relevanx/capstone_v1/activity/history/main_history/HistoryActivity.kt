package com.relevanx.capstone_v1.activity.history.main_history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.relevanx.capstone_v1.BottomNavigationHelper
import com.relevanx.capstone_v1.R
import com.relevanx.capstone_v1.activity.camera.CameraActivity
import com.relevanx.capstone_v1.activity.home.HomeActivity
import com.relevanx.capstone_v1.api.HistoryResponse
import com.relevanx.capstone_v1.databinding.ActivityHistoryBinding
import com.relevanx.capstone_v1.stater_view.SavedPreference

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = SavedPreference.getToken(this)
        uid = SavedPreference.getUid(this)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HistoryViewModel::class.java]
        viewModel.api.observe(this){ setHistoryData(it) }
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.getHistory(token.toString(), uid.toString(), 1, 100)

        bottom()
    }

    override fun onBackPressed() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun setHistoryData(history: HistoryResponse) {
        val historyList = history.historyResponse ?: emptyList()
        binding.viewRec.layoutManager = LinearLayoutManager(this)
        val historyAdapter = HistoryAdapter(historyList.filterNotNull())
        binding.viewRec.adapter = historyAdapter
    }


    private fun bottom() {
        binding.fabCamera.setOnClickListener {
            BottomNavigationHelper.setupFabCameraClick2(this, binding.fabCamera, binding.fabCameraUp, binding.fabGalleryUp)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.bottom_history
        BottomNavigationHelper.setupBottomNavigation(this)
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        private var token: String? = null
        private var uid: String? = null
    }
}