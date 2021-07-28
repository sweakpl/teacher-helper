package com.sweak.teacherhelper.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.database.HelperDatabase
import com.sweak.teacherhelper.database.dao.GroupDao
import com.sweak.teacherhelper.database.entity.Group

class GroupRepository(application: Application) {

    private val groupDao: GroupDao = HelperDatabase(application).groupDao()
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