package com.jarica.compartirgastos.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.jarica.compartirgastos.core.navigation.NavigationWrapper
import com.jarica.compartirgastos.presentation.createGroupScreens.addPeopleScreen.AddPeopleScreenViewModel
import com.jarica.compartirgastos.presentation.createGroupScreens.newGroupScreen.NewGroupViewModel
import com.jarica.compartirgastos.presentation.groupsScreen.GroupsScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.addCostScreen.AddCostScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.addPayScreen.AddPaymentScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.addPeopleScreenFromMain.AddPeopleScreenFromMainViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.ConfigurationScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.editCostScreen.EditCostScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.ui.theme.CompartirGastosTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val newGroupViewModel: NewGroupViewModel by viewModels()
    private val groupViewModel: MainScreenViewModel by viewModels()
    private val addPeopleViewModel: AddPeopleScreenViewModel by viewModels()
    private val addPeopleScreenFromMainViewModel: AddPeopleScreenFromMainViewModel by viewModels()
    private val addCostViewModel: AddCostScreenViewModel by viewModels()
    private val groupScreenViewModel: GroupsScreenViewModel by viewModels()
    private val addPaymentScreenViewModel: AddPaymentScreenViewModel by viewModels()
    private val editCostScreenViewModel: EditCostScreenViewModel by viewModels()
    private val configurationScreenViewModel: ConfigurationScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CompartirGastosTheme {
                NavigationWrapper(
                    newGroupViewModel,
                    groupViewModel,
                    addPeopleViewModel,
                    addCostViewModel,
                    groupScreenViewModel,
                    addPeopleScreenFromMainViewModel,
                    addPaymentScreenViewModel,
                    editCostScreenViewModel,
                    configurationScreenViewModel
                )
            }
        }
    }
}
