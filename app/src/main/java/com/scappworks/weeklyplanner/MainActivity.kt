package com.scappworks.weeklyplanner

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.scappworks.weeklyplanner.databinding.ActivityMainBinding
import com.scappworks.weeklyplanner.roomdb.tasktable.Task
import com.scappworks.weeklyplanner.roomdb.weekdaytable.Weekday
import com.scappworks.weeklyplanner.viewmodel.PlannerViewModel
import com.scappworks.weeklyplanner.viewmodel.PlannerViewModelFactory
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val plannerViewModel: PlannerViewModel by viewModels {
        PlannerViewModelFactory((application as PlannerApplication).repository)
    }

    lateinit var weekdayList: List<Weekday>
    lateinit var taskList: List<Task>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clearButtonText = binding.clearCardText
        val sundayButtonText = binding.sundayCardText
        val mondayButtonText = binding.mondayCardText
        val tuesdayButtonText = binding.tuesdayCardText
        val wednesdayButtonText = binding.wednesdayCardText
        val thursdayButtonText = binding.thursdayCardText
        val fridayButtonText = binding.fridayCardText
        val saturdayButtonText = binding.saturdayCardText

        // Setting day card names
        clearButtonText.text = "Clear"
        sundayButtonText.text = "Sun"
        mondayButtonText.text = "Mon"
        tuesdayButtonText.text = "Tue"
        wednesdayButtonText.text = "Wed"
        thursdayButtonText.text = "Thu"
        fridayButtonText.text = "Fri"
        saturdayButtonText.text = "Sat"
        sundayButtonText.text = "Sun"

        plannerViewModel.allTasks.observe(this, {
            taskList = it
        })

        plannerViewModel.allWeekdays.observe(this, {
            weekdayList = it
        })
    }

    private fun startWeekdayActivity(day: Weekday) {
        val intent = Intent(this, WeekdayActivity::class.java)
        intent.putExtra("dayId", day.id)
        this.startActivity(intent)
    }

    private fun clearDb(taskList: List<Task>) {
        val alertDialog: AlertDialog? = this?.let { outerIt ->
            val builder = AlertDialog.Builder(outerIt)
            builder.apply {
                setPositiveButton("Clear",
                        DialogInterface.OnClickListener { dialog, id ->
                            if (taskList.count() == 0) {
                                Toast.makeText(context, "No tasks to clear", Toast.LENGTH_SHORT).show()
                            } else {
                                plannerViewModel.deleteAllTasks()
                                Toast.makeText(context, "Tasks cleared", Toast.LENGTH_SHORT).show()
                            }
                        })
                setNegativeButton("Cancel",
                        DialogInterface.OnClickListener { dialog, id ->
                            // User cancelled the dialog
                        })
            }
                    .setTitle("Clear tasks?")
                    .setMessage("Are you sure you want to clear the weeks tasks?")

            builder.create()
        }

        alertDialog?.show()
    }

    private fun checkDay(dayIn: String, weekdayList: List<Weekday>) {
        weekdayList.forEach {
            if (dayIn == "clear_card" && it.day == "Clear") {
                clearDb(taskList)
            } else {
                val dayInAbr = dayIn.substring(0, 3).toLowerCase(Locale.ROOT)
                val dayOutAbr = it.day.substring(0, 3).toLowerCase(Locale.ROOT)

                if (dayInAbr == dayOutAbr) {
                    startWeekdayActivity(it)
                }
            }
        }
    }

    fun buttonClick(view: View) {
        when (view.id) {
            R.id.clear_card -> checkDay(view.context.resources.getResourceEntryName(R.id.clear_card).toString(), weekdayList)
            R.id.sunday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.sunday_card).toString(), weekdayList)
            R.id.monday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.monday_card).toString(), weekdayList)
            R.id.tuesday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.tuesday_card).toString(), weekdayList)
            R.id.wednesday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.wednesday_card).toString(), weekdayList)
            R.id.thursday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.thursday_card).toString(), weekdayList)
            R.id.friday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.friday_card).toString(), weekdayList)
            R.id.saturday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.saturday_card).toString(), weekdayList)
        }
    }
}