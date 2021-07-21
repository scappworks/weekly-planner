package com.scappworks.weeklyplanner.roomdb.tasktable

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Query("SELECT * FROM task ORDER BY id ASC")
    fun getTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Query("DELETE FROM task")
    suspend fun deleteAllTasks()
}