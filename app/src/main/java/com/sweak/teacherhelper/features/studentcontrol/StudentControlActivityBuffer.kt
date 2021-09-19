package com.sweak.teacherhelper.features.studentcontrol

import com.sweak.teacherhelper.data.database.dao.StudentActivityDao
import com.sweak.teacherhelper.data.database.entity.StudentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class StudentControlActivityBuffer @Inject constructor(
    val studentActivityDao: StudentActivityDao
) {
    private val studentActivityMap = mutableMapOf<Int, String>()
    private val studentMissingKitSet = mutableSetOf<Int>()

    fun handleCheckboxSelection(isChecked: Boolean, studentId: Int) {
        if (isChecked)
            studentMissingKitSet.add(studentId)
        else
            studentMissingKitSet.remove(studentId)
    }

    fun handleSpinnerSelection(selectedItem: String, studentId: Int) {
        if (selectedItem.isNotEmpty())
            studentActivityMap[studentId] = selectedItem
        else
            studentActivityMap.remove(studentId)
    }

    fun flush(): Boolean {
        if (!containsData())
            return false

        CoroutineScope(Dispatchers.IO).launch {
            for (studentMissingKit in studentMissingKitSet)
                studentActivityDao.insertAll(
                    StudentActivity(StudentActivity.MISSING_KIT_ACTIVITY_TYPE, studentMissingKit)
                )
        }

        CoroutineScope(Dispatchers.IO).launch {
            for (studentActivity in studentActivityMap) {
                studentActivityDao.insertAll(
                    StudentActivity(
                        activityType =
                        if (studentActivity.value == StudentActivity.MINUS_ACTIVITY_TYPE)
                            StudentActivity.MINUS_ACTIVITY_TYPE
                        else
                            StudentActivity.PLUS_ACTIVITY_TYPE,
                        studentId = studentActivity.key
                    )
                )
            }
        }

        return true
    }

    private fun containsData() =
        studentActivityMap.isNotEmpty() or studentMissingKitSet.isNotEmpty()
}