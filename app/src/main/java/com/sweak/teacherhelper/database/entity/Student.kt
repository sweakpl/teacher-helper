package com.sweak.teacherhelper.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(tableName = "students",
    foreignKeys = [ForeignKey(entity = Group::class,
        parentColumns = ["id"],
        childColumns = ["group_id"],
        onUpdate = CASCADE,
        onDelete = CASCADE)])
data class Student(
    @ColumnInfo(name = "first_name") var firstName: String,
    @ColumnInfo(name = "last_name") var lastName: String,
    @ColumnInfo(name = "class_name") var className: String,
    @ColumnInfo(name = "group_id") var groupId: Int) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
