package com.jarica.compartirgastos.core.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.runtime.LaunchedEffect
import androidx.core.view.WindowCompat
import com.jarica.compartirgastos.core.navigation.NavigationWrapper
import com.jarica.compartirgastos.core.presentation.ui.theme.CompartirGastosTheme
import com.jarica.compartirgastos.features.Splash.presentation.SplashScreen.SplashScreenViewModel
import com.jarica.compartirgastos.features.appInfo.presentation.aboutEquify.AboutEquifyScreenViewModel
import com.jarica.compartirgastos.features.balances.presentation.doTheCountsScreen.DoTheCountsScreenViewModel
import com.jarica.compartirgastos.features.balances.presentation.resumeScreen.ResumeViewModel
import com.jarica.compartirgastos.features.costs.presentation.addCostScreen.AddCostScreenViewModel
import com.jarica.compartirgastos.features.costs.presentation.editCostScreen.EditCostScreenViewModel
import com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen.MainScreenViewModel
import com.jarica.compartirgastos.features.groups.presentation.configurationScreen.ConfigurationScreenViewModel
import com.jarica.compartirgastos.features.groups.presentation.configurationScreen.fragments.CustomizeGroupScreenViewModel
import com.jarica.compartirgastos.features.groups.presentation.groupsScreen.GroupsScreenViewModel
import com.jarica.compartirgastos.features.groups.presentation.newGroupScreen.NewGroupViewModel
import com.jarica.compartirgastos.features.payments.presentation.addPayScreen.AddPaymentScreenViewModel
import com.jarica.compartirgastos.features.people.presentation.addPeopleScreen.AddPeopleScreenViewModel
import com.jarica.compartirgastos.features.people.presentation.addPeopleScreenFromMain.AddPeopleScreenFromMainViewModel
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
    private val customizeGroupScreenViewModel: CustomizeGroupScreenViewModel by viewModels()
    private val doTheCountsScreenViewModel: DoTheCountsScreenViewModel by viewModels()
    private val splashScreenViewModel: SplashScreenViewModel by viewModels()
    private val aboutScreenViewModel: AboutEquifyScreenViewModel by viewModels()
    private val resumeScreenViewModel: ResumeViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // Para el caso de 3 botones, desactiva la protección del contraste
        val windowInsetsController = WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.isAppearanceLightNavigationBars =
            true // O false, según el color de fondo
        setContent {

            LaunchedEffect(Unit) {
                groupViewModel.loadAd()
            }

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
                    configurationScreenViewModel,
                    customizeGroupScreenViewModel,
                    doTheCountsScreenViewModel,
                    splashScreenViewModel,
                    aboutScreenViewModel,
                    resumeScreenViewModel
                )
            }
        }
    }
}