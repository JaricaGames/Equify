package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import javax.inject.Inject

class GetCostByIdCost @Inject constructor(
    private val costsRepository: CostsRepository
) {
    suspend operator fun invoke(idCost: String): CostModel? {
        return costsRepository.getCostByIdCost(idCost)
    }
}
