package com.studentsapp.app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.studentsapp.app.controller.StudentsListActivity
import com.studentsapp.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGoToStudents.setOnClickListener {
            val intent = Intent(this, StudentsListActivity::class.java)
            startActivity(intent)
        }
    }
}
