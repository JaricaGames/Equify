package com.jarica.compartirgastos.core.forceUpdate.presentation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.net.toUri
import com.jarica.compartirgastos.R

/**
 * Diálogo bloqueante de actualización obligatoria: no se puede cerrar tocando fuera
 * ni con el botón atrás. La única acción posible es ir a Google Play a actualizar.
 */
@Composable
fun ForceUpdateDialog() {
    val context = LocalContext.current

    // Bloquea el botón "atrás" mientras el diálogo está visible.
    BackHandler(enabled = true) { }

    AlertDialog(
        onDismissRequest = { /* no cancelable */ },
        title = { Text(text = stringResource(R.string.force_update_title)) },
        text = { Text(text = stringResource(R.string.force_update_body)) },
        confirmButton = {
            TextButton(onClick = { openPlayStore(context) }) {
                Text(text = stringResource(R.string.force_update_button))
            }
        }
    )
}

private fun openPlayStore(context: Context) {
    val packageName = context.packageName
    try {
        context.startActivity(
            Intent(Intent.ACTION_VIEW, "market://details?id=$packageName".toUri())
        )
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                "https://play.google.com/store/apps/details?id=$packageName".toUri()
            )
        )
    }
}
