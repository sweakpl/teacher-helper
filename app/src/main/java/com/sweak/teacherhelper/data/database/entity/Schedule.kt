package com.sweak.teacherhelper.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "schedule")
data class Schedule(
    var day: String,
    var activity: String,
    @ColumnInfo(name = "time_start") var timeStart: String,
    @ColumnInfo(name = "time_end") var timeEnd: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}