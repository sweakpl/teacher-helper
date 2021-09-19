package com.sweak.teacherhelper.data.repository

import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.data.database.HelperDatabase
import com.sweak.teacherhelper.data.database.dao.ScheduleDao
import com.sweak.teacherhelper.data.database.entity.Schedule
import javax.inject.Inject

class ScheduleRepository @Inject constructor(
    private val scheduleDao: ScheduleDao
) {
    fun getAllSchedule(day: String): LiveData<List<Schedule>> =
        scheduleDao.getAll(day)

    fun insert(schedule: Schedule) {
        scheduleDao.insertAll(schedule)
    }

    fun update(schedule: Schedule) {
        scheduleDao.updateSchedule(schedule)
    }

    fun delete(schedule: Schedule) {
        scheduleDao.delete(schedule)
    }
}