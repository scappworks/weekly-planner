package com.scappworks.weeklyplanner.roomdb

import androidx.annotation.WorkerThread
import com.scappworks.weeklyplanner.roomdb.weekdaytable.Weekday
import com.scappworks.weeklyplanner.roomdb.weekdaytable.WeekdayDao
import kotlinx.coroutines.flow.Flow

class DbRepository(private val weekdayDao: WeekdayDao) {
    val allWeekdays: Flow<List<Weekday>> = weekdayDao.getWeekdays()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(weekday: Weekday) {
        weekdayDao.insert(weekday)
    }
    }