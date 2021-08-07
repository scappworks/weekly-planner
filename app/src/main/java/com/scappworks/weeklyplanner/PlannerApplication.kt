package com.scappworks.weeklyplanner

import android.app.Application
import com.scappworks.weeklyplanner.roomdb.DB
import com.scappworks.weeklyplanner.roomdb.DbRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class PlannerApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { DB.getDatabase(this, applicationScope) }
    val repository by lazy { DbRepository(database.weekdayDao(), database.taskDao()) }
}