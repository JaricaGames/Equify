package com.jarica.compartirgastos.presentation.mainViewsScreens.editCostScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.produceState
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.presentation.ui.PayFor
import com.jarica.compartirgastos.presentation.ui.descriptionPlaceHolder
import com.jarica.compartirgastos.presentation.ui.editCost
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditCostScreen(
    idCost: Int,
    amount: Float,
    description: String,
    personString: String,
    editCostScreenViewModel: EditCostScreenViewModel,
    navigateToMainScreen: () -> Unit
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiEditCostUiState by produceState<EditCostUiState>(
        initialValue = EditCostUiState.Loading,
        key1 = lifecycle,
        key2 = editCostScreenViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            editCostScreenViewModel.uiEditCostUiState.collect { value = it }
        }
    }

    when (uiEditCostUiState) {
        is EditCostUiState.Loading -> {}
        is EditCostUiState.Error -> {}
        is EditCostUiState.Success -> {

            val listOfCostOfPerson =
                (uiEditCostUiState as EditCostUiState.Success).listOfCostOfPerson

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
                                    .clip(
                                        shape = CircleShape
                                    )
                                    .size(40.dp), onClick = {
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
                            IconButton(
                                modifier = Modifier
                                    .clip(
                                        shape = CircleShape
                                    )
                                    .size(40.dp), onClick = {
                                    editCostScreenViewModel.onDeletedSelected(
                                        idCost,
                                        listOfCostOfPerson,
                                    )
                                    navigateToMainScreen()

                                }) {
                                Icon(
                                    modifier = Modifier.size(25.dp),
                                    painter = painterResource(R.drawable.delete_svgrepo),
                                    tint = Black,
                                    contentDescription = "",

                                    )

                            }

                        },
                        title = {
                        }
                    )
                }
            ) { paddingValues ->
                MainViewEditCostScreen(
                    paddingValues,
                    amount,
                    description,
                    personString
                )

            }
        }
    }


}

@Composable
fun MainViewEditCostScreen(
    paddingValues: PaddingValues,
    amount: Float,
    description: String,
    personString: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(
            editCost,
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
            value = description,
            readOnly = true,
            onValueChange = {},
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(descriptionPlaceHolder) },
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

        Spacer(Modifier.height(16.dp))

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


        Spacer(modifier = Modifier.weight(0.8f))
    }
}

