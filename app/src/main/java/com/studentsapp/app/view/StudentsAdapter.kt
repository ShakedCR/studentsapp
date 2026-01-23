package com.studentsapp.app.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.studentsapp.app.R
import com.studentsapp.app.model.Student

class StudentsAdapter(
    private val students: MutableList<Student>,
    private val onEditClick: (position: Int) -> Unit,
    private val onDeleteClick: (position: Int) -> Unit,
    private val onItemClick: (position: Int) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.StudentVH>() {

    class StudentVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvId: TextView = itemView.findViewById(R.id.tvId)
        val cbSelected: CheckBox = itemView.findViewById(R.id.cbSelected)
        val btnEdit: Button = itemView.findViewById(R.id.btnEdit)
        val btnDelete: Button = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentVH {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_student, parent, false)
        return StudentVH(view)
    }

    override fun onBindViewHolder(holder: StudentVH, position: Int) {
        val student = students[position]

        holder.tvName.text = student.name
        holder.tvId.text = student.id

        holder.cbSelected.setOnCheckedChangeListener(null)
        holder.cbSelected.isChecked = student.isSelected
        holder.cbSelected.setOnCheckedChangeListener { _, isChecked ->
            student.isSelected = isChecked
        }

        holder.btnEdit.setOnClickListener {
            val adapterPos = holder.adapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) onEditClick(adapterPos)
        }

        holder.btnDelete.setOnClickListener {
            val adapterPos = holder.adapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) onDeleteClick(adapterPos)
        }

        holder.itemView.setOnClickListener {
            val adapterPos = holder.adapterPosition
            if (adapterPos != RecyclerView.NO_POSITION) onItemClick(adapterPos)
        }
    }

    override fun getItemCount(): Int = students.size
}
