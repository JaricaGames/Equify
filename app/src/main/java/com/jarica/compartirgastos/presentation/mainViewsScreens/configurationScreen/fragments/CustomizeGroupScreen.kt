package com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.fragments

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.presentation.ui.changeGroupName
import com.jarica.compartirgastos.presentation.ui.customizeGroupScreenText
import com.jarica.compartirgastos.presentation.ui.labelCustomizeGroupScreenText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomizeGroupScreen(customizeGroupScreenViewModel: CustomizeGroupScreenViewModel) {

    val newGroupNameToGroup: String by customizeGroupScreenViewModel.newGroupNameToGroup.observeAsState("")

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
                IconButton(
                    modifier = Modifier
                        .clip(
                            shape = CircleShape
                        )
                        .size(40.dp), onClick = {

                    }) {
                    Icon(
                        modifier = Modifier.size(25.dp),
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = "",
                    )

                }
            },


            actions = {

                if (newGroupNameToGroup.isNotEmpty()) {
                    Text(
                        changeGroupName,
                        fontFamily = rubik,
                        fontWeight = FontWeight.Medium,
                        color = Black,
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clickable {

                            })
                }

            },
            title = {
            }
        )
    }
    ) { paddingValues ->
        CustomizeGroupMainScreen(
            paddingValues,
            customizeGroupScreenViewModel,
            newGroupNameToGroup
        )

    }
}

@Composable
fun CustomizeGroupMainScreen(
    paddingValues: PaddingValues,
    customizeGroupScreenViewModel: CustomizeGroupScreenViewModel,
    newGroupNameToGroup: String,

    ) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(125.dp))
        HeaderCustomizeGroupScreen(customizeGroupScreenViewModel, newGroupNameToGroup)
    }
}

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

    Spacer(Modifier.height(16.dp))
    TextField(
        modifier = Modifier.fillMaxWidth().height(50.dp),
        value = newGroupNameToGroup,
        onValueChange = {
            customizeGroupScreenViewModel.onValueTextFieldChange(it)
        },
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text(labelCustomizeGroupScreenText) },
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
}
