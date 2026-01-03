package com.jarica.compartirgastos.features.groups.data.repository

import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.data.toDomain
import com.jarica.compartirgastos.features.groups.data.dao.GroupsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupsRepository @Inject constructor(
    val groupNameDao: GroupsDao
) {

    //Mapear de GroupEntity a GroupNameModel
    val groupNamesModel: Flow<List<GroupModel>> = groupNameDao.getAllGroupName()
        .map { items -> items.map { GroupModel(it.idGroupName, it.groupName) } }


    suspend fun getGroupNameById(id: String): GroupModel {
        return groupNameDao.getGroupNameById(idGroup = id).toDomain()
    }


    suspend fun getGroupMembersById(id: Int): List<String> {
        return groupNameDao.getGroupsMembersById(id)
    }

    //Metodo que actualiza algun valor del grupo (ej:nombre)
    suspend fun updateGroup(groupNameModel: GroupModel) {
        return groupNameDao.updateGroupName(
            GroupNameEntity(
                idGroupName = groupNameModel.idGroupName,
                groupName = groupNameModel.groupName

            )
        )
    }


    //Metodo que borra un grupo y lo que lleva ese grupo (costos, personas, etc...)
    suspend fun deleteGroup(groupNameModel: GroupModel, iDGroupName: String) {

        groupNameDao.deleteGroupName(
            GroupNameEntity(
                idGroupName = groupNameModel.idGroupName,
                groupName = groupNameModel.groupName

            )
        )
        /*
        personNameDao.deletePersonNameByIdGroup(iDGroupName)
        costsDao.deleteAllCostOfAGroup(iDGroupName)
        // costsDao.deleteCostOfPersonOfAGroup(iDGroupName)
        paymentsDao.deletePaymentsOfAGroup(iDGroupName)*/
    }
}