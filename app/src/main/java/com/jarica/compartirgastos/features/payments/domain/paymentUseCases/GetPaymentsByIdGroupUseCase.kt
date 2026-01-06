package com.jarica.compartirgastos.features.payments.domain.paymentUseCases

import com.jarica.compartirgastos.core.domain.models.PaymentsModel
import com.jarica.compartirgastos.features.payments.data.paymentsRepository.PaymentsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaymentsByIdGroupUseCase @Inject constructor(
    private val paymentsRepository: PaymentsRepository
) {
    operator fun invoke(idGroup: String): Flow<List<PaymentsModel>> =
        paymentsRepository.getPaymentsByIdGroup(idGroup)
}
