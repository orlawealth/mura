package com.example.ola.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ola.CourseActivity
import com.example.ola.databinding.CourseLayoutBinding
import com.example.ola.models.Course

class CourseAdapter(private val courses: ArrayList<Course>) :
    RecyclerView.Adapter<CourseAdapter.ViewHolder>() {
    inner class ViewHolder(private val binding: CourseLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(course: Course) {
            binding.tvBuildingName.text = course.buildingName
            binding.tvRoom.text = course.room
            binding.tvCourseName.text = course.courseName
            binding.tvDay.text = "${course.day + " ( " + course.time + " ) "}"

            binding.llCourse.setOnClickListener {
                val intent = Intent(binding.llCourse.context,CourseActivity::class.java)
                intent.putExtra("course", course.courseName)
                intent.putExtra("courseLecturer", course.lecturer)
                intent.putExtra("courseDay", course.day)
                intent.putExtra("courseTime", course.time)
                intent.putExtra("courseDescription", course.description)
                binding.llCourse.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseAdapter.ViewHolder {
        val binding =
            CourseLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CourseAdapter.ViewHolder, position: Int) {

        holder.bind(courses[position])
    }

    override fun getItemCount(): Int = courses.size
}