package com.jarica.compartirgastos.features.costs.presentation.costsScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.presentation.composables.CustomIcon
import com.jarica.compartirgastos.core.presentation.composables.EmptyState
import com.jarica.compartirgastos.core.presentation.ui.emptyCostsSubtitle
import com.jarica.compartirgastos.core.presentation.ui.emptyCostsTitle
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.core.utils.toMoneyDisplay

private val CostDivider = Color(0xFFE6E4DE)
private val CostInk     = Color(0xFF1F2A33)

@Composable
fun CostFragment(
    idGroup: String?,
    navigateToEditCost: (CostModel) -> Unit,
    costsViewModel: CostsViewModel,
    modifier: Modifier = Modifier,
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
                navigateToEditCost,
                modifier
            )
        }
    }
}

@Composable
fun CostsList(
    costList: List<CostModel>,
    navigateToEditCost: (CostModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (costList.isEmpty()) {
        EmptyState(
            title = emptyCostsTitle,
            subtitle = emptyCostsSubtitle
        )
    } else {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(costList, key = { it.idCost }) { cost ->
                ItemCost(cost, navigateToEditCost)
            }
        }
    }
}

@Composable
fun ItemCost(
    item: CostModel,
    navigateToEditCost: (CostModel) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateToEditCost(item)
            }
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomIcon(name = item.description)

        Text(
            item.description,
            fontSize = 14.sp,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Medium,
            color = CostInk
        )

        Spacer(Modifier.weight(1f))

        Text(
            item.amount.toMoneyDisplay(),
            fontSize = 15.sp,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Bold,
            color = DarkOrange
        )
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 18.dp),
        color = CostDivider.copy(alpha = 0.5f),
        thickness = 1.dp
    )
}
