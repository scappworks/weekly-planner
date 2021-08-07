package com.scappworks.weeklyplanner

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
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

    // Initialize result contract to send data back to activity that called this activity
    private val contractResult = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result: ActivityResult? ->
        if (result?.resultCode == Activity.RESULT_OK) {
            val taskString = result.data?.getStringExtra("newTask").toString()
            val taskDayId = result.data!!.getIntExtra("dayId", 0)
            val task = Task(0, taskString, taskDayId)
            plannerViewModel.insertTask(task)
        }
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

        plannerViewModel.allWeekdays.observe(this, { weekdays ->
            weekdays?.let {
                weekdays.forEach { weekday ->
                    // If the day ID passed in matches the day being looked at...
                    if (weekday.id == selectedDay) {
                        // Set the header to the correct day, and start looking through tasks
                        header.text = weekday.day
                    }
                }
            }
        })

        plannerViewModel.allTasks.observe(this, { tasks ->
            tasks?.let {
                // Do tasks exist for the day?
                var hasTasks = false
                // List of tasks for this day
                val dayTasks: MutableList<Task> = mutableListOf()

                tasks.forEach { task ->
                    /* If the weekday ID of the task matches the ID that
                        * was passed in from the previous activity,
                        * add that task to the list
                        * that will be presented */

                        if (task.weekdayId == selectedDay) {
                            dayTasks.add(task)
                            hasTasks = true
                        }
                }

                if (hasTasks) {
                    taskRvAdapter.submitList(dayTasks)
                    // Hide no tasks view and show task recyclerview
                    binding.noTasks.visibility = View.GONE
                    binding.tasksRv.visibility = View.VISIBLE
                } else {
                    // Hide task recyclerview and show no tasks view
                    binding.noTasks.visibility = View.VISIBLE
                    binding.tasksRv.visibility = View.GONE
                }
            }
        })

        // Send activity result to previous activity when finished
        binding.addTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            intent.putExtra("dayId", selectedDay)
            contractResult.launch(intent)
        }
    }

    fun deleteTask(task: Task) {
        val alertDialog: AlertDialog? = this.let { outerIt ->
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