package com.jarica.compartirgastos.data.dto

import com.jarica.compartirgastos.domain.models.CostPaymentsModel

data class CostPaymentDto (
    val idPerson: String,
    val name: String,
    val amount: Float
)

    fun CostPaymentDto.toDomain(): CostPaymentsModel =
        CostPaymentsModel(
            idPerson = idPerson,
            name = name,
            amount = amount
        )
