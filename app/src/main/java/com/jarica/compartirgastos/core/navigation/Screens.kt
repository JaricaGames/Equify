package com.jarica.compartirgastos.core.navigation

import kotlinx.serialization.Serializable

@Serializable
object InitialScreen

@Serializable
object NewGroupScreen

@Serializable
data class GroupScreen(val iDGroupName:Int)

@Serializable
data class AddPeopleScreen(val iDGroupName:Int, val groupName:String)
