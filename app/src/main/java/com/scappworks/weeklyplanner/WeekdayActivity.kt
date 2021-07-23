package com.scappworks.weeklyplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import com.scappworks.weeklyplanner.databinding.ActivityMainBinding
import com.scappworks.weeklyplanner.databinding.ActivityWeekdayBinding
import com.scappworks.weeklyplanner.recyclerviews.TaskRvAdapter
import com.scappworks.weeklyplanner.viewmodel.PlannerViewModel
import com.scappworks.weeklyplanner.viewmodel.PlannerViewModelFactory

class WeekdayActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWeekdayBinding
    private val plannerViewModel: PlannerViewModel by viewModels {
        PlannerViewModelFactory((application as PlannerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeekdayBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val mainActivityIntent = intent
        val selectedDay = mainActivityIntent.getIntExtra("dayId", 0)
        val header = binding.topBox
        val taskRv = binding.tasksRv
        val taskRvAdapter = TaskRvAdapter()
        taskRv.adapter = taskRvAdapter

        plannerViewModel.allWeekdays.observe(this, {weekdays ->
            weekdays?.let {
                weekdays.forEach {
                    if (it.id == selectedDay) {
                        header.text = it.day
                    }
                }
            }
        })

        plannerViewModel.allTasks.observe(this,  { tasks ->
            tasks?.let {
                if (it.count() > 0) {
                    binding.noTasks.visibility = View.GONE
                    binding.tasksRv.visibility = View.VISIBLE
                    taskRvAdapter.submitList(tasks)
                }
                else {
                    binding.noTasks.visibility = View.VISIBLE
                    binding.tasksRv.visibility = View.GONE
                }
            }
        })
    }
}