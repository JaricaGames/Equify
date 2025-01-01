package com.jarica.compartirgastos.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jarica.compartirgastos.presentation.addCostScreen.AddCostScreen
import com.jarica.compartirgastos.presentation.addCostScreen.AddCostScreenViewModel
import com.jarica.compartirgastos.presentation.addPeopleScreen.AddPeopleScreen
import com.jarica.compartirgastos.presentation.addPeopleScreen.AddPeopleScreenViewModel
import com.jarica.compartirgastos.presentation.groupScreen.GroupScreen
import com.jarica.compartirgastos.presentation.groupScreen.GroupScreenViewModel
import com.jarica.compartirgastos.presentation.initialScreen.InitialScreen
import com.jarica.compartirgastos.presentation.listPeopleScreen.ListPeopleScreen
import com.jarica.compartirgastos.presentation.newGroupScreen.NewGroupScreen
import com.jarica.compartirgastos.presentation.newGroupScreen.NewGroupViewModel
import com.jarica.compartirgastos.presentation.newGroupScreen.NewGroupViewModel.Companion.iDGroupName

@Composable
fun NavigationWrapper(
    newGroupViewModel: NewGroupViewModel,
    groupViewModel: GroupScreenViewModel,
    addPeopleViewModel: AddPeopleScreenViewModel,
    addCostViewModel: AddCostScreenViewModel
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = chooseFirstScreen()

    ) {
        composable<InitialScreen> {
            InitialScreen(navigateToNewGroup = { navController.navigate(NewGroupScreen) })
        }

        composable<NewGroupScreen> {
            NewGroupScreen(
                newGroupViewModel,
                navigateToInitial = { navController.navigate(InitialScreen) },
                navigateToAddPeople = { idGroupName, groupName ->
                    navController.navigate(
                        AddPeopleScreen(idGroupName, groupName)
                    )
                }
            )
        }

        composable<GroupScreen> { backStackEntry ->
            val groupScreen: GroupScreen = backStackEntry.toRoute()

            GroupScreen(
                groupScreen.iDGroupName,
                groupViewModel,
                navigateToAddCostScreen = {navController.navigate(AddCostScreen)}
            )
        }

        composable<AddPeopleScreen> { backStackEntry ->
            val addPeopleScreen: AddPeopleScreen = backStackEntry.toRoute()
            AddPeopleScreen(
                addPeopleScreen.iDGroupName,
                addPeopleScreen.groupName,
                addPeopleViewModel = addPeopleViewModel,
                navigateToNewGroupScreen = { navController.navigate(NewGroupScreen) },
                navigateToGroupScreen = { navController.navigate(GroupScreen(addPeopleScreen.iDGroupName)) }

                )
        }

        composable<AddCostScreen> {
            AddCostScreen(addCostViewModel, navigateToFromPerson = {navController.navigate(ListPeople)})
        }

        composable<ListPeople> {
            ListPeopleScreen(navigateBack = {navController.previousBackStackEntry})
        }
    }
}


fun chooseFirstScreen(): Any {
    return if (iDGroupName == null) {
        InitialScreen
    } else {
        GroupScreen(iDGroupName!!)
    }
}

