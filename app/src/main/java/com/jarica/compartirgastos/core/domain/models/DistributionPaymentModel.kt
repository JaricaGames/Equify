package com.jarica.compartirgastos.core.domain.models

data class DistributionPaymentModel(
    val iDCost: String,
    val iDPerson: String,
    val amount: Long,
    val idGroup: String,
    val name: String
)