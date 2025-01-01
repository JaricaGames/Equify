package com.jarica.compartirgastos.core.navigation

import com.jarica.compartirgastos.domain.models.PersonModel
import kotlinx.serialization.Serializable

@Serializable
object InitialScreen

@Serializable
object NewGroupScreen

@Serializable
data class GroupScreen(val iDGroupName:Int)

@Serializable
data class AddPeopleScreen(val iDGroupName:Int, val groupName:String)

@Serializable
object AddCostScreen

@Serializable
object ListPeople
