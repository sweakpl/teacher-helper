package com.sweak.teacherhelper.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.database.HelperDatabase
import com.sweak.teacherhelper.database.dao.NoteDao
import com.sweak.teacherhelper.database.entity.Note

class NoteRepository(application: Application) {

    private val noteDao: NoteDao = HelperDatabase(application).noteDao()
    val allNotes: LiveData<List<Note>> = noteDao.getAll()

    fun insert(note: Note) {
        noteDao.insertAll(note)
    }

    fun update(note: Note) {
        noteDao.updateNote(note)
    }

    fun delete(note: Note) {
        noteDao.delete(note)
    }
}