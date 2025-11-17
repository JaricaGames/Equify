package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.costsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.presentation.ui.theme.Grey
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.presentation.ui.totalCostText

@Composable
fun CostFragment(
    idGroup: Int?,
    mainScreenViewModel: MainScreenViewModel,
    navigateToEditCost: (CostModel) -> Unit,
    modifier: Modifier
) {

    val totalCost: Float by mainScreenViewModel.totalCost.observeAsState(0f)

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

            CostsList(
                (uiStateCosts as CostsScreenUiState.Success).costsList,
                idGroup,
                mainScreenViewModel,
                navigateToEditCost
            )
            Box(
                modifier = modifier
                    .padding(horizontal = 16.dp)
                    .clip(shape = RoundedCornerShape(16.dp))
            )
            {

                TotalCostComponent(
                    totalCost,
                )
            }
        }

    }

}

@Composable
fun TotalCostComponent(
    totalCost: Float,
) {

    Row(
        modifier = Modifier
            .background(DarkOrange)
            .clip(shape = RoundedCornerShape(8.dp))
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
            },
        horizontalArrangement = Arrangement.End
    ) {
        Text(
            totalCostText,
            fontFamily = parkinsans,
            fontWeight = FontWeight.SemiBold,
            color = White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 12.sp,
            )
        )
        Text(
            "$totalCost €",
            fontFamily = parkinsans,
            fontWeight = FontWeight.SemiBold,
            color = White,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 12.sp,
            )
        )


    }
}

@Composable
fun CostsList(
    costList: List<CostModel>,
    idGroup: Int?,
    mainScreenViewModel: MainScreenViewModel,
    navigateToEditCost: (CostModel) -> Unit,
) {

    mainScreenViewModel.clearCosts()
    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {

        items(costList) { cost ->
            if (cost.idGroup == idGroup) {
                mainScreenViewModel.addCostToTotal(cost.amount)
                ItemCost(cost, mainScreenViewModel, navigateToEditCost)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }

    }
}

@Composable
fun ItemCost(
    item: CostModel,
    mainScreenViewModel: MainScreenViewModel,
    navigateToEditCost: (CostModel) -> Unit
) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(Grey)
                .padding(horizontal = 32.dp, vertical = 8.dp)
                .clickable {
                    mainScreenViewModel.onCostListSelected(item)
                    navigateToEditCost(item)
                },
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                item.description,
                fontSize = 12.sp,
                color = Black,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "%.2f".format(item.amount),
                fontSize = 12.sp,
                color = Black,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal
            )
            Text(
                " €",
                fontSize = 12.sp,
                color = DarkOrange,
                fontFamily = parkinsans,
                fontWeight = FontWeight.Normal
            )
        }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 8.dp),
        thickness = 1.dp,
        color = DarkOrange.copy(0.2f)
    )
}

