package com.studentsapp.app.controller

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.studentsapp.app.databinding.ActivityStudentDetailsBinding
import com.studentsapp.app.model.Student

class StudentDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentDetailsBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStudentDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.navigationIcon?.setTint(
            getColor(android.R.color.white)
        )


        val student: Student? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("extra_student", Student::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra("extra_student")
            }

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

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
