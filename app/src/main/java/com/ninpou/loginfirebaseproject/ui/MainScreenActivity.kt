package com.ninpou.loginfirebaseproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.ninpou.loginfirebaseproject.R
import com.ninpou.loginfirebaseproject.databinding.ActivityMainBinding
import com.ninpou.loginfirebaseproject.databinding.ActivityMainScreenBinding

class MainScreenActivity : AppCompatActivity() {
    private lateinit var mainScreenBinding: ActivityMainScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainScreenBinding = ActivityMainScreenBinding.inflate(layoutInflater)
        val view = mainScreenBinding.root
        setContentView(view)

        mainScreenBinding.logoutButton.setOnClickListener {
            // add dialog to ask user is he really want to sign out.
            mainScreenBinding.mainScreenProgressBar.visibility = View.VISIBLE
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }

    override fun onStop() {
        super.onStop()
        mainScreenBinding.mainScreenProgressBar.visibility = View.GONE
    }
}