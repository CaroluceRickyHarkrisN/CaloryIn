package com.relevanx.capstone_v1.activity.camera

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.relevanx.capstone_v1.databinding.ActivityCameraBinding
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.provider.OpenableColumns
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.relevanx.capstone_v1.BottomNavigationHelper
import com.relevanx.capstone_v1.R
import com.relevanx.capstone_v1.activity.history.main_history.HistoryActivity
import com.relevanx.capstone_v1.activity.home.HomeActivity
import com.relevanx.capstone_v1.api.ApiConfig
import com.relevanx.capstone_v1.api.UploadBody
import com.relevanx.capstone_v1.api.UploadResponse
import com.relevanx.capstone_v1.api.UsdaResponse
import com.relevanx.capstone_v1.ml.FcModel
import com.relevanx.capstone_v1.stater_view.SavedPreference
import ir.mahozad.android.PieChart
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.common.ops.NormalizeOp
import org.tensorflow.lite.support.image.ImageProcessor
import org.tensorflow.lite.support.image.TensorImage
import org.tensorflow.lite.support.image.ops.ResizeOp
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@Suppress("DEPRECATION")
class CameraActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCameraBinding
    private lateinit var bitmap: Bitmap
    private val storageRef = Firebase.storage.reference
    private var selectedImageUri: Uri? = null
    private lateinit var viewModel: FoodSearchViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[FoodSearchViewModel::class.java]
        viewModel.api.observe(this) { api ->
            setNutri(api)
        }

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        token = SavedPreference.getToken(this)
        uid = SavedPreference.getUid(this)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bottom()

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }


        val extras = intent.extras
        val code = extras?.getString(EXTRA_CODE)
        if (code == "camera") {
            startTakePhoto()
        }
        if (code == "gallery") {
            startGallery()
        }
        binding.cameraButton.setOnClickListener { startTakePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.uploadButton.setOnClickListener { uploadImage() }
    }

    private fun setNutri(api: UsdaResponse?) {

        val energi = "${api?.foods?.get(1)?.foodNutrients?.get(3)?.nutrientNumber}"
        binding.energyPercentage.text = energi

        val protein = "${api?.foods?.get(1)?.foodNutrients?.get(0)?.nutrientNumber}"
        binding.proteinPercentage.text = protein

        val lemak = "${api?.foods?.get(1)?.foodNutrients?.get(1)?.nutrientNumber}"
        binding.fatPercentage.text = lemak

        val karbohidrat = "${api?.foods?.get(1)?.foodNutrients?.get(2)?.nutrientNumber}"
        binding.carboPercentage.text = karbohidrat

        val serat = "${api?.foods?.get(1)?.foodNutrients?.get(5)?.nutrientNumber}"
        binding.fiberPercentage.text = serat

//        val energiPemenuhan = (energi.toInt() / 2650) * 100
//        val proteinPemenuhan = (protein.toInt()  / 65) * 100
//        val lemakPemenuhan = (lemak.toInt()  / 75) * 100
//        val karboPemenuhan = (karbohidrat.toInt()  / 430) * 100
//        val seratPemenuhan = (serat.toInt()  / 37) * 100

//        val energiChart = energiPemenuhan.toFloat() / 500
//        val proteinChart = proteinPemenuhan.toFloat() / 500
//        val lemakChart = lemakPemenuhan.toFloat() / 500
//        val karboChart = karboPemenuhan.toFloat() / 500
//        val seratChart = seratPemenuhan.toFloat() / 500

//        Toast.makeText(this, "Energi: $energiPemenuhan%", Toast.LENGTH_SHORT).show()
//
//        val pieChart = findViewById<PieChart>(R.id.pieChart)
//
//        pieChart.apply {
//            slices = listOf(
//                PieChart.Slice(
//                    proteinChart,
//                    Color.rgb(240, 85, 63),
//                    Color.rgb(240, 85, 63),
//                    "Protein"
//                ),
//                PieChart.Slice(
//                    karboChart,
//                    Color.rgb(1, 172, 192),
//                    Color.rgb(1, 172, 192),
//                    "Carbo"
//                ),
//                PieChart.Slice(
//                    lemakChart,
//                    Color.rgb(62, 195, 123),
//                    Color.rgb(62, 195, 123),
//                    "Fat"
//                ),
//                PieChart.Slice(
//                    seratChart,
//                    Color.rgb(245, 187, 24),
//                    Color.rgb(245, 187, 24),
//                    "Fiber"
//                ),
//                PieChart.Slice(
//                    energiChart,
//                    Color.rgb(200, 0, 255),
//                    Color.rgb(200, 0, 255),
//                    "Energy"
//                ),
//                PieChart.Slice(
//                    0.1f,
//                    Color.rgb(255, 255, 255),
//                    Color.rgb(255, 255, 255)
//                )
//            )
//            startAngle = 0
//            labelType = PieChart.LabelType.OUTSIDE_CIRCULAR
//            labelIconsPlacement = PieChart.IconPlacement.START
//            gradientType = PieChart.GradientType.RADIAL
//
//        }

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun uploadImage() {
        showLoading(true)
        selectedImageUri?.let { uri ->
            val fileName = getFileName(applicationContext, uri)
            val uploadTask = storageRef.child("file/$fileName").putFile(uri)
            uploadTask.addOnSuccessListener { uploadTaskSnapshot ->
                uploadTaskSnapshot.storage.downloadUrl.addOnSuccessListener { downloadUri ->
                    imageUrl = downloadUri.toString()
                    Log.e("Firebase", "Image URL: $imageUrl")
                    uploadApi(imageUrl)
                }.addOnFailureListener {
                    Log.e("Firebase", "Failed to get image URL")
                    Toast.makeText(this, "Upload Failed URL", Toast.LENGTH_SHORT).show()
                    showLoading(false)
                }
            }.addOnFailureListener {
                Log.e("Firebase", "Image Upload failed")
                Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show()
                showLoading(false)
            }
        }
    }

    private fun uploadApi(imageUrl: String){
        val client = ApiConfig.getApiService()
        val currentDate = getCurrentDate()
        client.uploadImage(token.toString(), uid.toString(), UploadBody(imageUrl, currentDate, name)).enqueue(object :
            Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    Toast.makeText(this@CameraActivity, "Upload Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@CameraActivity, HistoryActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)
                } else {
                    showLoading(false)
                    Toast.makeText(this@CameraActivity, "Failed $response", Toast.LENGTH_SHORT).show()
                    Log.d("Failed", response.toString())
                }
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                showLoading(false)
                Toast.makeText(this@CameraActivity, "Upload Failed $t", Toast.LENGTH_SHORT).show()
                Log.d("Upload Failed", t.toString())
            }
        })
    }

    private fun bottom() {
        binding.fabCamera.setOnClickListener {
            BottomNavigationHelper.setupFabCameraClick2(this, binding.fabCamera, binding.fabCameraUp, binding.fabGalleryUp)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.placeholder
        BottomNavigationHelper.setupBottomNavigation(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }


    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePicture.launch(intent)
    }

    private fun startGallery() {
        getContent.launch(Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "image/*"
        })
    }

    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val data = result.data
            bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, data?.data)
            if (data != null) {
                selectedImageUri = data.data
                binding.foodImage.setImageURI(selectedImageUri)
                predict()
            }
        }
    }

    private val takePicture = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.extras?.get("data")?.let {
                bitmap = it as Bitmap
                binding.foodImage.setImageBitmap(bitmap)
                predict()
            }
        }
    }

    private fun predict() {
        val imageProcessor = ImageProcessor.Builder()
            .add(ResizeOp(224, 224, ResizeOp.ResizeMethod.BILINEAR))
            .add(NormalizeOp(0f, 255f))
            .build()

        val labels =  application.assets.open("labels.txt").bufferedReader().readLines()

        val model = FcModel.newInstance(this)

        val tensorImage = TensorImage(DataType.FLOAT32)
        tensorImage.load(bitmap)

        val processedImage = imageProcessor.process(tensorImage)

        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, 224, 224, 3), DataType.FLOAT32)
        inputFeature0.loadBuffer(processedImage.buffer)

        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer
        var maxIdx = 0
        outputFeature0.floatArray.forEachIndexed { idx, fl ->
            if (fl > outputFeature0.floatArray[maxIdx]) {
                maxIdx = idx
            }
        }
        name = labels[maxIdx]
        binding.foodName.text = name
        viewModel.searchFoods(name!!)
    }

    @SuppressLint("Range")
    private fun getFileName(context: Context, uri: Uri): String? {
        if (uri.scheme == "content") {
            val cursor = context.contentResolver.query(uri, null, null, null, null)
            cursor.use {
                if (cursor != null) {
                    if(cursor.moveToFirst()) {
                        return cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME))
                    }
                }
            }
        }
        return uri.path?.lastIndexOf('/')?.let { uri.path?.substring(it) }
    }

    private fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val currentDate = Date()
        return dateFormat.format(currentDate)
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        const val EXTRA_CODE = "extra_code"
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
        var name:String? = null
        lateinit var imageUrl: String
        private var token: String? = null
        private var uid: String? = null
    }

}