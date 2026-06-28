package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.DistributionCostModel
import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import javax.inject.Inject

class InsertDistributionCostUseCase @Inject constructor(
    private val costsRepository: CostsRepository
) {

    suspend operator fun invoke(distributionCostModel: DistributionCostModel){
        costsRepository.insertDistributionCost(distributionCostModel = distributionCostModel)

    }
}