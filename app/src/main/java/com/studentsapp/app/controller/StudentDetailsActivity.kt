package com.studentsapp.app.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.studentsapp.app.databinding.ActivityStudentDetailsBinding
import com.studentsapp.app.model.Student

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val student = intent.getParcelableExtra<Student>("extra_student")
        if (student == null) {
            finish()
            return
        }

        binding.tvName.text = student.name
        binding.tvId.text = "ID: ${student.id}"
        binding.tvPhone.text = "Phone: ${student.phone.ifEmpty { "N/A" }}"
        binding.tvAddress.text = "Address: ${student.address.ifEmpty { "N/A" }}"
        binding.tvSelected.text = if (student.isSelected) "Selected" else "Not Selected"
    }
}
