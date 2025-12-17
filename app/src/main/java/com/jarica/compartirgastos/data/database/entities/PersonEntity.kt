package com.jarica.compartirgastos.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.PEOPLE_TABLE


@Entity(
    tableName = PEOPLE_TABLE,
    indices = [Index("idGroupName")],
    foreignKeys = [
        ForeignKey(
            entity = GroupNameEntity::class,
            parentColumns = ["idGroupName"],
            childColumns = ["idGroupName"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PersonEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "idPerson") val idPerson: String,
    @ColumnInfo(name = "name") val name: String,
    //@ColumnInfo(name = "equity") val equity: String,
    @ColumnInfo(name = "idGroupName") val idGroupName: String,

    )

