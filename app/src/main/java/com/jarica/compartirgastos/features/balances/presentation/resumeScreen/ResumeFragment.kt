package com.jarica.compartirgastos.features.balances.presentation.resumeScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.core.domain.models.PersonBalance
import com.jarica.compartirgastos.core.presentation.composables.CustomIcon
import com.jarica.compartirgastos.core.presentation.composables.EmptyState
import com.jarica.compartirgastos.core.presentation.ui.emptyResumeSubtitle
import com.jarica.compartirgastos.core.presentation.ui.emptyResumeTitle
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import kotlin.math.absoluteValue

private val BalGreen  = Color(0xFF2E8B6F)
private val BalRed    = Color(0xFFC0533D)
private val BalMuted  = Color(0xFF6B7A86)
private val Divider   = Color(0xFFE6E4DE)
private val Ink       = Color(0xFF1F2A33)

@Composable
fun ResumeFragment(
    idGroup: String?,
    modifier: Modifier,
    resumeViewModel: ResumeViewModel,
) {
    LaunchedEffect(idGroup) {
        resumeViewModel.setGroup(idGroup)
    }

    val uiState by resumeViewModel.uiStateResumeGroup.collectAsState()

    when (val state = uiState) {
        is ResumeUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${state.message}", color = Color.Red)
            }
        }
        is ResumeUiState.Loading -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is ResumeUiState.Success -> {
            PeopleList(state.peopleList, modifier)
        }
    }
}

@Composable
fun PeopleList(personBalanceList: List<PersonBalance>, modifier: Modifier = Modifier) {
    if (personBalanceList.isEmpty() || personBalanceList.all { it.balance == 0f }) {
        EmptyState(
            title = emptyResumeTitle,
            subtitle = emptyResumeSubtitle
        )
    } else {
        LazyColumn(modifier = modifier.fillMaxWidth()) {
            items(personBalanceList, key = { it.idPerson }) { person ->
                MemberRow(person)
            }
        }
    }
}

@Composable
fun MemberRow(person: PersonBalance) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CustomIcon(name = person.name)

        Text(
            person.name,
            fontSize = 14.sp,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Medium,
            color = Ink
        )

        Spacer(Modifier.weight(1f))

        val (balanceText, balanceColor) = when {
            person.balance > 0f -> "+ ${"%.2f".format(person.balance)} €" to BalGreen
            person.balance < 0f -> "− ${"%.2f".format(person.balance.absoluteValue)} €" to BalRed
            else                -> "${"%.2f".format(0f)} €" to BalMuted
        }

        Text(
            balanceText,
            fontSize = 15.sp,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Bold,
            color = balanceColor
        )
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 18.dp),
        color = Divider.copy(alpha = 0.5f),
        thickness = 1.dp
    )
}
