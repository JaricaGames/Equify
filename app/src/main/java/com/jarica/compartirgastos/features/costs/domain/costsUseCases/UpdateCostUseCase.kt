package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import javax.inject.Inject

class UpdateCostUseCase @Inject constructor(
    private val costsRepository: CostsRepository
) {

    suspend operator fun invoke(costModel: CostModel){
        costsRepository.updateCost(costModel)
    }
}