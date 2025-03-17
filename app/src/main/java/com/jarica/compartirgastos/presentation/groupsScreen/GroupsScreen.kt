package com.jarica.compartirgastos.presentation.groupsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.presentation.ui.payToText
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupsScreen(
    groupViewModel: GroupsScreenViewModel,
    navigateToNewGroupScreen: () -> Unit,
    navigateToMainScreen: Any
) {


    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStateGroupScreen by produceState<GroupUiState>(
        initialValue = GroupUiState.Loading,
        key1 = lifecycle,
        key2 = groupViewModel,
    ){
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            groupViewModel.uiStateGroupName.collect { value = it}
        }
    }


    when(uiStateGroupScreen){
        is GroupUiState.Error ->  {}
        GroupUiState.Loading -> { CircularProgressIndicator()}
        is GroupUiState.Success -> {

            val listOfGroups = (uiStateGroupScreen as GroupUiState.Success).groupsList

            Scaffold(
                topBar = {

                    TopAppBar(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        colors = topAppBarColors(
                            containerColor = Transparent,
                            actionIconContentColor = Black,
                            navigationIconContentColor = Black
                        ),

                        navigationIcon = {
                            IconButton(modifier = Modifier
                                .clip(
                                    shape = CircleShape
                                )
                                .size(40.dp),
                                onClick = {/*
                                    addCostViewModel.onFromSelected(true)
                                    navigateToMainScreen()*/
                                }) {
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    painter = painterResource(R.drawable.arrow_back),
                                    contentDescription = "",

                                    )
                            }
                        },

                        actions = {


                        },
                        title = {
                            /*Text(
                                addCost,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            )*/

                        }
                    )
                }
            ) { paddingValues ->
                GroupList(
                    listOfGroups,
                    paddingValues,

                )

            }
        }
    }
}

@Composable
fun GroupList(groupsList: List<GroupNameModel>, paddingValues: PaddingValues) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)

    ) {
        items(groupsList){ group->
            ItemGroupName(group)
        }
    }
}

@Composable
fun ItemGroupName(group: GroupNameModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Bottom
    ) {

        Text(payToText, color = Black, fontFamily = rubik, fontSize = 10.sp)
        Text(group.groupName, color = Black, fontFamily = rubik)
    }
}