package com.sweak.teacherhelper.data.repository

import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.data.database.HelperDatabase
import com.sweak.teacherhelper.data.database.dao.StudentDao
import com.sweak.teacherhelper.data.database.entity.Student
import javax.inject.Inject

class StudentRepository @Inject constructor(
    private val studentDao: StudentDao) {

    fun getAllStudents(groupId: Int): LiveData<List<Student>> =
        studentDao.getAll(groupId)

    fun insert(student: Student) {
        studentDao.insertAll(student)
    }

    fun update(student: Student) {
        studentDao.updateStudent(student)
    }

    fun delete(student: Student) {
        studentDao.delete(student)
    }
}