package com.scappworks.weeklyplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.cardview.widget.CardView
import androidx.core.view.children
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.scappworks.weeklyplanner.databinding.ActivityMainBinding
import com.scappworks.weeklyplanner.recyclerviews.WeekdayRvAdapter
import com.scappworks.weeklyplanner.roomdb.weekdaytable.Weekday
import com.scappworks.weeklyplanner.viewmodel.PlannerViewModel
import com.scappworks.weeklyplanner.viewmodel.PlannerViewModelFactory
import java.util.*
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val plannerViewModel: PlannerViewModel by viewModels {
        PlannerViewModelFactory((application as PlannerApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val clearButton = binding.clearCard
        val clearButtonText = binding.clearCardText
        val sundayButton = binding.sundayCard
        val sundayButtonText = binding.sundayCardText
        val mondayCard = binding.mondayCard
        val mondayButtonText = binding.mondayCardText
        val tuesdayButton = binding.tuesdayCard
        val tuesdayButtonText = binding.tuesdayCardText
        val wednesdayButton = binding.wednesdayCard
        val wednesdayButtonText = binding.wednesdayCardText
        val thursdayButton = binding.thursdayCard
        val thursdayButtonText = binding.thursdayCardText
        val fridayButton = binding.fridayCard
        val fridayButtonText = binding.fridayCardText
        val saturdayButton = binding.saturdayCard
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
    }

    private fun checkDay(dayIn: String): Weekday? {
        var dayOut: Weekday? = null
        plannerViewModel.allWeekdays.observe(this, { weekdays ->
            weekdays?.let {
                weekdays.forEach {
                        if (dayIn == "clear_card" && it.day == "Clear") {
                            // Confirm/clear tasks here
                                dayOut = it
                            Log.i("worked?", it.day)
                        }
                    else {
                            val dayInAbr = dayIn.substring(0, 3).toLowerCase(Locale.ROOT)
                        val dayOutAbr = it.day.substring(0,3).toLowerCase(Locale.ROOT)
                            Log.i("worked!", dayInAbr)

                        if (dayInAbr == dayOutAbr) {
                            dayOut = it
                            Log.i("this day", it.day)
                        }
                    }

                    if (dayOut != null) {
                        return@let
                    }
                }
            }
        })
        return dayOut
    }

    fun buttonClick(view: View) {
        val s = view.id
        Log.i("view in", s.toString())
        when(s) {
            R.id.clear_card -> checkDay(view.context.resources.getResourceEntryName(R.id.clear_card).toString())
            R.id.sunday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.sunday_card).toString())
            R.id.monday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.monday_card).toString())
            R.id.tuesday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.tuesday_card).toString())
            R.id.wednesday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.wednesday_card).toString())
            R.id.thursday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.thursday_card).toString())
            R.id.friday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.friday_card).toString())
            R.id.saturday_card -> checkDay(view.context.resources.getResourceEntryName(R.id.saturday_card).toString())
        }
    }
}