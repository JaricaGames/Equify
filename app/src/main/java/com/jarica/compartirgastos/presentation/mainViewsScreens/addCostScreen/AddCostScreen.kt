package com.jarica.compartirgastos.presentation.mainViewsScreens.addCostScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
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
import com.jarica.compartirgastos.core.HEADER_WEIGHT
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.presentation.composables.CustomHeader
import com.jarica.compartirgastos.presentation.composables.CustomTextField
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import com.jarica.compartirgastos.presentation.ui.addCostText
import com.jarica.compartirgastos.presentation.ui.amountPlaceHolder
import com.jarica.compartirgastos.presentation.ui.descriptionPlaceHolder
import com.jarica.compartirgastos.presentation.ui.fromText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.presentation.ui.theme.Grey
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans


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

            MainViewAddCostScreen(
                addCostViewModel,
                descriptionText,
                amountText,
                isFromSelected,
                fromTextAddCosts,
                listOfPeople,
                navigateToMainScreen,
                personToAddCosts
            )
        }


    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainViewAddCostScreen(
    addCostViewModel: AddCostScreenViewModel,
    descriptionText: String,
    amountText: String,
    isFromSelected: Boolean,
    fromTextAddCosts: String,
    listOfPeople: List<PersonModel>,
    navigateToMainScreen: () -> Unit,
    personToAddCosts: PersonModel?,

    ) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        CustomHeader(
            navigateToMainScreen,
            modifier = Modifier.weight(HEADER_WEIGHT),
            addCostText,
            icon = R.drawable.arrow_back
        )

        Column(
            modifier = Modifier
            .padding(horizontal = 32.dp)
            .weight(1f-HEADER_WEIGHT)) {

            Spacer(Modifier.height(20.dp))
            //TEXTFIELD DESCRIPCION
            CustomTextField(
                value = descriptionText,
                onValueChange = { addCostViewModel.onDescriptionChange(it) },
                placeholderText = descriptionPlaceHolder,
                textStyle = TextStyle(
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp,
                color = DarkOrange.copy(0.2f)
            )
            Spacer(Modifier.height(20.dp))

            //TEXTFIELD CANTIDAD
            CustomTextField(
                value = amountText,
                onValueChange = { addCostViewModel.onAmountChange(it) },
                placeholderText = amountPlaceHolder,
                suffixText = "€",
                textStyle = TextStyle(
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp,
                color = DarkOrange.copy(0.2f)
            )
            Spacer(Modifier.height(20.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Grey)
                    .clickable {
                        addCostViewModel.onFromSelected(isFromSelected)
                    },

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {

                Text(
                    "$fromText:      $fromTextAddCosts",
                    modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                )
                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    thickness = 1.dp,
                    color = DarkOrange.copy(0.2f)
                )

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
                                        fontFamily = parkinsans,
                                        fontWeight = FontWeight.Normal,
                                        textAlign = TextAlign.Start,
                                        fontSize = 12.sp,
                                    )
                                    Spacer(modifier = Modifier.size(8.dp))
                                }
                            }
                        }
                    }
                }
            }
            Spacer(Modifier.size(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = descriptionText != "" && amountText != "" && fromTextAddCosts != "",
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = Grey,
                    disabledContentColor = Black
                ),
                onClick = {
                    addCostViewModel.addCostToGroup(
                        personToAddCosts = personToAddCosts!!,
                        listOfPeople,
                        iDGroupName
                    )
                    addCostViewModel.updatePerson(
                        personToAddCosts = personToAddCosts,
                        listOfPeople = listOfPeople
                    )
                    addCostViewModel.cleanTexts()
                    navigateToMainScreen()
                }) {
                Text(
                    addCostText,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                )
            }
        }
    }
}




