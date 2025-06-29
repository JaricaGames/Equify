package com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.AlertDialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.presentation.ui.alertDialogText
import com.jarica.compartirgastos.presentation.ui.cancel
import com.jarica.compartirgastos.presentation.ui.confirmAlertDialogText1
import com.jarica.compartirgastos.presentation.ui.confirmAlertDialogText2
import com.jarica.compartirgastos.presentation.ui.mainAlertDialogText
import com.jarica.compartirgastos.presentation.ui.ok
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.Yellow
import com.jarica.compartirgastos.presentation.ui.theme.rubik
import com.jarica.compartirgastos.presentation.ui.titleConfirmAlertDialogText


@Composable
fun AlertDialogErrorClear(
    personSelected: String,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            Button(
                onClick = { onDismiss() },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = Yellow,
                    contentColor = Black,
                    disabledContainerColor = Yellow,
                    disabledContentColor = Yellow
                )
            ) {
                Text(
                    "Ok",
                    fontFamily = rubik,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
            }
        },
        title = {
            Text(
                mainAlertDialogText,
                fontFamily = rubik,
                textAlign = TextAlign.Start,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                "$personSelected $alertDialogText",
                fontFamily = rubik,
                textAlign = TextAlign.Center,
                fontSize = 12.sp,
                fontWeight = FontWeight.W300,
            )
        }
    )
}


@Composable
fun AlertDialogConfirm(personSelected: String, onDismiss: () -> Unit, onConfirm: () -> Unit) {

    AlertDialog(
        onDismissRequest = { onDismiss() },

        confirmButton = {
            Button(
                onClick = { onConfirm() },
                modifier = Modifier,
                colors = ButtonColors(
                    containerColor = Yellow,
                    contentColor = Black,
                    disabledContainerColor = Yellow,
                    disabledContentColor = Yellow
                )
            ) {
                Text(
                    ok,
                    fontFamily = rubik,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
            }
        },

        dismissButton = {
            TextButton(
                onClick = {onDismiss()}
            ) {
                Text(
                cancel,
                fontFamily = rubik,
                textAlign = TextAlign.Start,
                fontSize = 11.sp,
                fontWeight = FontWeight.W200
            ) }
        },

        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    titleConfirmAlertDialogText,
                    fontFamily = rubik,
                    textAlign = TextAlign.Start,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,


                    )
            }
        },

        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    " $confirmAlertDialogText1 $personSelected $confirmAlertDialogText2" ,
                    fontFamily = rubik,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
                )
            }
        }
    )

}
