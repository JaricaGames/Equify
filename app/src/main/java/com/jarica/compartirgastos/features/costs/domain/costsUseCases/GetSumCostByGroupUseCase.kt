package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSumCostByGroupUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(groupId: String): Flow<Float> {
        return repository.getTotalExpensesByGroup(groupId)
    }
}