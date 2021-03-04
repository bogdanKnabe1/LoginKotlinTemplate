package com.ninpou.loginfirebaseproject.ui.forgotpassword

import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.ninpou.loginfirebaseproject.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var forgotPasswordBinding: ActivityForgotPasswordBinding
    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        forgotPasswordBinding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = forgotPasswordBinding.root
        setContentView(view)

        mAuth = FirebaseAuth.getInstance()

        forgotPasswordBinding.forgotPassButton.setOnClickListener {
            forgotPassword()
        }
    }

    private fun forgotPassword() {
        val userEmail = forgotPasswordBinding.userEmailForgotPass.text.trim()

        if (userEmail.isEmpty()) {
            forgotPasswordBinding.userEmailForgotPass.error = "Email is empty!"
            forgotPasswordBinding.userEmailForgotPass.requestFocus()
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            forgotPasswordBinding.userEmailForgotPass.error = "Provide valid email !"
            forgotPasswordBinding.userEmailForgotPass.requestFocus()
            return
        }

        forgotPasswordBinding.forgotPassProgressBar.visibility = View.VISIBLE

        //send code to email to create a new pass
        mAuth.sendPasswordResetEmail(userEmail.toString()).addOnCompleteListener { task ->
            if (task.isComplete) {
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Check Your email, verification sent !",
                        Toast.LENGTH_SHORT
                    ).show()
                    forgotPasswordBinding.forgotPassProgressBar.visibility = View.GONE
                } else {
                    Toast.makeText(
                        this,
                        "Try again, something happened !",
                        Toast.LENGTH_SHORT
                    ).show()
                    forgotPasswordBinding.forgotPassProgressBar.visibility = View.GONE
                }
            }
        }
    }
}