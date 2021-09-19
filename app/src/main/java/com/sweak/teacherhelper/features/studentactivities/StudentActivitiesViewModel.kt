package com.sweak.teacherhelper.features.studentactivities

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sweak.teacherhelper.data.database.entity.StudentActivity
import com.sweak.teacherhelper.data.repository.StudentActivityRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentActivitiesViewModel @Inject constructor(
    val repository: StudentActivityRepository
) : ViewModel() {

    lateinit var allStudentActivities: LiveData<List<StudentActivity>>

    fun initializeAllStudentActivities(studentId: Int) {
        allStudentActivities = repository.geAllStudentActivities(studentId)
    }

    fun delete(studentActivity: StudentActivity) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(studentActivity)
        }
    }
}