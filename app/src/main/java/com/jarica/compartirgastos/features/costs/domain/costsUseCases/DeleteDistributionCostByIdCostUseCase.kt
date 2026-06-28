package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import javax.inject.Inject

class DeleteDistributionCostByIdCostUseCase @Inject constructor(
    private val costsRepository: CostsRepository
) {
    suspend operator fun invoke(idCost: String) {
        costsRepository.deleteDistributionCostByIdCost(idCost)
    }
}
