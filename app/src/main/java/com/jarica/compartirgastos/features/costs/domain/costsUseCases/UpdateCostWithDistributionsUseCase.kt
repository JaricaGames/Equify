package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.DistributionCostModel
import com.jarica.compartirgastos.core.domain.models.DistributionPaymentModel
import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import javax.inject.Inject

class UpdateCostWithDistributionsUseCase @Inject constructor(
    private val costsRepository: CostsRepository
) {

    suspend operator fun invoke(
        costModel: CostModel,
        distributionCosts: List<DistributionCostModel>,
        distributionPayment: DistributionPaymentModel?,
    ) {
        costsRepository.updateCostWithDistributions(
            costModel = costModel,
            distributionCosts = distributionCosts,
            distributionPayment = distributionPayment
        )
    }
}
