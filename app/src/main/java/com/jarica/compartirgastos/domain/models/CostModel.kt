package com.jarica.compartirgastos.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CostModel(
    val idCost: String,
    //val idPerson: String,
    var amount: Float,
    var description: String,
    val idGroup: String?,
    //var personString:String
)
