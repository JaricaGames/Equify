package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import javax.inject.Inject

class GetCostByIdPersonUseCase @Inject constructor(
    private val costsRepository: CostsRepository
) {
/*    suspend operator fun invoke(idPerson: String): List<DistributionCost> {
        return costsRepository.getCostsById(idPerson)
    }*/
}