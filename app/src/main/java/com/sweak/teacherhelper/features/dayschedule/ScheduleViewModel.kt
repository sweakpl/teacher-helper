package com.sweak.teacherhelper.features.dayschedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.teacherhelper.data.database.entity.Schedule
import com.sweak.teacherhelper.data.repository.ScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScheduleViewModel @Inject constructor(
    val repository: ScheduleRepository
) : ViewModel() {

    lateinit var allSchedule: LiveData<List<Schedule>>

    fun initializeAllSchedule(day: String) {
        allSchedule = repository.getAllSchedule(day)
    }

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