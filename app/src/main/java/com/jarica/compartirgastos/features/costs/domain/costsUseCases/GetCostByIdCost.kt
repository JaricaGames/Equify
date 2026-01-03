package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject

class GetCostByIdCost @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(idCost: String): CostModel{
        return repository.getCostByIdCost(idCost)
    }
}