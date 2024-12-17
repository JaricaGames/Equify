package com.jarica.compartirgastos.presentation.addPeopleScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import com.jarica.compartirgastos.presentation.ui.currencyText
import com.jarica.compartirgastos.presentation.ui.labelTextField
import com.jarica.compartirgastos.presentation.ui.shareText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPeopleScreen(addPeopleViewModel: AddPeopleScreenViewModel) {


    val addNameToGroup: String by addPeopleViewModel.addNameToGroup.observeAsState("")

    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.Cyan,
                ),
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                actions = {


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
            .padding(horizontal = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.2f))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = addNameToGroup,
            maxLines = 1,
            singleLine = true,
            placeholder = { Text(labelTextField) },
            onValueChange = { addPeopleViewModel.onValueTextFieldChange(it) })
        Spacer(modifier = Modifier.weight(0.05f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(shareText)
            Switch(checked = false, onCheckedChange = {}, enabled = false)
        }

        Spacer(modifier = Modifier.weight(0.05f))

        HorizontalDivider(Modifier.height(3.dp))

        Spacer(modifier = Modifier.weight(0.05f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(currencyText)
            Text("Euro")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
