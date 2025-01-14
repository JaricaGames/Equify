package com.jarica.compartirgastos.domain.models


import kotlinx.serialization.Serializable

@Serializable
data class PersonModel(
    val idPerson: Int?,
    val name: String,
    var equity: String = "0.0",
    val idGroupName: Int,
)