package com.studentsapp.app.controller

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.studentsapp.app.databinding.ActivityEditStudentBinding
import com.studentsapp.app.model.Student

class EditStudentActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditStudentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditStudentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val student: Student? =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(EXTRA_STUDENT, Student::class.java)
            } else {
                @Suppress("DEPRECATION")
                intent.getParcelableExtra(EXTRA_STUDENT)
            }

        val position = intent.getIntExtra(EXTRA_POSITION, -1)


        // Validate received data
        if (student == null) {
            Log.e("EditStudentActivity", "Student is null - closing activity")
            finish()
            return
        }

        if (position == -1) {
            Log.e("EditStudentActivity", "Invalid position - closing activity")
            finish()
            return
        }


        binding.etName.setText(student.name)
        binding.etId.setText(student.id)
        binding.cbSelected.isChecked = student.isSelected


        binding.btnUpdate.setOnClickListener {
            val newName = binding.etName.text.toString().trim()
            val newId = binding.etId.text.toString().trim()
            val newSelected = binding.cbSelected.isChecked


            if (newName.isEmpty() || newId.isEmpty()) {
                AlertDialog.Builder(this)
                    .setTitle("Missing fields")
                    .setMessage("Name and ID are required.")
                    .setPositiveButton("OK", null)
                    .show()
                return@setOnClickListener
            }


            val updatedStudent = student.copy(
                name = newName,
                id = newId,
                isSelected = newSelected
            )


            val data = Intent().apply {
                putExtra(EXTRA_POSITION, position)
                putExtra(EXTRA_UPDATED_STUDENT, updatedStudent)
            }

            setResult(RESULT_OK, data)
            finish()
        }

        
        binding.btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Delete student")
                .setMessage("Delete ${student.name}?")
                .setPositiveButton("Delete") { _, _ ->
                    val data = Intent().apply {
                        putExtra(EXTRA_POSITION, position)
                        putExtra(EXTRA_DELETE, true)
                    }
                    setResult(RESULT_OK, data)
                    finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    companion object {
        const val EXTRA_STUDENT = "extra_student"
        const val EXTRA_POSITION = "extra_position"
        const val EXTRA_UPDATED_STUDENT = "extra_updated_student"
        const val EXTRA_DELETE = "extra_delete"
    }
}
