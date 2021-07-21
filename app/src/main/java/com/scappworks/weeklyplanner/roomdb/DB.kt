package com.scappworks.weeklyplanner.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.scappworks.weeklyplanner.roomdb.weekdaytable.Weekday
import com.scappworks.weeklyplanner.roomdb.weekdaytable.WeekdayDao

@Database(entities = arrayOf(Weekday::class), version = 1, exportSchema = false)
public abstract class DB : RoomDatabase() {
    abstract fun weekdayDao(): WeekdayDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: DB? = null

        fun getDatabase(context: Context): DB {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DB::class.java,
                    "word_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}