package com.jarica.compartirgastos.features.payments.presentation.addPayScreen

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
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.core.presentation.composables.CustomHeader
import com.jarica.compartirgastos.core.presentation.composables.CustomTextField
import com.jarica.compartirgastos.core.presentation.ui.addPayment
import com.jarica.compartirgastos.core.presentation.ui.amountPlaceHolder
import com.jarica.compartirgastos.core.presentation.ui.payForPlaceHolder
import com.jarica.compartirgastos.core.presentation.ui.payToPlaceHolder
import com.jarica.compartirgastos.core.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.core.presentation.ui.theme.Black
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.Grey
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.core.utils.HEADER_WEIGHT

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentScreen(
    groupId: String,
    addPaymentScreenViewModel: AddPaymentScreenViewModel,
    navigateToMainScreen: () -> Unit
) {


    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiAddPaymentState by produceState<AddPaymentUiState>(
        initialValue = AddPaymentUiState.LoadingAddPayment,
        key1 = lifecycle,
        key2 = addPaymentScreenViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            addPaymentScreenViewModel.uiAddPaymentUiState.collect { value = it }
        }
    }

    val amountText: String by addPaymentScreenViewModel.amountText.observeAsState("")
    val personWhoPayText: String by addPaymentScreenViewModel.personWhoPayText.observeAsState("")
    val personWhoReceiveText: String by addPaymentScreenViewModel.personWhoReceiveText.observeAsState(
        ""
    )
    val isPaidForSelected: Boolean by addPaymentScreenViewModel.isPaidForSelected.observeAsState(
        false
    )
    val isPaidToSelected: Boolean by addPaymentScreenViewModel.isPaidToSelected.observeAsState(false)

    val personWhoReceive: PersonModel by addPaymentScreenViewModel.personWhoReceive.observeAsState(
        PersonModel(idPerson = "", name = "", idGroupName = "")
    )
    val personWhoPay: PersonModel by addPaymentScreenViewModel.personWhoPay.observeAsState(
        PersonModel(idPerson = "", name = "", idGroupName = "")
    )


    when (uiAddPaymentState) {
        is AddPaymentUiState.ErrorAddPayment -> {}
        is AddPaymentUiState.LoadingAddPayment -> {}
        is AddPaymentUiState.SuccessAddPayment -> {

            val listOfPeople =
                (uiAddPaymentState as AddPaymentUiState.SuccessAddPayment).listOfPeople


                MainScreenAddPayment(
                    addPaymentScreenViewModel,
                    amountText,
                    personWhoPayText,
                    personWhoReceiveText,
                    listOfPeople,
                    isPaidForSelected,
                    isPaidToSelected,
                    navigateToMainScreen,
                    personWhoPay,
                    personWhoReceive,
                    groupId
                )
            }
        }


}

@Composable
fun MainScreenAddPayment(
    addPaymentScreenViewModel: AddPaymentScreenViewModel,
    amountText: String,
    personWhoPayText: String,
    personWhoReceiveText: String,
    listOfPeople: List<PersonModel>,
    isPaidForSelected: Boolean,
    isPaidToSelected: Boolean,
    navigateToMainScreen: () -> Unit,
    personWhoPay: PersonModel,
    personWhoReceive: PersonModel,
    groupId: String,
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
            text = addPayment,
            icon = R.drawable.arrow_back
        )
        Column(modifier = Modifier
            .padding(horizontal = 32.dp)
            .weight(1f-HEADER_WEIGHT)) {

            Spacer(Modifier.height(20.dp))
            // TEXTFIELD PAGADO POR
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Grey)
                    .clickable {
                        addPaymentScreenViewModel.onFromSelected(isPaidForSelected)
                    },

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    "$payForPlaceHolder:      $personWhoPayText",
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
                if (isPaidForSelected) {

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
                                        addPaymentScreenViewModel.onPersonWhoPaySelected(person)
                                    }) {
                                if (person.idGroupName == groupId && personWhoReceiveText != person.name) {
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

            Spacer(Modifier.height(20.dp))

            // TEXTFIELD PAGADO A
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Grey)
                    .clickable {
                        addPaymentScreenViewModel.onToSelected(isPaidToSelected)
                    },

                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.Top
            ) {
                 Text(
                    "$payToPlaceHolder:      $personWhoReceiveText",
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
                if (isPaidToSelected) {

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
                                        addPaymentScreenViewModel.onPersonWhoReceiveSelected(person)
                                    }) {
                                if (person.idGroupName == groupId && personWhoPayText != person.name) {
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

            Spacer(modifier = Modifier.size(20.dp))

            //TEXTFIELD CANTIDAD
            CustomTextField(
                value = amountText,
                onValueChange = { addPaymentScreenViewModel.onAmountChange(it) },
                placeholderText = amountPlaceHolder,
                textStyle = TextStyle(
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
            )
            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 8.dp),
                thickness = 1.dp,
                color = DarkOrange.copy(0.2f)
            )
            Spacer(Modifier.size(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = personWhoPayText != "" && personWhoReceiveText != "" && amountText != "",
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = Grey,
                    disabledContentColor = Black
                ),
                onClick = {
                    addPaymentScreenViewModel.addPayment(
                        personWhoPay,
                        personWhoReceive,
                        amountText
                    )
/*                    addPaymentScreenViewModel.updatePersons(
                        personWhoPay,
                        personWhoReceive,
                        amountText
                    )*/
                    addPaymentScreenViewModel.clearTexts()
                    navigateToMainScreen()
                }) {
                Text(
                    addPayment,
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

