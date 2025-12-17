package com.jarica.compartirgastos.domain.costsUseCases

import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject

class GetCostOfPersonsUseCase @Inject constructor(
    private val repository: AppRepository
) {

    //operator fun invoke() : Flow<List<CostOfPersonModel>> = repository.costOfPersonModel
}