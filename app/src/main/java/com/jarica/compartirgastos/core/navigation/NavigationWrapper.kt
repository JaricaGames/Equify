package com.jarica.compartirgastos.core.navigation

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jarica.compartirgastos.presentation.SplashScreen.SplashScreen
import com.jarica.compartirgastos.presentation.SplashScreen.SplashScreenViewModel
import com.jarica.compartirgastos.presentation.aboutEquify.AboutEquifyScreen
import com.jarica.compartirgastos.presentation.aboutEquify.AboutEquifyScreenViewModel
import com.jarica.compartirgastos.presentation.createGroupScreens.addPeopleScreen.AddPeopleScreen
import com.jarica.compartirgastos.presentation.createGroupScreens.addPeopleScreen.AddPeopleScreenViewModel
import com.jarica.compartirgastos.presentation.createGroupScreens.newGroupScreen.NewGroupScreen
import com.jarica.compartirgastos.presentation.createGroupScreens.newGroupScreen.NewGroupViewModel
import com.jarica.compartirgastos.presentation.groupsScreen.GroupsScreen
import com.jarica.compartirgastos.presentation.groupsScreen.GroupsScreenViewModel
import com.jarica.compartirgastos.presentation.initialScreen.InitialScreen
import com.jarica.compartirgastos.presentation.mainViewsScreens.addCostScreen.AddCostScreen
import com.jarica.compartirgastos.presentation.mainViewsScreens.addCostScreen.AddCostScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.addPayScreen.AddPaymentScreen
import com.jarica.compartirgastos.presentation.mainViewsScreens.addPayScreen.AddPaymentScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.addPeopleScreenFromMain.AddPeopleScreenFromMain
import com.jarica.compartirgastos.presentation.mainViewsScreens.addPeopleScreenFromMain.AddPeopleScreenFromMainViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.ConfigurationScreen
import com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.ConfigurationScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.fragments.CustomizeGroupScreen
import com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.fragments.CustomizeGroupScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.doTheCountsScreen.DoTheCountsScreen
import com.jarica.compartirgastos.presentation.mainViewsScreens.doTheCountsScreen.DoTheCountsScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.editCostScreen.EditCostScreen
import com.jarica.compartirgastos.presentation.mainViewsScreens.editCostScreen.EditCostScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreen
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName

@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavigationWrapper(
    newGroupViewModel: NewGroupViewModel,
    mainScreenViewModel: MainScreenViewModel,
    addPeopleViewModel: AddPeopleScreenViewModel,
    addCostViewModel: AddCostScreenViewModel,
    groupScreenViewModel: GroupsScreenViewModel,
    addPeopleScreenFromMainViewModel: AddPeopleScreenFromMainViewModel,
    addPaymentScreenViewModel: AddPaymentScreenViewModel,
    editCostScreenViewModel: EditCostScreenViewModel,
    configurationScreenViewModel: ConfigurationScreenViewModel,
    customizeGroupScreenViewModel: CustomizeGroupScreenViewModel,
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    splashScreenViewModel: SplashScreenViewModel,
    aboutScreenViewModel: AboutEquifyScreenViewModel,
) {

    val activity = LocalContext.current as? Activity
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = SplashScreenObject

    ) {

        composable<GroupsScreenObject> {

            GroupsScreen(
                groupScreenViewModel,
                navigateToMainScreen = {
                    navController.navigate(MainScreenObject(iDGroupName)) {
                        launchSingleTop = true
                    }
                },
                navigateToInitialScreen = {
                    navController.navigate(InitialScreenObject) {
                        launchSingleTop = true
                    }
                },
                navigateToNewGroup = {
                    navController.navigate(NewGroupScreenObject) {
                        launchSingleTop = true
                    }
                },
                navigateToAboutScreen = {
                    navController.navigate(AboutEquifyScreenObject) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<InitialScreenObject> {
            InitialScreen(navigateToNewGroup = {
                navController.navigate(NewGroupScreenObject) {
                    launchSingleTop = true
                }
            })
        }

        composable<NewGroupScreenObject> {

            NewGroupScreen(
                newGroupViewModel,
                navigateToGroupsScreen = {
                    navController.navigate(GroupsScreenObject) {
                        launchSingleTop = true
                    }
                },
                navigateToAddPeople = { idGroupName, groupName ->
                    navController.navigate(
                        AddPeopleScreenObject(idGroupName, groupName)
                    ) { launchSingleTop = true }
                }
            )
        }

        composable<MainScreenObject> { backStackEntry ->

            val groupScreen: MainScreenObject = backStackEntry.toRoute()

            MainScreen(
                groupScreen.iDGroupName,
                mainScreenViewModel,
                navigateToGroupsScreen = {
                    navController.navigate(GroupsScreenObject) {
                        launchSingleTop = true
                    }
                },
                navigateToAddCostScreen = {
                    navController.navigate(AddCostScreenObject) {
                        launchSingleTop = true
                    }
                },
                navigateToAddPeopleFromGroup = {
                    navController.navigate(
                        AddPeopleScreenFromMainObject
                    ) { launchSingleTop = true }
                },
                navigateToAddPayScreen = {
                    navController.navigate(AddPayScreenObject) {
                        launchSingleTop = true
                    }
                },
                navigateToEditCost = { costToEdit ->
                    navController.navigate(
                        EditCostScreenObject(
                            idCost = costToEdit.idCost,
                            amount = costToEdit.amount,
                            description = costToEdit.description,
                            personString = costToEdit.personString,
                        )
                    ) { launchSingleTop = true }
                },
                navigateToConfiguration = {
                    navController.navigate(ConfigurationScreenObject) {
                        launchSingleTop = true
                    }
                },
                navigateToDoTheCounts = {
                    navController.navigate(DoTheCountsScreenObject) {
                        launchSingleTop = true
                    }
                },
                doTheCountsScreenViewModel = doTheCountsScreenViewModel,
                onDoTheCountsClicked = {
                    activity?.let {
                        mainScreenViewModel.showAdThenNavigate(it) {
                            navController.navigate("detail")
                        }
                    }
                }
            )
        }

        composable<AddPeopleScreenObject> { backStackEntry ->

            val addPeopleScreen: AddPeopleScreenObject = backStackEntry.toRoute()

            AddPeopleScreen(
                addPeopleViewModel = addPeopleViewModel,
                navigateToNewGroupScreen = {
                    navController.navigate(NewGroupScreenObject) {
                        launchSingleTop = true
                    }
                },
                navigateToMainScreen = {
                    navController.navigate(MainScreenObject(iDGroupName = addPeopleScreen.iDGroupName)) {
                        launchSingleTop = true
                    }
                },
                idGroupName = addPeopleScreen.iDGroupName,
                groupName = addPeopleScreen.groupName!!
            )
        }

        composable<AddCostScreenObject> {
            AddCostScreen(
                addCostViewModel,
                navigateToMainScreen = {
                    navController.navigate(MainScreenObject(iDGroupName)) {
                        launchSingleTop = true
                    }
                }
            )
        }


        composable<AddPayScreenObject> {
            AddPaymentScreen(
                addPaymentScreenViewModel,
                navigateToMainScreen = {
                    navController.navigate(MainScreenObject(iDGroupName)) {
                        launchSingleTop = true
                    }
                })

        }


        composable<AddPeopleScreenFromMainObject> {
            AddPeopleScreenFromMain(
                addPeopleScreenFromMainViewModel,
                navigateToMainScreen = {
                    navController.navigate(MainScreenObject(iDGroupName)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<EditCostScreenObject> { backStackEntry ->

            val editCostScreen: EditCostScreenObject = backStackEntry.toRoute()

            EditCostScreen(
                idCost = editCostScreen.idCost,
                amount = editCostScreen.amount,
                description = editCostScreen.description,
                personString = editCostScreen.personString,
                editCostScreenViewModel = editCostScreenViewModel,
                navigateToMainScreen = {
                    navController.navigate(MainScreenObject(iDGroupName)) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<ConfigurationScreenObject> {
            ConfigurationScreen(
                configurationScreenViewModel,
                navigateToCustomizeGroup = {
                    navController.navigate(CustomizeGroupScreenObject) {
                        launchSingleTop = true
                    }
                },
                navigateToGroupScreen = {
                    navController.navigate(
                        MainScreenObject(
                            iDGroupName = iDGroupName
                        )
                    ) { launchSingleTop = true }
                },
                navigateToAddPeopleScreen = {
                    navController.navigate(AddPeopleScreenFromMainObject) {
                        launchSingleTop = true
                    }
                },
                navigateToAboutScreen = {
                    navController.navigate(AboutEquifyScreenObject) {
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<CustomizeGroupScreenObject> {
            CustomizeGroupScreen(
                customizeGroupScreenViewModel,
                navigateToConfiguration = {
                    navController.navigate(ConfigurationScreenObject) {
                        launchSingleTop = true
                    }
                },
            )

        }

        composable<DoTheCountsScreenObject> {
            DoTheCountsScreen(
                doTheCountsScreenViewModel,
                navigateToGroupScreen = {
                    navController.navigate(
                        MainScreenObject(iDGroupName = iDGroupName)
                    ) { launchSingleTop = true }
                },
                mainScreenViewModel = mainScreenViewModel,
            )
        }

        composable<SplashScreenObject> {
            SplashScreen(
                splashScreenViewModel,
                navigateToGroupsScreen = {
                    navController.navigate(GroupsScreenObject) {
                        popUpTo(SplashScreenObject) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
            )
        }

        composable<AboutEquifyScreenObject> {
            AboutEquifyScreen(
                navigateBack = {
                    navController.popBackStack()
                },
                aboutScreenViewModel = aboutScreenViewModel
            )
        }
    }
}



