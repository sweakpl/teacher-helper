package com.sweak.teacherhelper.data.repository

import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.data.database.HelperDatabase
import com.sweak.teacherhelper.data.database.dao.NoteDao
import com.sweak.teacherhelper.data.database.entity.Note
import javax.inject.Inject

class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
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