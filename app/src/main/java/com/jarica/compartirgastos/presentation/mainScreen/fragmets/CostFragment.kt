package com.jarica.compartirgastos.presentation.mainScreen.fragmets

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.presentation.costsScreen.CostsScreenUiState
import com.jarica.compartirgastos.presentation.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.mainScreen.MainScreenViewModel.Companion.iDGroupName
import com.jarica.compartirgastos.presentation.ui.addCost
import com.jarica.compartirgastos.presentation.ui.costs
import com.jarica.compartirgastos.presentation.ui.resume
import com.jarica.compartirgastos.presentation.ui.theme.DarkGrey
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellow
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

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        items(costsList) { cost ->
            if (cost.idGroup == idGroup) {
                ItemCost(cost)
            }
        }
    }

}


@Composable
fun ItemCost(cost: CostModel) {

    Row(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .background(DarkGrey)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween

    ) {

        Text(cost.description, color = White, fontFamily = rubik)
        Spacer(modifier = Modifier.weight(1f))

        Row {
            Text(cost.amount.toString().uppercase(Locale.getDefault()), color = DarkYellow, fontFamily = rubik)
            Text(" €", color = DarkYellow, fontFamily = rubik)
        }


    }



}

