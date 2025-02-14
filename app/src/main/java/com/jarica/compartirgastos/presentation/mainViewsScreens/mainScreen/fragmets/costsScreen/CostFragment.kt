package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.costsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik
import java.util.Locale

@Composable
fun CostFragment(
    idGroup: Int?,
    mainScreenViewModel: MainScreenViewModel
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStateCosts by produceState<CostsScreenUiState>(
        initialValue = CostsScreenUiState.Loading,
        key1 = lifecycle,
        key2 = mainScreenViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            mainScreenViewModel.uiStateCosts.collect { value = it }
        }
    }

    when (uiStateCosts) {
        is CostsScreenUiState.Error -> {}
        CostsScreenUiState.Loading -> {}
        is CostsScreenUiState.Success -> {

            CostsList((uiStateCosts as CostsScreenUiState.Success).costsList, idGroup)


        }
    }

}

@Composable
fun CostsList(costsList: List<CostModel>, idGroup: Int?) {

    LazyColumn {
        items(costsList) { cost ->
            if (cost.idGroup == idGroup) {
                ItemCost(cost)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }

}


@Composable
fun ItemCost(cost: CostModel) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(cost.description, color = Black, fontFamily = rubik)
        Spacer(modifier = Modifier.weight(1f))
        Text(
            "%.2f".format(Locale.ITALY, cost.amount),
            color = Black,
            fontFamily = rubik
        )
        Text(" €", color = Black, fontFamily = rubik)


    }


}

