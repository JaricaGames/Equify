package com.jarica.compartirgastos.domain.costsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.CostModel
import javax.inject.Inject

class GetCostByIdCost @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(idCost: Int): CostModel{
        return repository.getCostByIdCost(idCost)
    }
}