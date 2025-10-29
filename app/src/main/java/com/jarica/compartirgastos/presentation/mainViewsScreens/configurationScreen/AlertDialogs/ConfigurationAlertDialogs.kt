package com.jarica.compartirgastos.presentation.mainViewsScreens.configurationScreen.AlertDialogs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.presentation.ui.alertDialogText
import com.jarica.compartirgastos.presentation.ui.cancel
import com.jarica.compartirgastos.presentation.ui.confirmAlertDialogText1
import com.jarica.compartirgastos.presentation.ui.confirmAlertDialogText2
import com.jarica.compartirgastos.presentation.ui.mainAlertDialogText
import com.jarica.compartirgastos.presentation.ui.ok
import com.jarica.compartirgastos.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans
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
                shape = RoundedCornerShape(8.dp),
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = DarkOrange,
                    disabledContentColor = DarkOrange
                )
            ) {
                Text(
                    "Ok",
                    fontFamily = parkinsans,
                    textAlign = TextAlign.Start,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300
                )
            }
        },
        title = {
            Text(
                mainAlertDialogText,
                modifier = Modifier.fillMaxWidth(),
                fontFamily = parkinsans,
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                "$personSelected $alertDialogText",
                fontFamily = parkinsans,
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
                shape = RoundedCornerShape(8.dp),
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = DarkOrange,
                    disabledContentColor = DarkOrange
                )
            ) {
                Text(
                    ok,
                    fontFamily = parkinsans,
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
                    fontFamily = parkinsans,
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
                    fontFamily = parkinsans,
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
                    " $confirmAlertDialogText1 $personSelected$confirmAlertDialogText2" ,
                    fontFamily = parkinsans,
                    textAlign = TextAlign.Center,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.W300,
                )
            }
        }
    )

}
