package com.jarica.compartirgastos.features.costs.presentation.editCostScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.CostPaymentsModel
import com.jarica.compartirgastos.core.presentation.composables.CustomTextField
import com.jarica.compartirgastos.core.presentation.ui.editCost
import com.jarica.compartirgastos.core.presentation.ui.labelTextFieldAddPeopleScreen
import com.jarica.compartirgastos.core.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.core.presentation.ui.theme.Black
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.Grey
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.core.utils.HEADER_WEIGHT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCostScreen(
    idCost: String,
    amount: Float,
    description: String,
//personString: String,
    editCostScreenViewModel: EditCostScreenViewModel,
    navigateToMainScreen: () -> Unit
) {

    val uiStateEditCost by editCostScreenViewModel.uiStateEditCost.collectAsState()
    val descriptionCost: String by editCostScreenViewModel.descriptionCost.observeAsState(
        description
    )
    val amountCost: Float by editCostScreenViewModel.amountCost.observeAsState(amount)


    LaunchedEffect(idCost) {
        editCostScreenViewModel.setIdCost(idCost)
    }

    when (uiStateEditCost) {
        is EditCostUiState.Loading -> {}
        is EditCostUiState.Error -> {}
        is EditCostUiState.Success -> {
            // costPaymentsList = state.listOfCostPaymentsModel
            MainViewEditCostScreen(
                amountCost,
                descriptionCost,
                //payFor,
                navigateToMainScreen,
                editCostScreenViewModel,
                idCost,
                (uiStateEditCost as EditCostUiState.Success).listOfCostPaymentsModel
                //listOfCostOfPerson
            )
        }
    }
}

@Composable
fun MainViewEditCostScreen(
    amount: Float,
    description: String,
    //personString: String,
    navigateToMainScreen: () -> Unit,
    editCostScreenViewModel: EditCostScreenViewModel,
    idCost: String,
    costPaymentsList: List<CostPaymentsModel>,
   // listOfCostOfPerson: List<CostOfPersonModel>,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
                .background(color = DarkBlue)
                .padding(bottom = 20.dp)
                .weight(HEADER_WEIGHT),
            contentAlignment = Alignment.BottomCenter
        ) {
            IconButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomStart)
                    .padding(start = 16.dp)
                    .size(24.dp) // un poco más pequeño
                    .offset(x = 2.dp), // ajusta visualmente el centrado fino,
                onClick =
                    { navigateToMainScreen() }
            ) {
                Icon(

                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = "",
                    tint = White
                )
            }
            Text(
                editCost,
                fontSize = 16.sp,
                color = White,
                fontFamily = parkinsans,
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.Center
            )
            IconButton(
                modifier = Modifier
                    .align(alignment = Alignment.BottomEnd)
                    .padding(end = 16.dp)
                    .size(24.dp) // un poco más pequeño
                    .offset(x = 2.dp), // ajusta visualmente el centrado fino,
                onClick =
                    {
                       /* editCostScreenViewModel.onDeletedSelected(
                            idCost,
                           // listOfCostOfPerson,
                        )
                        navigateToMainScreen()*/
                    }
            ) {
                Icon(

                    painter = painterResource(
                        id = R.drawable.delete_svgrepo
                    ),
                    contentDescription = "",
                    tint = White
                )
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .weight(1f - HEADER_WEIGHT)
        ) {
            Spacer(modifier = Modifier.weight(0.02f))
            CustomTextField(
                value = description,
                onValueChange = { editCostScreenViewModel.onDescriptionTextFieldChange(it) },
                placeholderText = labelTextFieldAddPeopleScreen,
                textStyle = TextStyle(
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.weight(0.02f))
            CustomTextField(
                value = amount.toString(),
                onValueChange = { },
                placeholderText = "",
                textStyle = TextStyle(
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                ),
                suffixText = "€"
            )
            Spacer(modifier = Modifier.weight(0.02f))
            CustomTextField(
                value = costPaymentsList.joinToString(
                    separator = ", ",
                    prefix = "[",
                    postfix = "]"
                ) { it.name },
                onValueChange = { },
                placeholderText = "",
                textStyle = TextStyle(
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.weight(0.02f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = Grey,
                    disabledContentColor = Black
                ),
                onClick = {
                    editCostScreenViewModel.updateCost(description, amount, idCost )
                    navigateToMainScreen()
                }) {
                Text(
                    editCost,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }

    /*
    //TEXTFIELD CANTIDAD
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            value = amount.toString(),
            readOnly = true,
            onValueChange = {},
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            maxLines = 1,
            suffix = { Text("€") },
            textStyle = TextStyle(fontFamily = rubik),
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

    //TEXTFIELD person
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            value = "$PayFor      $personString",
            readOnly = true,
            onValueChange = {},
            shape = RoundedCornerShape(8.dp),
            singleLine = true,
            maxLines = 1,
            textStyle = TextStyle(fontFamily = rubik),
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


        Spacer(modifier = Modifier.weight(0.8f))*/
}




