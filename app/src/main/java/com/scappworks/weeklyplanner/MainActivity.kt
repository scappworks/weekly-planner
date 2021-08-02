package com.scappworks.weeklyplanner

import android.content.DialogInterface
import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.scappworks.weeklyplanner.databinding.ActivityMainBinding
import com.scappworks.weeklyplanner.recyclerviews.TaskRvAdapter
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

    private lateinit var weekdayList: List<Weekday>
    private lateinit var taskList: List<Task>
    private var taskDayList = mutableListOf<Task>()

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
        val sundayRv: RecyclerView = binding.sundayRv
        val sundayAdapter = TaskRvAdapter(WeekdayActivity())
        sundayRv.adapter = sundayAdapter
        sundayRv.layoutManager = LinearLayoutManager(this)
        val mondayRv: RecyclerView = binding.mondayRv
        val mondayAdapter = TaskRvAdapter(WeekdayActivity())
        mondayRv.adapter = mondayAdapter
        mondayRv.layoutManager = LinearLayoutManager(this)
        val tuesdayRv: RecyclerView = binding.tuesdayRv
        val tuesdayAdapter = TaskRvAdapter(WeekdayActivity())
        tuesdayRv.adapter = tuesdayAdapter
        tuesdayRv.layoutManager = LinearLayoutManager(this)
        val wednesdayRv: RecyclerView = binding.wednesdayRv
        val wednesdayAdapter = TaskRvAdapter(WeekdayActivity())
        wednesdayRv.adapter = wednesdayAdapter
        wednesdayRv.layoutManager = LinearLayoutManager(this)
        val thursdayRv: RecyclerView = binding.thursdayRv
        val thursdayAdapter = TaskRvAdapter(WeekdayActivity())
        thursdayRv.adapter = thursdayAdapter
        thursdayRv.layoutManager = LinearLayoutManager(this)
        val fridayRv: RecyclerView = binding.fridayRv
        val fridayAdapter = TaskRvAdapter(WeekdayActivity())
        fridayRv.adapter = fridayAdapter
        fridayRv.layoutManager = LinearLayoutManager(this)
        val saturdayRv: RecyclerView = binding.saturdayRv
        val saturdayAdapter = TaskRvAdapter(WeekdayActivity())
        saturdayRv.adapter = saturdayAdapter
        saturdayRv.layoutManager = LinearLayoutManager(this)


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

        plannerViewModel.allWeekdays.observe(this, {
            weekdayList = it
        })

        plannerViewModel.allTasks.observe(this, { tasks ->
            taskList = tasks
            taskDayList = mutableListOf()

            for (i in 1..7) {

                taskDayList = sortTasks(weekdayList[i], taskList)

                when (i) {
                    1 -> {
                        sundayAdapter.submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.sundayInner,
                                binding.sundayCardText, sundayRv, binding.sundayNoTasks)
                    }
                    2 -> {
                        mondayAdapter.submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.mondayInner,
                                binding.mondayCardText, mondayRv, binding.mondayNoTasks)
                    }
                    3 -> {
                        tuesdayAdapter.submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.tuesdayInner,
                                binding.tuesdayCardText, tuesdayRv, binding.tuesdayNoTasks)
                    }
                    4 -> {
                        wednesdayAdapter.submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.wednesdayInner,
                                binding.wednesdayCardText, wednesdayRv, binding.wednesdayNoTasks)
                    }
                    5 -> {
                        thursdayAdapter.submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.thursdayInner,
                                binding.thursdayCardText, thursdayRv, binding.thursdayNoTasks)
                    }
                    6 -> {
                        fridayAdapter.submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.fridayInner,
                                binding.fridayCardText, fridayRv, binding.fridayNoTasks)
                    }
                    7 -> {
                        saturdayAdapter.submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.saturdayInner,
                                binding.saturdayCardText, saturdayRv, binding.saturdayNoTasks)
                    }
                }
            }
        })
    }

    private fun toggleVisibility(taskDayList: List<Task>, inner: ConstraintLayout,
                                 cardText: View, rv: RecyclerView, noTask: View) {
        if (taskDayList.count() == 0 ) {
            val newConstraintSet = ConstraintSet()
            newConstraintSet.clone(inner)
            newConstraintSet.connect(noTask.id, ConstraintSet.TOP,
                    cardText.id, ConstraintSet.BOTTOM)
            newConstraintSet.applyTo(inner)

            newConstraintSet.connect(cardText.id, ConstraintSet.BOTTOM,
                    noTask.id, ConstraintSet.TOP)
            newConstraintSet.applyTo(inner)

            rv.visibility = View.GONE
            noTask.visibility = View.VISIBLE

            Log.i("this", "ran zero")
        } else {
            val newConstraintSet = ConstraintSet()
            newConstraintSet.clone(inner)
            newConstraintSet.connect(rv.id, ConstraintSet.TOP,
                    cardText.id, ConstraintSet.BOTTOM)
            newConstraintSet.applyTo(inner)

            newConstraintSet.connect(cardText.id, ConstraintSet.BOTTOM,
                    rv.id, ConstraintSet.TOP)
            newConstraintSet.applyTo(inner)

            rv.visibility = View.VISIBLE
            noTask.visibility = View.GONE

            Log.i("this", "ran else")
        }
    }

    private fun sortTasks(day: Weekday, tasks: List<Task>): MutableList<Task> {
        val newAdapterList = mutableListOf<Task>()

        tasks.forEach {
            if (it.weekdayId == day.id) {
                newAdapterList.add(it)
            }
        }

        return newAdapterList
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