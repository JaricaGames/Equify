package com.jarica.compartirgastos.presentation.createGroupScreens.newGroupScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.jarica.compartirgastos.core.HEADER_WEIGHT
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.presentation.composables.CustomHeader
import com.jarica.compartirgastos.presentation.composables.CustomTextField
import com.jarica.compartirgastos.presentation.ui.newGroupText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.presentation.ui.theme.Grey
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun NewGroupScreen(
    newGroupViewModel: NewGroupViewModel,
    navigateToGroupsScreen: () -> Unit,
    navigateToAddPeople: (String, String) -> Unit
) {

    val groupName: String by newGroupViewModel.groupName.observeAsState("")

    MainViewNewGroupScreen(newGroupViewModel, groupName, navigateToAddPeople, navigateToGroupsScreen)

}

@Composable
fun MainViewNewGroupScreen(
    newGroupViewModel: NewGroupViewModel,
    groupName: String,
    navigateToAddPeople: (String, String) -> Unit,
    navigateToGroupsScreen: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        CustomHeader(
            navigate = { navigateToGroupsScreen() },
            modifier = Modifier.weight(HEADER_WEIGHT),
            text = newGroupText,
            icon = R.drawable.arrow_back
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .weight(1f-HEADER_WEIGHT)
        ){
            Spacer(modifier = Modifier.weight(0.02f))

            CustomTextField(
                value = groupName,
                onValueChange = { newGroupViewModel.onValueTextFieldChange(it) },
                placeholderText = newGroupText,
                textStyle = TextStyle(
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
            Spacer(modifier = Modifier.weight(0.02f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                enabled = groupName.isNotEmpty(),
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = Grey,
                    disabledContentColor = Black
                ),
                onClick = {
                    val groupNameModel = GroupNameModel(
                        groupName = groupName
                    )
                    navigateToAddPeople(
                        groupNameModel.idGroupName,
                        groupNameModel.groupName
                    )
                    newGroupViewModel.onNextSelected()
                }) {
                Text(
                    newGroupText,
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
}

