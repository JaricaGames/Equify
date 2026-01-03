package com.jarica.compartirgastos.domain.distributionCostUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.DistributionCostModel
import javax.inject.Inject

class InsertDistributionCostUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(distributionCostModel: DistributionCostModel){
        repository.insertDistributionCost(distributionCostModel = distributionCostModel)

    }
}