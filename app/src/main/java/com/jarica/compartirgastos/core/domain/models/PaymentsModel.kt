package com.jarica.compartirgastos.core.domain.models

data class PaymentsModel(
    val idPayment: String,
    val amount: Float,
    val idPersonWhoPay: String,
    val idPersonWhoReceive: String,
    val idGroup: String

)
