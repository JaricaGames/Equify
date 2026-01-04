package com.jarica.compartirgastos.core.data.mappers

import com.jarica.compartirgastos.core.data.database.entities.GroupNameEntity
import com.jarica.compartirgastos.core.domain.models.GroupModel

fun GroupNameEntity.toDomain(): GroupModel {
    return GroupModel(this.idGroupName, this.groupName)
}