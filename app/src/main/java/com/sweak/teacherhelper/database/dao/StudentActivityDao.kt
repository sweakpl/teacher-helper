package com.sweak.teacherhelper.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sweak.teacherhelper.database.entity.MissingKitTuple
import com.sweak.teacherhelper.database.entity.StudentActivity

@Dao
interface StudentActivityDao {

    @Insert
    fun insertAll(vararg studentActivity: StudentActivity)

    @Delete
    fun delete(studentActivity: StudentActivity)

    @Update
    fun updateStudentActivity(vararg studentActivity: StudentActivity)

    @Query("SELECT * FROM student_activities WHERE student_id = :studentId ORDER BY activity_type")
    fun getAllWithId(studentId: Int): LiveData<List<StudentActivity>>

    @Query("SELECT s.id as 'studentId', COUNT(sa.id) as 'missingKitCount'" +
            " FROM students s, student_activities sa" +
            " WHERE s.id = sa.student_id AND sa.activity_type LIKE 'MK' AND s.group_id = :groupId" +
            " GROUP BY s.id")
    fun getMissingKitCountOfGroup(groupId: Int): LiveData<List<MissingKitTuple>>
}