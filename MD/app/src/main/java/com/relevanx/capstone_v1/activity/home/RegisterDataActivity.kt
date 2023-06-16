package com.relevanx.capstone_v1.activity.home

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.relevanx.capstone_v1.R
import com.relevanx.capstone_v1.api.ApiConfig
import com.relevanx.capstone_v1.api.RegisBody
import com.relevanx.capstone_v1.databinding.ActivityRegisterDataBinding
import com.relevanx.capstone_v1.stater_view.SavedPreference
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class RegisterDataActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterDataBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        token = SavedPreference.getToken(this)
        uid = SavedPreference.getUid(this)

        email = Firebase.auth.currentUser?.email.toString()
        name = Firebase.auth.currentUser?.displayName.toString()

        binding.edRegisterBirth.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                binding.edRegisterBirth.setText(formattedDate)
            }, year, month, day)

            datePickerDialog.show()
        }

        binding.submitButton.setOnClickListener {
            birthDate = binding.edRegisterBirth.text.toString()
            val radioButtonId = binding.radioGroup.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(radioButtonId)
            if (radioButtonId != -1) {
                gender = radioButton.text.toString()
            } else {
                Toast.makeText(this, "Data must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            height = binding.edRegisterHeight.text.toString()
            weight = binding.edRegisterWeight.text.toString()


            if (birthDate!!.isEmpty() || height!!.isEmpty() || weight!!.isEmpty()) {
                Toast.makeText(this, "Data must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                uploadUser(birthDate!!, gender!!, height!!, weight!!)
            }
        }

        binding.close.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun uploadUser(birthDate: String, gender: String, height: String, weight: String) {
        val client = ApiConfig.getApiService()
        client.register(token.toString(),RegisBody(uid, gender, name, weight, birthDate, email, height))
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@RegisterDataActivity, "Success", Toast.LENGTH_SHORT).show()
                        Log.d("HistoryViewModel", "onResponse: ${response.code()}")
                        startActivity(Intent(this@RegisterDataActivity, HomeActivity::class.java))
                    } else {
                        Toast.makeText(this@RegisterDataActivity, "Failed", Toast.LENGTH_SHORT).show()
                        Log.d("HistoryViewModel", "onResponse: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@RegisterDataActivity, "Failed server", Toast.LENGTH_SHORT).show()
                }
            })
    }

    companion object {
        private var token: String? = null
        private var uid: String? = null
        private var email: String? = null
        private var name: String? = null
        private var birthDate: String? = null
        private var gender: String? = null
        private var height: String? = null
        private var weight: String? = null
        private var selectedText: String? = null
    }
}