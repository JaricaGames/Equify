package com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.resumeScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainUiState
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik
import kotlin.math.absoluteValue

@Composable

fun ResumeFragment(
    idGroup: Int?,
    mainScreenViewModel: MainScreenViewModel,
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStatePeopleGroupFragment by produceState<MainUiState>(
        initialValue = MainUiState.Loading,
        key1 = lifecycle,
        key2 = mainScreenViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            mainScreenViewModel.uiStateResumeGroup.collect { value = it }
        }
    }

    when (uiStatePeopleGroupFragment) {

        is MainUiState.Error -> {}
        is MainUiState.Loading -> {

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is MainUiState.Success -> {

            mainScreenViewModel.getGroupNameById(idGroup!!)
            PeopleList((uiStatePeopleGroupFragment as MainUiState.Success).peopleList, idGroup)

        }
    }

}

@Composable
fun PeopleList(groupNameList: List<PersonModel>, idGroup: Int?) {

    LazyColumn {
        items(groupNameList) { person ->
            if (person.idGroupName == idGroup) {
                ItemPeopleName(person)
                Spacer(modifier = Modifier.size(8.dp))
            }
        }
    }
}

@Composable
fun ItemPeopleName(item: PersonModel) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Text(item.name, color = Black, fontFamily = rubik,
                fontSize = 12.sp,
            fontWeight = FontWeight.W300)
        Spacer(modifier = Modifier.weight(1f))
        if (item.equity.toFloat() >= 0f) {
            Row {
                Text(
                    "%.2f".format(item.equity.toFloat()),
                    color = Black,
                    fontFamily = rubik,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
                Text(
                    " €", color = Black, fontFamily = rubik,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
            }

        } else {
            Row {
                Text(
                    "- ", color = Black, fontFamily = rubik, fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
                Text(
                    "%.2f".format(item.equity.toFloat().absoluteValue),
                    color = Black,
                    fontFamily = rubik,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
                Text(" €", color = Black, fontFamily = rubik)
            }
        }


    }
}