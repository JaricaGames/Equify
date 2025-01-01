package com.jarica.compartirgastos.presentation.addCostScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.presentation.ui.addCost
import com.jarica.compartirgastos.presentation.ui.amountPlaceHolder
import com.jarica.compartirgastos.presentation.ui.descriptionPlaceHolder
import com.jarica.compartirgastos.presentation.ui.fromText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCostScreen(addCostViewModel: AddCostScreenViewModel, navigateToFromPerson: () -> Unit) {

    val descriptionText: String by addCostViewModel.descriptionText.observeAsState("")
    val amountText: String by addCostViewModel.amountText.observeAsState("")


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


                },
                title = {
                    Text(
                        addCost,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 18.sp
                    )
                }
            )
        }
    ) { paddingValues ->
        MainViewAddCostScreen(
            paddingValues,
            addCostViewModel,
            descriptionText,
            amountText,
            navigateToFromPerson
        )

    }
}

@Composable
fun MainViewAddCostScreen(
    paddingValues: PaddingValues,
    addCostViewModel: AddCostScreenViewModel,
    descriptionText: String,
    amountText: String,
    navigateToFromPerson: () -> Unit,
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
            onValueChange = { amountText -> addCostViewModel.onAmountChange(amountText) },
            placeholder = { Text(amountPlaceHolder) },
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            suffix = { Text("€") }

        )
        Spacer(Modifier.weight(0.02f))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 50.dp)
                .height(60.dp)
                .background(Color.Black.copy(alpha = 0.1f))
                .clickable { navigateToFromPerson()},
            contentAlignment = Alignment.CenterStart
        ) {
            Text(fromText, modifier = Modifier.padding(horizontal = 16.dp))
        }
        Spacer(modifier = Modifier.weight(0.8f))
    }
}

