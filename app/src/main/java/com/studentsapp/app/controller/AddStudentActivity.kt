package com.studentsapp.app.controller

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.studentsapp.app.databinding.ActivityAddStudentBinding
import com.studentsapp.app.model.Student
import com.studentsapp.app.model.StudentsRepository

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val id = binding.etId.text.toString().trim()
            val phone = binding.etPhone.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val selected = binding.cbSelected.isChecked

            if (name.isEmpty() || id.isEmpty()) {
                if (name.isEmpty()) binding.etName.error = "Required"
                if (id.isEmpty()) binding.etId.error = "Required"
                return@setOnClickListener
            }

            val student = Student(
                id = id,
                name = name,
                phone = phone,
                address = address,
                isSelected = selected
            )

            val added = StudentsRepository.addStudent(student)
            if (!added) {
                binding.etId.error = "ID already exists"
                return@setOnClickListener
            }

            setResult(RESULT_OK)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        setResult(RESULT_CANCELED)
        finish()
        return true
    }
}
