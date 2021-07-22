package com.scappworks.weeklyplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scappworks.weeklyplanner.databinding.ActivityMainBinding
import com.scappworks.weeklyplanner.recyclerviews.WeekdayRvAdapter
import com.scappworks.weeklyplanner.viewmodel.PlannerViewModel
import com.scappworks.weeklyplanner.viewmodel.PlannerViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val plannerViewModel: PlannerViewModel by viewModels {
        PlannerViewModelFactory((application as PlannerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val weekdayRecyclerView = binding.weekdayRv
//        val weekdayRvAdapter = WeekdayRvAdapter()
//        weekdayRecyclerView.adapter = weekdayRvAdapter
//        weekdayRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
//
//        plannerViewModel.allWeekdays.observe(this, Observer { weekdays ->
//            weekdays?.let { weekdayRvAdapter.submitList(it) }
//        })
//
//        plannerViewModel.allTasks.observe(this, Observer { tasks ->
//            tasks?.let { }
//        })
    }
}