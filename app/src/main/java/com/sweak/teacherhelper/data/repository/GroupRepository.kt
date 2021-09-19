package com.sweak.teacherhelper.data.repository

import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.data.database.HelperDatabase
import com.sweak.teacherhelper.data.database.dao.GroupDao
import com.sweak.teacherhelper.data.database.entity.Group
import javax.inject.Inject

class GroupRepository @Inject constructor(
    private val groupDao: GroupDao) {

    val allGroups: LiveData<List<Group>> = groupDao.getAll()

    fun insert(group: Group) {
        groupDao.insertAll(group)
    }

    fun update(group: Group) {
        groupDao.updateGroup(group)
    }

    fun delete(group: Group) {
        groupDao.delete(group)
    }
}