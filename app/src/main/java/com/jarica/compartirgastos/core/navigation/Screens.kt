package com.jarica.compartirgastos.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object InitialScreenObject

@Serializable
object NewGroupScreenObject

@Serializable
data class MainScreenObject(val iDGroupName:Int?)

@Serializable
data class AddPeopleScreenObject(val iDGroupName:Int, val groupName:String)

@Serializable
object AddCostScreenObject

@Serializable
object CostScreenObject


