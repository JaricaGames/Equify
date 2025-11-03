package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
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
import com.jarica.compartirgastos.presentation.ui.theme.VeryDarkBlue
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans

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

    /* Scaffold(
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
                             .size(75.dp),
                         onClick = {
                             navigateToGroupsScreen()
                         }
                     ) {
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
                             painter = painterResource(R.drawable.ellipsis),
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

 */
    MainView(
        navigateToAddCostScreen,
        navigateToAddPeopleFromGroup,
        idGroup,
        mainScreenViewModel,
        nameOfGroup,
        doTheCountsScreenViewModel,
        isResumeSelected,
        isCostSelected,
        navigateToAddPayScreen,
        navigateToEditCost,
        navigateToDoTheCounts,
        uiStatePeopleGroupFragment,
        onDoTheCountsClicked,
        navigateToGroupsScreen,
        navigateToConfiguration
    )
}


@Composable
fun MainView(
    navigateToAddCostScreen: () -> Unit,
    navigateToAddPeopleFromGroup: () -> Unit,
    idGroup: Int?,
    mainScreenViewModel: MainScreenViewModel,
    nameOfGroup: String,
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    isResumeSelected: Boolean,
    isCostSelected: Boolean,
    navigateToAddPayScreen: () -> Unit,
    navigateToEditCost: (CostModel) -> Unit,
    navigateToDoTheCounts: () -> Unit,
    uiStatePeopleGroupFragment: MainUiState,
    onDoTheCountsClicked: () -> Unit,
    navigateToGroupsScreen: () -> Unit,
    navigateToConfiguration: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        //HEADER
        Header(nameOfGroup, navigateToGroupsScreen, navigateToConfiguration)
        Spacer(Modifier.weight(0.02f))
        //BOXS DE SELECCIONAR ACCION
        ActionsBoxes(
            mainScreenViewModel,
            navigateToAddCostScreen,
            doTheCountsScreenViewModel,
            navigateToAddPeopleFromGroup,
            navigateToAddPayScreen,
            navigateToDoTheCounts,
            uiStatePeopleGroupFragment,
            onDoTheCountsClicked,
        )
        Spacer(Modifier.weight(0.02f))
        //BOXS SELECCIONAR FRAGMETS (RESUME O GASTOS)
        ChooseScreen(mainScreenViewModel, isResumeSelected, isCostSelected)
        Spacer(Modifier.weight(0.02f))
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
            .padding(horizontal = 16.dp)
            .background(White),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        if (isResumeSelected) {

            //RESUME FRAGMENT
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(16.dp))
                    .background(VeryDarkBlue)
                    .padding(vertical = 6.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    resume,
                    fontSize = 12.sp,
                    color = White,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal
                )
            }

            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, shape = RoundedCornerShape(16.dp), color = Black.copy(0.1f))
                    .padding(vertical = 6.dp)
                    .clickable { mainScreenViewModel.onCostSelected() },

                contentAlignment = Alignment.Center
            ) {
                Text(
                    costs,
                    fontSize = 11.sp,
                    color = Black,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal
                )
            }
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, shape = RoundedCornerShape(16.dp), color = Black.copy(0.1f))
                    .padding(vertical = 6.dp)
                    .clickable { mainScreenViewModel.onPaymentsSelected() },

                contentAlignment = Alignment.Center
            ) {
                Text(
                    payments,
                    fontSize = 11.sp,
                    color = Black,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal
                )
            }
        } else {

            if (isCostSelected) {

                //COSt FRAGMENT
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, shape = RoundedCornerShape(16.dp), color = Black.copy(0.1f))
                        .padding(vertical = 6.dp)
                        .clickable { mainScreenViewModel.onResumeSelected() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        resume,
                        fontSize = 11.sp,
                        color = Black,
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.Normal

                    )
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(VeryDarkBlue)
                        .padding(vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        costs,
                        fontSize = 12.sp,
                        color = White,
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.Normal
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, shape = RoundedCornerShape(16.dp), color = Black.copy(0.1f))
                        .padding(vertical = 6.dp)
                        .clickable { mainScreenViewModel.onPaymentsSelected() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        payments,
                        fontSize = 11.sp,
                        color = Black,
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.Normal
                    )
                }
            } else {
                //PAYMENTS FRAGMENT
                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, shape = RoundedCornerShape(16.dp), color = Black.copy(0.1f))
                        .padding(vertical = 6.dp)
                        .clickable {
                            mainScreenViewModel.onResumeSelected()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        resume,
                        fontSize = 11.sp,
                        color = Black,
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.Normal
                    )
                }

                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, shape = RoundedCornerShape(16.dp), color = Black.copy(0.1f))
                        .padding(vertical = 6.dp)
                        .clickable { mainScreenViewModel.onCostSelected() },

                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        costs,
                        fontSize = 11.sp,
                        color = Black,
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.Normal
                    )
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(16.dp))
                        .background(VeryDarkBlue)
                        .padding(vertical = 6.dp),

                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        payments,
                        fontSize = 12.sp,
                        color = White,
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.Normal
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
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .clickable {
                    navigateToAddCostScreen()
                }
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
                    .padding(vertical = 4.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(White),
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
            modifier = Modifier
                .weight(1f)
                .clickable {
                    navigateToAddPeopleFromGroup()
                }
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
                    .padding(vertical = 4.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(White),
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
            modifier = Modifier
                .weight(1f)
                .clickable {
                    navigateToAddPayScreen()
                }
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
                    .padding(vertical = 4.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    )
                    .background(White),
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
            modifier = Modifier
                .weight(1f)
                .clickable {
                    onDoTheCountsClicked()
                    when (peopleList) {

                        is MainUiState.Error -> {}
                        is MainUiState.Loading -> {}
                        is MainUiState.Success -> {
                            doTheCountsScreenViewModel.doTheCounts((peopleList).peopleList)
                            navigateToDoTheCounts()
                        }
                    }
                }
                .clip(shape = RoundedCornerShape(16.dp))
                .background(DarkOrange),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {

            Box(
                modifier = Modifier
                    .padding(vertical = 4.dp)
                    .clip(
                        RoundedCornerShape(8.dp)
                    ), contentAlignment = Alignment.Center
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
fun Header(
    nameOfGroup: String,
    navigateToGroupsScreen: () -> Unit,
    navigateToConfiguration: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
            .background(color = DarkBlue).padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 25.dp)) {
            IconButton(
                onClick = {
                    navigateToGroupsScreen()
                }
            ) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = "",
                    tint = White
                )
            }
            Spacer(Modifier.weight(1f))
            IconButton(
                onClick = {
                    navigateToConfiguration()
                }) {
                Icon(
                    modifier = Modifier.size(25.dp),
                    painter = painterResource(R.drawable.ellipsis),
                    contentDescription = "",
                    tint = White
                    )
            }
        }
        Text(
            nameOfGroup,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp, vertical = 8.dp),
            textAlign = TextAlign.Start,
            fontSize = 26.sp,
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




