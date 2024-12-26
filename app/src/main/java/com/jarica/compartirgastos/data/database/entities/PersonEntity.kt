package com.jarica.compartirgastos.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.GROUP_NAME_TABLE
import com.jarica.compartirgastos.core.PEOPLE_TABLE


@Entity(
    tableName = PEOPLE_TABLE,
    /*foreignKeys = [
        ForeignKey(
            entity = GroupNameEntity::class,
            parentColumns = ["idGroupName"],
            childColumns = ["idPerson"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]*/
)
data class PersonEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idPerson") val idPerson: Int?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "equity") val equity: Int,
    @ColumnInfo(name = "idGroupName") val idGroupName: Int,

    )

