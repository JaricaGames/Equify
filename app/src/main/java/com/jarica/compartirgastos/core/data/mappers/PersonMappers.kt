package com.jarica.compartirgastos.core.data.mappers

import com.jarica.compartirgastos.core.data.database.entities.PersonEntity
import com.jarica.compartirgastos.core.domain.models.PersonModel

fun PersonEntity.toDomain(): PersonModel {
    return PersonModel(
        idPerson = this.idPerson,
        name = this.name,
        // equity = this.equity,
        idGroupName = this.idGroup
    )
}