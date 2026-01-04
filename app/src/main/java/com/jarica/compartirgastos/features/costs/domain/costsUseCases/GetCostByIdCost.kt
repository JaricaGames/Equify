package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import com.jarica.compartirgastos.core.domain.models.CostModel
import javax.inject.Inject

class GetCostByIdCost @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(idCost: String): CostModel{
        return repository.getCostByIdCost(idCost)
    }
}