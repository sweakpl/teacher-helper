package com.sweak.teacherhelper.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sweak.teacherhelper.database.entity.MissingKitTuple
import com.sweak.teacherhelper.database.entity.Student
import com.sweak.teacherhelper.repository.MissingKitsRepository
import com.sweak.teacherhelper.repository.StudentRepository

class StudentControlViewModel(application: Application, groupId: Int) : AndroidViewModel(application) {

    private val studentRepository: StudentRepository = StudentRepository(application, groupId)
    private val missingKitsRepository: MissingKitsRepository = MissingKitsRepository(application, groupId)

    val allStudents: LiveData<List<Student>> = studentRepository.allStudents
    val missingKits: LiveData<List<MissingKitTuple>> = missingKitsRepository.missingKits
}

class StudentControlViewModelFactory(private val application: Application, private val groupId: Int)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StudentControlViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StudentControlViewModel(application, groupId) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}