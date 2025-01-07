package com.jarica.compartirgastos.domain

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.CostModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCostsUseCase @Inject constructor(
    private val repository: AppRepository
) {
    operator fun invoke(): Flow<List<CostModel>> = repository.costModel

}