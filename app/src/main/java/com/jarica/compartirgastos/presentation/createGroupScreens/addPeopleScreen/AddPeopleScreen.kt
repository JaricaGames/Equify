package com.jarica.compartirgastos.presentation.createGroupScreens.addPeopleScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.jarica.compartirgastos.domain.models.GroupModel
import com.jarica.compartirgastos.presentation.composables.CustomHeader
import com.jarica.compartirgastos.presentation.composables.CustomTextField
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.ui.addEverybodyText
import com.jarica.compartirgastos.presentation.ui.addPeopleText
import com.jarica.compartirgastos.presentation.ui.createText
import com.jarica.compartirgastos.presentation.ui.labelTextFieldAddPeopleScreen
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.presentation.ui.theme.Grey
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPeopleScreen(
    idGroupName: String,
    groupName: String,
    addPeopleViewModel: AddPeopleScreenViewModel,
    navigateToNewGroupScreen: () -> Unit,
    navigateToMainScreen: (String) -> Unit,
    mainScreenViewModel: MainScreenViewModel,
) {

    val peopleList = addPeopleViewModel.personList
    val addNameToGroup: String by addPeopleViewModel.addNameToGroup.observeAsState("")
    val isTextNext: Boolean by addPeopleViewModel.createText.observeAsState(false)


    MainViewAddPeopleScreen(
        addNameToGroup,
        addPeopleViewModel,
        peopleList,
        navigateToNewGroupScreen,
        isTextNext,
        idGroupName,
        groupName,
        navigateToMainScreen,
        mainScreenViewModel
    )
}


@Composable
fun MainViewAddPeopleScreen(
    addNameToGroup: String,
    addPeopleViewModel: AddPeopleScreenViewModel,
    peopleList: List<String>,
    navigateToNewGroupScreen: () -> Unit,
    isTextNext: Boolean,
    idGroupName: String,
    groupName: String,
    navigateToMainScreen: (String) -> Unit,
    mainScreenViewModel: MainScreenViewModel,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        CustomHeader(
            navigate = { navigateToNewGroupScreen() },
            modifier = Modifier.weight(HEADER_WEIGHT),
            text = addPeopleText,
            icon = R.drawable.arrow_back
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .weight(1f - HEADER_WEIGHT)
        ) {

            Spacer(modifier = Modifier.weight(0.02f))
            CustomTextField(
                value = addNameToGroup,
                onValueChange = { addPeopleViewModel.onValueTextFieldChange(it) },
                placeholderText = labelTextFieldAddPeopleScreen,
                textStyle = TextStyle(
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            )
            if (peopleList.isEmpty()) {
                Spacer(modifier = Modifier.weight(0.02f))
                Text(
                    addEverybodyText,
                    fontSize = 10.sp,
                    color = DarkOrange,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.ExtraLight,
                    textAlign = TextAlign.Center

                )
            }
            if (addNameToGroup.isNotEmpty()) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonColors(
                        containerColor = DarkOrange,
                        contentColor = White,
                        disabledContainerColor = Grey,
                        disabledContentColor = Black
                    ),
                    onClick = {
                        addPeopleViewModel.insertNameOnList(addNameToGroup)
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
               // Spacer(modifier = Modifier.weight(0.02f))
            }
            else {
                peopleList.forEach { name ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(DarkBlue),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            name,
                            fontFamily = parkinsans,
                            fontWeight = FontWeight.Normal,
                            textAlign = TextAlign.Start,
                            fontSize = 12.sp,
                            color = White,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                }
            }
            if (peopleList.isNotEmpty()) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !peopleList.isEmpty() && addNameToGroup.isEmpty(),
                    colors = ButtonColors(
                        containerColor = DarkOrange,
                        contentColor = White,
                        disabledContainerColor = Grey,
                        disabledContentColor = Black
                    ),
                    onClick = {
                        val newGroup = GroupModel(
                            idGroupName = idGroupName,
                            groupName = groupName

                        )
                        mainScreenViewModel.setGroupId(newGroup.idGroupName)
                        //iDGroupName = idGroupName
                       /* addPeopleViewModel.insertGroupName(newGroup)
                        addPeopleViewModel.insertPeople(peopleList, idGroupName)
                        navigateToMainScreen(newGroup.idGroupName)*/
                        addPeopleViewModel.saveGroupData(newGroup, peopleList) {
                            navigateToMainScreen(newGroup.idGroupName)
                        }
                    }) {
                    Text(
                        createText,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
        }



    }
}


