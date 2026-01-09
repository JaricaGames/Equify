package com.jarica.compartirgastos.features.payments.domain.paymentUseCases

import com.jarica.compartirgastos.features.payments.data.paymentsRepository.PaymentsRepository
import jakarta.inject.Inject

class DeletePaymentByIdUseCase @Inject constructor(
    private val paymentsRepository: PaymentsRepository
) {
    suspend operator fun invoke(idPayment: String) {
        paymentsRepository.deletePaymentById(idPayment)
    }
}


