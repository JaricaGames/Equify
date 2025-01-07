package com.jarica.compartirgastos.presentation.groupScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
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
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.presentation.ui.addCost

@Composable
fun GroupScreen(
    idGroup: Int?,
    groupViewModel: GroupScreenViewModel,
    navigateToAddCostScreen: () -> Unit
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



            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Cyan), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
            ) {

                GroupList((uiStateGroupScreen as GroupUiState.Success).peopleList)
                Spacer(modifier = Modifier.size(25.dp))
                Button(modifier = Modifier.fillMaxWidth().padding(horizontal = 35.dp), onClick = {navigateToAddCostScreen()}) {
                    Text(addCost)
                }

            }
        }
    }
}

@Composable
fun GroupList(groupNameList: List<PersonModel>) {
    LazyColumn {
        items(groupNameList) { person ->
            ItemGroupName(person)
        }
    }
}

@Composable
fun ItemGroupName(item: PersonModel) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .background(Color.Cyan)) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(item.name, fontSize = 36.sp, color = Color.Red)
            Text(item.equity, fontSize = 36.sp, color = Color.Red)
        }

    }
}



