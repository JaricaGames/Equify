package com.jarica.compartirgastos.features.costs.domain.distributionCostUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSumDistributionCostByIdPersonUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(idPerson: String): Flow<Float> {
        return repository.getSumDistributionCostByIdPerson(idPerson)
    }
}
