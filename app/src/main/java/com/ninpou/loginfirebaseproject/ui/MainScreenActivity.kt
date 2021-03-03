package com.ninpou.loginfirebaseproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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


    }
}