package com.scappworks.weeklyplanner

import android.app.Application
import com.scappworks.weeklyplanner.roomdb.DB
import com.scappworks.weeklyplanner.roomdb.DbRepository

class PlannerApplication : Application() {
    val database by lazy { DB.getDatabase(this) }
    val repository by lazy { DbRepository(database.weekdayDao()) }
}