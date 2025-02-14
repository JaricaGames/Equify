package com.jarica.compartirgastos.domain.models

data class PaymentsToDoCountsModel(
    val amount: Float,
    val namePersonWhoPay: String,
    val namePersonWhoReceive: String
)

