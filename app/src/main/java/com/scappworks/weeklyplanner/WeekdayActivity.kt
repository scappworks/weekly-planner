package com.scappworks.weeklyplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.scappworks.weeklyplanner.databinding.ActivityMainBinding
import com.scappworks.weeklyplanner.databinding.ActivityWeekdayBinding
import com.scappworks.weeklyplanner.recyclerviews.TaskRvAdapter
import com.scappworks.weeklyplanner.roomdb.tasktable.Task
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
        taskRv.layoutManager = LinearLayoutManager(this)

        plannerViewModel.allWeekdays.observe(this, {weekdays ->
            weekdays?.let {
                weekdays.forEach { weekday ->
                    // If the day ID passed in matches the day being looked at...
                    if (weekday.id == selectedDay) {
                        // Set the header to the correct day, and start looking through tasks
                        header.text = weekday.day

                        plannerViewModel.allTasks.observe(this,  { tasks ->
                            tasks?.let {
                                // If tasks exist in the db
                                if (tasks.count() > 0) {
                                    // List of tasks for this day
                                    val dayTasks: MutableList<Task> = mutableListOf()

                                    tasks.forEach { task ->
                                        /* If the weekday ID of the task matches the ID of
                                        * the current day being looked at by the preceding
                                        * weekday foreach loop, add that task to the list
                                        * that will be presented */
                                        if (task.weekdayId == weekday.id) {
                                            dayTasks.add(task)
                                        }
                                    }
                                    taskRvAdapter.submitList(dayTasks)
                                    // Hide no tasks view and show task recyclerview
                                    binding.noTasks.visibility = View.GONE
                                    binding.tasksRv.visibility = View.VISIBLE
                                }
                                else {
                                    // Hide task recyclerview and show no tasks view
                                    binding.noTasks.visibility = View.VISIBLE
                                    binding.tasksRv.visibility = View.GONE
                                }
                            }
                        })
                    }
                }
            }
        })
    }
}