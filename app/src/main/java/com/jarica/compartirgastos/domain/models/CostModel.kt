package com.jarica.compartirgastos.domain.models

import androidx.room.ColumnInfo

data class CostModel(
    val idCost: Int?,
    val idPerson: Int,
    val amount: Int,
)
