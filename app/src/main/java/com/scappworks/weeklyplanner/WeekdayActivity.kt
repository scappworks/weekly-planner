package com.scappworks.weeklyplanner

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
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
        val taskRvAdapter = TaskRvAdapter(this)
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
                                // If tasks exist for the day
                                var hasTasks = false
                                    // List of tasks for this day
                                    val dayTasks: MutableList<Task> = mutableListOf()

                                    tasks.forEach { task ->
                                        /* If the weekday ID of the task matches the ID of
                                        * the current day being looked at by the preceding
                                        * weekday foreach loop, add that task to the list
                                        * that will be presented */
                                        if (task.weekdayId == weekday.id) {
                                            dayTasks.add(task)
                                            hasTasks = true
                                        }
                                    }

                                if (hasTasks) {
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

    fun deleteTask(task: Task) {
        Log.i("yerp", task.task)
        val alertDialog: AlertDialog? = this?.let { outerIt ->
            val builder = AlertDialog.Builder(outerIt)
            builder.apply {
                setPositiveButton("Clear",
                        DialogInterface.OnClickListener { dialog, id ->
                            plannerViewModel.deleteTask(task)
                        })
                setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })
            }
                    .setTitle("Clear task?")
                    .setMessage("Are you sure you want to clear this task?")

            // Create the AlertDialog
            builder.create()
            builder.show()
        }
    }
}