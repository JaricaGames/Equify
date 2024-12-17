package com.jarica.compartirgastos.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jarica.compartirgastos.presentation.addPeopleScreen.AddPeopleScreen
import com.jarica.compartirgastos.presentation.addPeopleScreen.AddPeopleScreenViewModel
import com.jarica.compartirgastos.presentation.groupScreen.GroupScreen
import com.jarica.compartirgastos.presentation.groupScreen.GroupScreenViewModel
import com.jarica.compartirgastos.presentation.initialScreen.InitialScreen
import com.jarica.compartirgastos.presentation.newGroupScreen.NewGroupScreen
import com.jarica.compartirgastos.presentation.newGroupScreen.NewGroupViewModel

@Composable
fun NavigationWrapper(
    newGroupViewModel: NewGroupViewModel,
    groupViewModel: GroupScreenViewModel,
    addPeopleViewModel: AddPeopleScreenViewModel
) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = AddPeopleScreen){
        composable<InitialScreen> {
            InitialScreen { navController.navigate(NewGroupScreen) }
        }
        composable<NewGroupScreen> {
            NewGroupScreen(newGroupViewModel,  navigateToInitial = { navController.navigate(InitialScreen) }, navigateToAddPeople = {navController.navigate(AddPeopleScreen)})
        }
        composable<GroupScreen> {
            GroupScreen(groupViewModel)
        }
        composable<AddPeopleScreen> {
            AddPeopleScreen(addPeopleViewModel)
        }

    }
}