package com.scappworks.weeklyplanner.roomdb.weekdaytable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WeekdayDao {
    @Query("SELECT * FROM weekday ORDER BY id ASC")
    fun getWeekdays(): List<Weekday>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(day: Weekday)

    @Query("DELETE FROM weekday")
    suspend fun deleteAllWeekdays()
}