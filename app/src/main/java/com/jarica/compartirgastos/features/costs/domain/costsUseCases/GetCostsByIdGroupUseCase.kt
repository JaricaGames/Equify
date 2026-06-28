package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCostsByIdGroupUseCase @Inject constructor(
    private val costsRepository: CostsRepository
) {
    operator fun invoke(idGroup: String): Flow<List<CostModel>>
    =  costsRepository.getCostsByGroup(idGroup)

}
