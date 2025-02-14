package com.jarica.compartirgastos.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.PAYMENTS_TABLE


@Entity(tableName = PAYMENTS_TABLE)
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idPayment") val idPayment: Int?,
    @ColumnInfo(name = "amount") val amount: String,
    @ColumnInfo(name = "namePersonWhoPay") val namePersonWhoPay: String,
    @ColumnInfo(name = "namePersonWhoReceive") val namePersonWhoReceive: String,
    @ColumnInfo(name = "idGroup") val idGroup: Int,
)