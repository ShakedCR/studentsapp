package com.studentsapp.app.controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.studentsapp.app.databinding.ActivityAddStudentBinding

class AddStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val id = binding.etId.text.toString().trim()
            val selected = binding.cbSelected.isChecked

            if (name.isEmpty() || id.isEmpty()) {
                if (name.isEmpty()) binding.etName.error = "Required"
                if (id.isEmpty()) binding.etId.error = "Required"
                return@setOnClickListener
            }

            val data = Intent().apply {
                putExtra("student_name", name)
                putExtra("student_id", id)
                putExtra("student_selected", selected)
            }

            setResult(Activity.RESULT_OK, data)
            finish()
        }

        binding.btnCancel.setOnClickListener {
            finish()
        }
    }
}
