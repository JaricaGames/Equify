package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSumCostByGroupUseCase @Inject constructor(
    private val costsRepository: CostsRepository
) {
    operator fun invoke(groupId: String): Flow<Long> {
        return costsRepository.getSumCostsByIdGroup(groupId)
    }
}