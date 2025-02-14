package com.jarica.compartirgastos.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jarica.compartirgastos.presentation.mainViewScreens.addCostScreen.AddCostScreen
import com.jarica.compartirgastos.presentation.mainViewScreens.addCostScreen.AddCostScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewScreens.addPeopleScreenFromMain.AddPeopleScreenFromMain
import com.jarica.compartirgastos.presentation.mainViewScreens.addPeopleScreenFromMain.AddPeopleScreenFromMainViewModel
import com.jarica.compartirgastos.presentation.createGroupScreens.addPeopleScreen.AddPeopleScreen
import com.jarica.compartirgastos.presentation.createGroupScreens.addPeopleScreen.AddPeopleScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewScreens.costsScreen.CostsScreen
import com.jarica.compartirgastos.presentation.mainViewScreens.costsScreen.CostsScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewScreens.groupsScreen.GroupsScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewScreens.groupsScreen.GroupsScreen
import com.jarica.compartirgastos.presentation.initialScreen.InitialScreen
import com.jarica.compartirgastos.presentation.mainViewScreens.mainScreen.MainScreen
import com.jarica.compartirgastos.presentation.mainViewScreens.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import com.jarica.compartirgastos.presentation.createGroupScreens.newGroupScreen.NewGroupScreen
import com.jarica.compartirgastos.presentation.createGroupScreens.newGroupScreen.NewGroupViewModel

@Composable
fun NavigationWrapper(
    newGroupViewModel: NewGroupViewModel,
    groupViewModel: MainScreenViewModel,
    addPeopleViewModel: AddPeopleScreenViewModel,
    addCostViewModel: AddCostScreenViewModel,
    costViewModel: CostsScreenViewModel,
    groupScreenViewModel: GroupsScreenViewModel,
    addPeopleScreenFromMainViewModel: AddPeopleScreenFromMainViewModel,
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
                navigateToAddPeopleFromGroup = {
                    navController.navigate(AddPeopleScreenFromMainObject)
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

            AddPeopleScreenFromMain(
                addPeopleScreenFromMainViewModel,
                navigateToMainScreen = { navController.navigate(MainScreenObject(iDGroupName))}
            )
        }

    }
}


