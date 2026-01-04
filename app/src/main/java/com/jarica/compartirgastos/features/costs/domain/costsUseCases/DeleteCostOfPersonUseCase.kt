package com.jarica.compartirgastos.features.costs.domain.costsUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import javax.inject.Inject

class DeleteCostOfPersonUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(idCost: String){
        //appRepository.deleteCostOfPerson(idCost = idCost)
    }

}