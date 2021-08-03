package com.sweak.teacherhelper.viewmodel.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sweak.teacherhelper.database.entity.Schedule
import com.sweak.teacherhelper.repository.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ScheduleViewModel(application: Application, day: String) : AndroidViewModel(application) {

    private val repository: ScheduleRepository = ScheduleRepository(application, day)
    val allSchedule: LiveData<List<Schedule>> = repository.allSchedule

    fun insert(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(schedule)
        }
    }

    fun update(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(schedule)
        }
    }

    fun delete(schedule: Schedule) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(schedule)
        }
    }
}

class ScheduleViewModelFactory(private val application: Application, private val day: String)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScheduleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ScheduleViewModel(application, day) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}