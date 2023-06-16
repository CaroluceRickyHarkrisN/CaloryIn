package com.relevanx.capstone_v1

import android.content.Intent
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.relevanx.capstone_v1.activity.camera.CameraActivity
import com.relevanx.capstone_v1.activity.challange.ChallangeActivity
import com.relevanx.capstone_v1.activity.history.main_history.HistoryActivity
import com.relevanx.capstone_v1.activity.home.HomeActivity
import com.relevanx.capstone_v1.activity.profile.ProfileActivity

class BottomNavigationHelper {
    companion object {
        fun setupBottomNavigation(activity: AppCompatActivity) {

            val bottomNavigationView = activity.findViewById<BottomNavigationView>(R.id.bottomNavigationView)
            bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.bottom_home -> {
                        // Handle Home menu item click
                        if (activity !is HomeActivity) {
                            activity.startActivity(Intent(activity, HomeActivity::class.java))
                            activity.finish()
                        }
                        true
                    }
                    R.id.bottom_history -> {
                        // Handle History menu item click
                        if (activity !is HistoryActivity) {
                            activity.startActivity(Intent(activity, HistoryActivity::class.java))
                            activity.finish()
                        }
                        true
                    }
                    R.id.bottom_task -> {
                        // Handle Task menu item click
                        if (activity !is ChallangeActivity) {
                            activity.startActivity(Intent(activity, ChallangeActivity::class.java))
                            activity.finish()
                        }
                        true
                    }
                    R.id.bottom_profile -> {
                        if (activity !is ProfileActivity) {
                            activity.startActivity(Intent(activity, ProfileActivity::class.java))
                            activity.finish()
                        }
                        true
                    }
                    else -> false
                }
            }
        }


        fun setupFabCameraClick(activity: AppCompatActivity, fabCamera: FloatingActionButton) {
            val popupMenu = PopupMenu(activity, fabCamera)
            popupMenu.inflate(R.menu.camera_menu)

            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.camera -> {
                        if (activity !is CameraActivity) {
                            val intent = Intent(activity, CameraActivity::class.java)
                            intent.putExtra(CameraActivity.EXTRA_CODE, "camera")
                            activity.startActivity(intent)
                            activity.finish()
                        }

                        true
                    }
                    R.id.gallery -> {

                        if (activity !is CameraActivity) {
                            val intent = Intent(activity, CameraActivity::class.java)
                            intent.putExtra(CameraActivity.EXTRA_CODE, "gallery")
                            activity.startActivity(intent)
                            activity.finish()
                        }


                        true
                    }
                    else -> false
                }
            }

            popupMenu.show()
        }

        fun setupFabCameraClick2(activity: AppCompatActivity, fabCamera: FloatingActionButton, fabCameraUp: FloatingActionButton, fabGalleryUp: FloatingActionButton) {
            val rotateOpen: Animation =
                AnimationUtils.loadAnimation(activity, R.anim.rotate_open_anim)
            val rotateClose: Animation =
                AnimationUtils.loadAnimation(activity, R.anim.rotate_close_anim)
            val fromBottom: Animation =
                AnimationUtils.loadAnimation(activity, R.anim.from_bottom_anim)
            val toBottom: Animation = AnimationUtils.loadAnimation(activity, R.anim.to_bottom_anim)

            var clicked = false

            fun setClickable(clicked: Boolean) {
                if (!clicked) {
                    fabCameraUp.isClickable = true
                    fabGalleryUp.isClickable = true
                } else {
                    fabCameraUp.isClickable = false
                    fabGalleryUp.isClickable = false
                }
            }
            fun setVisibility(clicked: Boolean) {
                if (!clicked) {
                    fabCameraUp.visibility = FloatingActionButton.VISIBLE
                    fabGalleryUp.visibility = FloatingActionButton.VISIBLE
                } else {
                    fabCameraUp.visibility = FloatingActionButton.INVISIBLE
                    fabGalleryUp.visibility = FloatingActionButton.INVISIBLE
                }
            }

            fun setAnimation(clicked: Boolean) {
                if (!clicked) {
                    fabCameraUp.startAnimation(fromBottom)
                    fabGalleryUp.startAnimation(fromBottom)
                    fabCamera.startAnimation(rotateOpen)
                } else {
                    fabCameraUp.startAnimation(toBottom)
                    fabGalleryUp.startAnimation(toBottom)
                    fabCamera.startAnimation(rotateClose)
                }
            }

            fun fabCameraClick() {
                setVisibility(clicked)
                setAnimation(clicked)
                setClickable(clicked)
                clicked = !clicked
            }

            fabCamera.setOnClickListener {
                fabCameraClick()
            }
            fabCameraUp.setOnClickListener {
                val intent = Intent(activity, CameraActivity::class.java)
                intent.putExtra(CameraActivity.EXTRA_CODE, "camera")
                activity.startActivity(intent)
                activity.finish()
            }
            fabGalleryUp.setOnClickListener {
                val intent = Intent(activity, CameraActivity::class.java)
                intent.putExtra(CameraActivity.EXTRA_CODE, "gallery")
                activity.startActivity(intent)
                activity.finish()
            }
        }

    }
}
