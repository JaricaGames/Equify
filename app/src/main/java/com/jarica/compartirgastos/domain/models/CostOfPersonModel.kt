package com.jarica.compartirgastos.domain.models



data class CostOfPersonModel(
    val iDCostOfPerson: Int?,
    val iDCost: Int,
    val iDPerson: Int?,
    val amount: Float,
    val iDGroup: Int,
    )