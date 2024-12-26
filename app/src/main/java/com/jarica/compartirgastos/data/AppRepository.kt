package com.jarica.compartirgastos.data

import com.jarica.compartirgastos.data.database.dao.GroupNameDao
import com.jarica.compartirgastos.data.database.dao.PersonNameDao
import com.jarica.compartirgastos.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.data.database.entities.PersonEntity
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.domain.models.PersonModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(
    private val groupNameDao: GroupNameDao,
    private val personNameDao: PersonNameDao
) {

    //GROUP_NAME_DAO

    //Mapear de GroupEntity a GroupNameModel
    val groupNamesModel: Flow<List<GroupNameModel>> = groupNameDao.getAllGroupName()
        .map { items -> items.map { GroupNameModel(it.idGroupName, it.groupName) } }


    suspend fun insertGroupName(groupNameModel: GroupNameModel) {
        groupNameDao.insertGroupName(
            GroupNameEntity(
                idGroupName = groupNameModel.idGroupName,
                groupName = groupNameModel.groupName

            )
        )

    }

    //PERSON_NAME_DAO

    //Mapear de GroupEntuty a GroupNameModel
    val personModel: Flow<List<PersonModel>> = personNameDao.getAllPeopleName()
        .map { items -> items.map { PersonModel(it.idPerson, it.name, it.equity, it.idGroupName) } }


    suspend fun insertPersonName(personModel: PersonModel) {
        personNameDao.insertPersonName (
            PersonEntity(
                idPerson = null,
                name = personModel.name,
                equity = personModel.equity,
                idGroupName = personModel.idGroupName
            )
        )

    }



}