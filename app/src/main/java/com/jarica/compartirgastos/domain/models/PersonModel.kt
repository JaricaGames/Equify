package com.jarica.compartirgastos.domain.models

data class PersonModel(
    val idPerson: Int?,
    val name: String,
    val equity: Int = 0,
    val idGroupName: Int,
)