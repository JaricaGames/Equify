package com.jarica.compartirgastos.core.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.jarica.compartirgastos.core.utils.DISTRIBUTION_PAYMENT_TABLE


@Entity(
    tableName = DISTRIBUTION_PAYMENT_TABLE,
    primaryKeys = ["idCost", "idPerson"],
    indices = [
        Index("idPerson"),
        Index("idCost"),
        Index("idGroup")
    ],
    foreignKeys = [
        ForeignKey(
            entity = CostEntity::class,
            parentColumns = ["idCost"],
            childColumns = ["idCost"],
            onDelete = ForeignKey.CASCADE // <-- Si se borra el Gasto, se borra el pago
        ),
        ForeignKey(
            entity = PersonEntity::class,
            parentColumns = ["idPerson"],
            childColumns = ["idPerson"],
            onDelete = ForeignKey.CASCADE // <-- Si se borra la Persona, se borra su pago
        )
    ]
)
data class DistributionPaymentEntity(

    @ColumnInfo(name = "idCost") val idCost: String,
    @ColumnInfo(name = "idGroup") val idGroup: String,
    @ColumnInfo(name = "idPerson") val idPerson: String,
    @ColumnInfo(name = "amount") val amount: Float,
   // @ColumnInfo(name = "name") val name: String,
)