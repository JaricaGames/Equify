package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.features.costs.data.costsRepository.CostsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCostsUseCase @Inject constructor(
    private val costsRepository: CostsRepository
) {
    operator fun invoke(): Flow<List<CostModel>> = costsRepository.costModel

}