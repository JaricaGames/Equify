package com.jarica.compartirgastos.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.jarica.compartirgastos.core.navigation.NavigationWrapper
import com.jarica.compartirgastos.presentation.mainViewScreens.addCostScreen.AddCostScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewScreens.addPeopleScreenFromMain.AddPeopleScreenFromMainViewModel
import com.jarica.compartirgastos.presentation.createGroupScreens.addPeopleScreen.AddPeopleScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewScreens.costsScreen.CostsScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewScreens.groupsScreen.GroupsScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewScreens.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.createGroupScreens.newGroupScreen.NewGroupViewModel
import com.jarica.compartirgastos.presentation.ui.theme.CompartirGastosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity  : ComponentActivity() {

    private val newGroupViewModel: NewGroupViewModel by viewModels()
    private val groupViewModel: MainScreenViewModel by viewModels()
    private val addPeopleViewModel: AddPeopleScreenViewModel by viewModels()
    private val addPeopleScreenFromMainViewModel: AddPeopleScreenFromMainViewModel by viewModels()
    private val addCostViewModel: AddCostScreenViewModel by viewModels()
    private val costViewModel: CostsScreenViewModel by viewModels()
    private val groupScreenViewModel: GroupsScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompartirGastosTheme {
                NavigationWrapper(newGroupViewModel, groupViewModel, addPeopleViewModel, addCostViewModel, costViewModel, groupScreenViewModel, addPeopleScreenFromMainViewModel)
            }
        }
    }
}
