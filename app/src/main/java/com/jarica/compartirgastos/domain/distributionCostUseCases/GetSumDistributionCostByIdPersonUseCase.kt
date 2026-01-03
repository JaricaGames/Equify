package com.jarica.compartirgastos.domain.distributionCostUseCases

import com.jarica.compartirgastos.data.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSumDistributionCostByIdPersonUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(idPerson: String): Flow<Float> {
        return repository.getSumDistributionCostByIdPerson(idPerson)
    }
}
