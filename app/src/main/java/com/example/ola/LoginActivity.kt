package com.example.ola

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.ola.databinding.ActivityLoginBinding
import com.example.ola.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        binding.loginButton.setOnClickListener {
            logUserIn(
                binding.loginEmailEditText.text.toString(),
                binding.loginPasswordEditText.text.toString()
            )
        }
        binding.loginRegisterText.setOnClickListener {
            goToRegisterPage()
        }

        binding.loginButton.setOnClickListener {
            logUserIn(
                binding.loginEmailEditText.text.toString(),
                binding.loginPasswordEditText.text.toString()
            )
        }
    }

    private fun logUserIn(email: String, password: String) {
        if (email.isNotEmpty() || password.isNotEmpty()) {
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        var intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        // Sign in success, update UI with the signed-in user's information

                        val user = auth.currentUser
                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            baseContext,
                            "Something went wrong - ${task.exception.toString()}",
                            Toast.LENGTH_SHORT,
                        ).show()

                    }
                }
        } else{
            Toast.makeText(
                baseContext,
                "Complete login form to continue",
                Toast.LENGTH_SHORT,
            ).show()
        }

    }

    private fun goToRegisterPage() {
        val registrationActivity = Intent(this, RegistrationActivity::class.java)
        startActivity(registrationActivity)
    }
}