package com.jarica.compartirgastos.features.groups.data.repository

import com.jarica.compartirgastos.core.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.core.data.mappers.toDomain
import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.features.groups.data.dao.GroupsDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GroupsRepository @Inject constructor(
    val groupNameDao: GroupsDao
) {

    val groupNamesModel: Flow<List<GroupModel>> = groupNameDao.getAllGroupName()
        .map { items -> items.map { GroupModel(it.idGroup, it.groupName) } }


    suspend fun getGroupNameById(id: String): GroupModel {
        return groupNameDao.getGroupNameById(idGroup = id).toDomain()
    }


    suspend fun getGroupMembersById(id: Int): List<String> {
        return groupNameDao.getGroupsMembersById(id)
    }

    suspend fun updateGroup(groupNameModel: GroupModel) {
        return groupNameDao.updateGroupName(
            GroupNameEntity(
                idGroup = groupNameModel.idGroupName,
                groupName = groupNameModel.groupName

            )
        )
    }

    suspend fun deleteGroup(groupNameModel: GroupModel, iDGroupName: String) {

        groupNameDao.deleteGroupName(
            GroupNameEntity(
                idGroup = groupNameModel.idGroupName,
                groupName = groupNameModel.groupName

            )
        )
    }
}