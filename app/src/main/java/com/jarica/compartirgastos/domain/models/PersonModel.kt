package com.jarica.compartirgastos.domain.models


import kotlinx.serialization.Serializable

@Serializable
data class PersonModel(
    val idPerson: Int?,
    val name: String,
    val equity: String = "0.0",
    val idGroupName: Int,
)