package com.sweak.teacherhelper.features.studentcontrol

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.sweak.teacherhelper.data.database.entity.MissingKitTuple
import com.sweak.teacherhelper.data.database.entity.Student
import com.sweak.teacherhelper.data.repository.MissingKitsRepository
import com.sweak.teacherhelper.data.repository.StudentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StudentControlViewModel @Inject constructor(
    val missingKitsRepository: MissingKitsRepository,
    val studentRepository: StudentRepository
    ) : ViewModel() {

    lateinit var allStudents: LiveData<List<Student>>
    lateinit var missingKits: LiveData<List<MissingKitTuple>>

    fun initializeAllStudentsAndMissingKits(groupId: Int) {
        allStudents = studentRepository.getAllStudents(groupId)
        missingKits = missingKitsRepository.getMissingKits(groupId)
    }
}