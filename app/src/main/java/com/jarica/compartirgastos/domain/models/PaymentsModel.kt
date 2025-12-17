package com.jarica.compartirgastos.domain.models

data class PaymentsModel(
    val idPayment: String,
    val amount: Float,
    val idPersonWhoPay: String,
    val idPersonWhoReceive: String,
    val idGroup: String

)
