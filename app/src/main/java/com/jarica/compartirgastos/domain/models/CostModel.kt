package com.jarica.compartirgastos.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CostModel(
    val idCost: Int,
    val idPerson: Int,
    val amount: Float,
    val description: String,
    val idGroup: Int,
    val personString:String
)
