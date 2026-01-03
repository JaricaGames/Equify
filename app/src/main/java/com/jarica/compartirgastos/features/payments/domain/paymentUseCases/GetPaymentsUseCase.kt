package com.jarica.compartirgastos.features.payments.domain.paymentUseCases

import com.jarica.compartirgastos.core.domain.models.PaymentsModel
import com.jarica.compartirgastos.data.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPaymentsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(): Flow<List<PaymentsModel>> = repository.paymentsModel
}