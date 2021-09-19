package com.sweak.teacherhelper.data.repository

import androidx.lifecycle.LiveData
import com.sweak.teacherhelper.data.database.dao.StudentActivityDao
import com.sweak.teacherhelper.data.database.entity.MissingKitTuple
import javax.inject.Inject

class MissingKitsRepository @Inject constructor(
    private val studentActivityDao: StudentActivityDao
) {
    fun getMissingKits(groupId: Int): LiveData<List<MissingKitTuple>> =
        studentActivityDao.getMissingKitCountOfGroup(groupId)
}