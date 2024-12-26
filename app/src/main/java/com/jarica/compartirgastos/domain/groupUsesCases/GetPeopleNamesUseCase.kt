package com.jarica.compartirgastos.domain.groupUsesCases

import com.jarica.compartirgastos.data.AppRepository
import com.jarica.compartirgastos.domain.models.PersonModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPeopleNamesUseCase @Inject constructor(
    private val appRepository: AppRepository
){
    operator fun invoke(): Flow<List<PersonModel>> = appRepository.personModel
}