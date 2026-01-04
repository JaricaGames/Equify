package com.jarica.compartirgastos.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.utils.COSTS_TABLE

@Entity(
    tableName = COSTS_TABLE,
    indices = [Index("idGroup")]
)
data class CostEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "iDCost") val idCost: String,
    //@ColumnInfo(name = "idPerson") val idPerson: String,
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "idGroup") val idGroup: String?,
    //@ColumnInfo(name = "personString") val personString: String,
    )