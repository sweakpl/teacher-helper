package com.sweak.teacherhelper.viewmodel.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.sweak.teacherhelper.database.entity.StudentActivity
import com.sweak.teacherhelper.repository.StudentActivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentActivitiesViewModel(application: Application, studentId: Int) : AndroidViewModel(application) {

    private val repository: StudentActivityRepository = StudentActivityRepository(application, studentId)
    val allStudentActivities: LiveData<List<StudentActivity>> = repository.allStudentActivities

    fun delete(studentActivity: StudentActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(studentActivity)
        }
    }
}

class StudentActivitiesViewModelFactory(private val application: Application, private val studentId: Int)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentActivitiesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentActivitiesViewModel(application, studentId) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}

