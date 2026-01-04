package com.jarica.compartirgastos.features.costs.presentation.costsScreen

import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.presentation.ui.theme.Black
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.features.costs.presentation.editCostScreen.EditCostScreenViewModel

@Composable
fun CostFragment(
    idGroup: String?,
    navigateToEditCost: (CostModel) -> Unit,
    editCostScreenViewModel: EditCostScreenViewModel,
    costsViewModel: CostsViewModel,
) {

    LaunchedEffect(idGroup) {
        costsViewModel.setGroup(idGroup)
    }

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStateCosts by produceState<CostsScreenUiState>(
        initialValue = CostsScreenUiState.Loading,
        key1 = lifecycle,
        key2 = costsViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            costsViewModel.uiStateCosts.collect { value = it }
        }
    }

    when (uiStateCosts) {
        is CostsScreenUiState.Error -> {}
        CostsScreenUiState.Loading -> {}
        is CostsScreenUiState.Success -> {

            CostsList(
                (uiStateCosts as CostsScreenUiState.Success).costsList,
                idGroup,
                navigateToEditCost,
                editCostScreenViewModel
            )

        }

    }

}


@Composable
fun CostsList(
    costList: List<CostModel>,
    idGroup: String?,
    navigateToEditCost: (CostModel) -> Unit,
    editCostScreenViewModel: EditCostScreenViewModel,
) {

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        items(costList) { cost ->
            if (cost.idGroup == idGroup) {
                ItemCost(cost,  navigateToEditCost, editCostScreenViewModel)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }

    }
}

@Composable
fun ItemCost(
    item: CostModel,
    navigateToEditCost: (CostModel) -> Unit,
    editCostScreenViewModel: EditCostScreenViewModel
) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .clickable {
                    editCostScreenViewModel.setIdCost(item.idCost)
                    navigateToEditCost(item)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                item.description,
                fontSize = 15.sp,
                color = Black,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "%.2f".format(item.amount),
                fontSize = 15.sp,
                color = Black,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal
            )
            Text(
                " €",
                fontSize = 15.sp,
                color = DarkOrange,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal
            )
        }
}

