package com.sweak.teacherhelper.features.student

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.teacherhelper.data.database.entity.Student
import com.sweak.teacherhelper.data.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentViewModel @Inject constructor(
    val repository: StudentRepository
) : ViewModel() {

    lateinit var allStudents: LiveData<List<Student>>

    fun initializeAllStudents(groupId: Int) {
        allStudents = repository.getAllStudents(groupId)
    }

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