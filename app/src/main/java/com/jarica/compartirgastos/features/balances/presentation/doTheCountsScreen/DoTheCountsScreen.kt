package com.jarica.compartirgastos.features.balances.presentation.doTheCountsScreen

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.PaymentsToDoCountsModel
import com.jarica.compartirgastos.core.presentation.composables.CustomIcon
import com.jarica.compartirgastos.core.presentation.ui.doTheCount
import com.jarica.compartirgastos.core.presentation.ui.doTheCountsNoDebts
import com.jarica.compartirgastos.core.presentation.ui.doTheCountsTransfersLabel
import com.jarica.compartirgastos.core.presentation.ui.exportAdButtonText
import com.jarica.compartirgastos.core.presentation.ui.exportArrayListDoTheCountsLargeText
import com.jarica.compartirgastos.core.presentation.ui.exportArrayListDoTheCountsText
import com.jarica.compartirgastos.core.presentation.ui.noAppToOpenPDF
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.core.utils.toMoneyDisplay

private val LineColor = Color(0xFFE6E4DE)
private val MutedColor = Color(0xFF6B7A86)
private val InkColor = Color(0xFF1F2A33)

@Composable
fun DoTheCountsScreen(
    doTheCountsScreenViewModel: DoTheCountsScreenViewModel,
    navigateToGroupScreen: () -> Unit,
    idGroupName: String?,
) {
    LaunchedEffect(idGroupName) {
        doTheCountsScreenViewModel.calculatePayments(idGroupName)
    }

    val paymentsList by doTheCountsScreenViewModel.paymentsState.collectAsState()
    val pdfReady by doTheCountsScreenViewModel.pdfReady.collectAsState()
    val launchPicker by doTheCountsScreenViewModel.launchPicker.collectAsState()
    val groupName by doTheCountsScreenViewModel.groupName.collectAsState()
    val context = LocalContext.current
    val activity = context as Activity

    LaunchedEffect(Unit) {
        doTheCountsScreenViewModel.loadAd()
    }

    val createPdfLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.CreateDocument("application/pdf"),
        onResult = { uri: Uri? ->
            uri?.let { doTheCountsScreenViewModel.createPdf(context.contentResolver, it) }
        }
    )

    LaunchedEffect(launchPicker) {
        if (launchPicker) {
            createPdfLauncher.launch("Equify_$groupName.pdf")
            doTheCountsScreenViewModel.onPickerLaunched()
        }
    }

    val noAppToOpenPDFText = noAppToOpenPDF
    LaunchedEffect(pdfReady) {
        pdfReady?.let { uri ->
            val intent = Intent(Intent.ACTION_VIEW).apply {
                setDataAndType(uri, "application/pdf")
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            try {
                context.startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(context, noAppToOpenPDFText, Toast.LENGTH_SHORT).show()
            }
            doTheCountsScreenViewModel.onPdfOpened()
        }
    }

    DoTheCountsContent(
        paymentsList = paymentsList,
        groupName = groupName,
        onExportClick = { doTheCountsScreenViewModel.showAdThenLaunchPicker(activity) },
        navigateBack = navigateToGroupScreen
    )
}

@Composable
private fun DoTheCountsContent(
    paymentsList: List<PaymentsToDoCountsModel>,
    groupName: String,
    onExportClick: () -> Unit,
    navigateBack: () -> Unit,
) {
    val scrollState = rememberScrollState()

    val subtitle = if (paymentsList.isEmpty())
        doTheCountsNoDebts
    else
        pluralStringResource(R.plurals.do_the_counts_transfers_subtitle, paymentsList.size, paymentsList.size)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            DoTheCountsHeader(
                groupName = groupName,
                subtitle = subtitle,
                onBack = navigateBack
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(scrollState)
                    .padding(horizontal = 16.dp)
                    .padding(top = 24.dp, bottom = 100.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                // PDF icon hero
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(22.dp))
                        .background(Color(0xFFFFF3EE)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.pdfsvg),
                        contentDescription = null,
                        tint = Color.Unspecified,
                        modifier = Modifier.size(48.dp)
                    )
                }

                Spacer(Modifier.height(16.dp))

                Text(
                    text = exportArrayListDoTheCountsText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = InkColor,
                    fontFamily = parkinsans,
                    letterSpacing = (-0.02).em
                )

                Spacer(Modifier.height(6.dp))

                Text(
                    text = exportArrayListDoTheCountsLargeText,
                    fontSize = 12.sp,
                    color = MutedColor,
                    fontFamily = parkinsans,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

                // Transfer cards — compact, only when there are payments
                if (paymentsList.isNotEmpty()) {
                    Spacer(Modifier.height(24.dp))

                    Text(
                        text = doTheCountsTransfersLabel.uppercase(),
                        fontSize = 11.sp,
                        letterSpacing = 0.06.em,
                        color = MutedColor,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = parkinsans,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .border(1.dp, LineColor, RoundedCornerShape(14.dp))
                    ) {
                        paymentsList.forEachIndexed { index, payment ->
                            if (index > 0) {
                                Box(
                                    Modifier
                                        .fillMaxWidth()
                                        .height(1.dp)
                                        .background(LineColor)
                                )
                            }
                            CompactTransferRow(payment = payment)
                        }
                    }
                }
            }
        }

        // Bottom action bar
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    Brush.verticalGradient(
                        colorStops = arrayOf(
                            0f to Color.Transparent,
                            0.35f to White,
                            1f to White
                        )
                    )
                )
                .navigationBarsPadding()
                .padding(horizontal = 18.dp)
                .padding(top = 16.dp, bottom = 22.dp)
        ) {
            Button(
                onClick = onExportClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = DarkOrange,
                    contentColor = White
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                Text(
                    text = exportAdButtonText,
                    fontFamily = parkinsans,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 15.sp,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
            }
        }
    }
}

@Composable
private fun DoTheCountsHeader(
    groupName: String,
    subtitle: String,
    onBack: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp))
            .drawBehind {
                drawRect(DarkBlue)
                val side = 140.dp.toPx()
                val half = side / 2f
                val cx = size.width - 40.dp.toPx()
                val cy = size.height - 40.dp.toPx()
                withTransform({ rotate(degrees = 45f, pivot = Offset(cx, cy)) }) {
                    drawRoundRect(
                        color = DarkOrange,
                        topLeft = Offset(cx - half, cy - half),
                        size = Size(side, side),
                        cornerRadius = CornerRadius(6.dp.toPx()),
                        alpha = 0.95f
                    )
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 18.dp)
                .padding(top = 14.dp, bottom = 22.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(White.copy(alpha = 0.08f), RoundedCornerShape(12.dp))
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { onBack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.arrow_back),
                        contentDescription = "",
                        tint = White,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Text(
                    text = groupName,
                    fontSize = 13.sp,
                    color = White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium,
                    fontFamily = parkinsans
                )
                Spacer(modifier = Modifier.size(36.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = doTheCount,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = White,
                fontFamily = parkinsans,
                letterSpacing = (-0.02).em
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = subtitle,
                fontSize = 13.sp,
                color = White.copy(alpha = 0.75f),
                fontFamily = parkinsans
            )
        }
    }
}

@Composable
private fun CompactTransferRow(payment: PaymentsToDoCountsModel) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        CustomIcon(name = payment.namePersonWhoPay, size = 24.dp)
        Text(
            text = payment.namePersonWhoPay,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = InkColor,
            fontFamily = parkinsans,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
        Text(
            text = payment.amount.toMoneyDisplay(),
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            color = DarkOrange,
            fontFamily = parkinsans,
            letterSpacing = (-0.01).em
        )
        Icon(
            painter = painterResource(R.drawable.right_arrow),
            contentDescription = null,
            tint = MutedColor.copy(alpha = 0.45f),
            modifier = Modifier.size(14.dp)
        )
        CustomIcon(name = payment.namePersonWhoReceive, size = 24.dp)
        Text(
            text = payment.namePersonWhoReceive,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            color = InkColor,
            fontFamily = parkinsans,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
        )
    }
}
