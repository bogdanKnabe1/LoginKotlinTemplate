package com.ninpou.loginfirebaseproject.ui.registration

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ninpou.loginfirebaseproject.databinding.ActivityRegisterBinding
import com.ninpou.loginfirebaseproject.model.User

class RegisterUserActivity : AppCompatActivity() {

    private lateinit var registerBinding: ActivityRegisterBinding
    private lateinit var mAuth: FirebaseAuth
    private lateinit var database: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = registerBinding.root
        setContentView(view)

        mAuth = FirebaseAuth.getInstance()
        database = Firebase.database.reference

        registerBinding.buttonRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val userFullName = registerBinding.fullnameEditText.text.trim()
        val userAge = registerBinding.ageEditText.text.trim()
        val userEmail = registerBinding.emailEditText.text.trim()
        val userPassword = registerBinding.passwordRegisterEditText.text.trim()

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

        if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            registerBinding.emailEditText.error = "Please provide valid email!"
            registerBinding.emailEditText.requestFocus()
            return
        }

        if (userPassword.isEmpty()) {
            registerBinding.passwordRegisterEditText.error = "Password is required"
            registerBinding.passwordRegisterEditText.requestFocus()
            return
        }

        if (userPassword.length < 6) {
            registerBinding.emailEditText.error = "Password need to be 6 or more letters"
            registerBinding.emailEditText.requestFocus()
        }

        //set progress of registration visible
        registerBinding.progressBar.visibility = View.VISIBLE
        Log.d("TAG", userEmail.toString() + userPassword.toString())

        mAuth.createUserWithEmailAndPassword(userEmail.toString(), userPassword.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    //get user ID
                    val userId: String = FirebaseAuth.getInstance().currentUser?.uid ?: ""
                    // write to database
                    writeNewUser(
                        userId, userFullName.toString(),
                        userAge.toString().toInt(),
                        userEmail.toString(),
                        userPassword.toString().toInt()
                    )
                    //redirect to login layout
                    onBackPressed()
                } else {
                    //check global exceptions like user already exist etc
                    Toast.makeText(this, " Failed to create new user !", Toast.LENGTH_SHORT).show()
                    registerBinding.progressBar.visibility = View.GONE
                }
            }
    }



    // function for creating new user and check can we create a new one
    private fun writeNewUser(
        userId: String,
        fullName: String,
        age: Int,
        email: String,
        password: Int
    ) {
        val user = User(fullName, age, email, password)

        database.child("Users").child(userId).setValue(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, " USER ADDED !", Toast.LENGTH_SHORT).show()
                    registerBinding.progressBar.visibility = View.GONE
                } else {
                    //check exceptions
                    Toast.makeText(this, " Failed to register user !", Toast.LENGTH_SHORT).show()
                    registerBinding.progressBar.visibility = View.GONE
                }
            }
    }
}