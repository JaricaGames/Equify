package com.jarica.compartirgastos.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object InitialScreenObject

@Serializable
data object NewGroupScreenObject

@Serializable
data class MainScreenObject(
    val iDGroupName: String?)

@Serializable
data object GroupsScreenObject

@Serializable
data class AddPeopleScreenObject(
    val iDGroupName: String,
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
    val idCost: String,
    val amount: Float,
    val description: String,
    //val personString: String
)

@Serializable
data object ConfigurationScreenObject

@Serializable
data object CustomizeGroupScreenObject

@Serializable
data object DoTheCountsScreenObject

@Serializable
data object SplashScreenObject

@Serializable
data object AboutEquifyScreenObject




