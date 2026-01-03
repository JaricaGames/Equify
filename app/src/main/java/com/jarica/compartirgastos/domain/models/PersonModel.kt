package com.jarica.compartirgastos.domain.models


import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class PersonModel(
    val idPerson: String = UUID.randomUUID().toString(),
    val name: String,
    val idGroupName: String,
)