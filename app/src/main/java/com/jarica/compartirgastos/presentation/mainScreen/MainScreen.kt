package com.jarica.compartirgastos.presentation.mainScreen

import androidx.compose.foundation.background
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
import com.jarica.compartirgastos.presentation.ui.costs
import com.jarica.compartirgastos.presentation.ui.doTheCount
import com.jarica.compartirgastos.presentation.ui.resume

@Composable
fun MainScreen(
    idGroup: Int?,
    mainScreenViewModel: MainScreenViewModel,
    navigateToAddCostScreen: () -> Unit,
    navigateToCosts: () -> Unit
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStatePeopleGroupScreen by produceState<MainUiState>(
        initialValue = MainUiState.Loading,
        key1 = lifecycle,
        key2 = mainScreenViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            mainScreenViewModel.uiStateResumeGroup.collect { value = it }
        }
    }

    when (uiStatePeopleGroupScreen) {

        is MainUiState.Error -> {}

        is MainUiState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MainUiState.Success -> {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Cyan).padding(vertical = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding()) {
                    Box(modifier = Modifier.weight(1f).background(Color.White).padding(vertical = 10.dp), contentAlignment = Alignment.Center){
                        Text(resume)
                    }

                    Box(modifier = Modifier.weight(0.8f).background(Color.White.copy(alpha = 0.5f)).padding(vertical = 10.dp).clickable { navigateToCosts() }, contentAlignment = Alignment.Center){
                        Text(costs, fontSize = 12.sp)
                    }
                }
                Spacer(modifier = Modifier.size(25.dp))
                GroupList((uiStatePeopleGroupScreen as MainUiState.Success).peopleList)
                Spacer(modifier = Modifier.size(25.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp),
                    onClick = { navigateToAddCostScreen() }) {
                    Text(addCost)
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp),
                    onClick = {
                        mainScreenViewModel.doTheCounts((uiStatePeopleGroupScreen as MainUiState.Success).peopleList)
                        /*(uiStatePeopleGroupScreen as MainUiState.Success).peopleList.forEach {  person ->

                                                    mainScreenViewModel.doTheCounts2((uiStatePeopleGroupScreen as MainUiState.Success).peopleList, person)
                                                }*/
                        mainScreenViewModel.text()
                    })
                {
                    Text(doTheCount)
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Cyan)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(item.name, fontSize = 36.sp, color = Color.Red)
            Text(item.equity, fontSize = 36.sp, color = Color.Red)
        }

    }
}



