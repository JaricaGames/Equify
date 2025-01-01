package com.jarica.compartirgastos.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.COSTS_TABLE

@Entity(
    tableName = COSTS_TABLE, foreignKeys = [
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = ["idPerson"],
            childColumns = ["idPerson"],
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class CostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "iDCost") val idCost: Int?,
    @ColumnInfo(name = "idPerson") val idPerson: Int,
    @ColumnInfo(name = "amount") val amount: Int,

    )