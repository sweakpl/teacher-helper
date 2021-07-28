package com.sweak.teacherhelper.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sweak.teacherhelper.database.entity.Schedule

@Dao
interface ScheduleDao {

    @Insert
    fun insertAll(vararg schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)

    @Update
    fun updateSchedule(vararg schedule: Schedule)

    @Query("SELECT * FROM schedule WHERE day = :day ORDER BY time_start, time_end")
    fun getAll(day: String): LiveData<List<Schedule>>
}