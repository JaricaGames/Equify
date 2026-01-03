package com.jarica.compartirgastos.domain.distributionPaymentsUseCases

import com.jarica.compartirgastos.data.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSumDistributionPaymentByIdPersonUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(idPerson: String): Flow<Float> {
        return repository.getSumDistributionPaymentByIdPerson(idPerson)
    }
}
