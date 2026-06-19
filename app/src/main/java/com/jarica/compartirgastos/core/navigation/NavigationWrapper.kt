package com.jarica.compartirgastos.core.navigation

import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.features.appInfo.presentation.aboutEquify.AboutEquifyScreen
import com.jarica.compartirgastos.features.appInfo.presentation.aboutEquify.AboutEquifyScreenViewModel
import com.jarica.compartirgastos.features.appInfo.presentation.legal.LegalContent
import com.jarica.compartirgastos.features.appInfo.presentation.legal.LegalScreen
import com.jarica.compartirgastos.features.balances.presentation.doTheCountsScreen.DoTheCountsScreen
import com.jarica.compartirgastos.features.balances.presentation.doTheCountsScreen.DoTheCountsScreenViewModel
import com.jarica.compartirgastos.features.balances.presentation.resumeScreen.ResumeViewModel
import com.jarica.compartirgastos.features.costs.presentation.addCostScreen.AddCostScreen
import com.jarica.compartirgastos.features.costs.presentation.addCostScreen.AddCostScreenViewModel
import com.jarica.compartirgastos.features.costs.presentation.costsScreen.CostsViewModel
import com.jarica.compartirgastos.features.costs.presentation.editCostScreen.EditCostScreen
import com.jarica.compartirgastos.features.costs.presentation.editCostScreen.EditCostScreenViewModel
import com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen.GroupDetailsViewModel
import com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen.MainScreen
import com.jarica.compartirgastos.features.groups.presentation.configurationScreen.ConfigurationScreen
import com.jarica.compartirgastos.features.groups.presentation.configurationScreen.ConfigurationScreenViewModel
import com.jarica.compartirgastos.features.groups.presentation.editGroupNameScreen.CustomizeGroupScreen
import com.jarica.compartirgastos.features.groups.presentation.editGroupNameScreen.EditGroupNameScreenViewModel
import com.jarica.compartirgastos.features.groups.presentation.groupsScreen.GroupsScreen
import com.jarica.compartirgastos.features.groups.presentation.groupsScreen.GroupsScreenViewModel
import com.jarica.compartirgastos.features.groups.presentation.initialScreen.InitialScreen
import com.jarica.compartirgastos.features.groups.presentation.newGroupScreen.NewGroupScreen
import com.jarica.compartirgastos.features.groups.presentation.newGroupScreen.NewGroupViewModel
import com.jarica.compartirgastos.features.payments.presentation.addPayScreen.AddPaymentScreen
import com.jarica.compartirgastos.features.payments.presentation.addPayScreen.AddPaymentScreenViewModel
import com.jarica.compartirgastos.features.payments.presentation.editPaymentScreen.EditPaymentScreen
import com.jarica.compartirgastos.features.payments.presentation.editPaymentScreen.EditPaymentViewModel
import com.jarica.compartirgastos.features.payments.presentation.paymentsScreen.PaymentsScreenViewModel
import com.jarica.compartirgastos.features.people.presentation.addPeopleScreen.AddPeopleScreen
import com.jarica.compartirgastos.features.people.presentation.addPeopleScreen.AddPeopleScreenViewModel
import com.jarica.compartirgastos.features.people.presentation.addPeopleScreenFromMain.AddPeopleScreenFromMain
import com.jarica.compartirgastos.features.people.presentation.addPeopleScreenFromMain.AddPeopleScreenFromMainViewModel
import com.jarica.compartirgastos.features.splash.presentation.splashScreen.SplashScreen


@RequiresApi(Build.VERSION_CODES.S)
@Composable
fun NavigationWrapper(
    newGroupViewModel: NewGroupViewModel,
    mainScreenViewModel: GroupDetailsViewModel,
    addPeopleViewModel: AddPeopleScreenViewModel,
    addCostViewModel: AddCostScreenViewModel,
    groupScreenViewModel: GroupsScreenViewModel,
    addPeopleScreenFromMainViewModel: AddPeopleScreenFromMainViewModel,
    addPaymentScreenViewModel: AddPaymentScreenViewModel,
    editCostScreenViewModel: EditCostScreenViewModel,
    configurationScreenViewModel: ConfigurationScreenViewModel,
    customizeGroupScreenViewModel: EditGroupNameScreenViewModel,
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    aboutScreenViewModel: AboutEquifyScreenViewModel,
    resumeViewModel: ResumeViewModel,
    costsViewModel: CostsViewModel,
    paymentsViewModel: PaymentsScreenViewModel,
    editPaymentsViewModel: EditPaymentViewModel
) {

    val activity = LocalContext.current as? Activity
    val navController = rememberNavController()

    NavHost(
        navController = navController, startDestination = SplashScreenObject

    ) {

        composable<GroupsScreenObject> {

            GroupsScreen(
                groupScreenViewModel,
                mainScreenViewModel,
                navigateToMainScreen = { selectedId ->
                    navController.navigate(MainScreenObject(selectedId)) {
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
            InitialScreen(
                navigateToNewGroup = {
                    navController.navigate(NewGroupScreenObject) {
                        launchSingleTop = true
                    }
                })
        }

        composable<NewGroupScreenObject> {

            NewGroupScreen(
                newGroupViewModel,
                navigateToAddPeople = { idGroupName, groupName ->
                    navController.navigate(
                        AddPeopleScreenObject(idGroupName, groupName)
                    ) { launchSingleTop = true }
                },
                navigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable<MainScreenObject> { backStackEntry ->

            val groupScreen: MainScreenObject = backStackEntry.toRoute()
            val currentId = groupScreen.iDGroupName

            MainScreen(
                currentId,
                mainScreenViewModel,
                resumeViewModel,
                costsViewModel,
                paymentsViewModel,
                editCostScreenViewModel = editCostScreenViewModel,
                navigateToGroupsScreen = {
                    navController.navigate(GroupsScreenObject) {
                        popUpTo(GroupsScreenObject) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                navigateToAddCostScreen = {
                    navController.navigate(AddCostScreenObject(iDGroupName = currentId)) {
                        launchSingleTop = true
                    }
                },
                navigateToAddPeopleFromGroup = {
                    navController.navigate(
                        AddPeopleScreenFromMainObject(iDGroupName = currentId)
                    ) { launchSingleTop = true }
                },
                navigateToAddPayScreen = {
                    navController.navigate(AddPayScreenObject(iDGroupName = currentId)) {
                        launchSingleTop = true
                    }
                },
                navigateToEditCost = { costToEdit ->
                    navController.navigate(
                        EditCostScreenObject(
                            idCost = costToEdit.idCost,
                            amount = costToEdit.amount,
                            description = costToEdit.description,
                            iDGroupName = currentId

                        )
                    ) { launchSingleTop = true }
                },
                navigateToConfiguration = {
                    navController.navigate(ConfigurationScreenObject(iDGroupName = currentId)) {
                        launchSingleTop = true
                    }
                },
                navigateToDoTheCounts = {
                    navController.navigate(DoTheCountsScreenObject(iDGroupName = currentId)) {
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
                },
                navigateToEditPayments = { paymentToEdit ->
                    navController.navigate(
                        EditPaymentScreenObject(
                            idGroup = paymentToEdit.idGroup,
                            idPayment = paymentToEdit.idPayment,
                            amount = paymentToEdit.amount,
                            personWhoPay = paymentToEdit.idPersonWhoPay,
                            personWhoReceive = paymentToEdit.idPersonWhoReceive
                        )
                    )
                }
            )
        }

        composable<AddPeopleScreenObject> { backStackEntry ->

            val addPeopleScreen: AddPeopleScreenObject = backStackEntry.toRoute()

            AddPeopleScreen(
                addPeopleViewModel = addPeopleViewModel,
                navigateToNewGroupScreen = {
                    navController.popBackStack()
                },

                navigateToMainScreen = {
                    navController.navigate(MainScreenObject(iDGroupName = addPeopleScreen.iDGroupName)) {
                        launchSingleTop = true
                    }
                },
                idGroupName = addPeopleScreen.iDGroupName,
                groupName = addPeopleScreen.groupName!!,
                mainScreenViewModel = mainScreenViewModel
            )
        }

        composable<AddCostScreenObject> { backStackEntry ->

            val args: AddCostScreenObject = backStackEntry.toRoute()
            val groupId = args.iDGroupName

            AddCostScreen(
                addCostViewModel,
                idGroupName = groupId,
                navigateToMainScreen = {
                    navController.popBackStack()
                }
            )
        }


        composable<AddPayScreenObject> { backStackEntry ->
            val args: AddPayScreenObject = backStackEntry.toRoute()
            val groupId = args.iDGroupName

            AddPaymentScreen(
                groupId,
                addPaymentScreenViewModel,
                navigateToMainScreen = {
                    navController.popBackStack()
                })

        }


        composable<AddPeopleScreenFromMainObject> { backStackEntry ->

            val args: AddPeopleScreenFromMainObject = backStackEntry.toRoute()
            val iDGroupName = args.iDGroupName

            AddPeopleScreenFromMain(
                iDGroupName,
                addPeopleScreenFromMainViewModel,
                navigateToMainScreen = {
                    navController.popBackStack()
                }
            )
        }

        composable<EditCostScreenObject> { backStackEntry ->

            val editCostScreen: EditCostScreenObject = backStackEntry.toRoute()

            EditCostScreen(
                idCost = editCostScreen.idCost,
                amount = editCostScreen.amount,
                description = editCostScreen.description,
                //personString = editCostScreen.personString,
                editCostScreenViewModel = editCostScreenViewModel,
                navigateToMainScreen = {
                    navController.popBackStack()
                }
            )
        }

        composable<ConfigurationScreenObject> { backStackEntry ->
            val args: ConfigurationScreenObject = backStackEntry.toRoute()
            val iDGroupName = args.iDGroupName

            ConfigurationScreen(
                configurationScreenViewModel,
                iDGroupName,
                navigateToCustomizeGroup = {
                    navController.navigate(CustomizeGroupScreenObject(iDGroupName)) {
                        launchSingleTop = true
                    }
                },
                navigateBack = {
                    navController.popBackStack()
                },
                navigateToGroupsList = {
                    navController.navigate(GroupsScreenObject) {
                        popUpTo(GroupsScreenObject) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                },
                navigateToAddPeopleScreen = {
                    navController.navigate(AddPeopleScreenFromMainObject(iDGroupName)) {
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

        composable<CustomizeGroupScreenObject> { backStackEntry ->
            val args: CustomizeGroupScreenObject = backStackEntry.toRoute()
            val iDGroupName = args.iDGroupName

            CustomizeGroupScreen(
                idGroupName = iDGroupName,
                customizeGroupScreenViewModel,
                navigateToGroupsDetails = {
                    navController.popBackStack()
                },
            )

        }

        composable<DoTheCountsScreenObject> { backStackEntry ->
            val args: DoTheCountsScreenObject = backStackEntry.toRoute()
            val currentGroupId = args.iDGroupName

            DoTheCountsScreen(
                doTheCountsScreenViewModel,
                navigateToGroupScreen = {
                    navController.popBackStack()
                },
                idGroupName = currentGroupId
            )
        }

        composable<SplashScreenObject> {
            SplashScreen(
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
                navigateToPrivacy = {
                    navController.navigate(PrivacyPolicyScreenObject) {
                        launchSingleTop = true
                    }
                },
                navigateToTerms = {
                    navController.navigate(TermsScreenObject) {
                        launchSingleTop = true
                    }
                },
                aboutScreenViewModel = aboutScreenViewModel
            )
        }

        composable<PrivacyPolicyScreenObject> {
            LegalScreen(
                title       = stringResource(R.string.about_privacy_label),
                lastUpdated = LegalContent.LAST_UPDATED,
                sections    = LegalContent.privacyPolicy,
                onBack      = { navController.popBackStack() }
            )
        }

        composable<TermsScreenObject> {
            LegalScreen(
                title       = stringResource(R.string.about_terms_label),
                lastUpdated = LegalContent.LAST_UPDATED,
                sections    = LegalContent.terms,
                onBack      = { navController.popBackStack() }
            )
        }

        composable<EditPaymentScreenObject> { backStackEntry ->

            val editPaymentScreen: EditPaymentScreenObject = backStackEntry.toRoute()
            editPaymentScreen.idGroup

            EditPaymentScreen(
                idPayment = editPaymentScreen.idPayment,
                amount = editPaymentScreen.amount,
                personWhoPay = editPaymentScreen.personWhoPay,
                personWhoReceive = editPaymentScreen.personWhoReceive,
                editPaymentsViewModel = editPaymentsViewModel,
                navigateToMainScreen = {
                    navController.popBackStack()
                }
            )
        }
    }
}



