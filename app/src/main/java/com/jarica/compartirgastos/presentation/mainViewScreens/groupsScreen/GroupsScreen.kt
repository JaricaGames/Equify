package com.jarica.compartirgastos.presentation.mainViewScreens.groupsScreen

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.presentation.mainViewScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import com.jarica.compartirgastos.presentation.ui.groupsText
import com.jarica.compartirgastos.presentation.ui.newGroupText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkGrey
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellow
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    groupScreenViewModel: GroupsScreenViewModel,
    navigateToNewGroupScreen: () -> Unit,
    navigateToMainScreen: (Int) -> Unit,
    navigateToInitialScreen: () -> Unit,
) {


    val isFABSelected: Boolean by groupScreenViewModel.isFABSelected.observeAsState(false)

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiStateGroups by produceState<GroupsScreenUiState>(
        initialValue = GroupsScreenUiState.Loading,
        key1 = lifecycle,
        key2 = groupScreenViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            groupScreenViewModel.uiStateGroups.collect { value = it }
        }
    }

    when (uiStateGroups) {

        is GroupsScreenUiState.Error -> {}
        GroupsScreenUiState.Loading -> {}
        is GroupsScreenUiState.Success -> {

            //SI NO HAY NINGUN GRUPO CREADO VAMOS A LA PANTALLA INICIAL
            if ((uiStateGroups as GroupsScreenUiState.Success).groupsList.isEmpty()) {

                navigateToInitialScreen()

            } else {

                Scaffold(

                    modifier = Modifier
                        .clickable { groupScreenViewModel.isFABSelected(true) },
                    topBar = {

                        TopAppBar(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            colors = topAppBarColors(
                                containerColor = Transparent,
                                actionIconContentColor = White,
                                navigationIconContentColor = White
                            ),

                            navigationIcon = {

                            },

                            actions = {

                            },
                            title = {

                                Text(
                                    groupsText,
                                    color = if (isFABSelected) {
                                        White.copy(0.15f)
                                    } else {
                                        White
                                    },
                                    fontFamily = rubik,
                                    fontWeight = FontWeight.Bold,
                                    textAlign = TextAlign.Center,
                                    modifier = Modifier.fillMaxWidth()
                                )

                            }
                        )
                    },

                    floatingActionButton = {
                        Column(
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.End,
                            modifier = Modifier.padding(24.dp)
                        ) {


                            AnimatedVisibility(isFABSelected) {
                                FloatingActionButton(
                                    onClick = {
                                        groupScreenViewModel.isNewGroupScreenSelected()
                                        navigateToNewGroupScreen()

                                    },
                                    containerColor = DarkYellow,
                                    contentColor = White
                                ) {
                                    Text(
                                        newGroupText,
                                        modifier = Modifier.padding(horizontal = 16.dp),
                                        fontFamily = rubik,
                                        color = White
                                    )
                                }
                            }

                            Spacer(Modifier.height(16.dp))

                            FloatingActionButton(
                                onClick = { groupScreenViewModel.isFABSelected(isFABSelected) },
                                containerColor = DarkYellow,
                                contentColor = White,
                            ) {
                                if (!isFABSelected) {
                                    Icon(Icons.Default.Add, contentDescription = "Add")
                                } else {
                                    Icon(Icons.Default.Close, contentDescription = "Close")
                                }

                            }

                        }

                    },


                    ) { paddingValues ->


                    MainViewGroupsScreen(
                        paddingValues,
                        uiStateGroups as GroupsScreenUiState.Success,
                        navigateToMainScreen,
                        isFABSelected

                    )

                }

            }
        }

    }
}

@Composable
fun MainViewGroupsScreen(
    paddingValues: PaddingValues,
    uiStateGroups: GroupsScreenUiState.Success,
    navigateToMainScreen: (Int) -> Unit,
    isFABSelected: Boolean
) {

    GroupList(
        (uiStateGroups as GroupsScreenUiState.Success).groupsList,
        navigateToMainScreen,
        paddingValues,
        isFABSelected
    )

}

@Composable
fun GroupList(
    groupsList: List<GroupNameModel>,
    navigateToMainScreen: (Int) -> Unit,
    paddingValues: PaddingValues,
    isFABSelected: Boolean
) {


    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(horizontal = 16.dp, vertical = 150.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(groupsList) { group ->
            ItemsGroup(group, navigateToMainScreen)
        }
    }

    if (isFABSelected) {

        Box(
            Modifier
                .fillMaxSize()
                .background(Black.copy(0.7f))
        )
    }


}


@Composable
fun ItemsGroup(group: GroupNameModel, navigateToMainScreen: (Int) -> Unit) {

    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(DarkGrey)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .clickable {
                iDGroupName = group.idGroupName
                navigateToMainScreen(group.idGroupName) },
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Text(group.groupName, color = White, fontFamily = rubik)


    }
    /*Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Cyan)
            .clickable { navigateToMainScreen(group.idGroupName) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(group.groupName, fontSize = 36.sp, color = Color.Red)
        }

    }*/
}
