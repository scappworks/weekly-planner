package com.scappworks.weeklyplanner.roomdb

import androidx.annotation.WorkerThread
import com.scappworks.weeklyplanner.roomdb.tasktable.Task
import com.scappworks.weeklyplanner.roomdb.tasktable.TaskDao
import com.scappworks.weeklyplanner.roomdb.weekdaytable.Weekday
import com.scappworks.weeklyplanner.roomdb.weekdaytable.WeekdayDao
import kotlinx.coroutines.flow.Flow

class DbRepository(private val weekdayDao: WeekdayDao, private val taskDao: TaskDao) {
    val allWeekdays: Flow<List<Weekday>> = weekdayDao.getWeekdays()
    val allTasks: Flow<List<Task>> = taskDao.getTasks()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertWeekday(weekday: Weekday) {
        weekdayDao.insert(weekday)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insertTask(task: Task) {
        taskDao.insert(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteTask(task: Task) {
        taskDao.deleteTask(task)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun deleteAllTasks() {
        taskDao.deleteAllTasks()
    }
    }