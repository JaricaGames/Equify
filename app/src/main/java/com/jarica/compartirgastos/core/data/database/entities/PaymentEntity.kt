package com.jarica.compartirgastos.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.utils.PAYMENTS_TABLE
import java.util.UUID


@Entity(
    tableName = PAYMENTS_TABLE,
    indices = [
        Index("idGroup"),
        Index("idPersonWhoPay"),
        Index("idPersonWhoReceive")
    ],
    foreignKeys = [
        ForeignKey(
            entity = GroupNameEntity::class,
            parentColumns = ["idGroup"],
            childColumns = ["idGroup"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class PaymentEntity(
    @PrimaryKey
    @ColumnInfo(name = "idPayment") val idPayment: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "amount") val amount: Float,
    @ColumnInfo(name = "idPersonWhoPay") val idPersonWhoPay: String,
    @ColumnInfo(name = "idPersonWhoReceive") val idPersonWhoReceive: String,
    @ColumnInfo(name = "idGroup") val idGroup: String,
)