package com.relevanx.capstone_v1.activity.home

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.relevanx.capstone_v1.BottomNavigationHelper
import com.relevanx.capstone_v1.R
import com.relevanx.capstone_v1.api.HomeResponse
import com.relevanx.capstone_v1.databinding.ActivityHomeBinding
import com.relevanx.capstone_v1.stater_view.SavedPreference
import ir.mahozad.android.PieChart
import java.text.DecimalFormat

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = SavedPreference.getToken(this)
        uid = SavedPreference.getUid(this)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[HomeViewModel::class.java]
        viewModel.aapi.observe(this){ profile ->
            if (profile != null) {
                if (profile){
                    startActivity(Intent(this, RegisterDataActivity::class.java))
                }else{
                    viewModel.api.observe(this){ setHomeData(it) }
                    viewModel.getHome(token.toString(), uid.toString())
                }
            }
        }
        viewModel.getProfile(token.toString(), uid.toString())
        bottom()
    }

    private fun setHomeData(it: HomeResponse?) {
        val pieChart = findViewById<PieChart>(R.id.pieChart)

        val decimalFormat = DecimalFormat("0")

        protein = it?.proteinPerc?.toFloat()
        carbohydrate = it?.carbohydratePerc?.toFloat()
        fat = it?.fatPerc?.toFloat()
        fiber = it?.fiberPerc?.toFloat()
        calory = it?.caloryPerc?.toFloat()

        val carboo = decimalFormat.format(it?.carbohydratePerc ?: 0f)
        val carboText = "$carboo%"
        binding.nutriPercentageCarbo.text = carboText

        val prooo = decimalFormat.format(it?.proteinPerc ?: 0f)
        val proText = "$prooo%"
        binding.nutriPercentageProtein.text = proText

        val fato = decimalFormat.format(it?.fatPerc ?: 0f)
        val fatText = "$fato%"
        binding.nutriPercentageFat.text = fatText

        val fiboo = decimalFormat.format(it?.fiberPerc ?: 0f)
        val fibText = "$fiboo%"
        binding.nutriPercentageFiber.text = fibText

        val caloo = decimalFormat.format(it?.caloryPerc ?: 0f)
        val calText = "$caloo%"
        binding.nutriPercentageEnergy.text = calText

        val prozz = prooo.toFloat()/ 500
        val carzz = carboo.toFloat()/ 500
        val fatzz = fato.toFloat()/ 500
        val fibzz = fiboo.toFloat()/ 500
        val calzz = caloo.toFloat()/ 500

        val allo = (prooo.toInt() + carboo.toInt() + fato.toInt() + fiboo.toInt() + caloo.toInt()) / 5
        val homePercen = "$allo%"
        binding.percentage.text = homePercen

        total = prozz + carzz + fatzz + fibzz + calzz

        pieChart.apply {
            slices = listOf(
                PieChart.Slice(
                    prozz,
                    Color.rgb(240, 85, 63),
                    Color.rgb(240, 85, 63),
                    "Protein"
                ),
                PieChart.Slice(
                    carzz,
                    Color.rgb(1, 172, 192),
                    Color.rgb(1, 172, 192),
                    "Carbo"
                ),
                PieChart.Slice(
                    fatzz,
                    Color.rgb(62, 195, 123),
                    Color.rgb(62, 195, 123),
                    "Fat"
                ),
                PieChart.Slice(
                    fibzz,
                    Color.rgb(245, 187, 24),
                    Color.rgb(245, 187, 24),
                    "Fiber"
                ),
                PieChart.Slice(
                    calzz,
                    Color.rgb(200, 0, 255),
                    Color.rgb(200, 0, 255),
                    "Energy"
                ),
                    PieChart.Slice(
                        1 - total!!,
                        Color.rgb(235, 235, 224),
                        Color.rgb(235, 235, 224),
                        ""
                    )
            )
                startAngle = 0
                labelType = PieChart.LabelType.OUTSIDE_CIRCULAR
                labelIconsPlacement = PieChart.IconPlacement.START
                gradientType = PieChart.GradientType.RADIAL
        }
    }

    private fun bottom() {
        binding.fabCamera.setOnClickListener {
            BottomNavigationHelper.setupFabCameraClick2(this, binding.fabCamera, binding.fabCameraUp, binding.fabGalleryUp)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.bottom_home
        BottomNavigationHelper.setupBottomNavigation(this)
    }

    private var backPressedOnce = false

    override fun onBackPressed() {
        if (backPressedOnce) {
            super.onBackPressed()
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        } else {
            backPressedOnce = true
            Toast.makeText(this, "Tekan tombol Back sekali lagi untuk keluar", Toast.LENGTH_SHORT).show()

            // Mengatur timer untuk mengatur ulang backPressedOnce setelah beberapa detik
            Handler().postDelayed({
                backPressedOnce = false
            }, 2000) // Waktu dalam milidetik sebelum backPressedOnce diatur ulang
        }

    }


    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        private var token: String? = null
        private var uid: String? = null
        private var protein: Float? = null
        private var carbohydrate: Float? = null
        private var fat: Float? = null
        private var fiber: Float? = null
        private var calory: Float? = null
        private var total: Float? = 1f
    }
}
