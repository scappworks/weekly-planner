package com.scappworks.weeklyplanner.roomdb.weekdaytable

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weekday(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val day: String)