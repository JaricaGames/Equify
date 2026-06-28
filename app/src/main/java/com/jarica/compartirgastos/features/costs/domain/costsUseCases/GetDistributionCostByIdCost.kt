package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.CostPaymentsModel
import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDistributionCostByIdCost @Inject constructor(
    private val costsRepository: CostsRepository
) {
    operator fun invoke(idCost: String): Flow<List<CostPaymentsModel>> {
        return costsRepository.getDistributionCostByIdCost(idCost)
    }
}
