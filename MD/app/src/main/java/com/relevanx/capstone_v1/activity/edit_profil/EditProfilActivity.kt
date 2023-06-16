package com.relevanx.capstone_v1.activity.edit_profil

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import com.relevanx.capstone_v1.R
import com.relevanx.capstone_v1.activity.camera.CameraActivity
import com.relevanx.capstone_v1.activity.camera.CameraActivity.Companion.name
import com.relevanx.capstone_v1.activity.home.RegisterDataActivity
import com.relevanx.capstone_v1.activity.profile.ProfileActivity
import com.relevanx.capstone_v1.api.ApiConfig
import com.relevanx.capstone_v1.api.UpdateUserResponse
import com.relevanx.capstone_v1.databinding.ActivityEditProfilBinding
import com.relevanx.capstone_v1.databinding.ActivityProfileBinding
import com.relevanx.capstone_v1.databinding.ActivityRegisterDataBinding
import com.relevanx.capstone_v1.stater_view.SavedPreference
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class EditProfilActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfilBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfilBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.close.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()
        }

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

        binding.submitButton.setOnClickListener{
            val name = binding.edRegisterName.text.toString()
            val email = binding.edRegisterEmail.text.toString()
            val birth = binding.edRegisterBirth.text.toString()
            val radioButtonId = binding.radioGroup.checkedRadioButtonId
            val radioButton = findViewById<RadioButton>(radioButtonId)
            var gender = ""
            if (radioButtonId != -1) {
                gender = radioButton.text.toString()
            } else {
                Toast.makeText(this@EditProfilActivity, "Data must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val height = binding.edRegisterHeight.text.toString()
            val weight = binding.edRegisterWeight.text.toString()
            val token = SavedPreference.getToken(this)
            val uid = SavedPreference.getUid(this)



            if (name.isEmpty() || email.isEmpty() || birth.isEmpty() || height.isEmpty() || weight.isEmpty()){
                Toast.makeText(this, "Data must be filled", Toast.LENGTH_SHORT).show()
            } else {
                val client = ApiConfig.getApiService()
                client.updateUser(token.toString(), uid.toString(), UpdateUserResponse(uid.toString(), gender, name, weight, birth, email, height))
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                            if (response.isSuccessful) {
                                Toast.makeText(this@EditProfilActivity, "Success", Toast.LENGTH_SHORT).show()
                                Log.d("HistoryViewModel", "onResponse: ${response.code()}")
                                startActivity(Intent(this@EditProfilActivity, ProfileActivity::class.java))
                            } else {
                                Toast.makeText(this@EditProfilActivity, "Failed, ${response.code()}", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(this@EditProfilActivity, "onFailure: ${t.message}", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // No need to explicitly start the ProfileActivity as it will be resumed from the background
    }

}