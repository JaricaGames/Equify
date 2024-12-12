package com.jarica.compartirgastos.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jarica.compartirgastos.presentation.initial.InitialScreen
import com.jarica.compartirgastos.presentation.newGroup.NewGroupScreen
import com.jarica.compartirgastos.presentation.newGroup.NewGroupViewModel

@Composable
fun NavigationWrapper(newGroupViewModel: NewGroupViewModel) {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Initial){
        composable<Initial> {
            InitialScreen ({ navController.navigate(NewGroup) })
        }
        composable<NewGroup> {
            NewGroupScreen(newGroupViewModel,  navigateToInitial = { navController.navigate(Initial) })
        }

    }
}