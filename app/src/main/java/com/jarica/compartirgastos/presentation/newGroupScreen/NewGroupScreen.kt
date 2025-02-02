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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.domain.models.GroupNameModel
import com.jarica.compartirgastos.presentation.ui.currencyText
import com.jarica.compartirgastos.presentation.ui.descriptionPlaceHolder
import com.jarica.compartirgastos.presentation.ui.labelTextFieldNewGroupScreen
import com.jarica.compartirgastos.presentation.ui.next
import com.jarica.compartirgastos.presentation.ui.shareText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.DarkGrey
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellow
import com.jarica.compartirgastos.presentation.ui.theme.Grey
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun NewGroupScreen(
    newGroupViewModel: NewGroupViewModel,
    navigateToGroupsScreen: () -> Unit,
    navigateToAddPeople: (Int, String) -> Unit
) {

    val context = LocalContext.current
    val groupName: String by newGroupViewModel.groupName.observeAsState("")
    val textNext: Boolean by newGroupViewModel.textNext.observeAsState(false)


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
                        navigateToGroupsScreen()
                    }) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = "",
                            )
                    }
                },
                actions = {

                    if (groupName.isNotEmpty()) {
                        Text(
                            next,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .clickable {

                                    val groupNameModel = GroupNameModel(
                                        groupName = groupName
                                    )
                                    navigateToAddPeople(
                                        groupNameModel.idGroupName,
                                        groupNameModel.groupName
                                    )
                                    newGroupViewModel.onNextSelected()


                                })

                    }


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
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(modifier = Modifier.height(150.dp))

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = groupName,
            onValueChange = { descriptionText ->
                newGroupViewModel.onValueTextFieldChange(descriptionText)
            },

            shape = RoundedCornerShape(16.dp),
            placeholder = { Text(labelTextFieldNewGroupScreen) },
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


        Spacer(modifier = Modifier.weight(0.05f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(shareText, fontFamily = rubik, color = White)

            Switch(checked = false, onCheckedChange = {}, enabled = false)
        }
        Spacer(modifier = Modifier.weight(0.01f))
        HorizontalDivider(Modifier.height(3.dp))
        Spacer(modifier = Modifier.weight(0.01f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(currencyText, fontFamily = rubik, color = White)
            Text("Euro", fontFamily = rubik, color = White)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

