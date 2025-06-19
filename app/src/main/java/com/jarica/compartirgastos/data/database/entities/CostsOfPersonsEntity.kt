package com.jarica.compartirgastos.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.COSTS_OF_PERSONS_TABLE


@Entity(
    tableName = COSTS_OF_PERSONS_TABLE
)

data class CostsOfPersonsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "iDCostOfPerson") val iDCostOfPerson: Int?,
    @ColumnInfo(name = "iDCost") val iDCost: Int,
    @ColumnInfo(name = "iDPerson") val iDPerson: Int?,
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "iDGroup") val iDGroup: Int,
)
