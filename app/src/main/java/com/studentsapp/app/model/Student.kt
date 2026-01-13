package com.studentsapp.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Student(
    var id: String,
    var name: String,
    var phone: String = "",
    var address: String = "",
    var isSelected: Boolean = false
) : Parcelable
