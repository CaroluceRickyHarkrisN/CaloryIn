package com.relevanx.capstone_v1.activity.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.relevanx.capstone_v1.activity.home.HomeActivity
import com.relevanx.capstone_v1.activity.register.RegisterActivity
import com.relevanx.capstone_v1.databinding.ActivityMainBinding
import com.relevanx.capstone_v1.stater_view.SavedPreference
import com.relevanx.capstone_v1.stater_view.UserPreference

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val reqCode:Int=123
    private var firebaseAuth= FirebaseAuth.getInstance()
    private lateinit var userPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.registerButton.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

        userPreference = UserPreference(this)

        auth = FirebaseAuth.getInstance()
        binding.loginButton.setOnClickListener {
            val email = binding.edLoginEmail.text.toString()
            val password = binding.edLoginPassword.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email and password must be filled", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else{
            binding.loginButton.isEnabled
                showLoading(true)
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                                if (tokenTask.isSuccessful) {
                                    val idToken: String? = tokenTask.result?.token
                                    val uid = user.uid
                                    if (idToken != null) {
                                        SavedPreference.setToken(this,idToken)
                                        SavedPreference.setUid(this,uid)
                                        Log.d("Tokken", "Token : $idToken")
                                        login()
                                    }
                                } else {
                                    Log.d("Tokken", "Failed to retrieve token")
                                }
                            }
                            showLoading(false)
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            startActivity(intent)
                        } else {
                            showLoading(false)
                            Toast.makeText(this, "Login Failed", Toast.LENGTH_SHORT).show()
                            Log.w("signInWithEmail:failure", task.exception)
                        }
                    }
            }
        }


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("253869776960-cs1hdeqo3rpnpm10ibokm8fqh695l6ov.apps.googleusercontent.com")
            .requestEmail()
            .build()

        mGoogleSignInClient= GoogleSignIn.getClient(this,gso)
        firebaseAuth= FirebaseAuth.getInstance()
        binding.Signin.setOnClickListener {
            signInGoogle()
        }

    }


    private  fun signInGoogle(){
        val signInIntent:Intent=mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,reqCode)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==reqCode){
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleResult(task)
        }
    }

    private fun handleResult(completedTask: Task<GoogleSignInAccount>){
        try {
            val account: GoogleSignInAccount? =completedTask.getResult(ApiException::class.java)
            if (account != null) {
                login(account)
            }
        } catch (e:ApiException){
            Toast.makeText(this,e.toString(),Toast.LENGTH_SHORT).show()
            Log.d("Failed",e.toString())
        }
    }

    private fun login(account: GoogleSignInAccount){
        showLoading(true)
        val credential= GoogleAuthProvider.getCredential(account.idToken,null)
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener {task->
            if(task.isSuccessful) {
                SavedPreference.setEmail(this,account.email.toString())
                SavedPreference.setUsername(this,account.displayName.toString())
                val intent = Intent(this, LoginActivity::class.java)
                val user = auth.currentUser
                user?.getIdToken(true)?.addOnCompleteListener { tokenTask ->
                    if (tokenTask.isSuccessful) {
                        val idToken: String? = tokenTask.result?.token
                        val uid = user.uid
                        if (idToken != null) {
                            SavedPreference.setToken(this,idToken)
                            SavedPreference.setUid(this,uid)
                            Log.d("Tokken", "Token : $idToken")
                            Log.d("Uid", "Uid : $uid")
                            login()
                        }
                    } else {
                        Log.d("TokkenDD", "Failed to retrieve token")
                    }
                }
                showLoading(false)
                startActivity(intent)
                finish()
            }else{
                showLoading(false)
                Toast.makeText(this,"Failed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login() {
        if(GoogleSignIn.getLastSignedInAccount(this)!=null){
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }
}