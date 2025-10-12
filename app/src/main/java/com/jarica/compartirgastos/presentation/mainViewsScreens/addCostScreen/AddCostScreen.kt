package com.jarica.compartirgastos.presentation.mainViewsScreens.addCostScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import com.jarica.compartirgastos.presentation.ui.addCost
import com.jarica.compartirgastos.presentation.ui.amountPlaceHolder
import com.jarica.compartirgastos.presentation.ui.descriptionPlaceHolder
import com.jarica.compartirgastos.presentation.ui.fromText
import com.jarica.compartirgastos.presentation.ui.next
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCostScreen(
    addCostViewModel: AddCostScreenViewModel,
    navigateToMainScreen: () -> Unit,
) {

    val descriptionText: String by addCostViewModel.descriptionText.observeAsState("")
    val amountText: String by addCostViewModel.amountText.observeAsState("")
    val isFromSelected: Boolean by addCostViewModel.isFromSelected.observeAsState(false)
    val fromTextAddCosts: String by addCostViewModel.fromTextAddCost.observeAsState("")
    val personToAddCosts: PersonModel? by addCostViewModel.personToAddCost.observeAsState(null)

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiAddCostState by produceState<AddCostsUiState>(
        initialValue = AddCostsUiState.Loading,
        key1 = lifecycle,
        key2 = addCostViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            addCostViewModel.uiAddCostsUiState.collect { value = it }
        }
    }

    when (uiAddCostState) {
        is AddCostsUiState.Error -> {}
        is AddCostsUiState.Loading -> {}
        is AddCostsUiState.Success -> {

            val listOfPeople = (uiAddCostState as AddCostsUiState.Success).listOfPeople
            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp),
                        colors = topAppBarColors(
                            containerColor = Transparent,
                            actionIconContentColor = Black,
                            navigationIconContentColor = Black
                        ),

                        navigationIcon = {
                            IconButton(
                                modifier = Modifier
                                    .size(40.dp),
                                onClick = {
                                    addCostViewModel.cleanTexts()
                                    navigateToMainScreen()
                                }) {
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    painter = painterResource(R.drawable.arrow_back),
                                    contentDescription = "",
                                )
                            }
                        },
                        actions = {
                            if (descriptionText != "" && amountText != "" && fromTextAddCosts != "") {

                                Text(
                                    next,
                                    fontFamily = rubik,
                                    fontWeight = FontWeight.Medium,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .clickable {
                                            addCostViewModel.addCostToGroup(
                                                personToAddCosts = personToAddCosts!!,
                                                listOfPeople,
                                                iDGroupName
                                            )
                                            addCostViewModel.updatePerson(
                                                personToAddCosts = personToAddCosts!!,
                                                listOfPeople = listOfPeople
                                            )
                                            addCostViewModel.cleanTexts()
                                            navigateToMainScreen()
                                        })

                            }


                        },
                        title = {

                        }
                    )
                }
            ) { paddingValues ->
                MainViewAddCostScreen(
                    paddingValues,
                    addCostViewModel,
                    descriptionText,
                    amountText,
                    isFromSelected,
                    fromTextAddCosts,
                    listOfPeople
                )
            }

        }
    }

}


@Composable
fun MainViewAddCostScreen(
    paddingValues: PaddingValues,
    addCostViewModel: AddCostScreenViewModel,
    descriptionText: String,
    amountText: String,
    isFromSelected: Boolean,
    fromTextAddCosts: String,
    listOfPeople: List<PersonModel>,

    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(Modifier.height(125.dp))
        Text(
            addCost,
            fontFamily = rubik,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))

        //TEXTFIELD DESCRIPCION
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            value = descriptionText,
            onValueChange = { descriptionText ->
                addCostViewModel.onDescriptionChange(
                    descriptionText
                )
            },
            textStyle = TextStyle(
                fontFamily = rubik,
                fontSize = 12.sp,
                fontWeight = FontWeight.W300
            ),
            shape = RoundedCornerShape(8.dp),
            placeholder = {
                Text(
                    descriptionPlaceHolder, fontFamily = rubik,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
                    color = Black
                )
            },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = White,
                unfocusedLabelColor = Black,
                unfocusedTextColor = Black,
                focusedContainerColor = White,
                focusedTextColor = Black,
                focusedLabelColor = Black,
                unfocusedPlaceholderColor = Black,
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
                cursorColor = Black

            ),
        )

        Spacer(Modifier.height(16.dp))

        //TEXTFIELD CANTIDAD
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            value = amountText,
            onValueChange = { addCostViewModel.onAmountChange(it) },
            shape = RoundedCornerShape(8.dp),
            placeholder = {
                Text(
                    amountPlaceHolder, fontFamily = rubik,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
                    color = Black
                )
            },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            suffix = { Text("€") },
            textStyle = TextStyle(
                fontFamily = rubik,
                fontSize = 12.sp,
                fontWeight = FontWeight.W300
            ),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = White,
                unfocusedLabelColor = Black,
                unfocusedTextColor = Black,
                focusedContainerColor = White,
                focusedTextColor = Black,
                focusedLabelColor = Black,
                unfocusedPlaceholderColor = Black,
                focusedIndicatorColor = Transparent,
                unfocusedIndicatorColor = Transparent,
                cursorColor = White,
                unfocusedSuffixColor = Black,
                focusedSuffixColor = Black
            ),
        )

        Spacer(Modifier.height(16.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(White)
                .clickable {
                    addCostViewModel.onFromSelected(isFromSelected)
                },

            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.size(6.dp))
            Text(
                "$fromText:      $fromTextAddCosts",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontFamily = rubik,
                fontSize = 12.sp,
                fontWeight = FontWeight.W300,
                color = Black
            )
            Spacer(modifier = Modifier.size(6.dp))

            if (isFromSelected) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)

                ) {
                    items(listOfPeople) { person ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    addCostViewModel.onPersonSelected(person)

                                }) {
                            if (person.idGroupName == iDGroupName) {
                                Text(
                                    person.name,
                                    fontFamily = rubik,
                                    color = Black,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.W300
                                )
                                Spacer(modifier = Modifier.size(8.dp))
                            }
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(0.8f))
    }
}


