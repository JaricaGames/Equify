package com.jarica.compartirgastos.domain.costsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.CostOfPersonModel
import javax.inject.Inject

class InsertCostOfPersonsUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(costOfPersonModel: CostOfPersonModel){
        repository.insertCostOfPerson(
            costModelOfPerson = costOfPersonModel
        )

    }
}