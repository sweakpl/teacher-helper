package com.sweak.teacherhelper.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sweak.teacherhelper.data.database.entity.Student

@Dao
interface StudentDao {

    @Insert
    fun insertAll(vararg student: Student)

    @Delete
    fun delete(student: Student)

    @Update
    fun updateStudent(vararg student: Student)

    @Query(
        "SELECT * FROM students WHERE group_id = :groupId" +
                " ORDER BY class_name, first_name, last_name ASC"
    )
    fun getAll(groupId: Int): LiveData<List<Student>>
}