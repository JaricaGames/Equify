package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject

class InsertCostOfPersonsUseCase @Inject constructor(
    private val repository: AppRepository
) {

 /*   suspend operator fun invoke(costOfPersonModel: CostOfPersonModel){
        repository.insertCostOfPerson(
            costModelOfPerson = costOfPersonModel
        )

    }*/
}