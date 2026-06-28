package com.jarica.compartirgastos.features.costs.data.dto

import com.jarica.compartirgastos.core.domain.models.CostPaymentsModel

data class CostPaymentDto (
    val idPerson: String,
    val name: String,
    val amount: Long
)

    fun CostPaymentDto.toDomain(): CostPaymentsModel =
        CostPaymentsModel(
            idPerson = idPerson,
            name = name,
            amount = amount
        )
