package com.scappworks.weeklyplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class WeekdayActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weekday)

        var i = intent
        var ii = i.getIntExtra("dayId", 0)

        Log.i("This thing", ii.toString())
    }
}