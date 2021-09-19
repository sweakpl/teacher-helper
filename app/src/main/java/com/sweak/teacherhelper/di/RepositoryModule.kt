package com.sweak.teacherhelper.di

import com.sweak.teacherhelper.data.database.dao.*
import com.sweak.teacherhelper.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    fun provideGroupRepository(dao: GroupDao): GroupRepository =
        GroupRepository(dao)

    @Provides
    fun provideNoteRepository(dao: NoteDao): NoteRepository =
        NoteRepository(dao)

    @Provides
    fun provideScheduleRepository(dao: ScheduleDao): ScheduleRepository =
        ScheduleRepository(dao)

    @Provides
    @Singleton
    fun provideMissingKitsRepository(dao: StudentActivityDao): MissingKitsRepository =
        MissingKitsRepository(dao)

    @Provides
    @Singleton
    fun provideStudentActivityRepository(dao: StudentActivityDao): StudentActivityRepository =
        StudentActivityRepository(dao)

    @Provides
    @Singleton
    fun provideStudentRepository(dao: StudentDao): StudentRepository =
        StudentRepository(dao)
}