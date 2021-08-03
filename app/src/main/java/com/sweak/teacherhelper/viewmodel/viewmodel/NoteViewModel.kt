package com.sweak.teacherhelper.viewmodel.viewmodel

import androidx.lifecycle.*
import android.app.Application
import com.sweak.teacherhelper.database.entity.Note
import com.sweak.teacherhelper.repository.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: NoteRepository = NoteRepository(application)
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

class NoteViewModelFactory(private val application: Application) : ViewModelProvider.Factory{

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return NoteViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}
