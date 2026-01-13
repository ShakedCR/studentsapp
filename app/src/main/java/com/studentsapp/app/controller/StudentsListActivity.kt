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
import com.studentsapp.app.model.Student
import com.studentsapp.app.view.StudentsAdapter

class StudentsListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStudentsListBinding
    private lateinit var students: MutableList<Student>
    private lateinit var adapter: StudentsAdapter

    private val editStudentLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode != Activity.RESULT_OK) return@registerForActivityResult

            val data = result.data ?: return@registerForActivityResult
            val position = data.getIntExtra(EditStudentActivity.EXTRA_POSITION, -1)
            if (position == -1) return@registerForActivityResult

            val isDelete = data.getBooleanExtra(EditStudentActivity.EXTRA_DELETE, false)
            if (isDelete) {
                students.removeAt(position)
                adapter.notifyItemRemoved(position)
                return@registerForActivityResult
            }

            val updatedStudent =
                data.getParcelableExtra<Student>(EditStudentActivity.EXTRA_UPDATED_STUDENT)
                    ?: return@registerForActivityResult

            students[position] = updatedStudent
            adapter.notifyItemChanged(position)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        students = mutableListOf(
            Student("123456789", "Noa Levi"),
            Student("987654321", "Itay Cohen"),
            Student("555666777", "Maya Rosen")
        )

        val layoutManager = LinearLayoutManager(this)
        binding.rvStudents.layoutManager = layoutManager

        adapter = StudentsAdapter(
            students = students,
            onEditClick = { position ->
                val intent = Intent(this, EditStudentActivity::class.java).apply {
                    putExtra(EditStudentActivity.EXTRA_STUDENT, students[position])
                    putExtra(EditStudentActivity.EXTRA_POSITION, position)
                }
                editStudentLauncher.launch(intent)
            },
            onDeleteClick = { position -> showDeleteDialog(position) }
        )
        binding.rvStudents.adapter = adapter

        val divider = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStudents.addItemDecoration(divider)

        if (binding.root.findViewById<android.view.View?>(com.studentsapp.app.R.id.btnAddStudent) != null) {
            binding.btnAddStudent.setOnClickListener {
                startActivity(Intent(this, AddStudentActivity::class.java))
            }
        }
    }

    private fun showDeleteDialog(position: Int) {
        val student = students[position]

        AlertDialog.Builder(this)
            .setTitle("Delete student")
            .setMessage("Delete ${student.name}?")
            .setPositiveButton("Delete") { _, _ ->
                students.removeAt(position)
                adapter.notifyItemRemoved(position)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
