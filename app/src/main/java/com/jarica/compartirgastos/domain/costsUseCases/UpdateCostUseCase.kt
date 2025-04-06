package com.jarica.compartirgastos.domain.costsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.CostModel
import javax.inject.Inject

class UpdateCostUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(costModel: CostModel){
        //appRepository.updateCost(costModel)
    }
}