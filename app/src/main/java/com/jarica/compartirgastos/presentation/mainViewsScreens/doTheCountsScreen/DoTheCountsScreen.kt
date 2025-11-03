package com.jarica.compartirgastos.presentation.mainViewsScreens.doTheCountsScreen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.jarica.compartirgastos.presentation.composables.CustomHeader
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.groupNameCompanionObject
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.costsScreen.CostsScreenUiState
import com.jarica.compartirgastos.presentation.ui.exportArrayListDoTheCountsLargeText
import com.jarica.compartirgastos.presentation.ui.exportArrayListDoTheCountsText
import com.jarica.compartirgastos.presentation.ui.noAppToOpenPDF
import com.jarica.compartirgastos.presentation.ui.oweToText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.presentation.ui.theme.Grey
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.parkinsans

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoTheCountsScreen(
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    navigateToGroupScreen: () -> Unit,
    mainScreenViewModel: MainScreenViewModel,
) {


    val context = LocalContext.current

    val listOfPayments by doTheCountsScreenViewModel.listOfPayments.observeAsState(arrayListOf())

    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val uiStateCosts by produceState<CostsScreenUiState>(
        initialValue = CostsScreenUiState.Loading,
        key1 = lifecycle,
        key2 = mainScreenViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            mainScreenViewModel.uiStateCosts.collect { value = it }
        }
    }


    val createPdfLauncher = rememberLauncherForActivityResult(

        contract = ActivityResultContracts.CreateDocument("application/pdf"),
        onResult = { uri: Uri? ->
            uri?.let {

                // Crear el PDF
                doTheCountsScreenViewModel.createPdf(
                    context.contentResolver,
                    it,
                    listOfPayments,
                    (uiStateCosts as CostsScreenUiState.Success).costsList
                )

                // Intent para abrirlo inmediatamente
                val intent = Intent(Intent.ACTION_VIEW).apply {
                    setDataAndType(it, "application/pdf")
                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }
                try {
                    context.startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(context, noAppToOpenPDF, Toast.LENGTH_SHORT).show()
                }
            }
        }
    )

    MainViewDoTheCountsScreen(
        listOfPayments,
        createPdfLauncher,
        doTheCountsScreenViewModel,
        navigateToGroupScreen
    )

}


@Composable
fun MainViewDoTheCountsScreen(
    listOfPayments: ArrayList<PaymentsToCountsModel>,
    createPdfLauncher: ManagedActivityResultLauncher<String, Uri?>,
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    navigateToGroupScreen: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient)),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Top
    ) {

        CustomHeader(
            navigate = navigateToGroupScreen,
            modifier = Modifier.weight(HEADER_WEIGHT),
            text = exportArrayListDoTheCountsText,
            icon = R.drawable.arrow_back
        )
        Column(
            modifier = Modifier
                .padding(horizontal = 32.dp)
                .weight(1f - HEADER_WEIGHT),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(Modifier.weight(0.05f))
            Icon(
                modifier = Modifier.size(75.dp),
                painter = painterResource(R.drawable.pdfsvg),
                contentDescription = "",
                tint = Color.Unspecified
            )
            Spacer(Modifier.weight(0.05f))
            Text(
                exportArrayListDoTheCountsLargeText,
                color = Black,
                fontFamily = parkinsans,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.weight(0.05f))
            Button(
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = DarkOrange,
                    contentColor = White,
                    disabledContainerColor = Grey,
                    disabledContentColor = Black
                ),
                onClick = {
                    groupNameCompanionObject?.let { createPdfLauncher.launch(it) }
                })
            {
                Text(
                    exportArrayListDoTheCountsText,
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.ExtraBold,
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                )
            }
            Spacer(Modifier.weight(0.05f))
            listOfPayments.forEach { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(DarkBlue)
                        .padding(horizontal = 32.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        item.namePersonWhoPay + oweToText + item.namePersonWhoReceive,
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center,
                        fontSize = 12.sp,
                        color = White
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        "%.2f".format(item.amount.toFloat()) + " €",
                        fontFamily = parkinsans,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        fontSize = 12.sp,
                        color = White
                    )


                }
                Spacer(Modifier.weight(0.01f))

            }
            Spacer(Modifier.weight(1f))

        }

    }
}





