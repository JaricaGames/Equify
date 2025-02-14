package com.jarica.compartirgastos.domain.models

data class PaymentsModel(
    val idPayment: Int?,
    val amount: String,
    val namePersonWhoPay: String,
    val namePersonWhoReceive: String,
    val idGroup: Int

)
