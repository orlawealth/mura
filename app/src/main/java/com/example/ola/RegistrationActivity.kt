package com.example.ola

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.ola.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    val options = arrayOf("Male", "Female")
    private lateinit var binding: ActivityRegistrationBinding
    lateinit var adapter: ArrayAdapter<String>

    private lateinit var auth: FirebaseAuth

    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth

        adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, options)
        binding.spinner.adapter = adapter

        binding.registrationButton.setOnClickListener {
            registerUser(
                binding.registerEmailEditText.text.toString(),
                binding.registerPasswordEditText.text.toString(),
                binding.editTextPhone.text.toString()
            )
        }


    }

    private fun registerUser(email: String, password: String, number: String) {
        if (email.isNotEmpty() && password.isNotEmpty() && number.isNotEmpty()) {
            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser

                        val userDetails = hashMapOf(
                            "id" to user?.uid.toString(),
                            "email" to email,
                            "phoneNumber" to number,
                            "gender" to binding.spinner.selectedItem.toString().lowercase(),
                        )

                        db.collection("users")
                            .add(userDetails)
                            .addOnSuccessListener { documentReference ->
                                val intent = Intent(this, LoginActivity::class.java)
                                startActivity(intent)
                                finish()
                                Toast.makeText(
                                    baseContext,
                                    "Account Registered Successfully",
                                    Toast.LENGTH_LONG,
                                ).show()
                            }
                            .addOnFailureListener { e ->
                            }


                    } else {

                        Toast.makeText(
                            baseContext,
                            "Authentication failed - ${task.exception.toString()}",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }  else{
            Toast.makeText(
                baseContext,
                "Complete registration form to continue",
                Toast.LENGTH_SHORT,
            ).show()
        }


    }
}