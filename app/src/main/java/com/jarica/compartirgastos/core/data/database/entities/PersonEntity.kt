package com.jarica.compartirgastos.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.utils.PEOPLE_TABLE


@Entity(
    tableName = PEOPLE_TABLE,
    indices = [Index("idGroup")],
    foreignKeys = [
        ForeignKey(
            entity = GroupNameEntity::class,
            parentColumns = ["idGroup"],
            childColumns = ["idGroup"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PersonEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idPerson") val idPerson: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "idGroup") val idGroup: String,

    )

