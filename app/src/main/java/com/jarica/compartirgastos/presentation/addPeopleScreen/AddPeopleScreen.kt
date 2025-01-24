package com.jarica.compartirgastos.presentation.addPeopleScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.presentation.ui.addEverybodyText
import com.jarica.compartirgastos.presentation.ui.addPeopleText
import com.jarica.compartirgastos.presentation.ui.createText
import com.jarica.compartirgastos.presentation.ui.labelTextFieldAddPeopleScreen

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
                colors = topAppBarColors(containerColor = Color.Cyan),
                navigationIcon = {
                    IconButton(onClick = { navigateToNewGroupScreen() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    if (isTextNext) {
                        Text(
                            addPeopleText,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {
                                    addPeopleViewModel.insertNameOnList(addNameToGroup)
                                })
                    } else {
                        Text(createText, modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                val newGroup = GroupNameModel(idGroupName = idGroupName, groupName = groupName)
                                addPeopleViewModel.insertGroupName(newGroup)
                                addPeopleViewModel.insertPeople(peopleList, idGroupName)
                                navigateToMainScreen(newGroup.idGroupName)

                            })
                    }

                },
                title = {
                }
            )
        }
    ) { paddingValues ->
        MainViewAddPeopleScreen(paddingValues, addNameToGroup, addPeopleViewModel, peopleList)

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
            .padding(paddingValues)
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.025f))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = addNameToGroup,
            maxLines = 1,
            singleLine = true,
            placeholder = { Text(labelTextFieldAddPeopleScreen) },
            onValueChange = { addPeopleViewModel.onValueTextFieldChange(it) })


        if (peopleList.isEmpty()) {
            Text(addEverybodyText, modifier = Modifier.padding(top = 8.dp))
        } else {
            peopleList.forEach { name ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(name)
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}

