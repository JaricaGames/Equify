package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import com.jarica.compartirgastos.core.domain.models.CostModel
import javax.inject.Inject

class UpdateCostUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(costModel: CostModel){
        appRepository.updateCost(costModel)
    }
}