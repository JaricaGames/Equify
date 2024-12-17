package com.jarica.compartirgastos.presentation.newGroupScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.jarica.compartirgastos.domain.model.GroupNameModel
import com.jarica.compartirgastos.presentation.ui.currencyText
import com.jarica.compartirgastos.presentation.ui.labelTextFieldNewGroupScreen
import com.jarica.compartirgastos.presentation.ui.next
import com.jarica.compartirgastos.presentation.ui.shareText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewGroupScreen(
    newGroupViewModel: NewGroupViewModel,
    navigateToInitial: () -> Unit,
    navigateToAddPeople: () -> Unit
) {

    val context = LocalContext.current
    val groupName: String by newGroupViewModel.groupName.observeAsState("")
    val textNext: Boolean by newGroupViewModel.textNext.observeAsState(false)


    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.Cyan,
                ),
                navigationIcon = {
                    IconButton(onClick = { navigateToInitial() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                actions = {
                    Text(
                        next,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {
                                if (textNext) {
                                    newGroupViewModel.insertGroupName(
                                        groupName = GroupNameModel(
                                            groupName = groupName
                                        ),
                                        navigateToGroup = navigateToAddPeople()
                                    )
                                } else {
                                    Toast.makeText(context, "Rellene el nombre del grupo", Toast.LENGTH_LONG).show()
                                }
                            })


                },
                title = {
                }
            )
        }
    ) { paddingValues ->
        MainViewNewGroupScreen(paddingValues, newGroupViewModel, groupName)
    }
}

@Composable
fun MainViewNewGroupScreen(
    paddingValues: PaddingValues,
    newGroupViewModel: NewGroupViewModel,
    groupName: String,
) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize()
            .background(Color.Cyan)
            .padding(horizontal = 45.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.2f))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = groupName,
            maxLines = 1,
            singleLine = true,
            placeholder = { Text(labelTextFieldNewGroupScreen) },
            onValueChange = { newGroupViewModel.onValueTextFieldChange(it) })
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

