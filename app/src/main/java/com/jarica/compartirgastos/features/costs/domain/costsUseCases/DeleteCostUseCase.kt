package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import javax.inject.Inject


class DeleteCostUseCase @Inject constructor(
    private val costsRepository: CostsRepository
) {

    suspend operator fun invoke(idCost: String){
        costsRepository.deleteCost(idCost = idCost)
    }

}