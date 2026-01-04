package com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.presentation.ui.addCost
import com.jarica.compartirgastos.core.presentation.ui.addPay
import com.jarica.compartirgastos.core.presentation.ui.addPeople
import com.jarica.compartirgastos.core.presentation.ui.costs
import com.jarica.compartirgastos.core.presentation.ui.doTheCount
import com.jarica.compartirgastos.core.presentation.ui.payments
import com.jarica.compartirgastos.core.presentation.ui.resume
import com.jarica.compartirgastos.core.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.core.presentation.ui.totalCostText
import com.jarica.compartirgastos.features.balances.presentation.doTheCountsScreen.DoTheCountsScreenViewModel
import com.jarica.compartirgastos.features.balances.presentation.resumeScreen.ResumeFragment
import com.jarica.compartirgastos.features.balances.presentation.resumeScreen.ResumeViewModel
import com.jarica.compartirgastos.features.costs.presentation.costsScreen.CostFragment
import com.jarica.compartirgastos.features.costs.presentation.costsScreen.CostsViewModel
import com.jarica.compartirgastos.features.costs.presentation.editCostScreen.EditCostScreenViewModel
import com.jarica.compartirgastos.features.payments.presentation.paymentsScreen.PaymentsFragment
import com.jarica.compartirgastos.features.payments.presentation.paymentsScreen.PaymentsScreenViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    idGroup: String?,
    mainScreenViewModel: GroupDetailsViewModel,
    resumeViewModel: ResumeViewModel,
    costsViewModel: CostsViewModel,
    paymentsViewModel: PaymentsScreenViewModel,
    navigateToAddCostScreen: () -> Unit,
    navigateToAddPeopleFromGroup: () -> Unit,
    navigateToGroupsScreen: () -> Unit,
    navigateToAddPayScreen: () -> Unit,
    navigateToEditCost: (CostModel) -> Unit,
    navigateToConfiguration: () -> Unit,
    navigateToDoTheCounts: () -> Unit,
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    onDoTheCountsClicked: () -> Unit,
    editCostScreenViewModel: EditCostScreenViewModel
) {

    val nameOfGroup: String by mainScreenViewModel.nameOfGroup.collectAsState("")
    val isFabExpanded: Boolean by mainScreenViewModel.isFabExpanded.collectAsState(false)
    val sumCosts by mainScreenViewModel.sumCostByGroup.collectAsState()



    Scaffold(
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                tonalElevation = 0.dp,
            ) {
                ButtonDoTheCounts(mainScreenViewModel, navigateToDoTheCounts)

            }

        },
        floatingActionButton = {
            ExpandableFab(
                expanded = isFabExpanded,
                onFabClick = { mainScreenViewModel.onFabClick() },
                onAddCost = {
                    navigateToAddCostScreen()
                    mainScreenViewModel.onFabClick()
                },
                onAddPayment = {
                    navigateToAddPayScreen()
                    mainScreenViewModel.onFabClick()
                },
                onAddPerson = {
                    navigateToAddPeopleFromGroup()
                    mainScreenViewModel.onFabClick()
                }
            )
        }
    ) { innerPadding ->
        when (sumCosts) {
            is TotalExpensesUiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is TotalExpensesUiState.Error -> {}
            is TotalExpensesUiState.Success -> {
                Box {
                    MainView(
                        navigateToAddCostScreen,
                        navigateToAddPeopleFromGroup,
                        idGroup,
                        mainScreenViewModel,
                        nameOfGroup,
                        doTheCountsScreenViewModel,
                        navigateToAddPayScreen,
                        navigateToEditCost,
                        navigateToDoTheCounts,
                        //uiStatePeopleGroupFragment,
                        onDoTheCountsClicked,
                        navigateToGroupsScreen,
                        navigateToConfiguration,
                        resumeViewModel,
                        innerPadding,
                        (sumCosts as TotalExpensesUiState.Success),
                        editCostScreenViewModel,
                        costsViewModel,
                        paymentsViewModel
                    )
                    if (isFabExpanded) {
                        Scrim(onDismiss = { mainScreenViewModel.onFabClick() })
                    }
                }
            }
        }

    }
}

@Composable
fun Scrim(onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                onDismiss()
            }
    )
}

@Composable
fun ExpandableFab(
    expanded: Boolean,
    onFabClick: () -> Unit,
    onAddCost: () -> Unit,
    onAddPayment: () -> Unit,
    onAddPerson: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.End
    ) {

        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically { it / 2 },
            exit = fadeOut() + slideOutVertically { it / 2 }
        ) {
            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                SmallFab(addCost, R.drawable.moneycash, onAddCost)
                SmallFab(addPay, R.drawable.addpay, onAddPayment)
                SmallFab(addPeople, R.drawable.people_add, onAddPerson)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        FloatingActionButton(
            containerColor = DarkOrange,
            onClick = onFabClick
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = White,
                modifier = Modifier.rotate(
                    animateFloatAsState(
                        if (expanded) 45f else 0f,
                    ).value
                )
            )
        }
    }
}

@Composable
fun SmallFab(
    text: String,
    icon: Int,
    onClick: () -> Unit
) {
    Row(

        verticalAlignment = Alignment.CenterVertically,
    ) {
        Surface(
            color = DarkOrange,
            modifier = Modifier.clickable(onClick = onClick),
            tonalElevation = 0.dp,
            shadowElevation = 2.dp,
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                style = MaterialTheme.typography.labelLarge,
                color = White


            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        SmallFloatingActionButton(onClick = onClick, containerColor = DarkOrange) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "",
                modifier = Modifier.size(24.dp),
                tint = White
            )
        }
    }
}


@Composable
fun ButtonDoTheCounts(mainScreenViewModel: GroupDetailsViewModel, navigateToDoTheCounts: () -> Unit) {
    Row {

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {
               // mainScreenViewModel.onDoTheCountsClicked()
                navigateToDoTheCounts()
            },
            modifier = Modifier
                .padding(vertical = 32.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkOrange,
                contentColor = White
            )
        ) {
            Text(
                text = doTheCount,
                modifier = Modifier.padding(horizontal = 32.dp),
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenWithPager(
    mainScreenViewModel: GroupDetailsViewModel,
    idGroup: String?,
    resumeViewModel: ResumeViewModel,
    navigateToEditCost: (CostModel) -> Unit,
    editCostScreenViewModel: EditCostScreenViewModel,
    costsViewModel: CostsViewModel,
    paymentsViewModel: PaymentsScreenViewModel,
) {
    LaunchedEffect(idGroup) {
        if (idGroup != null) {
            mainScreenViewModel.setGroupId(idGroup)
        }
    }

    val pagerState = rememberPagerState(
        initialPage = mainScreenViewModel.selectedTab.ordinal,
        pageCount = { GroupDetailsViewModel.MainTab.entries.size }
    )

    val scope = rememberCoroutineScope()
    LaunchedEffect(pagerState.currentPage) {
        mainScreenViewModel.onTabSelected(GroupDetailsViewModel.MainTab.entries[pagerState.currentPage])
    }

    Column {
        MainTabs(
            selectedTab = mainScreenViewModel.selectedTab,
            onTabSelected = {
                mainScreenViewModel.onTabSelected(it)
                scope.launch {
                    pagerState.animateScrollToPage(it.ordinal)
                }
            }
        )

        HorizontalPager(state = pagerState) { page ->
            when (GroupDetailsViewModel.MainTab.entries[page]) {
                GroupDetailsViewModel.MainTab.RESUME -> ResumeFragment(
                    idGroup = idGroup,
                    Modifier.weight(1f),
                    resumeViewModel
                )

                GroupDetailsViewModel.MainTab.COSTS -> CostFragment(
                    idGroup,
                    navigateToEditCost,
                    editCostScreenViewModel,
                    costsViewModel
                )

                GroupDetailsViewModel.MainTab.PAYMENTS -> PaymentsFragment(
                    idGroup,
                    paymentsViewModel,
                    Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun MainTabs(
    selectedTab: GroupDetailsViewModel.MainTab,
    onTabSelected: (GroupDetailsViewModel.MainTab) -> Unit
) {
    val tabs = GroupDetailsViewModel.MainTab.entries.toTypedArray()

    TabRow(
        selectedTabIndex = selectedTab.ordinal,
        containerColor = White,
        contentColor = MaterialTheme.colorScheme.primary,
        indicator = { tabPositions ->
            // Obtenemos la posición de la pestaña seleccionada actual
            val currentTabPosition = tabPositions[selectedTab.ordinal]

            // Creamos una Box que ocupe el ancho y posición de la pestaña actual
            Box(
                modifier = Modifier
                    .tabIndicatorOffset(currentTabPosition)
                    .fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter // Alineamos el contenido (la barrita) abajo y al centro
            ) {
                // Dibujamos el indicador real con el tamaño deseado
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                        .height(3.dp) // Altura de la línea
                        .clip(RoundedCornerShape(3.dp))
                        .background(DarkOrange)
                )
            }
        },
        divider = {
            HorizontalDivider(color = Color.Transparent)
        }
    ) {
        tabs.forEach { tab ->

            val tabTitle = when (tab) {
                GroupDetailsViewModel.MainTab.RESUME -> resume
                GroupDetailsViewModel.MainTab.COSTS -> costs
                GroupDetailsViewModel.MainTab.PAYMENTS -> payments
            }
            val isSelected = selectedTab == tab
            Tab(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                text = {
                    Text(
                        text = tabTitle.lowercase().replaceFirstChar { it.uppercase() },
                        fontWeight = if (isSelected) FontWeight.Normal else FontWeight.Light,
                        fontFamily = parkinsans,
                        fontSize = 16.sp
                    )
                }
            )
        }
    }
}


@Composable
fun MainView(
    navigateToAddCostScreen: () -> Unit,
    navigateToAddPeopleFromGroup: () -> Unit,
    idGroup: String?,
    mainScreenViewModel: GroupDetailsViewModel,
    nameOfGroup: String,
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    navigateToAddPayScreen: () -> Unit,
    navigateToEditCost: (CostModel) -> Unit,
    navigateToDoTheCounts: () -> Unit,
    // uiStatePeopleGroupFragment: MainUiState,
    onDoTheCountsClicked: () -> Unit,
    navigateToGroupsScreen: () -> Unit,
    navigateToConfiguration: () -> Unit,
    resumeViewModel: ResumeViewModel,
    innerPadding: PaddingValues,
    success: TotalExpensesUiState.Success,
    editCostScreenViewModel: EditCostScreenViewModel,
    costsViewModel: CostsViewModel,
    paymentsViewModel: PaymentsScreenViewModel,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            //.padding(innerPadding)
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        mainScreenViewModel.getGroupNameById(idGroup!!)
        //HEADER
        Header(nameOfGroup, navigateToGroupsScreen, navigateToConfiguration, success)
        Spacer(Modifier.size(6.dp))
        //BOXS DE SELECCIONAR ACCION
        /* ActionsBoxes(
             navigateToAddCostScreen,
             doTheCountsScreenViewModel,
             navigateToAddPeopleFromGroup,
             navigateToAddPayScreen,
             navigateToDoTheCounts,
             uiStatePeopleGroupFragment,
             onDoTheCountsClicked,
         )*/
        MainScreenWithPager(mainScreenViewModel, idGroup, resumeViewModel, navigateToEditCost, editCostScreenViewModel, costsViewModel, paymentsViewModel)
        Spacer(Modifier.weight(1f))
        // BannerAdViewMainScreen()
    }
}

/*
@Composable
fun ActionsBoxes(
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
}*/

@Composable
fun Header(
    nameOfGroup: String,
    navigateToGroupsScreen: () -> Unit,
    navigateToConfiguration: () -> Unit,
    success: TotalExpensesUiState.Success
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
            .background(color = DarkBlue)
            .padding(horizontal = 8.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(WindowInsets.statusBars.asPaddingValues())
        ) {
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, top = 8.dp),
            textAlign = TextAlign.Start,
            fontSize = 26.sp,
            color = White,
            fontFamily = parkinsans,
            fontWeight = FontWeight.W600
        )
        Text(
            totalCostText + ": " + success.totalCost.toString() + " €",
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 32.dp, bottom = 8.dp),
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            color = White,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Light
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




