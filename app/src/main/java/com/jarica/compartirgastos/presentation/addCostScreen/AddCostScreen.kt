package com.jarica.compartirgastos.presentation.addCostScreen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.presentation.ui.amountPlaceHolder
import com.jarica.compartirgastos.presentation.ui.descriptionPlaceHolder
import com.jarica.compartirgastos.presentation.ui.fromText
import com.jarica.compartirgastos.presentation.ui.saveCost


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCostScreen(
    addCostViewModel: AddCostScreenViewModel,
    navigateToGroupScreen: () -> Unit
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
                        colors = topAppBarColors(containerColor = Color.Cyan),
                        navigationIcon = {
                            IconButton(onClick = { }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                                    contentDescription = ""
                                )
                            }
                        },
                        actions = {
                            Text(
                                saveCost,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        if (descriptionText.isEmpty() || amountText.isEmpty() || fromTextAddCosts.isEmpty()) {
                                            Log.d("Nono", "fallaste")
                                        } else {
                                            addCostViewModel.addCostToGroup(personToAddCosts!!)
                                            addCostViewModel.updatePerson(personToAddCosts!!)
                                            navigateToGroupScreen()
                                        }
                                    },
                                textAlign = TextAlign.End,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )

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
            .padding(paddingValues)
            .background(Color.Cyan),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(Modifier.weight(0.1f))
        TextField(
            value = descriptionText,
            onValueChange = { descriptionText ->
                addCostViewModel.onDescriptionChange(
                    descriptionText
                )
            },
            placeholder = { Text(descriptionPlaceHolder) },
            singleLine = true,
            maxLines = 1
        )
        Spacer(Modifier.weight(0.02f))
        TextField(
            value = amountText,
            onValueChange = { addCostViewModel.onAmountChange(it) },
            placeholder = { Text(amountPlaceHolder) },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            suffix = { Text("€") }

        )
        Spacer(Modifier.weight(0.02f))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .background(Color.Black.copy(alpha = 0.1f))
                .clickable {
                    addCostViewModel.onFromSelected(isFromSelected)
                },
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            //if (name.isNullOrEmpty()) {
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                "$fromText:      $fromTextAddCosts",
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.size(6.dp))
            /* } else {
                 name.forEach { name ->
                     Row(modifier = Modifier.fillMaxWidth()) {
                         Text("$fromText:", modifier = Modifier.padding(horizontal = 16.dp))
                         Spacer(modifier = Modifier.size(2.dp))
                         Text(name, modifier = Modifier.padding(horizontal = 16.dp))
                     }
                 }
             }*/

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
                            Text(person.name)
                            Spacer(modifier = Modifier.size(6.dp))
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.weight(0.8f))
    }
}


@Composable
fun PeopleList(peopleNameList: List<PersonModel>) {
    LazyColumn {
        items(peopleNameList) { person ->
            PeopleNameList(person)
        }
    }
}

@Composable
fun PeopleNameList(item: PersonModel) {
    Card(modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .background(Color.Cyan)
        .clickable {


        }) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Start) {
            Text(item.name, fontSize = 36.sp, color = Color.Red)
        }

    }
}

