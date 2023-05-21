package com.example.ola

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ola.adapters.CourseAdapter
import com.example.ola.databinding.ActivityHomeBinding
import com.example.ola.databinding.ActivityLoginBinding
import com.example.ola.models.Course
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONArray

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        var courses = ArrayList<Course>()

        val fileInString: String =
            applicationContext.assets.open("extraClasses.json").bufferedReader()
                .use { it.readText() }
        val jsonArray = JSONArray(fileInString)
        for (i in 0 until jsonArray.length()) {
            val course = Course(jsonArray.getJSONObject(i))
            courses.add(course)
        }

        binding.rvHome.layoutManager = LinearLayoutManager(this)
        binding.rvHome.adapter = CourseAdapter(courses)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.home_menu, menu)
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menuSettings -> {
                val settingsPage = Intent(this, SettingsActivity::class.java)
                startActivity(settingsPage)
                true
            }
            R.id.menuLogout -> {
                FirebaseAuth.getInstance().signOut()
                val user = FirebaseAuth.getInstance().currentUser
                if (user == null) {
                    val loginPage = Intent(this, LoginActivity::class.java)
                    startActivity(loginPage)
                    finish()
                } else {
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }


    }
}