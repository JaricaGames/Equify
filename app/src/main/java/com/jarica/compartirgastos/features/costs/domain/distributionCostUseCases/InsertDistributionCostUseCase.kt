package com.jarica.compartirgastos.features.costs.domain.distributionCostUseCases

import com.jarica.compartirgastos.core.domain.models.DistributionCostModel
import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject

class InsertDistributionCostUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(distributionCostModel: DistributionCostModel){
        repository.insertDistributionCost(distributionCostModel = distributionCostModel)

    }
}