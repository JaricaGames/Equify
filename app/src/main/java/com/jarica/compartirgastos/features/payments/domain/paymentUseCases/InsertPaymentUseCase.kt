package com.jarica.compartirgastos.features.payments.domain.paymentUseCases

import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject


class InsertPaymentUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

/*    suspend operator fun invoke(paymentsModel: PaymentsModel){
        appRepository.insertPayment(paymentsModel)
    }*/
}