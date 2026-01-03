package com.jarica.compartirgastos.features.groups.presentation.configurationScreen.fragments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.jarica.compartirgastos.core.presentation.composables.CustomHeader
import com.jarica.compartirgastos.core.presentation.composables.CustomTextField
import com.jarica.compartirgastos.core.presentation.ui.changeGroupName
import com.jarica.compartirgastos.core.presentation.ui.customizeGroupScreenText
import com.jarica.compartirgastos.core.presentation.ui.labelCustomizeGroupScreenText
import com.jarica.compartirgastos.core.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.core.presentation.ui.theme.Black
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.Grey
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.core.utils.HEADER_WEIGHT
import com.jarica.compartirgastos.features.groupDetail.presentation.groupDetailsScreen.MainScreenViewModel.Companion.iDGroupName

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizeGroupScreen(
    customizeGroupScreenViewModel: CustomizeGroupScreenViewModel,
    navigateToConfiguration: () -> Unit
) {

    val newGroupNameToGroup: String by customizeGroupScreenViewModel.newGroupNameToGroup.observeAsState(
        ""
    )

    CustomizeGroupMainScreen(
        customizeGroupScreenViewModel,
        newGroupNameToGroup,
        navigateToConfiguration
    )

}


@Composable
fun CustomizeGroupMainScreen(
    customizeGroupScreenViewModel: CustomizeGroupScreenViewModel,
    newGroupNameToGroup: String,
    navigateToConfiguration: () -> Unit,

    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        CustomHeader(
            navigateToConfiguration,
            modifier = Modifier.weight(HEADER_WEIGHT),
            customizeGroupScreenText,
            icon = R.drawable.arrow_back
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .weight(1f - HEADER_WEIGHT)
        ) {

            Spacer(Modifier.height(20.dp))
            CustomTextField(
                value = newGroupNameToGroup,
                onValueChange = { customizeGroupScreenViewModel.onValueTextFieldChange(it) },
                placeholderText = labelCustomizeGroupScreenText,
                textStyle = TextStyle(
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    color = Black
                )
            )
            Spacer(Modifier.height(20.dp))
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = newGroupNameToGroup.isNotEmpty(),
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = Grey,
                    disabledContentColor = Black
                ),
                onClick = {
                    customizeGroupScreenViewModel.onEditGroupNameById(
                        iDGroupName!!,
                        newGroupNameToGroup
                    )
                    navigateToConfiguration()
                }) {
                Text(
                    changeGroupName,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                )
            }
            Spacer(Modifier.weight(1f))
            //HeaderCustomizeGroupScreen(customizeGroupScreenViewModel, newGroupNameToGroup)
        }
    }
}

/*

@Composable
fun HeaderCustomizeGroupScreen(
    customizeGroupScreenViewModel: CustomizeGroupScreenViewModel,
    newGroupNameToGroup: String
) {

    Text(
        customizeGroupScreenText,
        fontFamily = rubik,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
    Spacer(Modifier.height(20.dp))
    CustomTextField(
        value = newGroupNameToGroup,
        onValueChange = { customizeGroupScreenViewModel.onValueTextFieldChange(it) },
        placeholderText = labelCustomizeGroupScreenText,
        textStyle = TextStyle(
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            color = Black
        ),

        Spacer(Modifier(20.dp))
}
*/
