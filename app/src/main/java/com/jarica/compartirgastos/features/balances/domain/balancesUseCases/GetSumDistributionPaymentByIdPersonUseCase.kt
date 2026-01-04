package com.jarica.compartirgastos.features.balances.domain.balancesUseCases

import com.jarica.compartirgastos.features.balances.data.repository.BalancesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSumDistributionPaymentByIdPersonUseCase @Inject constructor(
    private val balancesRepository: BalancesRepository

) {
    operator fun invoke(idPerson: String): Flow<Float> {
        return balancesRepository.getSumDistributionPaymentByIdPerson(idPerson)
    }
}