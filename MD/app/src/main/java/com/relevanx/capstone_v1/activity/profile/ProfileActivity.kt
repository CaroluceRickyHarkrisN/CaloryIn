package com.relevanx.capstone_v1.activity.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.relevanx.capstone_v1.BottomNavigationHelper
import com.relevanx.capstone_v1.R
import com.relevanx.capstone_v1.activity.edit_profil.EditProfilActivity
import com.relevanx.capstone_v1.activity.home.HomeActivity
import com.relevanx.capstone_v1.activity.login.LoginActivity
import com.relevanx.capstone_v1.api.ProfileResponse
import com.relevanx.capstone_v1.databinding.ActivityProfileBinding
import com.relevanx.capstone_v1.stater_view.SavedPreference

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private lateinit var viewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("253869776960-cs1hdeqo3rpnpm10ibokm8fqh695l6ov.apps.googleusercontent.com")
            .requestEmail()
            .build()
        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
        profilePhotoUrl = Firebase.auth.currentUser?.photoUrl.toString()

        token = SavedPreference.getToken(this)
        uid = SavedPreference.getUid(this)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[ProfileViewModel::class.java]
        viewModel.api.observe(this){ setProfileData(it) }
        viewModel.isLoading.observe(this) { showLoading(it) }
        viewModel.getProfile(token.toString(), uid.toString())

        bottom()

        binding.editProfile.setOnClickListener {
            val intent = Intent(this, EditProfilActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setProfileData(it: ProfileResponse?) {
        binding.name.text = it?.name
        binding.email.text = it?.email
        binding.height.text = it?.height
        binding.weight.text = it?.weight
        binding.age.text = it?.birthDate

        Glide.with(this)
            .load(profilePhotoUrl)
            .into(binding.profileImage)
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }

    private fun bottom() {
        binding.fabCamera.setOnClickListener {
            BottomNavigationHelper.setupFabCameraClick2(this, binding.fabCamera, binding.fabCameraUp, binding.fabGalleryUp)
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.bottom_profile
        BottomNavigationHelper.setupBottomNavigation(this)

        binding.buttonLogout.setOnClickListener {
            Firebase.auth.signOut()
            mGoogleSignInClient.signOut()
            SavedPreference.clear(this)
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    companion object {
        private var token: String? = null
        private var uid: String? = null
        private var profilePhotoUrl: String? = null
    }
}
