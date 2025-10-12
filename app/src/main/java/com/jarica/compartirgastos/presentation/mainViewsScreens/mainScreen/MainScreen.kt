package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.doTheCountsScreen.DoTheCountsScreenViewModel
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
import com.jarica.compartirgastos.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellow
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans
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
    navigateToDoTheCounts: () -> Unit,
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    onDoTheCountsClicked: () -> Unit
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
                colors = topAppBarColors(
                    containerColor = DarkBlue,
                    actionIconContentColor = White,
                    navigationIconContentColor = White
                ),

                navigationIcon = {
                    IconButton(
                        modifier = Modifier
                            .size(50.dp),
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
                            .size(50.dp),
                        onClick = {
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
            doTheCountsScreenViewModel,
            isResumeSelected,
            isCostSelected,
            navigateToAddPayScreen,
            navigateToEditCost,
            navigateToDoTheCounts,
            uiStatePeopleGroupFragment,
            onDoTheCountsClicked
        )
        BackHandler {
            // Aquí decides qué hacer al pulsar atrás
            navigateToGroupsScreen()
        }
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
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    isResumeSelected: Boolean,
    isCostSelected: Boolean,
    navigateToAddPayScreen: () -> Unit,
    navigateToEditCost: (CostModel) -> Unit,
    navigateToDoTheCounts: () -> Unit,
    uiStatePeopleGroupFragment: MainUiState,
    onDoTheCountsClicked: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        // .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(70.dp))
        //HEADER
        Header(nameOfGroup)
        Spacer(Modifier.height(16.dp))
        //BOXS DE SELECCIONAR ACCION
        ActionsBoxes(
            mainScreenViewModel,
            navigateToAddCostScreen,
            doTheCountsScreenViewModel,
            navigateToAddPeopleFromGroup,
            navigateToAddPayScreen,
            navigateToDoTheCounts,
            uiStatePeopleGroupFragment,
            onDoTheCountsClicked
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
        Spacer(modifier = Modifier.weight(1f))
        BannerAdViewMainScreen()
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
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    navigateToAddPeopleFromGroup: () -> Unit,
    navigateToAddPayScreen: () -> Unit,
    navigateToDoTheCounts: () -> Unit,
    peopleList: MainUiState,
    onDoTheCountsClicked: () -> Unit,
) {

    Row(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
            Column(
                modifier = Modifier.padding(vertical = 8.dp)
                    .weight(1f)
                    .clip(shape = RoundedCornerShape(16.dp))
                    .border(
                        width = 1.dp,
                        color = DarkOrange.copy(alpha = 0.4f),
                        shape = RoundedCornerShape(16.dp)
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,

                ) {

                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .background(White)
                        .clickable {
                            navigateToAddCostScreen()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painterResource(R.drawable.moneycash),
                        "",
                        modifier = Modifier.size(40.dp),
                        tint = DarkOrange
                    )
                }
                Text(
                    addCost,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.SemiBold,
                    color = DarkOrange,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontSize = 11.sp,
                        lineHeight = 15.sp // 🔹 menos de 18 si quieres líneas más cercanas
                    )
                )
                Spacer(Modifier.size(3.dp))
            }

        // Box Añadir persona

        Column(
            modifier = Modifier.padding(vertical = 8.dp)
                .weight(1f)
                .clip(shape = RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = DarkOrange.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(White)
                    .clickable {
                        navigateToAddPeopleFromGroup()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.people_add),
                    "",
                    modifier = Modifier.size(40.dp),
                    tint = DarkOrange
                )
            }
            Text(
                addPeople,
                fontFamily = parkinsans,
                fontWeight = FontWeight.SemiBold,
                color = DarkOrange,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 11.sp,
                    lineHeight = 15.sp // 🔹 menos de 18 si quieres líneas más cercanas
                )
            )
            Spacer(Modifier.size(3.dp))
        }

        // Box Añadir Pago

        Column(
            modifier = Modifier.padding(vertical = 8.dp)
                .weight(1f)
                .clip(shape = RoundedCornerShape(16.dp))
                .border(
                    width = 1.dp,
                    color = DarkOrange.copy(alpha = 0.4f),
                    shape = RoundedCornerShape(16.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(White)
                    .clickable {
                        navigateToAddPayScreen()
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(R.drawable.addpay),
                    "",
                    modifier = Modifier.size(40.dp),
                    tint = DarkOrange
                )

            }
            Spacer(Modifier.size(3.dp))
            Text(
                addPay,
                fontFamily = parkinsans,
                fontWeight = FontWeight.SemiBold,
                color = DarkOrange,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 11.sp,
                    lineHeight = 15.sp // 🔹 menos de 18 si quieres líneas más cercanas
                )
            )
            Spacer(Modifier.size(3.dp))
        }

        // Box Echar cuentas

        Column(
            modifier = Modifier.padding(vertical = 8.dp)
                .weight(1f)
                .clip(shape = RoundedCornerShape(16.dp))
                .background(DarkOrange),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Box(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .clickable {
                        onDoTheCountsClicked()
                        //  navigateToDoTheCounts()
                        when (peopleList) {

                            is MainUiState.Error -> {}

                            is MainUiState.Loading -> {}

                            is MainUiState.Success -> {
                                doTheCountsScreenViewModel.doTheCounts((peopleList).peopleList)
                                navigateToDoTheCounts()
                            }
                        }


                        //mainScreenViewModel.text()
                    }, contentAlignment = Alignment.Center
            ) {

                Icon(
                    painterResource(R.drawable.arrowsclock),
                    "",
                    modifier = Modifier.size(40.dp),
                    tint = White
                )
            }
            Spacer(Modifier.size(3.dp))
            Text(
                doTheCount,
                fontFamily = parkinsans,
                fontWeight = FontWeight.SemiBold,
                color = White,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 11.sp,
                    lineHeight = 15.sp // 🔹 menos de 18 si quieres líneas más cercanas
                )
            )
            Spacer(Modifier.size(3.dp))
        }


    }
}

@Composable
fun Header(nameOfGroup: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
            .background(color = DarkBlue)
            .padding(vertical = 30.dp, horizontal = 40.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            nameOfGroup,
            fontSize = 30.sp,
            color = White,
            fontFamily = parkinsans,
            fontWeight = FontWeight.W600
        )
    }
}

@SuppressLint("MissingPermission")
@Composable
fun BannerAdViewMainScreen() {
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.FULL_BANNER)
                adUnitId = "ca-app-pub-4979320410432560/4688560090"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}




