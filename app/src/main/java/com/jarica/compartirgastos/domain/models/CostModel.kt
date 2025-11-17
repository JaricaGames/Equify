package com.jarica.compartirgastos.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CostModel(
    val idCost: Int,
    val idPerson: Int,
    var amount: Float,
    var description: String,
    val idGroup: Int,
    var personString:String
)
