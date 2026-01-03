package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject

class GetCostByIdPersonUseCase @Inject constructor(
    private val repository: AppRepository
) {
/*    suspend operator fun invoke(idPerson: String): List<DistributionCost> {
        return repository.getCostsById(idPerson)
    }*/
}