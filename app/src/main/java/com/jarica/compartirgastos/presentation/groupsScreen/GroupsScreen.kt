package com.jarica.compartirgastos.presentation.groupsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.presentation.ui.groupsText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    groupViewModel: GroupsScreenViewModel,
    navigateToMainScreen: (Int) -> Unit,
    navigateToInitialScreen: () -> Unit,

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
        GroupUiState.Loading -> {
            CircularProgressIndicator()
        }

        is GroupUiState.Success -> {

            val listOfGroups = (uiStateGroupScreen as GroupUiState.Success).groupsList

            // Si la lista esta vacia vamos a la pantalla inicial, si tiene grupos seguimos en la pantalla
            if (listOfGroups.isEmpty()) {
                navigateToInitialScreen()
            } else {

                //Si no esta vacia mostramos la venta de grupos

                Scaffold(
                    topBar = {

                        TopAppBar(
                            modifier = Modifier.padding(start = 16.dp, top = 16.dp),
                            colors = topAppBarColors(
                                containerColor = Transparent,
                                actionIconContentColor = Black,
                                navigationIconContentColor = Black
                            ),

                            actions = {

                            },
                            title = {
                                Text(
                                    groupsText,
                                    fontFamily = rubik,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Bold
                                )

                            }
                        )
                    }
                ) { paddingValues ->
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
                            .padding(vertical = paddingValues.calculateTopPadding()),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        GroupList(
                            listOfGroups,
                            paddingValues,
                            groupViewModel,
                            navigateToMainScreen
                        )

                    }
                }
            }


        }
    }
}

@Composable
fun GroupList(
    groupsList: List<GroupNameModel>,
    paddingValues: PaddingValues,
    groupViewModel: GroupsScreenViewModel,
    navigateToMainScreen: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 16.dp)
    ) {

        items(groupsList) { group ->
            ItemGroupName(group, groupViewModel, navigateToMainScreen)
            Spacer(modifier = Modifier.size(8.dp))

        }
    }
}

@Composable
fun ItemGroupName(
    group: GroupNameModel,
    groupViewModel: GroupsScreenViewModel,
    navigateToMainScreen: (Int) -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .padding(horizontal = 32.dp, vertical = 16.dp)
            .clickable {
                groupViewModel.onGroupSelected(group.idGroupName) // Guardame el grupo elegido
                navigateToMainScreen(group.idGroupName) // navegamos a la pantalla del grupo elegido
                       },
        horizontalArrangement = Arrangement.Start
    ) {

        Text(group.groupName, color = Black, fontFamily = rubik)
        Spacer(modifier = Modifier.weight(1f))

    }
}