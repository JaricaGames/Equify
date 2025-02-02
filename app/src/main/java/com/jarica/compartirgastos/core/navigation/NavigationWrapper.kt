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
import com.jarica.compartirgastos.presentation.groupsScreen.GroupsScreenViewModel
import com.jarica.compartirgastos.presentation.groupsScreen.GroupsScreen
import com.jarica.compartirgastos.presentation.initialScreen.InitialScreen
import com.jarica.compartirgastos.presentation.mainScreen.MainScreen
import com.jarica.compartirgastos.presentation.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.mainScreen.MainScreenViewModel.Companion.iDGroupName
//import com.jarica.compartirgastos.presentation.mainScreen.MainScreenViewModel.Companion.iDGroupName
import com.jarica.compartirgastos.presentation.newGroupScreen.NewGroupScreen
import com.jarica.compartirgastos.presentation.newGroupScreen.NewGroupViewModel

@Composable
fun NavigationWrapper(
    newGroupViewModel: NewGroupViewModel,
    groupViewModel: MainScreenViewModel,
    addPeopleViewModel: AddPeopleScreenViewModel,
    addCostViewModel: AddCostScreenViewModel,
    costViewModel: CostsScreenViewModel,
    groupScreenViewModel: GroupsScreenViewModel,
) {

    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = GroupsScreenObject

    ) {

        composable<GroupsScreenObject> {

            GroupsScreen(groupScreenViewModel,

                navigateToInitialScreen = {navController.navigate(InitialScreenObject)},
                navigateToNewGroupScreen = { navController.navigate(NewGroupScreenObject) },
                navigateToMainScreen = { iDGroupName ->
                    navController.navigate(
                        MainScreenObject(
                            iDGroupName = iDGroupName,
                        )
                    )
                })
        }

        composable<InitialScreenObject> {
            InitialScreen(navigateToNewGroup = { navController.navigate(NewGroupScreenObject) })
        }

        composable<NewGroupScreenObject> {

            NewGroupScreen(
                newGroupViewModel,

                navigateToGroupsScreen = { navController.navigate(GroupsScreenObject) },
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
                navigateToGroupsScreen = {navController.navigate(GroupsScreenObject)},
                navigateToAddCostScreen = {
                    navController.navigate(
                        AddCostScreenObject
                    )
                },
                navigateToCosts = { navController.navigate(CostScreenObject) },
                navigateToAddPeopleFromGroup = { idGroupName ->
                    navController.navigate(
                        AddPeopleScreenFromMainObject(idGroupName)
                    )
                },
            )
        }

        composable<AddPeopleScreenObject> { backStackEntry ->

            val addPeopleScreen: AddPeopleScreenObject = backStackEntry.toRoute()

            AddPeopleScreen(
                addPeopleViewModel = addPeopleViewModel,
                navigateToNewGroupScreen = { navController.navigate(NewGroupScreenObject) },
                navigateToMainScreen = {
                    navController.navigate(MainScreenObject(iDGroupName = addPeopleScreen.iDGroupName))
                },
                idGroupName = addPeopleScreen.iDGroupName,
                groupName = addPeopleScreen.groupName!!
            )
        }

        composable<AddCostScreenObject> {

            AddCostScreen(
                addCostViewModel,
                navigateToMainScreen = { navController.navigate(MainScreenObject(iDGroupName)) }
            )
        }

        composable<CostScreenObject> {

            CostsScreen(
                costViewModel,
                navigateToMainScreen = {
                    navController.navigate(MainScreenObject(iDGroupName)) })
        }

        composable<AddPeopleScreenFromMainObject> {

            //AddPeopleScreenFromMain()
        }

    }
}


