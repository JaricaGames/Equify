package com.jarica.compartirgastos.data.database.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.jarica.compartirgastos.core.utils.PAYMENTS_BETWEEN_PERSONS_TABLE
import java.util.UUID

@Entity(
    tableName = PAYMENTS_BETWEEN_PERSONS_TABLE,
    indices = [
        Index("idUsuarioPaga"),
        Index("idUsuarioCobra"),
        Index("idGrupo")
    ]
)
data class PagoUsuarioEntity(

    @PrimaryKey
    val idPayment: String = UUID.randomUUID().toString(),
    val idPersonWhoPay: String,
    val idUsuarioCobra: String,
    val cantidad: Double,
    val fecha: Long = System.currentTimeMillis(),
    val idGrupo: String,
    val nota: String? = null
)