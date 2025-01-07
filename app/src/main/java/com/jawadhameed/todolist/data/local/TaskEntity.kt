package com.jawadhameed.todolist.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val taskName: String = "",
    val isChecked: Int = 0
)