package com.jarica.compartirgastos.domain.models

data class PaymentsModel(
    val amount: Float,
    val namePersonWhoPay: String,
    val namePersonWhoReceive: String
)

