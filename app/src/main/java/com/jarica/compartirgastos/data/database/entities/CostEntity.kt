package com.jarica.compartirgastos.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.COSTS_TABLE

@Entity(
    tableName = COSTS_TABLE
)
data class CostEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "iDCost") val idCost: Int?,
    @ColumnInfo(name = "idPerson") val idPerson: Int,
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "idGroup") val idGroup: Int,

    )