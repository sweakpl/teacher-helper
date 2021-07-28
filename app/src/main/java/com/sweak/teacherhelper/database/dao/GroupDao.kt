package com.sweak.teacherhelper.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sweak.teacherhelper.database.entity.Group

@Dao
interface GroupDao {

    @Insert
    fun insertAll(vararg group: Group)

    @Delete
    fun delete(group: Group)

    @Update
    fun updateGroup(vararg group: Group)

    @Query("SELECT * FROM groups")
    fun getAll(): LiveData<List<Group>>
}