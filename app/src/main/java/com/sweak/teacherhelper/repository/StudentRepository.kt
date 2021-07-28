package com.sweak.teacherhelper.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.database.HelperDatabase
import com.sweak.teacherhelper.database.dao.StudentDao
import com.sweak.teacherhelper.database.entity.Student

class StudentRepository(application: Application, groupId: Int) {

    private val studentDao: StudentDao = HelperDatabase(application).studentDao()
    val allStudents: LiveData<List<Student>> = studentDao.getAll(groupId)

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