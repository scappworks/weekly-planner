package com.scappworks.weeklyplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.scappworks.weeklyplanner.recyclerviews.WeekdayRvAdapter
import com.scappworks.weeklyplanner.viewmodel.PlannerViewModel
import com.scappworks.weeklyplanner.viewmodel.PlannerViewModelFactory

class MainActivity : AppCompatActivity() {
    private val plannerViewModel: PlannerViewModel by viewModels {
        PlannerViewModelFactory((application as PlannerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val weekdayRecyclerView = findViewById<RecyclerView>(R.id.weekday_rv)
        val weekdayRvAdapter = WeekdayRvAdapter()


        plannerViewModel.allWeekdays.observe(this, Observer { weekdays ->
            weekdays.let {  }
        })

        plannerViewModel.allTasks.observe(this, Observer { tasks ->
            tasks.let { }
        })
    }
}