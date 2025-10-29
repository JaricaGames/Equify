package com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.HEADER_WEIGHT
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.presentation.composables.CustomHeader
import com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.AlertDialogs.AlertDialogConfirm
import com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.AlertDialogs.AlertDialogErrorClear
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainUiState
import com.jarica.compartirgastos.presentation.ui.addPeopleConfigurationText
import com.jarica.compartirgastos.presentation.ui.administratePeopleConfigurationText
import com.jarica.compartirgastos.presentation.ui.configurationTextScreen
import com.jarica.compartirgastos.presentation.ui.customizeGroupScreenText
import com.jarica.compartirgastos.presentation.ui.deleteGroupText
import com.jarica.compartirgastos.presentation.ui.groupMembersText
import com.jarica.compartirgastos.presentation.ui.otherText
import com.jarica.compartirgastos.presentation.ui.personalizationGroupText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.presentation.ui.theme.Grey
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfigurationScreen(
    configurationScreenViewModel: ConfigurationScreenViewModel,
    navigateToCustomizeGroup: () -> Unit,
    navigateToGroupScreen: () -> Unit,
    navigateToAddPeopleScreen: () -> Unit
) {

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStatePeopleGroupFragment by produceState<MainUiState>(
        initialValue = MainUiState.Loading,
        key1 = lifecycle,
        key2 = configurationScreenViewModel,
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            configurationScreenViewModel.uiStateConfigurationScreen.collect { value = it }
        }
    }

    val nameOfGroup: String by configurationScreenViewModel.nameOfGroup.observeAsState("")
    val showDialogError: Boolean by configurationScreenViewModel.showDialogError.observeAsState(
        false
    )
    val showDialogConfirm: Boolean by configurationScreenViewModel.showDialogConfirm.observeAsState(
        false
    )
    val personSelected: String by configurationScreenViewModel.personSelected.observeAsState("")

    when (uiStatePeopleGroupFragment) {

        is MainUiState.Error -> {}
        is MainUiState.Loading -> {}
        is MainUiState.Success -> {

                MainConfigurationScreen(
                    configurationScreenViewModel,
                    nameOfGroup,
                    (uiStatePeopleGroupFragment as MainUiState.Success).peopleList,
                    navigateToCustomizeGroup,
                    navigateToGroupScreen,
                    navigateToAddPeopleScreen
                )

                if (showDialogError) {
                    AlertDialogErrorClear(
                        personSelected,
                        onDismiss = { configurationScreenViewModel.onDismiss() }
                    )
                }

                if (showDialogConfirm) {
                    AlertDialogConfirm(
                        personSelected,
                        onDismiss = { configurationScreenViewModel.onDismiss() },
                        onConfirm = { configurationScreenViewModel.onConfirmDeletePerson() }
                    )
                }

            }

        }
    }




@Composable
fun MainConfigurationScreen(
    configurationScreenViewModel: ConfigurationScreenViewModel,
    nameOfGroup: String,
    peopleList: List<PersonModel>,
    navigateToCustomizeGroup: () -> Unit,
    navigateToMainScreen: () -> Unit,
    navigateToAddPeopleScreen: () -> Unit,
) {

    configurationScreenViewModel.getGroupNameById(iDGroupName!!)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {
        CustomHeader(
            navigateToMainScreen,
            modifier = Modifier.weight(HEADER_WEIGHT),
            text = configurationTextScreen,
            icon = R.drawable.arrow_back
        )

        Column(
            modifier = Modifier.padding(horizontal = 32.dp).weight(1f-HEADER_WEIGHT)
        ) {
            Spacer(Modifier.height(20.dp))
            PersonalizationGroup(nameOfGroup, navigateToCustomizeGroup)
            Spacer(Modifier.height(20.dp))
            AdministrateGroupMembers(navigateToAddPeopleScreen)
            Spacer(Modifier.height(20.dp))
            GroupMembers(peopleList, configurationScreenViewModel)
            Spacer(Modifier.height(20.dp))
            Other(configurationScreenViewModel, navigateToMainScreen)
        }
    }

}

@Composable
fun Other(
    configurationScreenViewModel: ConfigurationScreenViewModel,
    navigateToMainScreen: () -> Unit,
) {
    Text(
        otherText,
        fontFamily = parkinsans,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        fontSize = 12.sp,
        color = Black
    )
    Spacer(Modifier.height(6.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Grey)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                configurationScreenViewModel.deleteGroup(iDGroupName!!)
                navigateToMainScreen()
            },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Spacer(Modifier.size(6.dp))
        Text(
            deleteGroupText,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painterResource(R.drawable.exit),
            "",
            tint = DarkOrange,
            modifier = Modifier.size(20.dp)
        )

    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 8.dp),
        thickness = 1.dp,
        color = DarkOrange.copy(0.2f)
    )
}

@Composable
fun GroupMembers(
    peopleList: List<PersonModel>,
    configurationScreenViewModel: ConfigurationScreenViewModel,
) {
    Text(
        groupMembersText,
        fontFamily = parkinsans,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        fontSize = 12.sp,
        color = Black
    )

    Spacer(Modifier.height(6.dp))

    LazyColumn(modifier = Modifier.background(White)) {

        items(peopleList) { person ->
            if (person.idGroupName == iDGroupName) {
                ItemPeopleNameConfigurationScreen(person, configurationScreenViewModel)
            }
        }
    }

}

@Composable
fun ItemPeopleNameConfigurationScreen(
    person: PersonModel,
    configurationScreenViewModel: ConfigurationScreenViewModel,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Grey)
            .padding(horizontal = 16.dp, vertical = 8.dp)

    ) {

        Text(
            person.name,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
        )
        Spacer(modifier = Modifier.weight(1f))
        Icon(
            painterResource(R.drawable.cancel_close),
            "",
            tint = DarkOrange,
            modifier = Modifier
                .size(18.dp)
                .clickable { configurationScreenViewModel.onGroupMemberClicked(person) },
        )

    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 8.dp),
        thickness = 1.dp,
        color = DarkOrange.copy(0.2f)
    )
    Spacer(modifier = Modifier.size(3.dp))

}

@Composable
fun AdministrateGroupMembers(navigateToAddPeopleScreen: () -> Unit) {
    Text(
        administratePeopleConfigurationText,
        fontFamily = parkinsans,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        fontSize = 12.sp,
        color = Black
    )
    Spacer(Modifier.height(6.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Grey)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navigateToAddPeopleScreen() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(R.drawable.people_add),
            "",
            tint = DarkOrange,
            modifier = Modifier.size(20.dp)
        )
        Spacer(Modifier.size(6.dp))
        Text(
            addPeopleConfigurationText,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painterResource(R.drawable.right_arrow),
            "",
            tint = DarkOrange,
            modifier = Modifier.size(20.dp)
        )

    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 8.dp),
        thickness = 1.dp,
        color = DarkOrange.copy(0.2f)
    )
}

@Composable
fun PersonalizationGroup(nameOfGroup: String, navigateToCustomizeGroup: () -> Unit) {

    Text(
        personalizationGroupText,
        fontFamily = parkinsans,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Start,
        fontSize = 12.sp,
        color = Black
    )
    Spacer(Modifier.height(6.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Grey)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navigateToCustomizeGroup() },
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painterResource(R.drawable.list_right),
            contentDescription = "",
            tint = DarkOrange,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.size(6.dp))
        Text(
            nameOfGroup,
            fontFamily = parkinsans,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Start,
            fontSize = 12.sp,
        )
        Spacer(Modifier.weight(1f))
        Icon(
            painterResource(R.drawable.right_arrow),
            "",
            tint = DarkOrange,
            modifier = Modifier.size(20.dp)
        )
    }
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 8.dp),
        thickness = 1.dp,
        color = DarkOrange.copy(0.2f)
    )


}

@Composable
fun HeaderConfigurationScreen(navigateToMainScreen: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp))
            .background(color = DarkBlue),
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            modifier = Modifier
                .size(75.dp)
                .align(Alignment.CenterStart)
                .padding(start = 16.dp),
            onClick = {
                navigateToMainScreen()
            }) {
            Icon(
                modifier = Modifier.size(25.dp),
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = "",
                tint = White
            )

        }

        Text(
            customizeGroupScreenText,
            fontSize = 16.sp,
            color = White,
            fontFamily = parkinsans,
            fontWeight = FontWeight.W600,
            textAlign = TextAlign.Center
        )
    }


}
