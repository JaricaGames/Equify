package com.jarica.compartirgastos.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import com.jarica.compartirgastos.core.DISTRIBUTION_PAYMENT_TABLE


@Entity(
    tableName = DISTRIBUTION_PAYMENT_TABLE,
    primaryKeys = ["iDCost", "iDPerson"],
    indices = [Index("iDPerson")]
)
data class DistributionPaymentEntity(

    @ColumnInfo(name = "iDCost") val iDCost: String,
    @ColumnInfo(name = "iDPerson") val iDPerson: String,
    @ColumnInfo(name = "amount") val amount: Float,
)