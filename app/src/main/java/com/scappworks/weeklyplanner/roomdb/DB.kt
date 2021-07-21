package com.scappworks.weeklyplanner.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.scappworks.weeklyplanner.roomdb.weekdaytable.Weekday
import com.scappworks.weeklyplanner.roomdb.weekdaytable.WeekdayDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Weekday::class), version = 1, exportSchema = false)
public abstract class DB : RoomDatabase() {
    abstract fun weekdayDao(): WeekdayDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DB? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DB::class.java,
                    "word_database"
                ).addCallback(PlannerDatabaseCallback(scope)).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

    private class PlannerDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.weekdayDao())
                }
            }
        }

        suspend fun populateDatabase(weekdayDao: WeekdayDao) {
            // Delete all content here.
            weekdayDao.deleteAllWeekdays()

            // Add weekdays
            var weekday = Weekday(0, "Sunday")
            weekdayDao.insert(weekday)
            weekday = Weekday(0, "Monday")
            weekdayDao.insert(weekday)
            weekday = Weekday(0, "Tuesday")
            weekdayDao.insert(weekday)
            weekday = Weekday(0, "Wednesday")
            weekdayDao.insert(weekday)
            weekday = Weekday(0, "Thursday")
            weekdayDao.insert(weekday)
            weekday = Weekday(0, "Friday")
            weekdayDao.insert(weekday)
            weekday = Weekday(0, "Saturday")
            weekdayDao.insert(weekday)
        }
    }
}