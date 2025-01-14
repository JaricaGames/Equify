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
import com.jarica.compartirgastos.presentation.costsScreen.CostsScreen
import com.jarica.compartirgastos.presentation.costsScreen.CostsScreenViewModel
import com.jarica.compartirgastos.presentation.mainScreen.MainScreen
import com.jarica.compartirgastos.presentation.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.initialScreen.InitialScreen
import com.jarica.compartirgastos.presentation.newGroupScreen.NewGroupScreen
import com.jarica.compartirgastos.presentation.newGroupScreen.NewGroupViewModel
import com.jarica.compartirgastos.presentation.newGroupScreen.NewGroupViewModel.Companion.iDGroupName

@Composable
fun NavigationWrapper(
    newGroupViewModel: NewGroupViewModel,
    groupViewModel: MainScreenViewModel,
    addPeopleViewModel: AddPeopleScreenViewModel,
    addCostViewModel: AddCostScreenViewModel,
    costViewModel: CostsScreenViewModel,
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = chooseFirstScreen()

    ) {
        composable<InitialScreenObject> {
            InitialScreen(navigateToNewGroup = { navController.navigate(NewGroupScreenObject) })
        }

        composable<NewGroupScreenObject> {
            NewGroupScreen(
                newGroupViewModel,
                navigateToInitial = { navController.navigate(InitialScreenObject) },
                navigateToAddPeople = { idGroupName, groupName ->
                    navController.navigate(
                        AddPeopleScreenObject(idGroupName, groupName)
                    )
                }
            )
        }

        composable<MainScreenObject> { backStackEntry ->
            val groupScreen: MainScreenObject = backStackEntry.toRoute()

            MainScreen(
                groupScreen.iDGroupName,
                groupViewModel,
                navigateToAddCostScreen = {
                    navController.navigate(
                        AddCostScreenObject
                    )
                },
                navigateToCosts = {navController.navigate(CostScreenObject)}
            )
        }

        composable<AddPeopleScreenObject> { backStackEntry ->
            val addPeopleScreen: AddPeopleScreenObject = backStackEntry.toRoute()
            AddPeopleScreen(
                addPeopleViewModel = addPeopleViewModel,
                navigateToNewGroupScreen = { navController.navigate(NewGroupScreenObject) },
                navigateToGroupScreen = {
                    navController.navigate(MainScreenObject(iDGroupName = iDGroupName))
                },
                idGroupName = addPeopleScreen.iDGroupName,
                groupName = addPeopleScreen.groupName
            )
        }

        composable<AddCostScreenObject> {

            AddCostScreen(
                addCostViewModel,
                navigateToGroupScreen = { navController.navigate(MainScreenObject(null)) }
            )
        }

        composable<CostScreenObject> {
            CostsScreen(
                costViewModel,
                navigateToResume = {navController.navigate(MainScreenObject(null))})
        }

    }
}


fun chooseFirstScreen(): Any {
    return if (iDGroupName == null) {
        InitialScreenObject
    } else {
        MainScreenObject(iDGroupName!!)
    }
}

