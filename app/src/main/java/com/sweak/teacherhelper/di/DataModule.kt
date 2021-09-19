package com.sweak.teacherhelper.di

import android.app.Application
import androidx.room.Room
import com.sweak.teacherhelper.data.database.HelperDatabase
import com.sweak.teacherhelper.data.database.dao.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): HelperDatabase =
        Room.databaseBuilder(app, HelperDatabase::class.java, "notebook_database.db")
            .build()

    @Provides
    fun provideGroupDao(database: HelperDatabase): GroupDao =
        database.groupDao()

    @Provides
    fun provideNoteDao(database: HelperDatabase): NoteDao =
        database.noteDao()

    @Provides
    fun provideScheduleDao(database: HelperDatabase): ScheduleDao =
        database.scheduleDao()

    @Provides
    @Singleton
    fun provideStudentActivityDao(database: HelperDatabase): StudentActivityDao =
        database.studentActivityDao()

    @Provides
    @Singleton
    fun provideStudentDao(database: HelperDatabase): StudentDao =
        database.studentDao()
}