package com.jarica.compartirgastos.presentation.initialScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.presentation.ui.joinGroupText
import com.jarica.compartirgastos.presentation.ui.mainText
import com.jarica.compartirgastos.presentation.ui.newGroupText
import com.jarica.compartirgastos.presentation.ui.secondaryText

@Composable
fun InitialScreen(navigateToNewGroup: () -> Unit) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.Cyan)
            .padding(horizontal = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.8f))

        Text(text = mainText, fontSize = 25.sp, fontWeight = FontWeight.Bold )

        Spacer(Modifier.weight(0.05f))

        Text(text = secondaryText)

        Spacer(Modifier.weight(0.05f))

        Button(onClick = {navigateToNewGroup()}) {
            Text(text = newGroupText)
        }

        Spacer(Modifier.weight(0.02f))

        Button(onClick = {}) {
            Text(text = joinGroupText)
        }
        Spacer(modifier = Modifier.weight(1f))

    }
}