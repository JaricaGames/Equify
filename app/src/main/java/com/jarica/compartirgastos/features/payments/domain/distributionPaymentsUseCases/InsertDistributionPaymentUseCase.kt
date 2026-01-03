package com.jarica.compartirgastos.features.payments.domain.distributionPaymentsUseCases

import com.jarica.compartirgastos.core.domain.models.DistributionPaymentModel
import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject

class InsertDistributionPaymentUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(distributionPaymentModel: DistributionPaymentModel) {
        repository.insertDistributionPayment(distributionPaymentModel = distributionPaymentModel)
    }
}
