package com.jarica.compartirgastos.core.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class CostModel(
    val idCost: String,
    var amount: Float,
    var description: String,
    val idGroup: String?
)
