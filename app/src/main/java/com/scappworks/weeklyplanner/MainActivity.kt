package com.scappworks.weeklyplanner

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.children
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

        clearButtonText.text = "Clear"
        sundayButtonText.text = "Sun"
        mondayButtonText.text = "Mon"
        tuesdayButtonText.text = "Tues"
        wednesdayButtonText.text = "Wed"
        thursdayButtonText.text = "Thurs"
        fridayButtonText.text = "Fri"
        saturdayButtonText.text = "Sat"
        sundayButtonText.text = "Sun"
    }
}