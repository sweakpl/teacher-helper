package com.sweak.teacherhelper.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sweak.teacherhelper.data.database.entity.Note

@Dao
interface NoteDao {

    @Insert
    fun insertAll(vararg note: Note)

    @Delete
    fun delete(note: Note)

    @Update
    fun updateNote(vararg note: Note)

    @Query("SELECT * FROM notes")
    fun getAll(): LiveData<List<Note>>
}