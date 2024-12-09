package com.jarica.compartirgastos.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jarica.compartirgastos.presentation.initial.InitialScreen
import com.jarica.compartirgastos.presentation.newGroup.NewGroupScreen

@Composable
fun NavigationWrapper(){

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Initial){
        composable<Initial> {
            InitialScreen ({ navController.navigate(NewGroup) })
        }
        composable<NewGroup> {
            NewGroupScreen( navigateToInitial = { navController.navigate(Initial) })
        }

    }
}