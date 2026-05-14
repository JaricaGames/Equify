package com.jarica.compartirgastos.features.groups.presentation.groupsScreen

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.GroupModel
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen.GroupDetailsViewModel

private val CardBorder = Color(0xFFE6E4DE)
private val Ink        = Color(0xFF1F2A33)
private val Muted      = Color(0xFF6B7A86)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    groupViewModel: GroupsScreenViewModel,
    mainScreenViewModel: GroupDetailsViewModel,
    navigateToMainScreen: (String) -> Unit,
    navigateToInitialScreen: () -> Unit,
    navigateToNewGroup: () -> Unit,
    navigateToAboutScreen: () -> Unit,
) {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStateGroupScreen by produceState<GroupUiState>(
        initialValue = GroupUiState.Loading,
        key1 = lifecycle,
        key2 = groupViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            groupViewModel.uiStateGroupName.collect { value = it }
        }
    }

    when (uiStateGroupScreen) {
        is GroupUiState.Error -> {}
        is GroupUiState.Loading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is GroupUiState.Success -> {
            val listOfGroups = (uiStateGroupScreen as GroupUiState.Success).groupsList
            if (listOfGroups.isEmpty()) {
                navigateToInitialScreen()
            } else {
                Scaffold(
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { navigateToNewGroup() },
                            containerColor = DarkOrange,
                            contentColor = White,
                            shape = RoundedCornerShape(18.dp)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = null)
                        }
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues)
                    ) {
                        GroupsHeader(
                            groupCount = listOfGroups.size,
                            navigateToAboutScreen = navigateToAboutScreen
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                "ACTIVOS",
                                fontSize = 11.sp,
                                fontFamily = parkinsans,
                                fontWeight = FontWeight.SemiBold,
                                color = Muted,
                                letterSpacing = 0.08.em
                            )
                            Spacer(Modifier.weight(1f))
                            Text(
                                "${listOfGroups.size} grupos",
                                fontSize = 12.sp,
                                fontFamily = parkinsans,
                                fontWeight = FontWeight.Normal,
                                color = Muted
                            )
                        }

                        GroupList(
                            listOfGroups,
                            groupViewModel,
                            navigateToMainScreen,
                            navigateToInitialScreen,
                            mainScreenViewModel
                        )
                        Spacer(Modifier.height(16.dp))
                        BannerAdViewGroupScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun GroupsHeader(groupCount: Int, navigateToAboutScreen: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 22.dp, bottomStart = 22.dp))
            .drawBehind {
                drawRect(DarkBlue)
                val side = 140.dp.toPx()
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
                .padding(horizontal = 18.dp)
        ) {
            Spacer(Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(White.copy(alpha = 0.08f))
                        .clickable { navigateToAboutScreen() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.information),
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Text(
                    "Equify",
                    fontSize = 18.sp,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.SemiBold,
                    color = White,
                    letterSpacing = (-0.01).em
                )
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(White.copy(alpha = 0.08f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ellipsis),
                        contentDescription = null,
                        tint = White,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }
            Spacer(Modifier.height(14.dp))
            Text(
                "Tus grupos",
                fontSize = 32.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Bold,
                color = White,
                letterSpacing = (-0.02).em,
                lineHeight = 36.sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "$groupCount activos",
                fontSize = 13.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal,
                color = White.copy(alpha = 0.75f)
            )
            Spacer(Modifier.height(22.dp))
        }
    }
}

@Composable
fun GroupList(
    groupsList: List<GroupModel>,
    groupViewModel: GroupsScreenViewModel,
    navigateToMainScreen: (String) -> Unit,
    navigateToInitialScreen: () -> Unit,
    mainScreenViewModel: GroupDetailsViewModel,
) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(groupsList, key = { it.idGroupName }) { group ->
            GroupCard(
                group = group,
                groupViewModel = groupViewModel,
                navigateToMainScreen = navigateToMainScreen,
                navigateToInitialScreen = navigateToInitialScreen,
                groupsList = groupsList,
                mainScreenViewModel = mainScreenViewModel
            )
        }
    }
}

@Composable
fun GroupCard(
    group: GroupModel,
    groupViewModel: GroupsScreenViewModel,
    navigateToMainScreen: (String) -> Unit,
    navigateToInitialScreen: () -> Unit,
    groupsList: List<GroupModel>,
    mainScreenViewModel: GroupDetailsViewModel,
) {
    val peopleCount by produceState(initialValue = 0, key1 = group.idGroupName) {
        value = groupViewModel.getPeopleCount(group.idGroupName)
    }
    val totalCost by produceState(initialValue = 0f, key1 = group.idGroupName) {
        value = groupViewModel.getTotalCost(group.idGroupName)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, CardBorder, RoundedCornerShape(16.dp))
            .clickable {
                groupViewModel.onGroupSelected(group.idGroupName, group.groupName)
                mainScreenViewModel.setGroupId(group.idGroupName)
                navigateToMainScreen(group.idGroupName)
            }
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                group.groupName,
                fontSize = 15.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.SemiBold,
                color = Ink,
                letterSpacing = (-0.01).em
            )
            Text(
                "$peopleCount personas",
                fontSize = 12.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal,
                color = Muted
            )
        }

        Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.spacedBy(2.dp)) {
            Text(
                "${"%.2f".format(totalCost)} €",
                fontSize = 15.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Bold,
                color = Ink
            )
            Text(
                "TOTAL",
                fontSize = 10.sp,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal,
                color = Muted,
                letterSpacing = 0.1.em
            )
        }

        Icon(
            painter = painterResource(R.drawable.delete_svgrepo),
            contentDescription = null,
            tint = Muted.copy(alpha = 0.4f),
            modifier = Modifier
                .size(18.dp)
                .clickable {
                    groupViewModel.onDeletedSelected(group, group.idGroupName)
                    if (groupsList.size <= 1) navigateToInitialScreen()
                }
        )
    }
}

@SuppressLint("MissingPermission")
@Composable
fun BannerAdViewGroupScreen() {
    Box(contentAlignment = Alignment.TopCenter) {
        AndroidView(
            modifier = Modifier.fillMaxWidth(),
            factory = { context ->
                AdView(context).apply {
                    setAdSize(AdSize.MEDIUM_RECTANGLE)
                    adUnitId = "ca-app-pub-4979320410432560/4688560090"
                    loadAd(AdRequest.Builder().build())
                }
            }
        )
    }
}
