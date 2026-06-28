package com.jarica.compartirgastos.core.data.mappers

import com.jarica.compartirgastos.core.data.database.entities.CostEntity
import com.jarica.compartirgastos.core.domain.models.CostModel

fun CostEntity.toDomain(): CostModel {
    return CostModel(
        idCost = this.idCost,
        amount = this.amount,
        description = this.description,
        idGroup = this.idGroup,
    )
}