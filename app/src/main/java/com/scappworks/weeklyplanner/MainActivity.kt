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
        val sundayAdapter = TaskRvAdapter(null)
        sundayRv.adapter = sundayAdapter
        sundayRv.layoutManager = LinearLayoutManager(this)
        val mondayRv: RecyclerView = binding.mondayRv
        val mondayAdapter = TaskRvAdapter(null)
        mondayRv.adapter = mondayAdapter
        mondayRv.layoutManager = LinearLayoutManager(this)
        val tuesdayRv: RecyclerView = binding.tuesdayRv
        val tuesdayAdapter = TaskRvAdapter(null)
        tuesdayRv.adapter = tuesdayAdapter
        tuesdayRv.layoutManager = LinearLayoutManager(this)
        val wednesdayRv: RecyclerView = binding.wednesdayRv
        val wednesdayAdapter = TaskRvAdapter(null)
        wednesdayRv.adapter = wednesdayAdapter
        wednesdayRv.layoutManager = LinearLayoutManager(this)
        val thursdayRv: RecyclerView = binding.thursdayRv
        val thursdayAdapter = TaskRvAdapter(null)
        thursdayRv.adapter = thursdayAdapter
        thursdayRv.layoutManager = LinearLayoutManager(this)
        val fridayRv: RecyclerView = binding.fridayRv
        val fridayAdapter = TaskRvAdapter(null)
        fridayRv.adapter = fridayAdapter
        fridayRv.layoutManager = LinearLayoutManager(this)
        val saturdayRv: RecyclerView = binding.saturdayRv
        val saturdayAdapter = TaskRvAdapter(null)
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

        val adapterList: List<TaskRvAdapter> = listOf(
                sundayAdapter, mondayAdapter, tuesdayAdapter, wednesdayAdapter,
                thursdayAdapter, fridayAdapter, saturdayAdapter
        )
        val rvList: List<RecyclerView> = listOf(
                sundayRv, mondayRv, tuesdayRv, wednesdayRv, thursdayRv, fridayRv, saturdayRv
        )

        plannerViewModel.allWeekdays.observe(this, {
            weekdayList = it
                    setAdapterLists(adapterList, rvList)
        })

        plannerViewModel.allTasks.observe(this, {
            taskList = it
                    setAdapterLists(adapterList, rvList)
        })
    }

    private fun setAdapterLists(adapterList: List<TaskRvAdapter>, rvList: List<RecyclerView>) {
        if (this::weekdayList.isInitialized  && this::taskList.isInitialized) {
            adapterList.forEach {
                taskDayList = mutableListOf()
                val i = adapterList.indexOf(it)
                taskDayList = sortTasks(weekdayList[i + 1], taskList)

                Log.i("rvli", rvList[i].toString())

                when (i) {
                    0 -> {
                        adapterList[i].submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.sundayInner,
                                binding.sundayCardText, rvList[i], binding.sundayNoTasks)
                    }
                    1 -> {
                        adapterList[i].submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.mondayInner,
                                binding.mondayCardText, rvList[i], binding.mondayNoTasks)
                    }
                    2 -> {
                        adapterList[i].submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.tuesdayInner,
                                binding.tuesdayCardText, rvList[i], binding.tuesdayNoTasks)
                    }
                    3 -> {
                        adapterList[i].submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.wednesdayInner,
                                binding.wednesdayCardText, rvList[i], binding.wednesdayNoTasks)
                    }
                    4 -> {
                        adapterList[i].submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.thursdayInner,
                                binding.thursdayCardText, rvList[i], binding.thursdayNoTasks)
                    }
                    5 -> {
                        adapterList[i].submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.fridayInner,
                                binding.fridayCardText, rvList[i], binding.fridayNoTasks)
                    }
                    6 -> {
                        adapterList[i].submitList(taskDayList)
                        toggleVisibility(taskDayList, binding.saturdayInner,
                                binding.saturdayCardText, rvList[i], binding.saturdayNoTasks)
                    }
                }
            }
        }
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
        val alertDialog: AlertDialog = this.let { outerIt ->
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

        alertDialog.show()
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