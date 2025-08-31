package com.jarica.compartirgastos.presentation.createGroupScreens.addPeopleScreen

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
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.presentation.ui.addEverybodyText
import com.jarica.compartirgastos.presentation.ui.addPeopleText
import com.jarica.compartirgastos.presentation.ui.createText
import com.jarica.compartirgastos.presentation.ui.labelTextFieldAddPeopleScreen
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPeopleScreen(
    idGroupName: Int,
    groupName: String,
    addPeopleViewModel: AddPeopleScreenViewModel,
    navigateToNewGroupScreen: () -> Unit,
    navigateToMainScreen: (Int) -> Unit,
) {

    val peopleList = addPeopleViewModel.personList
    val addNameToGroup: String by addPeopleViewModel.addNameToGroup.observeAsState("")
    val isTextNext: Boolean by addPeopleViewModel.createText.observeAsState(false)

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
                        .size(40.dp), onClick = {
                            addPeopleViewModel.onBackPressed()
                        navigateToNewGroupScreen()
                    }) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = "",

                            )

                    }
                },

                actions = {

                    if (isTextNext) {
                        Text(
                            addPeopleText,
                            fontFamily = rubik,
                            fontWeight = FontWeight.Medium,
                            color = Black,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    addPeopleViewModel.insertNameOnList(addNameToGroup)
                                })
                    } else {
                        if (peopleList.isNotEmpty()) {
                            Text(createText,
                                fontFamily = rubik,
                                fontWeight = FontWeight.Medium,
                                color = Black,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .clickable {
                                        val newGroup = GroupNameModel(
                                            idGroupName = idGroupName,
                                            groupName = groupName

                                        )
                                        addPeopleViewModel.insertGroupName(newGroup)
                                        addPeopleViewModel.insertPeople(peopleList, idGroupName)
                                        navigateToMainScreen(newGroup.idGroupName)

                                    })
                        }
                    }

                },
                title = {
                }
            )
        }
    ) { paddingValues ->
        MainViewAddPeopleScreen(
            paddingValues,
            addNameToGroup,
            addPeopleViewModel,
            peopleList
        )

    }

}


@Composable
fun MainViewAddPeopleScreen(
    paddingValues: PaddingValues,
    addNameToGroup: String,
    addPeopleViewModel: AddPeopleScreenViewModel,
    peopleList: List<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(horizontal = 16.dp, vertical = paddingValues.calculateTopPadding()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        //Spacer(Modifier.height(125.dp))
        Text(
            addPeopleText,
            fontFamily = rubik,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(16.dp))
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = addNameToGroup,
            onValueChange = {
                addPeopleViewModel.onValueTextFieldChange(it)
            },
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(labelTextFieldAddPeopleScreen) },
            singleLine = true,
            maxLines = 1,
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


        if (peopleList.isEmpty()) {
            Text(
                addEverybodyText,
                modifier = Modifier.padding(top = 10.dp),
                fontFamily = rubik,
                color = Black
            )

        } else {

            peopleList.forEach { name ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Black),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        name,
                        fontFamily = rubik,
                        color = White,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}


