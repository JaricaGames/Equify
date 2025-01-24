package com.jarica.compartirgastos.presentation.mainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.presentation.ui.addCost
import com.jarica.compartirgastos.presentation.ui.addPay
import com.jarica.compartirgastos.presentation.ui.addPeople
import com.jarica.compartirgastos.presentation.ui.addPeopleText
import com.jarica.compartirgastos.presentation.ui.costs
import com.jarica.compartirgastos.presentation.ui.doTheCount
import com.jarica.compartirgastos.presentation.ui.resume
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkGrey
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellow
import com.jarica.compartirgastos.presentation.ui.theme.Grey
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    idGroup: Int?,
    mainScreenViewModel: MainScreenViewModel,
    navigateToAddCostScreen: () -> Unit,
    navigateToCosts: () -> Unit,
    navigateToAddPeopleFromGroup: (Int) -> Unit,
) {

    val nameOfGroup: String by mainScreenViewModel.nameOfGroup.observeAsState("")

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

            mainScreenViewModel.getGroupNameById(idGroup!!)

            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        colors = topAppBarColors(
                            containerColor = Transparent,
                            actionIconContentColor = White,
                            navigationIconContentColor = White
                        ),

                        navigationIcon = {
                            IconButton(modifier = Modifier
                                .clip(
                                    shape = CircleShape
                                )
                                .background(Grey)
                                .size(40.dp), onClick = {
                                // Navegar a la pantalla de lista de grupos
                            }) {
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    painter = painterResource(R.drawable.arrow_back),
                                    contentDescription = "",

                                    )
                            }
                        },
                        actions = {
                            IconButton(modifier = Modifier
                                .clip(
                                    shape = CircleShape
                                )
                                .background(Grey)
                                .size(40.dp), onClick = {
                                // Navegar a la pantalla de lista de grupos
                            }) {
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    painter = painterResource(R.drawable.settings),
                                    contentDescription = "",

                                    )
                            }
                        },
                        title = {
                        }
                    )
                }
            ) { paddingValues ->
                MainView(
                    uiStatePeopleGroupScreen,
                    navigateToAddCostScreen,
                    navigateToCosts,
                    navigateToAddPeopleFromGroup,
                    idGroup,
                    mainScreenViewModel,
                    paddingValues,
                    nameOfGroup
                )
            }


        }
    }
}


@Composable
fun MainView(
    uiStatePeopleGroupScreen: MainUiState,
    navigateToAddCostScreen: () -> Unit,
    navigateToCosts: () -> Unit,
    navigateToAddPeopleFromGroup: (Int) -> Unit,
    idGroup: Int?,
    mainScreenViewModel: MainScreenViewModel,
    paddingValues: PaddingValues,
    nameOfGroup: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(vertical = paddingValues.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Header(nameOfGroup)
        ActionsBoxes(mainScreenViewModel)
        Spacer(Modifier.height(16.dp))
        ChooseScreen(navigateToCosts)

        Spacer(modifier = Modifier.size(25.dp))
        PeopleList((uiStatePeopleGroupScreen as MainUiState.Success).peopleList, idGroup)
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
                mainScreenViewModel.text()
            })
        {
            Text(doTheCount)
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 35.dp),
            onClick = {
                // navigateToAddPeople(idGroup!!, "")
            })
        {
            Text(addPeopleText)
        }

    }

}

@Composable
fun ChooseScreen(navigateToCosts: () -> Unit) {

    Row(
        modifier = Modifier
            .fillMaxWidth().background(DarkGrey).border(width = 1.dp, color = DarkYellow)
    ) {
        Box(
            modifier = Modifier
                .weight(1f).clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 32.dp))
                .background(DarkYellow)
                .padding(vertical = 10.dp), contentAlignment = Alignment.Center
        ) {
            Text(resume, fontFamily = rubik, color = Black, fontWeight = FontWeight.SemiBold)
        }

        Box(
            modifier = Modifier
                .weight(0.6f)
                .padding(vertical = 10.dp)
                .clickable { navigateToCosts() },
            contentAlignment = Alignment.Center
        ) {
            Text(costs, fontFamily = rubik, color = White, fontWeight = FontWeight.Normal, fontSize = 12.sp)
        }
    }
}

@Composable
fun ActionsBoxes(mainScreenViewModel: MainScreenViewModel) {

    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(DarkGrey)
                    .size(60.dp)
                    .border(width = 1.dp, color = DarkYellow, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(painterResource(R.drawable.moneycash), "", modifier = Modifier.size(40.dp), tint = White)
            }
            Spacer(Modifier.size(3.dp))
            Text(
                addCost,
                fontSize = 10.sp,
                fontFamily = rubik,
                fontWeight = FontWeight.Normal,
                color = White,
                textAlign = TextAlign.Center
            )
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(DarkGrey)
                    .size(60.dp)
                    .border(width = 1.dp, color = DarkYellow, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(painterResource(R.drawable.people_add), "", tint = White, modifier = Modifier.size(40.dp))
            }
            Spacer(Modifier.size(3.dp))
            Text(
                addPeople,
                fontSize = 10.sp,
                fontFamily = rubik,
                fontWeight = FontWeight.Normal,
                color = White,
                textAlign = TextAlign.Center
            )
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(DarkGrey)
                    .size(60.dp)
                    .border(width = 1.dp, color = DarkYellow, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(painterResource(R.drawable.addpay), "", tint = White, modifier = Modifier.size(40.dp))

            }
            Spacer(Modifier.size(3.dp))
            Text(
                addPay,
                fontSize = 10.sp,
                fontFamily = rubik,
                fontWeight = FontWeight.Normal,
                color = White,
                textAlign = TextAlign.Center
            )
        }
        Column(modifier = Modifier.weight(1f), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(16.dp)
                    )
                    .background(DarkYellow)
                    .size(60.dp), contentAlignment = Alignment.Center
            ) {

                Icon(painterResource(R.drawable.arrowsclock), "", tint = Black, modifier = Modifier.size(40.dp))
            }
            Spacer(Modifier.size(3.dp))
            Text(
                doTheCount,
                fontSize = 10.sp,
                fontFamily = rubik,
                fontWeight = FontWeight.Normal,
                color = White,
                textAlign = TextAlign.Center,
            )
        }




    }
}

@Composable
fun Header(nameOfGroup: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 30.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            nameOfGroup,
            fontSize = 30.sp,
            color = White,
            fontFamily = rubik,
            fontWeight = FontWeight.W600
        )
    }
}


@Composable
fun PeopleList(groupNameList: List<PersonModel>, idGroup: Int?) {
    LazyColumn {
        items(groupNameList) { person ->
            if (person.idGroupName == idGroup) {
                ItemPeopleName(person)
            }
        }
    }
}

@Composable
fun ItemPeopleName(item: PersonModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Cyan)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(item.name, fontSize = 36.sp, color = Color.Red)
            Text(item.equity, fontSize = 36.sp, color = Color.Red)
        }

    }
}



