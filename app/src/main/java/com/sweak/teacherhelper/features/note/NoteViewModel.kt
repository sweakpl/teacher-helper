package com.sweak.teacherhelper.features.note

import androidx.lifecycle.*
import android.app.Application
import com.sweak.teacherhelper.data.database.entity.Note
import com.sweak.teacherhelper.data.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    val repository: NoteRepository
) : ViewModel() {

    val allNotes: LiveData<List<Note>> = repository.allNotes

    fun insert(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(note)
        }
    }

    fun update(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(note)
        }
    }

    fun delete(note: Note) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(note)
        }
    }
}
