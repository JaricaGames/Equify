package com.jarica.compartirgastos.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object InitialScreenObject

@Serializable
data object NewGroupScreenObject

@Serializable
data class MainScreenObject(
    val iDGroupName: Int?)

@Serializable
data object GroupsScreenObject

@Serializable
data class AddPeopleScreenObject(
    val iDGroupName: Int,
    val groupName: String?
)

@Serializable
data object AddCostScreenObject

@Serializable
data object AddPayScreenObject

@Serializable
data object AddPeopleScreenFromMainObject

@Serializable
data class EditCostScreenObject(
    val idCost: Int,
    val amount: Float,
    val description: String,
    val personString: String
)

@Serializable
data object ConfigurationScreenObject

@Serializable
data object CustomizeGroupScreenObject

@Serializable
data object DoTheCountsObject




