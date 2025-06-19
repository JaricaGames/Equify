package com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.AlertDialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.jarica.compartirgastos.presentation.ui.alertDialogText
import com.jarica.compartirgastos.presentation.ui.mainAlertDialogText
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.Yellow
import com.jarica.compartirgastos.presentation.ui.theme.rubik


@Composable
fun AlertDialogErrorClear(
    personSelected: String,
    onDismiss: () -> Unit
) {
    Dialog(onDismissRequest = { onDismiss() }) {
        Column(
            modifier =
                Modifier
                    .background(White)
                    .padding(24.dp)
                    .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                mainAlertDialogText,
                fontFamily = rubik,
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.size(8.dp))

            Text(
                "$personSelected $alertDialogText",
                fontFamily = rubik,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = FontWeight.W300,
            )

            Spacer(Modifier.size(8.dp))

            Button(
                onClick = { onDismiss() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = Yellow,
                    contentColor = Black,
                    disabledContainerColor = Yellow,
                    disabledContentColor = Yellow
                )
            )

            {
                Text(
                    "Ok",
                    fontFamily = rubik,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )


            }

        }

    }
}

@Composable
fun AlertDialogConfirm(onDismiss: () -> Unit, onConfirm: () -> Unit) {

    
}