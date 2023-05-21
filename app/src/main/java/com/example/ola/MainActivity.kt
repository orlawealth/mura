package com.example.ola

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var sharedPreferences: SharedPreferences = getSharedPreferences(
            "extraHelp", Context.MODE_PRIVATE
        )

        if (sharedPreferences.getBoolean(
                "darkMode", false
            )
        ) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        Handler().postDelayed({
            auth = FirebaseAuth.getInstance()

            if (auth.currentUser != null) {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val loginActivity = Intent(this, LoginActivity::class.java)
                startActivity(loginActivity)
                finish()
            }

        }, 2500)
    }
}