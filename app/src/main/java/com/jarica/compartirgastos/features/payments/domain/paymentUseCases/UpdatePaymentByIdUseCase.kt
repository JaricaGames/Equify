package com.jarica.compartirgastos.features.payments.domain.paymentUseCases

import com.jarica.compartirgastos.core.domain.models.PaymentsModel
import com.jarica.compartirgastos.features.payments.data.paymentsRepository.PaymentsRepository
import javax.inject.Inject

class UpdatePaymentUseCase @Inject constructor(
    private val paymentsRepository: PaymentsRepository
) {

    suspend operator fun invoke(paymentModel: PaymentsModel) {
        paymentsRepository.updatePaymentById(paymentModel)
    }
}