package com.jarica.compartirgastos.domain.costsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.CostOfPersonModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCostOfPersonsUseCase @Inject constructor(
    private val repository: AppRepository
) {

    operator fun invoke() : Flow<List<CostOfPersonModel>> = repository.costOfPersonModel
}