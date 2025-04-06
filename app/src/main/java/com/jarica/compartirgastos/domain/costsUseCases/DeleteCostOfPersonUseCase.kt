package com.jarica.compartirgastos.domain.costsUseCases

import com.jarica.compartirgastos.data.AppRepository
import javax.inject.Inject

class DeleteCostOfPersonUseCase @Inject constructor(
    private val appRepository: AppRepository
) {

    suspend operator fun invoke(idCost:Int){
        appRepository.deleteCostOfPerson(idCost = idCost)
    }

}