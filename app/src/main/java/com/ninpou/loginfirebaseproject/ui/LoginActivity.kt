package com.ninpou.loginfirebaseproject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.ninpou.loginfirebaseproject.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        mAuth = FirebaseAuth.getInstance()

        mainBinding.registerNewUser.setOnClickListener {
            startActivity(Intent(this, RegisterUserActivity::class.java))
        }

        mainBinding.buttonLogin.setOnClickListener {
            userLogin()
        }
    }

    private fun userLogin() {
        val userEmail = mainBinding.emailLoginEditText.text.trim()
        val userPassword = mainBinding.passwordLoginEditText.text.trim()

        if (userEmail.isEmpty()) {
            mainBinding.emailLoginEditText.error = "Please enter email!"
            mainBinding.emailLoginEditText.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            mainBinding.emailLoginEditText.error = "Please enter valid email!"
            mainBinding.emailLoginEditText.requestFocus()
            return
        }

        if (userPassword.isEmpty()) {
            mainBinding.passwordLoginEditText.error = "Please enter password!"
            mainBinding.passwordLoginEditText.requestFocus()
            return
        }

        if (userPassword.length < 6) {
            mainBinding.passwordLoginEditText.error = "Password length is not enough!"
            mainBinding.passwordLoginEditText.requestFocus()
            return
        }

        mainBinding.loginProgressBar.visibility = View.VISIBLE

        mAuth.signInWithEmailAndPassword(userEmail.toString(), userPassword.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //redirect to user profile
                    startActivity(Intent(this, MainScreenActivity::class.java))
                    mainBinding.loginProgressBar.visibility = View.GONE
                    //check user input and add password field
                } else {
                    //check errors
                    Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show()
                }
            }
    }
}