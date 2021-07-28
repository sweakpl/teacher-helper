package com.sweak.teacherhelper.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sweak.teacherhelper.database.entity.Student
import com.sweak.teacherhelper.repository.StudentRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(application: Application, groupId: Int) : AndroidViewModel(application) {

    private val repository: StudentRepository = StudentRepository(application, groupId)
    val allStudents: LiveData<List<Student>> = repository.allStudents

    fun insert(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(student)
        }
    }

    fun update(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(student)
        }
    }

    fun delete(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(student)
        }
    }
}

class StudentViewModelFactory(private val application: Application, private val groupId: Int)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentViewModel(application, groupId) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}