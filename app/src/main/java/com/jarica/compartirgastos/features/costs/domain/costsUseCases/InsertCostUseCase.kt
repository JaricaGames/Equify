package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import com.jarica.compartirgastos.core.domain.models.CostModel
import javax.inject.Inject

class InsertCostUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(costModel: CostModel){
        repository.insertCost(costModel = costModel)

    }
}