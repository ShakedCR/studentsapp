package com.studentsapp.app.controller

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.studentsapp.app.databinding.ActivityStudentsListBinding
import com.studentsapp.app.model.StudentsRepository
import com.studentsapp.app.view.StudentsAdapter

class StudentsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentsListBinding
    private lateinit var adapter: StudentsAdapter

    private val students = StudentsRepository.students

    private val addStudentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            adapter.notifyItemInserted(students.lastIndex)
        }

    private val editStudentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult
            adapter.notifyDataSetChanged()
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStudents.layoutManager = layoutManager

        adapter = StudentsAdapter(
            students = students,
            onEditClick = { position ->
                val intent = Intent(this, EditStudentActivity::class.java).apply {
                    putExtra(EditStudentActivity.EXTRA_POSITION, position)
                }
                editStudentLauncher.launch(intent)
            },
            onDeleteClick = { position ->
                showDeleteDialog(position)
            },
                    onItemClick = { position ->
                val student = students[position]
                val intent = Intent(this, StudentDetailsActivity::class.java).apply {
                    putExtra("extra_student", student)
                }
                startActivity(intent)
            }
        )

        binding.rvStudents.adapter = adapter
        binding.rvStudents.addItemDecoration(
            DividerItemDecoration(this, layoutManager.orientation)
        )

        binding.btnAddStudent.setOnClickListener {
            addStudentLauncher.launch(Intent(this, AddStudentActivity::class.java))
        }
    }

    private fun showDeleteDialog(position: Int) {
        AlertDialog.Builder(this)
            .setTitle("Delete student")
            .setMessage("Delete student?")
            .setPositiveButton("Delete") { _, _ ->
                StudentsRepository.deleteStudentByIndex(position)
                adapter.notifyItemRemoved(position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
