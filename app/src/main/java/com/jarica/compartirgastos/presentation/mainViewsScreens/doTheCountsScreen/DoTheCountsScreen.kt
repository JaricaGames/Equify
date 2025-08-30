package com.jarica.compartirgastos.presentation.mainViewsScreens.doTheCountsScreen

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
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
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.groupNameCompanionObject
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.fragmets.costsScreen.CostsScreenUiState
import com.jarica.compartirgastos.presentation.ui.exportArrayListDoTheCountsLargeText
import com.jarica.compartirgastos.presentation.ui.exportArrayListDoTheCountsText
import com.jarica.compartirgastos.presentation.ui.noAppToOpenPDF
import com.jarica.compartirgastos.presentation.ui.oweToText
import com.jarica.compartirgastos.presentation.ui.theme.BackgroundColorGradient
import com.jarica.compartirgastos.presentation.ui.theme.Black
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellow2
import com.jarica.compartirgastos.presentation.ui.theme.Transparent
import com.jarica.compartirgastos.presentation.ui.theme.White
import com.jarica.compartirgastos.presentation.ui.theme.rubik

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DoTheCountsScreen(
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    navigateToGroupScreen: () -> Unit,
    mainScreenViewModel: MainScreenViewModel,
) {
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

    val context = LocalContext.current
    val createPdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/pdf"),
        onResult = { uri: Uri? ->
            uri?.let {

                // Crear el PDF
                doTheCountsScreenViewModel.createPdf(context.contentResolver, it, listOfPayments, (uiStateCosts as CostsScreenUiState.Success).costsList,)

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
                            navigateToGroupScreen()
                        }) {
                        Icon(
                            modifier = Modifier.size(25.dp),
                            painter = painterResource(R.drawable.arrow_back),
                            contentDescription = "",

                            )

                    }
                },
                actions = {},
                title = {}
            )
        }
    ) { paddingValues ->
        MainViewDoTheCountsScreen(
            paddingValues,
            listOfPayments,
            createPdfLauncher
        )

    }
}


@Composable
fun MainViewDoTheCountsScreen(
    paddingValues: PaddingValues,
    listOfPayments: ArrayList<PaymentsToCountsModel>,
    createPdfLauncher: ManagedActivityResultLauncher<String, Uri?>
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(colorStops = BackgroundColorGradient))
            .padding(vertical = paddingValues.calculateTopPadding(), horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Spacer(Modifier.size(32.dp))

        Icon(
            modifier = Modifier.size(75.dp),
            painter = painterResource(R.drawable.pdfsvg),
            contentDescription = "",
            tint = Color.Unspecified
        )
        Spacer(Modifier.size(16.dp))

        Text(
            exportArrayListDoTheCountsLargeText,
            color = Black,
            fontFamily = rubik,
            fontSize = 12.sp,
            fontWeight = FontWeight.W300,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.size(16.dp))

        Button(onClick = {
            groupNameCompanionObject?.let { createPdfLauncher.launch(it) }
        }) {
            Text(
                exportArrayListDoTheCountsText,
                modifier = Modifier.padding(horizontal = 16.dp),
                color = DarkYellow2,
                fontFamily = rubik,
                fontSize = 20.sp,
                fontWeight = FontWeight.W500
            )
        }

        Spacer(Modifier.size(16.dp))

        listOfPayments.forEach { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(White)
                    .padding(horizontal = 32.dp, vertical = 8.dp)
                    .clickable {

                    },
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    item.namePersonWhoPay + oweToText + item.namePersonWhoReceive,
                    color = Black,
                    fontFamily = rubik,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "%.2f".format(item.amount.toFloat()) + " €",
                    color = Black,
                    fontFamily = rubik,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.W300
                )


            }
            Spacer(modifier = Modifier.size(8.dp))

        }
    }

}





