package com.sweak.teacherhelper.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sweak.teacherhelper.data.database.entity.Schedule

@Dao
interface ScheduleDao {

    @Insert
    fun insertAll(vararg schedule: Schedule)

    @Delete
    fun delete(schedule: Schedule)

    @Update
    fun updateSchedule(vararg schedule: Schedule)

    @Query("SELECT * FROM schedule WHERE day = :day ORDER BY time(time_start), time(time_end)")
    fun getAll(day: String): LiveData<List<Schedule>>
}