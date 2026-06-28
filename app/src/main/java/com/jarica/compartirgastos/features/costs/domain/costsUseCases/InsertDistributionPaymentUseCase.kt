package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.DistributionPaymentModel
import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import javax.inject.Inject

class InsertDistributionPaymentUseCase @Inject constructor(
    private val costsRepository: CostsRepository
) {
    suspend operator fun invoke(distributionPaymentModel: DistributionPaymentModel) {
        costsRepository.insertDistributionPayment(distributionPaymentModel = distributionPaymentModel)
    }
}