package com.scappworks.weeklyplanner.roomdb.tasktable

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val task: String,
    val weekdayId: Int
)
