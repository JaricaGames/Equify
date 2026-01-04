package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import com.jarica.compartirgastos.core.domain.models.PersonBalance
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBalancesByGroupUseCase @Inject constructor(
    private val repository : AppRepository
)
{
    operator fun invoke(groupId: String): Flow<List<PersonBalance>> =
        repository.getBalancesByGroup(groupId)
}