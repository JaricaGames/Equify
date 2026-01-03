package com.jarica.compartirgastos.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.jarica.compartirgastos.core.DISTRIBUTION_PAYMENT_TABLE


@Entity(
    tableName = DISTRIBUTION_PAYMENT_TABLE,
    primaryKeys = ["idCost", "idPerson"],
    indices = [
        Index("idPerson"),
        Index("idCost"),
        Index("idGroup")
    ]
)
data class DistributionPaymentEntity(

    @ColumnInfo(name = "idCost") val idCost: String,
    @ColumnInfo(name = "idGroup") val idGroup: String,
    @ColumnInfo(name = "idPerson") val idPerson: String,
    @ColumnInfo(name = "amount") val amount: Float,
   // @ColumnInfo(name = "name") val name: String,
)