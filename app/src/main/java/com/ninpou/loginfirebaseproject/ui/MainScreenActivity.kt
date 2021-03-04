package com.ninpou.loginfirebaseproject.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.ninpou.loginfirebaseproject.databinding.ActivityMainScreenBinding
import com.ninpou.loginfirebaseproject.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainScreenActivity : ScopedAppActivity() {
    private lateinit var mainScreenBinding: ActivityMainScreenBinding
    private var user: FirebaseUser? = null
    private lateinit var userID: String
    private lateinit var databaseReference: DatabaseReference


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

        getUserFromDb()
    }

    private fun getUserFromDb() = launch(Dispatchers.IO) {
        // getting database links and current user
        user = FirebaseAuth.getInstance().currentUser
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")
        userID = user?.uid.toString()

        databaseReference.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {

            @SuppressLint("SetTextI18n") // annotation for concat strings
            override fun onDataChange(snapshot: DataSnapshot) {
                val userProfile = snapshot.getValue(User::class.java)
                // retrieving data  from database
                if (userProfile != null) {
                    mainScreenBinding.userNameTextView.text =
                        "User full name - ${userProfile.fullName.toString()}"
                    mainScreenBinding.userAgeTextView.text =
                        "User age - ${userProfile.age.toString()}"
                    mainScreenBinding.userEmailTextView.text =
                        "User email - ${userProfile.email.toString()}"
                    mainScreenBinding.userPasswordTextView.text =
                        "User password - ${userProfile.password.toString()}"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@MainScreenActivity,
                    " Something wrong happened after trying to place data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    override fun onStop() {
        super.onStop()
        mainScreenBinding.mainScreenProgressBar.visibility = View.GONE
    }
}