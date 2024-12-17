package com.jarica.compartirgastos.presentation.addPeopleScreen

import androidx.compose.foundation.background
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
import com.jarica.compartirgastos.presentation.ui.addEverybodyText
import com.jarica.compartirgastos.presentation.ui.addPeopleText
import com.jarica.compartirgastos.presentation.ui.createText
import com.jarica.compartirgastos.presentation.ui.labelTextFieldAddPeopleScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPeopleScreen(addPeopleViewModel: AddPeopleScreenViewModel) {


    val addNameToGroup: String by addPeopleViewModel.addNameToGroup.observeAsState("")
    val isTextNext: Boolean by addPeopleViewModel.createText.observeAsState(false)

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
                    if (isTextNext) {
                        Text(addPeopleText, modifier = Modifier.padding(horizontal = 16.dp))
                    } else {
                        Text(createText, modifier = Modifier.padding(horizontal = 16.dp))
                    }

                },
                title = {
                }
            )
        }
    ) { paddingValues ->
        MainViewAddPeopleScreen(paddingValues, addNameToGroup, addPeopleViewModel)

    }

}


@Composable
fun MainViewAddPeopleScreen(
    paddingValues: PaddingValues,
    addNameToGroup: String,
    addPeopleViewModel: AddPeopleScreenViewModel,
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
        Spacer(modifier = Modifier.weight(0.05f))
        Text(addEverybodyText)
        Spacer(modifier = Modifier.weight(1f))
    }
}
