package com.sweak.teacherhelper.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sweak.teacherhelper.database.dao.*
import com.sweak.teacherhelper.database.entity.*

@Database(
    entities = [ Note::class, Schedule::class, Group::class, Student::class, StudentActivity::class ],
    version = 1)
abstract class HelperDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun groupDao(): GroupDao
    abstract fun studentDao(): StudentDao
    abstract fun studentActivityDao(): StudentActivityDao

    companion object {
        @Volatile private var instance: HelperDatabase? = null

        operator fun invoke(context: Context) = instance ?: synchronized(this) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            HelperDatabase::class.java, "notebook_database.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}