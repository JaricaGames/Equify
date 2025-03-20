package com.jarica.compartirgastos.presentation.mainViewsScreens.addPayScreen

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
import com.jarica.compartirgastos.presentation.ui.addPayment
import com.jarica.compartirgastos.presentation.ui.amountPlaceHolder
import com.jarica.compartirgastos.presentation.ui.payForPlaceHolder
import com.jarica.compartirgastos.presentation.ui.payToPlaceHolder
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPaymentScreen(addPaymentScreenViewModel: AddPaymentScreenViewModel) {


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
        PersonModel(idPerson = null, name = "", equity = "", idGroupName = 0)
    )
    val personWhoPay: PersonModel by addPaymentScreenViewModel.personWhoPay.observeAsState(
        PersonModel(idPerson = null, name = "", equity = "", idGroupName = 0))


    when (uiAddPaymentState) {
        is AddPaymentUiState.ErrorAddPayment -> {}
        is AddPaymentUiState.LoadingAddPayment -> {}
        is AddPaymentUiState.SuccessAddPayment -> {

            val listOfPeople =
                (uiAddPaymentState as AddPaymentUiState.SuccessAddPayment).listOfPeople

            Scaffold(
                topBar = {
                    TopAppBar(
                        modifier = Modifier.padding(top = 16.dp),
                        colors = topAppBarColors(
                            containerColor = Transparent,
                            actionIconContentColor = Black,
                            navigationIconContentColor = Black
                        ),

                        navigationIcon = {
                            IconButton(modifier = Modifier
                                .clip(
                                    shape = CircleShape
                                )
                                .size(40.dp), onClick = {

                            }) {
                                Icon(
                                    //añadir cliclable para ir a a la pantalla principal
                                    modifier = Modifier.size(25.dp),
                                    painter = painterResource(R.drawable.arrow_back),
                                    contentDescription = "",
                                    )

                            }
                        },


                        actions = {

                            if (personWhoPayText != "" && personWhoReceiveText != "" && amountText != "") {
                                Text(
                                    addPayment,
                                    modifier = Modifier
                                        .padding(horizontal = 16.dp)
                                        .clickable {
                                            addPaymentScreenViewModel.addPayment(personWhoPay, personWhoReceive, amountText)
                                            addPaymentScreenViewModel.updatePersons(personWhoPay, personWhoReceive, amountText)
                                        },
                                    textAlign = TextAlign.End,
                                    fontSize = 16.sp,
                                    fontFamily = rubik,
                                    fontWeight = FontWeight.Medium,
                                    color = Black,
                                )
                            }


                        },
                        title = {
                        }
                    )
                }
            ) { paddingValues ->
                MainScreenAddPayment(
                    addPaymentScreenViewModel,
                    paddingValues,
                    amountText,
                    personWhoPayText,
                    personWhoReceiveText,
                    listOfPeople,
                    isPaidForSelected,
                    isPaidToSelected,
                )
            }
        }

    }


}

@Composable
fun MainScreenAddPayment(
    addPaymentScreenViewModel: AddPaymentScreenViewModel,
    paddingValues: PaddingValues,
    amountText: String,
    personWhoPayText: String,
    personWhoReceiveText: String,
    listOfPeople: List<PersonModel>,
    isPaidForSelected: Boolean,
    isPaidToSelected: Boolean,
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

        // TEXTFIELD PAGADO POR
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(White)
                .clickable {
                    addPaymentScreenViewModel.onFromSelected(isPaidForSelected)
                },

            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.size(6.dp))
            Text(
                "$payForPlaceHolder:      $personWhoPayText",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontFamily = rubik,
                color = Black
            )
            Spacer(modifier = Modifier.size(6.dp))

            if (isPaidForSelected) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)

                ) {
                    items(listOfPeople) { person ->
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                addPaymentScreenViewModel.onPersonWhoPaySelected(person)
                            }) {
                            if (person.idGroupName == iDGroupName && personWhoReceiveText != person.name) {
                                Text(person.name, fontFamily = rubik, color = Black)
                                Spacer(modifier = Modifier.size(8.dp))
                            }
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(16.dp))

        // TEXTFIELD PAGADO A
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
                .background(White)
                .clickable {
                    addPaymentScreenViewModel.onToSelected(isPaidToSelected)
                },

            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.size(6.dp))

            Text(
                "$payToPlaceHolder:      $personWhoReceiveText",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                fontFamily = rubik,
                color = Black
            )

            Spacer(modifier = Modifier.size(6.dp))

            if (isPaidToSelected) {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)

                ) {
                    items(listOfPeople) { person ->
                        Column(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                addPaymentScreenViewModel.onPersonWhoReceiveSelected(person)
                            }) {
                            if (person.idGroupName == iDGroupName && personWhoPayText != person.name) {
                                Text(person.name, fontFamily = rubik, color = Black)
                                Spacer(modifier = Modifier.size(8.dp))
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.size(16.dp))

        //TEXTFIELD CANTIDAD
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            value = amountText,
            onValueChange = { addPaymentScreenViewModel.onAmountChange(it) },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(amountPlaceHolder) },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
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

    }

}
