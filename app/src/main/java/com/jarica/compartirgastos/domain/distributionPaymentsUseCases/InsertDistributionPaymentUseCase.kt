package com.jarica.compartirgastos.domain.distributionPaymentsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.DistributionPaymentModel
import javax.inject.Inject

class InsertDistributionPaymentUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(distributionPaymentModel: DistributionPaymentModel) {
        repository.insertDistributionPayment(distributionPaymentModel = distributionPaymentModel)
    }
}
