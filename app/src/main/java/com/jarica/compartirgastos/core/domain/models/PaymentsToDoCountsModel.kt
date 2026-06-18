package com.jarica.compartirgastos.core.domain.models

data class PaymentsToDoCountsModel(
    val amount: Long,
    val idPersonWhoPay: String,
    val namePersonWhoPay: String,
    val idPersonWhoReceive: String,
    val namePersonWhoReceive: String
)

