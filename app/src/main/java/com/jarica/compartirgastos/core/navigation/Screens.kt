package com.jarica.compartirgastos.core.navigation

import kotlinx.serialization.Serializable

@Serializable
data object InitialScreenObject

@Serializable
data object NewGroupScreenObject

@Serializable
data class MainScreenObject(
    val iDGroupName: String)

@Serializable
data object GroupsScreenObject

@Serializable
data class AddPeopleScreenObject(
    val iDGroupName: String,
    val groupName: String?
)

@Serializable
data class AddCostScreenObject(
    val iDGroupName: String,
)

@Serializable
data class AddPayScreenObject(
    val iDGroupName: String,
)

@Serializable
data class AddPeopleScreenFromMainObject(
    val iDGroupName: String
)

@Serializable
data class EditCostScreenObject(
    val idCost: String,
    val amount: Long,
    val description: String,
    val iDGroupName: String
)

@Serializable
data class ConfigurationScreenObject(
    val iDGroupName: String
)

@Serializable
data class CustomizeGroupScreenObject(
    val iDGroupName: String
)


@Serializable
data class DoTheCountsScreenObject(
    val iDGroupName : String
)

@Serializable
data object SplashScreenObject

@Serializable
data object AboutEquifyScreenObject

@Serializable
data class EditPaymentScreenObject(
    val idGroup: String,
    val idPayment: String,
    val amount: Long,
    val personWhoPay: String,
    val personWhoReceive: String
)




