package com.scappworks.weeklyplanner

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.os.bundleOf
import com.scappworks.weeklyplanner.databinding.ActivityAddTaskBinding

class AddTaskActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTaskBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val intent = intent
        val selectedDay = intent.getIntExtra("dayId", 0)

        binding.taskSubmitButton.setOnClickListener {
            intent.putExtra("newTask", binding.addTaskEdittext.text.toString())
            intent.putExtra("dayId", selectedDay)

            setResult(Activity.RESULT_OK, intent)
            finish()
        }

        binding.addTaskHeader.text = setHeader(selectedDay)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}

private fun setHeader(dayId: Int): String {
    val day =  when(dayId) {
        2 -> "Sunday"
        3 -> "Monday"
        4 -> "Tuesday"
        5 -> "Wednesday"
        6 -> "Thursday"
        7-> "Friday"
        8 -> "Saturday"
        else -> ""
    }

    return if (day != "") {
        "New $day task"
    } else {
        "New task"
    }
}