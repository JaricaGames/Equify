package com.jarica.compartirgastos.presentation.costsScreen

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
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.presentation.groupScreen.GroupList
import com.jarica.compartirgastos.presentation.groupScreen.GroupUiState
import com.jarica.compartirgastos.presentation.groupScreen.ItemGroupName
import com.jarica.compartirgastos.presentation.ui.addCost
import com.jarica.compartirgastos.presentation.ui.costs
import com.jarica.compartirgastos.presentation.ui.resume
import kotlinx.coroutines.flow.collect

@Composable
fun CostsScreen(costViewModel: CostsScreenViewModel, navigateToResume: ()-> Unit) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStateCosts by produceState<CostsScreenUiState>(
        initialValue = CostsScreenUiState.Loading,
        key1 = lifecycle,
        key2 = costViewModel
    ){
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            costViewModel.uiStateCosts.collect {value = it}
        }
    }

    when(uiStateCosts){
        is CostsScreenUiState.Error -> {}
        CostsScreenUiState.Loading -> {}
        is CostsScreenUiState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Cyan).padding(vertical = 70.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding()) {
                    Box(modifier = Modifier.weight(0.8f).background(Color.White.copy(alpha = 0.5f)).padding(vertical = 10.dp).clickable {
                        navigateToResume()
                    }, contentAlignment = Alignment.Center){
                        Text(resume, fontSize = 12.sp)
                    }
                    //Spacer(Modifier.fillMaxWidth())
                    Box(modifier = Modifier.weight(1f).background(Color.White).padding(vertical = 10.dp), contentAlignment = Alignment.Center){
                        Text(costs)
                    }
                }
                Spacer(modifier = Modifier.size(25.dp))
                CostsList((uiStateCosts as CostsScreenUiState.Success).costsList)
                Spacer(modifier = Modifier.size(25.dp))
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 35.dp),
                    onClick = { }) {
                    Text(addCost)
                }

            }

        }
    }

}

@Composable
fun CostsList(costsList: List<CostModel>) {
    LazyColumn {
        items(costsList) { cost ->
            ItemCost(cost)
        }
    }
}

@Composable
fun ItemCost(cost: CostModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Cyan)
    ) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
            Text(cost.description, fontSize = 36.sp, color = Color.Red)
            Text(cost.amount.toString(), fontSize = 36.sp, color = Color.Red)
        }

    }
}
