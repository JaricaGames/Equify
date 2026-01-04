package com.jarica.compartirgastos.features.people.domain.peopleUseCases

import com.jarica.compartirgastos.core.data.AppRepository
import com.jarica.compartirgastos.core.domain.models.PersonModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPeopleNamesUseCase @Inject constructor(
    private val appRepository: AppRepository
){
    operator fun invoke(): Flow<List<PersonModel>> = appRepository.personModel
}