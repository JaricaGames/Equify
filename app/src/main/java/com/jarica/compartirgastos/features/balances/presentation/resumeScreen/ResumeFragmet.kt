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
import com.jarica.compartirgastos.core.presentation.ui.theme.Black
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkGreen
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.Red
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import kotlin.math.absoluteValue

@Composable
fun ResumeFragment(
    idGroup: String?,
    modifier: Modifier,
    resumeViewModel: ResumeViewModel,
) {
/*
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStatePeopleGroupFragment by produceState<ResumeUiState>(
        initialValue = ResumeUiState.Loading,
        key1 = lifecycle,
        key2 = resumeViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            resumeViewModel.uiStateResumeGroup.collect { value = it }
        }
    }*/

    LaunchedEffect(idGroup) {
        resumeViewModel.setGroup(idGroup)
    }

    val uiStateResumeFragment by resumeViewModel.uiStateResumeGroup.collectAsState()

    when (val state = uiStateResumeFragment) {

        is ResumeUiState.Error -> {
            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: ${(uiStateResumeFragment as ResumeUiState.Error).message}", color = Color.Red)
            }
        }
        is ResumeUiState.Loading -> {

            Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is ResumeUiState.Success -> {
          //  mainScreenViewModel.getGroupNameById(idGroup!!)
            val personBalanceList = state.peopleList
            PeopleList(personBalanceList)

        }
    }
}

@Composable
fun PeopleList(
    personBalanceList: List<PersonBalance>,
) {

    LazyColumn(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            personBalanceList,
            key = { it.idPerson }
        ) { balancePerson ->
                ItemPeopleName(balancePerson)
        }
    }
}

@Composable
fun ItemPeopleName(person: PersonBalance) {


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(
            person.name,
            fontSize = 15.sp,
            color = Black,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal
        )
        Spacer(modifier = Modifier.weight(1f))
        if (person.balance > 0f) {
            Row {
                Text(
                    "+" + "%.2f".format(person.balance) + " €",
                    fontSize = 15.sp,
                    color = DarkGreen,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal
                )
            }

        }
        if (person.balance < 0f) {
            Row {
                Text(
                    "-" + "%.2f".format(person.balance.absoluteValue) + " €",
                    fontSize = 15.sp,
                    color = Red,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal
                )

            }
        }
        if (person.balance == 0f) {
            Row {

                Text(
                    "%.2f".format(person.balance.absoluteValue),
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
        }
    }
}