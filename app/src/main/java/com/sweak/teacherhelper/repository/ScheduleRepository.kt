package com.sweak.teacherhelper.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.database.HelperDatabase
import com.sweak.teacherhelper.database.dao.ScheduleDao
import com.sweak.teacherhelper.database.entity.Schedule

class ScheduleRepository(application: Application, day: String) {

    private val scheduleDao: ScheduleDao = HelperDatabase(application).scheduleDao()
    val allSchedule: LiveData<List<Schedule>> = scheduleDao.getAll(day)

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