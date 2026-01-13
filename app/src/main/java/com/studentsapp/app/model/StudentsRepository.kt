package com.studentsapp.app.model

object StudentsRepository {

    val students: MutableList<Student> = mutableListOf()

    fun addStudent(student: Student): Boolean {
        if (students.any { it.id == student.id }) return false
        students.add(student)
        return true
    }

    fun updateStudentByIndex(index: Int, updated: Student): Boolean {
        if (index !in students.indices) return false
        if (students.anyIndexed { i, s -> i != index && s.id == updated.id }) return false
        students[index] = updated
        return true
    }

    fun deleteStudentByIndex(index: Int): Boolean {
        if (index !in students.indices) return false
        students.removeAt(index)
        return true
    }

    private inline fun <T> Iterable<T>.anyIndexed(
        predicate: (index: Int, item: T) -> Boolean
    ): Boolean {
        var idx = 0
        for (item in this) {
            if (predicate(idx, item)) return true
            idx++
        }
        return false
    }
}
