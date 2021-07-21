package com.scappworks.weeklyplanner.viewmodel

import androidx.lifecycle.*
import com.scappworks.weeklyplanner.roomdb.DbRepository
import com.scappworks.weeklyplanner.roomdb.tasktable.Task
import com.scappworks.weeklyplanner.roomdb.weekdaytable.Weekday
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class PlannerViewModel(private val repository: DbRepository) : ViewModel() {
    val allWeekdays: LiveData<List<Weekday>> = repository.allWeekdays.asLiveData()
    val allTasks: LiveData<List<Task>> = repository.allTasks.asLiveData()

    fun insertWeekday(weekday: Weekday) = viewModelScope.launch {
        repository.insertWeekday(weekday)
    }

    fun insertTask(task: Task) = viewModelScope.launch {
        repository.insertTask(task)
    }
}

class PlannerViewModelFactory(private val repository: DbRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlannerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlannerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}