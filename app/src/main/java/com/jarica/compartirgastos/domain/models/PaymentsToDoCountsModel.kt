package com.jarica.compartirgastos.domain.models

data class PaymentsToDoCountsModel(
    val amount: Float,
    val idPersonWhoPay: String,
    val namePersonWhoPay: String,
    val idPersonWhoReceive: String,
    val namePersonWhoReceive: String
)

