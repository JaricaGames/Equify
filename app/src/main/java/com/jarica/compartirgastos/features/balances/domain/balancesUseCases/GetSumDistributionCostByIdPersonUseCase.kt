package com.jarica.compartirgastos.features.balances.domain.balancesUseCases

import com.jarica.compartirgastos.features.balances.data.repository.BalancesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSumDistributionCostByIdPersonUseCase @Inject constructor(
    private val balancesRepository: BalancesRepository
) {
    operator fun invoke(idPerson: String): Flow<Long> {
        return balancesRepository.getSumDistributionCostByIdPerson(idPerson)
    }
}