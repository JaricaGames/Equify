package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import com.jarica.compartirgastos.core.domain.models.CostModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCostsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(): Flow<List<CostModel>> = repository.costModel

}