package com.jarica.compartirgastos.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.utils.GROUPS_NAME_TABLE

@Entity(tableName = GROUPS_NAME_TABLE)
data class GroupNameEntity(
    @PrimaryKey (autoGenerate = false)
    @ColumnInfo(name = "idGroupName") val idGroupName: String,
    @ColumnInfo(name = "groupName") val groupName: String
)

/*fun GroupNameModel.toDatabase() = GroupNameEntity(
    idGroupName = idGroupName,
    peopleList = peopleList
)*/
