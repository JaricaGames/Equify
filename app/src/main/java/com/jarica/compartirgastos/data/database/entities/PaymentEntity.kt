package com.jarica.compartirgastos.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.utils.PAYMENTS_TABLE
import java.util.UUID


@Entity(tableName = PAYMENTS_TABLE)
data class PaymentEntity(
    @PrimaryKey
    @ColumnInfo(name = "idPayment") val idPayment: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "idPersonWhoPay") val idPersonWhoPay: String,
    @ColumnInfo(name = "idPersonWhoReceive") val idPersonWhoReceive: String,
    @ColumnInfo(name = "idGroup") val idGroup: String,
)