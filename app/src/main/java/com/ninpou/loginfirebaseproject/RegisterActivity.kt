package com.ninpou.loginfirebaseproject

import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ninpou.loginfirebaseproject.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = registerBinding.root
        setContentView(view)

        mAuth = FirebaseAuth.getInstance()

        registerBinding.buttonRegister.setOnClickListener {
            registerUser()
        }
    }


    private fun registerUser() {
        val userFullName = registerBinding.fullnameEditText.toString().trim()
        val userAge = registerBinding.ageEditText.toString().trim()
        val userEmail = registerBinding.emailEditText.toString().trim()
        val userPassword = registerBinding.passwordEditText.toString().trim()

        //check logic
        if (userFullName.isEmpty()) {
            registerBinding.fullnameEditText.error = "Full name is required"
            registerBinding.fullnameEditText.requestFocus()
            return
        }

        if (userAge.isEmpty()) {
            registerBinding.ageEditText.error = "Age is required"
            registerBinding.ageEditText.requestFocus()
            return
        }

        if (userEmail.isEmpty()) {
            registerBinding.emailEditText.error = "Email is required"
            registerBinding.emailEditText.requestFocus()
            return
        }

        /*if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            registerBinding.emailEditText.error = "Please provide valid email"
            registerBinding.emailEditText.requestFocus()
            return
        }*/

        if (userPassword.length < 6) {
            registerBinding.emailEditText.error = "Password need to be 6 or more letters"
            registerBinding.emailEditText.requestFocus()
        }

        if (userPassword.isEmpty()) {
            registerBinding.passwordEditText.error = "Password is required"
            registerBinding.passwordEditText.requestFocus()
            return
        }

        Toast.makeText(this, "ITS WORKING", Toast.LENGTH_SHORT).show()
    }
}