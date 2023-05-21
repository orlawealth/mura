package com.example.ola

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.ola.databinding.ActivityCourseBinding
import com.example.ola.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class CourseActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCourseBinding
    private lateinit var auth: FirebaseAuth
    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCourseBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val intentExtras = intent.extras

        binding.courseLecturer.text = intentExtras?.getString("courseLecturer")
        binding.courseDescription.text = intentExtras?.getString("courseDescription")
        binding.courseTitle.text = intentExtras?.getString("course")
        binding.courseTime.text =  "${intentExtras?.getString("courseDay") + " ( " + intentExtras?.getString("courseTime")+ " ) "}"
    }
}