package com.jarica.compartirgastos.presentation.mainViewsScreens.addCostScreen

import android.util.Log
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
import androidx.compose.foundation.shape.CircleShape
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
import com.jarica.compartirgastos.presentation.ui.amountPlaceHolder
import com.jarica.compartirgastos.presentation.ui.descriptionPlaceHolder
import com.jarica.compartirgastos.presentation.ui.fromText
import com.jarica.compartirgastos.presentation.ui.saveCost
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.DarkGrey
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellow
import com.jarica.compartirgastos.presentation.ui.theme.Grey
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
        initialValue = AddCostsUiState.LoadingAddCosts,
        key1 = lifecycle,
        key2 = addCostViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            addCostViewModel.uiAddCostsUiState.collect { value = it }
        }
    }

    when (uiAddCostState) {
        is AddCostsUiState.ErrorAddCosts -> {}
        AddCostsUiState.LoadingAddCosts -> {}
        is AddCostsUiState.SuccessAddCosts -> {

            val listOfPeople = (uiAddCostState as AddCostsUiState.SuccessAddCosts).listOfPeople

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
                                .size(40.dp),
                                onClick = {
                                    addCostViewModel.onFromSelected(true)
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
                            if (descriptionText.isNotEmpty() && amountText.isNotEmpty() && fromTextAddCosts.isNotEmpty()) {

                                Text(
                                    saveCost,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .clickable {

                                            Log.d("Nono", "fallaste")
                                            addCostViewModel.onFromSelected(isFromSelected = true)
                                            addCostViewModel.addCostToGroup(personToAddCosts!!)
                                            addCostViewModel.updatePerson(
                                                personToAddCosts!!,
                                                listOfPeople
                                            )
                                            navigateToMainScreen()

                                        },
                                    textAlign = TextAlign.End,
                                    fontSize = 16.sp,
                                    fontFamily = rubik,
                                    fontWeight = FontWeight.Normal,
                                    color = White,
                                )
                            }

                        },
                        title = {
                            /*Text(
                                addCost,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center,
                                fontSize = 16.sp
                            )*/

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
                    listOfPeople,
                    fromTextAddCosts
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
    listOfPeople: List<PersonModel>,
    fromTextAddCosts: String,
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(vertical = paddingValues.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Spacer(Modifier.height(64.dp))

        //TEXTFIELD DESCRIPCION
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = descriptionText,
            onValueChange = { descriptionText ->
                addCostViewModel.onDescriptionChange(
                    descriptionText
                )
            },

            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(descriptionPlaceHolder) },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = DarkGrey,
                unfocusedLabelColor = White,
                unfocusedTextColor = White,
                focusedContainerColor = DarkGrey,
                focusedTextColor = White,
                focusedLabelColor = White,
                unfocusedPlaceholderColor = White,
                focusedIndicatorColor = DarkYellow,
                unfocusedIndicatorColor = Transparent,
                cursorColor = DarkYellow

            ),
        )

        Spacer(Modifier.height(16.dp))

        //TEXTFIELD CANTIDAD
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = amountText,
            onValueChange = { addCostViewModel.onAmountChange(it) },
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(amountPlaceHolder) },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            suffix = { Text("€") },
            textStyle = TextStyle(fontFamily = rubik),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = DarkGrey,
                unfocusedLabelColor = White,
                unfocusedTextColor = White,
                focusedContainerColor = DarkGrey,
                focusedTextColor = White,
                focusedLabelColor = White,
                unfocusedPlaceholderColor = White,
                focusedIndicatorColor = DarkYellow,
                unfocusedIndicatorColor = Transparent,
                cursorColor = DarkYellow,
                unfocusedSuffixColor = White,
                focusedSuffixColor = White
            ),
        )

        Spacer(Modifier.height(16.dp))


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(DarkGrey)
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
                color = White
            )
            Spacer(modifier = Modifier.size(6.dp))

            if (isFromSelected) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)

                ) {
                    items(listOfPeople) { person ->
                        Column(modifier = Modifier.clickable {
                            addCostViewModel.onPersonSelected(
                                person
                            )
                        }) {
                            if (person.idGroupName == iDGroupName) {
                                Text(person.name, fontFamily = rubik, color = White)
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


