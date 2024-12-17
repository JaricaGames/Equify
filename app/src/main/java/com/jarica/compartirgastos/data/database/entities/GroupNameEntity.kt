package com.jarica.compartirgastos.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.domain.model.GroupNameModel

@Entity(tableName = "group_name")
data class GroupNameEntity(
    @PrimaryKey (autoGenerate = false)
    @ColumnInfo(name = "idGroupName") val idGroupName: Int,
    @ColumnInfo(name = "groupName") val groupName: String
)

fun GroupNameModel.toDatabase() = GroupNameEntity(
    idGroupName = idGroupName,
    groupName = groupName
)
