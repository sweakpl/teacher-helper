package com.sweak.teacherhelper.data.repository

import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.data.database.HelperDatabase
import com.sweak.teacherhelper.data.database.dao.StudentActivityDao
import com.sweak.teacherhelper.data.database.entity.StudentActivity
import javax.inject.Inject

class StudentActivityRepository @Inject constructor(
    private val studentActivityDao: StudentActivityDao) {

    fun geAllStudentActivities(studentId: Int): LiveData<List<StudentActivity>> =
        studentActivityDao.getAllWithId(studentId)

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