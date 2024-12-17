package com.jarica.compartirgastos.data

import com.jarica.compartirgastos.data.database.dao.GroupNameDao
import com.jarica.compartirgastos.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.domain.model.GroupNameModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AppRepository @Inject constructor(
    private val groupNameDao: GroupNameDao
) {


    //Mapear de GroupEntuty a GroupNameModel
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


}