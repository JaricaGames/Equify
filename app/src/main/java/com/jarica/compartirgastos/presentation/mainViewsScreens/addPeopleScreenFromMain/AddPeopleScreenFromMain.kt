package com.jarica.compartirgastos.presentation.mainViewsScreens.addPeopleScreenFromMain

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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import com.jarica.compartirgastos.presentation.ui.addPeopleText
import com.jarica.compartirgastos.presentation.ui.labelTextFieldAddPeopleScreen
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.DarkGrey
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellow
import com.jarica.compartirgastos.presentation.ui.theme.Grey
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPeopleScreenFromMain(
    addPeopleFromMainViewModel: AddPeopleScreenFromMainViewModel,
    navigateToMainScreen: () -> Unit,
) {

    val addNameToGroup: String by addPeopleFromMainViewModel.addNameToGroup.observeAsState("")

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(horizontal = 16.dp),
                colors = topAppBarColors(
                    containerColor = Transparent,
                    actionIconContentColor = White,
                    navigationIconContentColor = White
                ),

                navigationIcon = {
                    IconButton(modifier = Modifier
                        .clip(
                            shape = CircleShape
                        )
                        .background(Grey)
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

                    if (addNameToGroup.isNotEmpty()) {
                        Text(
                            addPeopleText, fontFamily = rubik,
                            color = White,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {
                                      val newPerson = PersonModel(
                                          idPerson = null,
                                          name = addNameToGroup,
                                          equity = "0",
                                          idGroupName = iDGroupName!!
                                      )
                                    addPeopleFromMainViewModel.insertPeople(newPerson)
                                    navigateToMainScreen()


                                })
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
            addPeopleFromMainViewModel
        )

    }

}


@Composable
fun MainViewAddPeopleScreen(
    paddingValues: PaddingValues,
    addNameToGroup: String,
    addPeopleViewModel: AddPeopleScreenFromMainViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(vertical = paddingValues.calculateTopPadding(), horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(50.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = addNameToGroup,
            onValueChange = {
                addPeopleViewModel.onValueTextFieldChange(it)
            },
            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(labelTextFieldAddPeopleScreen) },
            singleLine = true,
            maxLines = 1,
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = DarkGrey,
                unfocusedLabelColor = White,
                unfocusedTextColor = White,
                focusedContainerColor = DarkGrey,
                focusedTextColor = White,
                focusedLabelColor = White,
                unfocusedPlaceholderColor = White,
                focusedIndicatorColor = DarkYellow,
                unfocusedIndicatorColor = Transparent,
                cursorColor = DarkYellow

            ),
        )


    }
}


