package com.sweak.teacherhelper.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "groups")
data class Group(
    var name: String) {

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
