package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.data.AppRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCostsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(): Flow<List<CostModel>> = repository.costModel

}