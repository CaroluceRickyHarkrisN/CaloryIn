package com.relevanx.capstone_v1.activity.history.detail_history

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.relevanx.capstone_v1.R
import com.relevanx.capstone_v1.activity.history.main_history.HistoryActivity
import com.relevanx.capstone_v1.api.ApiConfig
import com.relevanx.capstone_v1.api.DetailHistoryResponse
import com.relevanx.capstone_v1.databinding.ActivityDetailHistoryBinding
import com.relevanx.capstone_v1.stater_view.SavedPreference
import ir.mahozad.android.PieChart
import okhttp3.ResponseBody

class DetailHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailHistoryBinding
    private lateinit var viewModel: DetailHistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        recordId = intent.getStringExtra(EXTRA_DATA)

        token = SavedPreference.getToken(this)
        uid = SavedPreference.getUid(this)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailHistoryViewModel::class.java]
        viewModel.api.observe(this){ setDetailHistoryData(it) }
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.getDetailHistory(token.toString(), uid.toString(), recordId.toString())

        binding.delete.setOnClickListener{
            val client = ApiConfig.getApiService()
            client.deleteHistory(token.toString(), uid.toString(), recordId.toString())
                .enqueue(object : retrofit2.Callback<ResponseBody> {
                    override fun onResponse(call: retrofit2.Call<ResponseBody>, response: retrofit2.Response<ResponseBody>) {
                        if (response.isSuccessful) {
                            Toast.makeText(this@DetailHistoryActivity, "Success", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@DetailHistoryActivity, HistoryActivity::class.java))
                        } else {
                            Toast.makeText(this@DetailHistoryActivity, "Failed", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: retrofit2.Call<ResponseBody>, t: Throwable) {
                        Toast.makeText(this@DetailHistoryActivity, "Failed server", Toast.LENGTH_SHORT).show()
                    }
                })
        }

        val pieChart = findViewById<PieChart>(R.id.pieChart)

        pieChart.apply {
            slices = listOf(
                PieChart.Slice(
                    0.2f,
                    Color.rgb(240, 85, 63),
                    Color.rgb(240, 85, 63),
                    "Protein"
                ),
                PieChart.Slice(
                    0.2f,
                    Color.rgb(1, 172, 192),
                    Color.rgb(1, 172, 192),
                    "Carbo"
                ),
                PieChart.Slice(
                    0.3f,
                    Color.rgb(62, 195, 123),
                    Color.rgb(62, 195, 123),
                    "Fat"
                ),
                PieChart.Slice(
                    0.17f,
                    Color.rgb(245, 187, 24),
                    Color.rgb(245, 187, 24),
                    "Fiber"
                ),
                PieChart.Slice(
                    0.17f,
                    Color.rgb(200, 0, 255),
                    Color.rgb(200, 0, 255),
                    "Calory"
                ),
                PieChart.Slice(
                    0.13f,
                    Color.rgb(255, 255, 255),
                    Color.rgb(255, 255, 255)
                )
            )
            startAngle = 0
            labelType = PieChart.LabelType.OUTSIDE_CIRCULAR
            labelIconsPlacement = PieChart.IconPlacement.START
            gradientType = PieChart.GradientType.RADIAL
        }

        binding.back.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setDetailHistoryData(it: DetailHistoryResponse?) {
        binding.foodName.text = it?.nameFood
        binding.foodNumber.text = it?.recordId
        binding.proteinPercentage.text = it?.protein.toString()
        binding.carboPercentage.text = it?.carbohydrate.toString()
        binding.fatPercentage.text = it?.calory.toString()
        binding.fiberPercentage.text = it?.lipid.toString()
        binding.energyPercentage.text = it?.calory.toString()

        Glide.with(this)
            .load(it?.imageURL)
            .into(binding.foodImage)
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        private var token: String? = null
        private var uid: String? = null
        private var recordId: String? = null
        const val EXTRA_DATA = "extra_data"
    }
}