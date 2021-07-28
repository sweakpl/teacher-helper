package com.sweak.teacherhelper.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.database.HelperDatabase
import com.sweak.teacherhelper.database.dao.StudentActivityDao
import com.sweak.teacherhelper.database.entity.MissingKitTuple

class MissingKitsRepository(application: Application, groupId: Int) {

    private val studentActivityDao: StudentActivityDao = HelperDatabase(application).studentActivityDao()
    val missingKits: LiveData<List<MissingKitTuple>> = studentActivityDao.getMissingKitCountOfGroup(groupId)
}