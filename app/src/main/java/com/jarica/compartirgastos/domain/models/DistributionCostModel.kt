package com.jarica.compartirgastos.domain.models

data class DistributionCostModel(
    val iDCost: String,
    val iDPerson: String,
    val amount: Float,
    val idGroup: String,
    val name: String
)