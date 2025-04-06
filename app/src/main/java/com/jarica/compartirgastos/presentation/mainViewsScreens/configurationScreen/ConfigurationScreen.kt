package com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import com.jarica.compartirgastos.presentation.ui.addPeopleConfigurationText
import com.jarica.compartirgastos.presentation.ui.administratepeopleConfigurationText
import com.jarica.compartirgastos.presentation.ui.configurationTextScreen
import com.jarica.compartirgastos.presentation.ui.personalizationGroupText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurationScreen(
    configurationScreenViewModel: ConfigurationScreenViewModel,
) {

    val nameOfGroup: String by configurationScreenViewModel.nameOfGroup.observeAsState("")

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

                },
                title = {
                }
            )
        }
    ) { paddingValues ->
        MainConfigurationScreen(
            paddingValues,
            configurationScreenViewModel,
            nameOfGroup
        )

    }
}

@Composable
fun MainConfigurationScreen(
    paddingValues: PaddingValues,
    configurationScreenViewModel: ConfigurationScreenViewModel,
    nameOfGroup: String,
) {
    configurationScreenViewModel.getGroupNameById(iDGroupName!!)
    configurationScreenViewModel.getGroupMembersById(iDGroupName!!)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Spacer(Modifier.height(125.dp))
        HeaderConfigurationScreen()
        Spacer(Modifier.height(24.dp))
        PersonalizationGroup(nameOfGroup)
        Spacer(Modifier.height(24.dp))
        AdministrateGroupMembers()
        GroupMembers()

    }

}

@Composable
fun GroupMembers() {


}

@Composable
fun AdministrateGroupMembers() {
    Text(
        administratepeopleConfigurationText,
        fontFamily = rubik,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
    Spacer(Modifier.height(6.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(R.drawable.people_add),
            "",
            tint = Black,
            modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.size(6.dp))
        Text(
            addPeopleConfigurationText,
            fontFamily = rubik,
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painterResource(R.drawable.right_arrow),
            "",
            tint = Black,
            modifier = Modifier.size(16.dp)
        )

    }
}

@Composable
fun PersonalizationGroup(nameOfGroup: String) {
    Text(
        personalizationGroupText,
        fontFamily = rubik,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        fontSize = 12.sp,
        fontWeight = FontWeight.Light
    )
    Spacer(Modifier.height(6.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 32.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(R.drawable.list_paper),
            contentDescription = "",
            tint = Black,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.size(6.dp))
        Text(
            nameOfGroup,
            fontFamily = rubik,
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painterResource(R.drawable.right_arrow),
            "",
            tint = Black,
            modifier = Modifier.size(16.dp)
        )
    }
}

@Composable
fun HeaderConfigurationScreen() {
    Text(
        configurationTextScreen,
        fontFamily = rubik,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold
    )
}
