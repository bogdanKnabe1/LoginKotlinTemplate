package com.ninpou.loginfirebaseproject.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ninpou.loginfirebaseproject.databinding.ActivityLoginBinding
import com.ninpou.loginfirebaseproject.ui.MainScreenActivity
import com.ninpou.loginfirebaseproject.ui.forgotpassword.ForgotPasswordActivity
import com.ninpou.loginfirebaseproject.ui.registration.RegisterUserActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityLoginBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityLoginBinding.inflate(layoutInflater)
        val view = mainBinding.root
        setContentView(view)

        // get instance of authentication
        mAuth = FirebaseAuth.getInstance()

        //redirect registered and LOGGED user in profile
        if (FirebaseAuth.getInstance().currentUser != null) {
            startActivity(Intent(this, MainScreenActivity::class.java))
        }
        // forgot password redirect
        mainBinding.forgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
        }
        // redirect to registration
        mainBinding.registerNewUser.setOnClickListener {
            startActivity(Intent(this, RegisterUserActivity::class.java))
        }
        // make login in
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

        // user auth with email and password can be changed on any other backend query/call
        mAuth.signInWithEmailAndPassword(userEmail.toString(), userPassword.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //redirect to user profile
                    val user = FirebaseAuth.getInstance().currentUser
                    if (user?.isEmailVerified == true) {
                        startActivity(Intent(this, MainScreenActivity::class.java))
                    } else {
                        user?.sendEmailVerification()
                        Toast.makeText(this, "Email verification sent !", Toast.LENGTH_SHORT).show()
                    }
                    mainBinding.loginProgressBar.visibility = View.GONE
                    //check user input and add password field
                } else {
                    //check errors
                    Toast.makeText(this, "Login failed!", Toast.LENGTH_SHORT).show()
                    mainBinding.loginProgressBar.visibility = View.GONE
                }
            }
    }
}