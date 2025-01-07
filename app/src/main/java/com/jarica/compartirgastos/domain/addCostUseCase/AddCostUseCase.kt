package com.jarica.compartirgastos.domain.addCostUseCase

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.CostModel
import javax.inject.Inject

class AddCostUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(costModel: CostModel){
        repository.insertCost(costModel = costModel)

    }
}