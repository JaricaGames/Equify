package com.jarica.compartirgastos.core.domain.models

import java.util.UUID

data class PaymentsModel(
    val idPayment:String = UUID.randomUUID().toString(),
    val amount: Float,
    val idPersonWhoPay: String,
    val idPersonWhoReceive: String,
    val idGroup: String

)
