package com.jarica.compartirgastos.presentation.mainViewsScreens.addPeopleScreenFromMain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.HEADER_WEIGHT
import com.jarica.compartirgastos.presentation.composables.CustomHeader
import com.jarica.compartirgastos.presentation.composables.CustomTextField
import com.jarica.compartirgastos.presentation.ui.addPeopleText
import com.jarica.compartirgastos.presentation.ui.labelTextFieldAddPeopleScreen
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.presentation.ui.theme.Grey
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPeopleScreenFromMain(
    addPeopleFromMainViewModel: AddPeopleScreenFromMainViewModel,
    navigateToMainScreen: () -> Unit,
) {

    val addNameToGroup: String by addPeopleFromMainViewModel.addNameToGroup.observeAsState("")


    MainViewAddPeopleScreen(
        addNameToGroup,
        addPeopleFromMainViewModel,
        navigateToMainScreen
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainViewAddPeopleScreen(
    addNameToGroup: String,
    addPeopleFromMainViewModel: AddPeopleScreenFromMainViewModel,
    navigateToMainScreen: () -> Unit
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
            text = addPeopleText,
            icon = R.drawable.arrow_back
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .weight(1f - HEADER_WEIGHT)
        ) {

            Spacer(Modifier.height(20.dp))

            CustomTextField(
                value = addNameToGroup,
                onValueChange = { addPeopleFromMainViewModel.onValueTextFieldChange(it) },
                placeholderText = labelTextFieldAddPeopleScreen,
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
            Spacer(Modifier.size(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = addNameToGroup.isNotEmpty(),
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = Grey,
                    disabledContentColor = Black
                ),
                onClick = {
/*                    val newPerson = PersonModel(
                        idPerson = null,
                        name = addNameToGroup,
                        equity = "0",
                        idGroupName = iDGroupName!!
                    )
                    addPeopleFromMainViewModel.insertPeople(newPerson)*/
                    navigateToMainScreen()
                    navigateToMainScreen()
                }) {
                Text(
                    addPeopleText,
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



