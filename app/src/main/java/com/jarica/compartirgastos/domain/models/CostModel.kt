package com.jarica.compartirgastos.domain.models

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class CostModel(
    val idCost: String = UUID.randomUUID().toString(),
    //val idPerson: String,
    var amount: Float,
    var description: String,
    val idGroup: String,
    //var personString:String
)
