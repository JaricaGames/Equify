package com.jarica.compartirgastos.core.data.mappers

import com.jarica.compartirgastos.core.data.database.entities.PaymentEntity
import com.jarica.compartirgastos.core.domain.models.PaymentsModel

fun PaymentEntity.toDomain(): PaymentsModel {
    return PaymentsModel(
        idPayment = this.idPayment,
        amount = this.amount,
        idPersonWhoPay = this.idPersonWhoPay,
        idPersonWhoReceive = this.idPersonWhoReceive,
        idGroup = this.idGroup
    )
}

