package com.jarica.compartirgastos.presentation.groupsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.domain.models.GroupNameModel

@Composable
fun GroupsScreen(
    groupScreenViewModel: GroupScreenViewModel,
    navigateToInitialScreen: ()-> Unit,
    navigateToMainScreen: (Int) -> Unit) {


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

            if((uiStateGroups as GroupsScreenUiState.Success).groupsList.isEmpty()){
                navigateToInitialScreen()
            }else{
                Column(modifier = Modifier.fillMaxSize().padding(vertical = 45.dp) , horizontalAlignment = Alignment.Start ){
                    GroupList((uiStateGroups as GroupsScreenUiState.Success).groupsList, navigateToMainScreen)
                }
            }
        }

    }
}

@Composable
fun GroupList(groupsList: List<GroupNameModel>, navigateToMainScreen: (Int) -> Unit) {
    LazyColumn {
        items(groupsList) { group ->
                ItemsGroup(group, navigateToMainScreen)
        }
    }

}

@Composable
fun ItemsGroup(group: GroupNameModel, navigateToMainScreen: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Cyan)
            .clickable { navigateToMainScreen(group.idGroupName) }
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(group.groupName, fontSize = 36.sp, color = Color.Red)
        }

    }
}
