package com.jarica.compartirgastos.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.jarica.compartirgastos.core.utils.DISTRIBUTION_COST_TABLE


@Entity(
    tableName = DISTRIBUTION_COST_TABLE,
    primaryKeys = ["idCost", "idPerson"],
    indices = [
        Index("idPerson"),
        Index("idGroup"),
        Index("idCost")],
    foreignKeys = [
        ForeignKey(
            entity = CostEntity::class,
            parentColumns = ["idCost"], // Asegúrate que coincida con el @ColumnInfo name en CostEntity
            childColumns = ["idCost"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = ["idPerson"],
            childColumns = ["idPerson"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)

data class DistributionCostEntity(

    @ColumnInfo(name = "idCost") val idCost: String,
    @ColumnInfo(name = "idGroup") val idGroup: String,
    @ColumnInfo(name = "idPerson") val idPerson: String,
    @ColumnInfo(name = "amount") val amount: Float,
)
