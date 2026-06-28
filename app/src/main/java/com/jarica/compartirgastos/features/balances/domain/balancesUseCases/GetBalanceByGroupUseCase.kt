package com.jarica.compartirgastos.features.balances.domain.balancesUseCases

import com.jarica.compartirgastos.core.domain.models.PersonBalance
import com.jarica.compartirgastos.features.balances.data.repository.BalancesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBalancesByGroupUseCase @Inject constructor(
    private val balancesRepository: BalancesRepository
)
{
    operator fun invoke(groupId: String?): Flow<List<PersonBalance>> =
        balancesRepository.getBalancesByGroup(groupId)
}