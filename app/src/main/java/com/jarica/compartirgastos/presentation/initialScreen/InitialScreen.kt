package com.jarica.compartirgastos.presentation.initialScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.presentation.ui.joinGroupText
import com.jarica.compartirgastos.presentation.ui.mainText
import com.jarica.compartirgastos.presentation.ui.newGroupText
import com.jarica.compartirgastos.presentation.ui.secondaryText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.DarkGrey
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellow
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik


@Composable
fun InitialScreen(
    navigateToNewGroup: () -> Unit,
) {

    Scaffold(
    ) { paddingValues ->
        MainViewInitialScreen(
            paddingValues,
            navigateToNewGroup

        )


    }
}

@Composable
fun MainViewInitialScreen(paddingValues: PaddingValues, navigateToNewGroup: () -> Unit) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(paddingValues)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.weight(0.8f))

        Text(text = mainText, fontSize = 25.sp, fontWeight = FontWeight.Bold, fontFamily = rubik, color = DarkYellow)

        Spacer(Modifier.weight(0.05f))

        Text(text = secondaryText, fontFamily = rubik, color = White)

        Spacer(Modifier.weight(0.15f))

        Button(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            onClick = { navigateToNewGroup() }) {
            Text(text = newGroupText, fontFamily = rubik)
        }

        Spacer(Modifier.weight(0.02f))

/*        Button(onClick = {}) {
            Text(text = joinGroupText, fontFamily = rubik)
        }*/
        Spacer(modifier = Modifier.weight(1f))


    }
}
