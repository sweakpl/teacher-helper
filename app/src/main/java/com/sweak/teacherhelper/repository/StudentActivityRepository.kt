package com.sweak.teacherhelper.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.database.HelperDatabase
import com.sweak.teacherhelper.database.dao.StudentActivityDao
import com.sweak.teacherhelper.database.entity.StudentActivity

class StudentActivityRepository(application: Application, studentId: Int) {

    private val studentActivityDao: StudentActivityDao = HelperDatabase(application).studentActivityDao()
    val allStudentActivities: LiveData<List<StudentActivity>> = studentActivityDao.getAllWithId(studentId)

    fun insert(studentActivity: StudentActivity) {
        studentActivityDao.insertAll(studentActivity)
    }

    fun update(studentActivity: StudentActivity) {
        studentActivityDao.updateStudentActivity(studentActivity)
    }

    fun delete(studentActivity: StudentActivity) {
        studentActivityDao.delete(studentActivity)
    }
}