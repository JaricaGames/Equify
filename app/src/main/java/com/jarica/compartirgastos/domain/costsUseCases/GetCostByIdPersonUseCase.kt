package com.jarica.compartirgastos.domain.costsUseCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.data.database.entities.CostsOfPersonsEntity
import javax.inject.Inject

class GetCostByIdPersonUseCase @Inject constructor(
    private val repository: AppRepository
) {
    suspend operator fun invoke(idPerson: Int): List<CostsOfPersonsEntity> {
        return repository.getCostsById(idPerson)
    }
}