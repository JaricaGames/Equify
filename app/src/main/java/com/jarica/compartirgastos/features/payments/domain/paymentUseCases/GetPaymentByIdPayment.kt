package com.jarica.compartirgastos.features.payments.domain.paymentUseCases

import com.jarica.compartirgastos.core.domain.models.PaymentsModel
import com.jarica.compartirgastos.features.payments.data.paymentsRepository.PaymentsRepository
import javax.inject.Inject

class GetPaymentByIdPayment @Inject constructor(
    private val paymentsRepository: PaymentsRepository
) {
    suspend operator fun invoke(idPayment: String): PaymentsModel? {
        return paymentsRepository.getPaymentByIdPayment(idPayment)
    }
}
