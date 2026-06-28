package com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.PaymentsModel
import com.jarica.compartirgastos.core.presentation.LocalAdsRemoved
import com.jarica.compartirgastos.core.presentation.composables.BannerAdView
import com.jarica.compartirgastos.core.presentation.composables.ExpandableFab
import com.jarica.compartirgastos.core.presentation.composables.Scrim
import com.jarica.compartirgastos.core.presentation.ui.addFirstCost
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
import com.jarica.compartirgastos.core.utils.AdIds
import com.jarica.compartirgastos.features.balances.presentation.resumeScreen.ResumeFragment
import com.jarica.compartirgastos.features.balances.presentation.resumeScreen.ResumeViewModel
import com.jarica.compartirgastos.features.costs.presentation.costsScreen.CostFragment
import com.jarica.compartirgastos.features.costs.presentation.costsScreen.CostsViewModel
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
    onDoTheCountsClicked: () -> Unit,
    navigateToEditPayments: (PaymentsModel) -> Unit,
) {

    val nameOfGroup: String by mainScreenViewModel.nameOfGroup.collectAsState("")
    val isFabExpanded: Boolean by mainScreenViewModel.isFabExpanded.collectAsState(false)
    val sumCosts by mainScreenViewModel.sumCostByGroup.collectAsState()

    LaunchedEffect(idGroup) {
        // Sembramos el groupId aquí (no dentro de la rama Success) para que el VM,
        // ahora con ciclo de vida propio del destino, arranque el flujo de gastos.
        idGroup?.let { mainScreenViewModel.setGroupId(it) }
        mainScreenViewModel.getGroupNameById(idGroup)
    }

    Scaffold(
        contentWindowInsets = WindowInsets(0),
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color.White,
                tonalElevation = 0.dp,
            ) {
                Column(modifier = Modifier.navigationBarsPadding()) {
                    BannerAdView()
                    ButtonDoTheCounts(
                        mainScreenViewModel = mainScreenViewModel,
                        navigateToDoTheCounts = navigateToDoTheCounts,
                        hasCosts = (sumCosts as? TotalExpensesUiState.Success)?.totalCost?.let { it > 0f } ?: true,
                        navigateToAddCostScreen = navigateToAddCostScreen
                    )
                }
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
                        costsViewModel,
                        paymentsViewModel,
                        navigateToEditPayments
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
fun ButtonDoTheCounts(
    mainScreenViewModel: GroupDetailsViewModel,
    navigateToDoTheCounts: () -> Unit,
    hasCosts: Boolean,
    navigateToAddCostScreen: () -> Unit
) {
    Button(
        onClick = {
            if (hasCosts) navigateToDoTheCounts()
            else navigateToAddCostScreen()
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 32.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = DarkOrange,
            contentColor = White
        )
    ) {
        Text(
            text = if (hasCosts) doTheCount else addFirstCost,
            modifier = Modifier.padding(vertical = 4.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreenWithPager(
    mainScreenViewModel: GroupDetailsViewModel,
    idGroup: String?,
    resumeViewModel: ResumeViewModel,
    navigateToEditCost: (CostModel) -> Unit,
    costsViewModel: CostsViewModel,
    paymentsViewModel: PaymentsScreenViewModel,
    navigateToEditPayments: (PaymentsModel) -> Unit,
    modifier: Modifier = Modifier,
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

    Column(modifier = modifier.fillMaxSize()) {
        MainTabs(
            selectedTab = mainScreenViewModel.selectedTab,
            onTabSelected = {
                mainScreenViewModel.onTabSelected(it)
                scope.launch {
                    pagerState.animateScrollToPage(it.ordinal)
                }
            }
        )

        HorizontalPager(
            state = pagerState,
            verticalAlignment = Alignment.Top,
            modifier = Modifier.weight(1f)
        ) { page ->
            when (GroupDetailsViewModel.MainTab.entries[page]) {
                GroupDetailsViewModel.MainTab.RESUME -> ResumeFragment(
                    idGroup = idGroup,
                    modifier = Modifier.fillMaxSize(),
                    resumeViewModel = resumeViewModel
                )

                GroupDetailsViewModel.MainTab.COSTS -> CostFragment(
                    idGroup = idGroup,
                    navigateToEditCost = navigateToEditCost,
                    costsViewModel = costsViewModel,
                    modifier = Modifier.fillMaxSize()
                )

                GroupDetailsViewModel.MainTab.PAYMENTS -> PaymentsFragment(
                    idGroup = idGroup,
                    paymentsViewModel = paymentsViewModel,
                    modifier = Modifier.fillMaxSize(),
                    navigateToEditPayments = navigateToEditPayments
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
    costsViewModel: CostsViewModel,
    paymentsViewModel: PaymentsScreenViewModel,
    navigateToEditPayments: (PaymentsModel) -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        Header(nameOfGroup, navigateToGroupsScreen, navigateToConfiguration, success)
        Spacer(Modifier.size(6.dp))
        MainScreenWithPager(
            mainScreenViewModel,
            idGroup,
            resumeViewModel,
            navigateToEditCost,
            costsViewModel,
            paymentsViewModel,
            navigateToEditPayments,
            modifier = Modifier.weight(1f)
        )
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
                    // Muestra el intersticial (si procede) y navega a la pantalla
                    // de cuentas, que carga sus propios datos al entrar.
                    onDoTheCountsClicked()
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
    val total = success.totalCost
    val intPart = (total / 100).toString()
    val decPart = ",%02d".format(total % 100)
    val scale = (LocalConfiguration.current.screenHeightDp / 800f).coerceIn(0.65f, 1.0f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 22.dp, bottomStart = 22.dp))
            .drawBehind {
                drawRect(DarkBlue)
                val side = (140 * scale).dp.toPx()
                val half = side / 2f
                val cx = size.width - 40.dp.toPx()
                val cy = size.height - 40.dp.toPx()
                withTransform({ rotate(degrees = 45f, pivot = Offset(cx, cy)) }) {
                    drawRoundRect(
                        color = DarkOrange,
                        topLeft = Offset(cx - half, cy - half),
                        size = Size(side, side),
                        cornerRadius = CornerRadius(6.dp.toPx()),
                        alpha = 0.95f
                    )
                }
            }
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(WindowInsets.statusBars.asPaddingValues())
            .padding(horizontal = 18.dp),
        verticalArrangement = Arrangement.Top
    ) {
        // Top row: back · settings
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = (14 * scale).dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { navigateToGroupsScreen() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = "",
                    tint = White,
                    modifier = Modifier.size(22.dp)
                )
            }
            /*Text(
                "Grupo",
                fontSize = 13.sp,
                color = White.copy(alpha = 0.7f),
                fontFamily = parkinsans,
                fontWeight = FontWeight.Medium
            )*/
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { navigateToConfiguration() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ellipsis),
                    contentDescription = "",
                    tint = White,
                    modifier = Modifier.size(22.dp)
                )
            }
        }

        // Group name — h1
        Text(
            nameOfGroup,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = (14 * scale).dp),
            fontSize = (32 * scale).sp,
            color = White,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.02).em
        )

        // Total hero: big number + € + label
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = (4 * scale).dp, bottom = (22 * scale).dp)
        ) {
            Text(
                intPart,
                modifier = Modifier.alignByBaseline(),
                fontSize = (40 * scale).sp,
                color = White,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.03).em
            )
            Text(
                decPart,
                modifier = Modifier.alignByBaseline(),
                fontSize = (26 * scale).sp,
                color = White,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Bold,
                letterSpacing = (-0.03).em
            )
            Text(
                " €",
                modifier = Modifier.alignByBaseline(),
                fontSize = (18 * scale).sp,
                color = White.copy(alpha = 0.8f),
                fontFamily = parkinsans,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(Modifier.weight(1f))
            Text(
                totalCostText.uppercase(),
                modifier = Modifier.alignByBaseline(),
                fontSize = 11.sp,
                color = White.copy(alpha = 0.7f),
                fontFamily = parkinsans,
                letterSpacing = 0.1.em
            )
        }
    } // Column
    } // Box
}

@SuppressLint("MissingPermission")
@Composable
fun BannerAdViewMainScreen() {
    if (LocalAdsRemoved.current) return
    AndroidView(
        modifier = Modifier
            .fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.FULL_BANNER)
                adUnitId = AdIds.bannerMainScreen
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}




