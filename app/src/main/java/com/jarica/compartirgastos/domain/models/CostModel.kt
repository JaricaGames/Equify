package com.jarica.compartirgastos.domain.models


data class CostModel(
    val idCost: Int?,
    val idPerson: Int,
    val amount: Float,
    val description: String,
    val idGroup: Int
)
