package com.jarica.compartirgastos.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.utils.PEOPLE_TABLE


@Entity(
    tableName = PEOPLE_TABLE,
    indices = [Index("idGroupName")]
)
data class PersonEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idPerson") val idPerson: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "idGroupName") val idGroupName: String,

    )

