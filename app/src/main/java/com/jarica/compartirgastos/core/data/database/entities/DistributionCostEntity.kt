package com.jarica.compartirgastos.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.jarica.compartirgastos.core.utils.DISTRIBUTION_COST_TABLE


@Entity(
    tableName = DISTRIBUTION_COST_TABLE,
    primaryKeys = ["idCost", "idPerson"],
    indices = [
        Index("idPerson"),
        Index("idGroup"),
        Index("idCost")]
)

data class DistributionCostEntity(

    @ColumnInfo(name = "idCost") val idCost: String,
    @ColumnInfo(name = "idGroup") val idGroup: String,
    @ColumnInfo(name = "idPerson") val idPerson: String,
    @ColumnInfo(name = "amount") val amount: Float,
   // @ColumnInfo(name = "name") val name: String,
)
