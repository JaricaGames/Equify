package com.jarica.compartirgastos.domain.groupUsesCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.PersonModel
import javax.inject.Inject

class UpdatePersonUseCase @Inject constructor(
    private val repository: AppRepository
) {

    suspend operator fun invoke(personModel: PersonModel){
        repository.updateEquity(personModel = personModel)
    }
}