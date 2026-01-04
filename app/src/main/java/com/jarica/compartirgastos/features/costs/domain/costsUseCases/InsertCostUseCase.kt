package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import javax.inject.Inject

class InsertCostUseCase @Inject constructor(
    private val costsRepository: CostsRepository
) {

    suspend operator fun invoke(costModel: CostModel){
        costsRepository.insertCost(costModel = costModel)

    }
}