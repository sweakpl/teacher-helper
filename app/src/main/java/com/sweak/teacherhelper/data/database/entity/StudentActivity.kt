package com.sweak.teacherhelper.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    tableName = "student_activities",
    foreignKeys = [ForeignKey(
        entity = Student::class,
        parentColumns = ["id"],
        childColumns = ["student_id"],
        onUpdate = CASCADE,
        onDelete = CASCADE
    )]
)
data class StudentActivity(
    @ColumnInfo(name = "activity_type") var activityType: String,
    @ColumnInfo(name = "student_id") var studentId: Int
) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    companion object {
        const val MISSING_KIT_ACTIVITY_TYPE: String = "MK"
        const val PLUS_ACTIVITY_TYPE: String = "+"
        const val MINUS_ACTIVITY_TYPE: String = "-"
    }
}
