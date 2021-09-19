package com.sweak.teacherhelper.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sweak.teacherhelper.data.database.dao.*
import com.sweak.teacherhelper.data.database.entity.*

@Database(
    entities = [ Note::class, Schedule::class, Group::class, Student::class, StudentActivity::class ],
    version = 1)
abstract class HelperDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun groupDao(): GroupDao
    abstract fun studentDao(): StudentDao
    abstract fun studentActivityDao(): StudentActivityDao
}