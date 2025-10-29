package com.jarica.compartirgastos.presentation.groupsScreen

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
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
import com.jarica.compartirgastos.core.HEADER_WEIGHT
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.presentation.composables.CustomHeader
import com.jarica.compartirgastos.presentation.ui.groupsText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.presentation.ui.theme.VeryDarkBlue
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    groupViewModel: GroupsScreenViewModel,
    navigateToMainScreen: (Int) -> Unit,
    navigateToInitialScreen: () -> Unit,
    navigateToNewGroup: () -> Unit,

    ) {

    val isDeleteGroupDialog: Boolean by groupViewModel.isDeleteGroupClicked.observeAsState(false)
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
            CircularProgressIndicator()
        }

        is GroupUiState.Success -> {

            val listOfGroups = (uiStateGroupScreen as GroupUiState.Success).groupsList

            // Si la lista esta vacia vamos a la pantalla inicial, si tiene grupos seguimos en la pantalla
            if (listOfGroups.isEmpty()) {
                navigateToInitialScreen()
            } else {

                FloatingActionButton(
                    onClick = { navigateToNewGroup() },
                    modifier = Modifier.padding(30.dp),
                    containerColor = DarkOrange,
                    contentColor = White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.Top) {

                    CustomHeader(
                        { TODO() },
                        modifier = Modifier.weight(HEADER_WEIGHT),
                        text = groupsText,
                        icon = null
                    )
                    Column(modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f - HEADER_WEIGHT)) {
                        Spacer(modifier = Modifier.size(20.dp))
                        GroupList(
                            listOfGroups,
                            groupViewModel,
                            navigateToMainScreen,
                            navigateToInitialScreen,
                        )
                        Spacer(modifier = Modifier.size(20.dp))
                        BannerAdViewGroupScreen()

                    }
                }
            }


            }
        }
    }

@Composable
fun GroupList(
    groupsList: List<GroupNameModel>,
    groupViewModel: GroupsScreenViewModel,
    navigateToMainScreen: (Int) -> Unit,
    navigateToInitialScreen: () -> Unit,
) {

    LazyColumn(
    ) {

        items(groupsList) { group ->
            ItemGroupName(
                group,
                groupViewModel,
                navigateToMainScreen,
                navigateToInitialScreen,
                groupsList
            )
            Spacer(modifier = Modifier.size(8.dp))

        }
    }
}

@Composable
fun ItemGroupName(
    group: GroupNameModel,
    groupViewModel: GroupsScreenViewModel,
    navigateToMainScreen: (Int) -> Unit,
    navigateToInitialScreen: () -> Unit,
    groupsList: List<GroupNameModel>,
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(VeryDarkBlue)
            .border(width = 2.dp, color = DarkOrange, shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 32.dp, vertical = 8.dp)
            .clickable {
                groupViewModel.onGroupSelected(
                    group.idGroupName,
                    group.groupName
                ) // Guardame el grupo elegido
                navigateToMainScreen(group.idGroupName) // navegamos a la pantalla del grupo elegido
            },
        horizontalArrangement = Arrangement.Start
    ) {

        Text(
            group.groupName,
            color = White,
            fontFamily = parkinsans,
            fontSize = 13.sp,
            fontWeight = FontWeight.Medium
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painter = painterResource(R.drawable.delete_svgrepo),
            contentDescription = "",
            tint = DarkOrange,
            modifier = Modifier
                .size(20.dp)
                .clickable {
                    groupViewModel.onDeletedSelected(group, group.idGroupName)
                    if (groupsList.isEmpty()) navigateToInitialScreen()

                }
        )
    }

}

@SuppressLint("MissingPermission")
@Composable
fun BannerAdViewGroupScreen() {
    Box(contentAlignment = Alignment.TopCenter) {

        AndroidView(
            modifier = Modifier
                .fillMaxWidth(),

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
