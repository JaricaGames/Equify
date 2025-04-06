package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.costsScreen.CostFragment
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.paymentsScreen.PaymentsFragment
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.resumeScreen.ResumeFragment
import com.jarica.compartirgastos.presentation.ui.addCost
import com.jarica.compartirgastos.presentation.ui.addPay
import com.jarica.compartirgastos.presentation.ui.addPeople
import com.jarica.compartirgastos.presentation.ui.costs
import com.jarica.compartirgastos.presentation.ui.doTheCount
import com.jarica.compartirgastos.presentation.ui.payments
import com.jarica.compartirgastos.presentation.ui.resume
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellow
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    idGroup: Int?,
    mainScreenViewModel: MainScreenViewModel,
    navigateToAddCostScreen: () -> Unit,
    navigateToAddPeopleFromGroup: () -> Unit,
    navigateToGroupsScreen: () -> Unit,
    navigateToAddPayScreen: () -> Unit,
    navigateToEditCost: (CostModel) -> Unit,
    navigateToConfiguration: () -> Unit,
) {

    val nameOfGroup: String by mainScreenViewModel.nameOfGroup.observeAsState("")
    val isResumeSelected: Boolean by mainScreenViewModel.isResumeSelected.observeAsState(true)
    val isCostSelected: Boolean by mainScreenViewModel.isCostsSelected.observeAsState(false)

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStatePeopleGroupFragment by produceState<MainUiState>(
        initialValue = MainUiState.Loading,
        key1 = lifecycle,
        key2 = mainScreenViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            mainScreenViewModel.uiStateResumeGroup.collect { value = it }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(top = 16.dp),
                colors = topAppBarColors(
                    containerColor = Transparent,
                    actionIconContentColor = Black,
                    navigationIconContentColor = Black
                ),

                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .size(40.dp),
                        onClick = {
                            navigateToGroupsScreen()
                        }) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = "",


                            )
                    }
                },
                actions = {
                    IconButton(
                        modifier = Modifier
                            .size(40.dp), onClick = {
                            navigateToConfiguration()
                        }) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(R.drawable.settings),
                            contentDescription = "",

                            )
                    }
                },
                title = {
                }
            )
        }
    ) { paddingValues ->
        MainView(
            navigateToAddCostScreen,
            navigateToAddPeopleFromGroup,
            idGroup,
            mainScreenViewModel,
            paddingValues,
            nameOfGroup,
            uiStatePeopleGroupFragment,
            isResumeSelected,
            isCostSelected,
            navigateToAddPayScreen,
            navigateToEditCost
        )
    }


}


@Composable
fun MainView(
    navigateToAddCostScreen: () -> Unit,
    navigateToAddPeopleFromGroup: () -> Unit,
    idGroup: Int?,
    mainScreenViewModel: MainScreenViewModel,
    paddingValues: PaddingValues,
    nameOfGroup: String,
    uiStatePeopleGroupFragment: MainUiState,
    isResumeSelected: Boolean,
    isCostSelected: Boolean,
    navigateToAddPayScreen: () -> Unit,
    navigateToEditCost: (CostModel) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(100.dp))
        //HEADER
        Header(nameOfGroup)

        //BOXS DE SELECCIONAR ACCION
        ActionsBoxes(
            mainScreenViewModel,
            navigateToAddCostScreen,
            uiStatePeopleGroupFragment,
            navigateToAddPeopleFromGroup,
            navigateToAddPayScreen
        )
        Spacer(Modifier.height(16.dp))

        //BOXS SELECCIONAR FRAGMETS (RESUME O GASTOS)
        ChooseScreen(mainScreenViewModel, isResumeSelected, isCostSelected)
        Spacer(modifier = Modifier.size(32.dp))

        // LISTADOS (RESUMEN O GASTOS)
        if (isResumeSelected) {
            ResumeFragment(idGroup, mainScreenViewModel)
        } else {
            if (isCostSelected) {
                CostFragment(idGroup, mainScreenViewModel, navigateToEditCost)
            } else {
                PaymentsFragment(idGroup, mainScreenViewModel)
            }
        }
    }
}

@Composable
fun ChooseScreen(
    mainScreenViewModel: MainScreenViewModel,
    isResumeSelected: Boolean,
    isCostSelected: Boolean
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
    ) {
        if (isResumeSelected) {

            //RESUME FRAGMENT
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 32.dp))
                    .background(Black)
                    .padding(vertical = 10.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    resume,
                    fontFamily = rubik,
                    color = DarkYellow,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(vertical = 10.dp)
                    .clickable { mainScreenViewModel.onCostSelected() },

                contentAlignment = Alignment.Center
            ) {
                Text(
                    costs,
                    fontFamily = rubik,
                    color = Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(vertical = 10.dp)
                    .clickable { mainScreenViewModel.onPaymentsSelected() },

                contentAlignment = Alignment.Center
            ) {
                Text(
                    payments,
                    fontFamily = rubik,
                    color = Black,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            }
        } else {

            if (isCostSelected) {

                //COSt FRAGMENT
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(vertical = 10.dp)
                        .clickable { mainScreenViewModel.onResumeSelected() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        resume,
                        fontFamily = rubik,
                        color = Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp

                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(
                            RoundedCornerShape(
                                topEnd = 16.dp,
                                bottomStart = 32.dp,
                                bottomEnd = 32.dp,
                                topStart = 16.dp
                            )
                        )
                        .background(Black)
                        .padding(vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        costs,
                        fontFamily = rubik,
                        color = DarkYellow,
                        fontWeight = FontWeight.SemiBold,
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(vertical = 10.dp)
                        .clickable { mainScreenViewModel.onPaymentsSelected() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        payments,
                        fontFamily = rubik,
                        color = Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
            } else {
                //PAYMENTS FRAGMENT
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 32.dp))
                        .padding(vertical = 10.dp)
                        .clickable {
                            mainScreenViewModel.onResumeSelected()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        resume,
                        fontFamily = rubik,
                        color = Black,
                        fontSize = 12.sp
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .padding(vertical = 10.dp)
                        .clickable { mainScreenViewModel.onCostSelected() },

                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        costs,
                        fontFamily = rubik,
                        color = Black,
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp
                    )
                }
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 16.dp, bottomStart = 32.dp))
                        .background(Black)
                        .weight(1f)
                        .padding(vertical = 10.dp),

                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        payments,
                        fontFamily = rubik,
                        color = DarkYellow,
                        fontWeight = FontWeight.SemiBold,
                    )
                }

            }
        }
    }
}

@Composable
fun ActionsBoxes(
    mainScreenViewModel: MainScreenViewModel,
    navigateToAddCostScreen: () -> Unit,
    peopleList: MainUiState,
    navigateToAddPeopleFromGroup: () -> Unit,
    navigateToAddPayScreen: () -> Unit,
) {

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(White)
                    .size(70.dp)
                    .clickable {
                        navigateToAddCostScreen()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.moneycash),
                    "",
                    modifier = Modifier.size(40.dp),
                    tint = Black
                )
            }
            Spacer(Modifier.size(3.dp))
            Text(
                addCost,
                fontSize = 12.sp,
                fontFamily = rubik,
                fontWeight = FontWeight.Normal,
                color = Black,
                textAlign = TextAlign.Center
            )
        }

        // Box Añadir persona

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(White)
                    .size(70.dp)
                    .clickable {
                        navigateToAddPeopleFromGroup()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.people_add),
                    "",
                    tint = Black,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(Modifier.size(3.dp))
            Text(
                addPeople,
                fontSize = 12.sp,
                fontFamily = rubik,
                fontWeight = FontWeight.Normal,
                color = Black,
                textAlign = TextAlign.Center
            )
        }

        // Box Añadir Pago

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        navigateToAddPayScreen()
                    }
                    .background(White)
                    .size(70.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.addpay),
                    "",
                    tint = Black,
                    modifier = Modifier.size(40.dp)
                )

            }
            Spacer(Modifier.size(3.dp))
            Text(
                addPay,
                fontSize = 12.sp,
                fontFamily = rubik,
                fontWeight = FontWeight.Normal,
                color = Black,
                textAlign = TextAlign.Center
            )
        }

        // Box Echar cuentas

        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(Black)
                    .size(70.dp)
                    .clickable {
                        when (peopleList) {

                            is MainUiState.Error -> {}

                            is MainUiState.Loading -> {}

                            is MainUiState.Success -> {
                                mainScreenViewModel.doTheCounts((peopleList).peopleList)

                            }
                        }


                        mainScreenViewModel.text()
                    }, contentAlignment = Alignment.Center
            ) {

                Icon(
                    painterResource(R.drawable.arrowsclock),
                    "",
                    tint = DarkYellow,
                    modifier = Modifier.size(40.dp)
                )
            }
            Spacer(Modifier.size(3.dp))
            Text(
                doTheCount,
                fontSize = 12.sp,
                fontFamily = rubik,
                fontWeight = FontWeight.Normal,
                color = Black,
                textAlign = TextAlign.Center,
            )
        }


    }
}

@Composable
fun Header(nameOfGroup: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp, horizontal = 30.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            nameOfGroup,
            fontSize = 30.sp,
            color = Black,
            fontFamily = rubik,
            fontWeight = FontWeight.W600
        )
    }
}



