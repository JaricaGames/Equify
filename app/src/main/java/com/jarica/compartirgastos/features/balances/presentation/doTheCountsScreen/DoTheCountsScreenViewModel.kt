package com.jarica.compartirgastos.features.balances.presentation.doTheCountsScreen

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Image
import com.itextpdf.text.PageSize
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.BaseFont
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfPageEventHelper
import com.itextpdf.text.pdf.PdfWriter
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.PaymentsToDoCountsModel
import com.jarica.compartirgastos.features.balances.domain.balancesUseCases.DoTheCountsUseCase
import com.jarica.compartirgastos.features.balances.domain.balancesUseCases.GetBalancesByGroupUseCase
import com.jarica.compartirgastos.features.costs.domain.costsUseCases.GetCostsByIdGroupUseCase
import com.jarica.compartirgastos.features.groups.domain.useCases.GetGroupByIdUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.UpdatePersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class DoTheCountsScreenViewModel @Inject constructor(
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val getBalancesByGroupUseCase: GetBalancesByGroupUseCase,
    private val doTheCountsUseCase: DoTheCountsUseCase,
    private val getCostsByIdGroupUseCase: GetCostsByIdGroupUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _paymentsState = MutableStateFlow<List<PaymentsToDoCountsModel>>(emptyList())
    val paymentsState: StateFlow<List<PaymentsToDoCountsModel>> = _paymentsState

    private val _costsState = MutableStateFlow<List<CostModel>>(emptyList())

    private val _groupName = MutableStateFlow("")
    val groupName: StateFlow<String> = _groupName

    private val _pdfReady = MutableStateFlow<Uri?>(null)
    val pdfReady: StateFlow<Uri?> = _pdfReady

    private val _launchPicker = MutableStateFlow(false)
    val launchPicker: StateFlow<Boolean> = _launchPicker

    fun calculatePayments(groupId: String?) {
        viewModelScope.launch {
            try {
                val balances = getBalancesByGroupUseCase(groupId).first()
                _paymentsState.value = doTheCountsUseCase(balances)

                if (groupId != null) {
                    _costsState.value = getCostsByIdGroupUseCase(groupId).first()
                    _groupName.value = getGroupByIdUseCase(groupId).groupName
                }
            } catch (e: Exception) {
                _paymentsState.value = emptyList()
            }
        }
    }

    private var interstitialAd: InterstitialAd? = null

    fun loadAd() {
        InterstitialAd.load(
            context,
            "ca-app-pub-3940256099942544/1033173712",
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }
                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    fun showAdThenLaunchPicker(activity: Activity) {
        val ad = interstitialAd
        if (ad != null) {
            ad.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    interstitialAd = null
                    loadAd()
                    _launchPicker.value = true
                }
            }
            ad.show(activity)
        } else {
            _launchPicker.value = true
        }
    }

    fun onPickerLaunched() {
        _launchPicker.value = false
    }

    fun createPdf(contentResolver: ContentResolver, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val costs    = _costsState.value
                val payments = _paymentsState.value
                val name     = _groupName.value

                val navy   = BaseColor(53, 82, 106)
                val orange = BaseColor(228, 86, 55)
                val ink    = BaseColor(31, 42, 51)
                val muted  = BaseColor(107, 122, 134)
                val line   = BaseColor(230, 228, 222)
                val cream  = BaseColor(246, 241, 232)
                val rowAlt = BaseColor(248, 250, 251)

                val dateStr     = SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(Date())
                val totalSpent  = costs.sumOf { it.amount.toDouble() }.toFloat()
                val totalSettle = payments.sumOf { it.amount.toDouble() }.toFloat()

                // Load ic_launcher as the header logo (already has its own cream background)
                val launcherBmp = BitmapFactory.decodeResource(
                    context.resources, R.drawable.equify_icon,
                    BitmapFactory.Options().apply { inPreferredConfig = android.graphics.Bitmap.Config.ARGB_8888 }
                )
                val launcherBaos = java.io.ByteArrayOutputStream()
                launcherBmp.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, launcherBaos)
                val logoImg = Image.getInstance(launcherBaos.toByteArray())
                val logoSize = 52f
                logoImg.scaleAbsolute(logoSize, logoSize)

                // top=84 leaves room for the 72pt header band + 12pt gap
                val document = Document(PageSize.A4, 40f, 40f, 84f, 60f)
                contentResolver.openOutputStream(uri)?.use { outputStream ->
                    val writer = PdfWriter.getInstance(document, outputStream)
                    val bfBold = BaseFont.createFont(BaseFont.HELVETICA_BOLD, BaseFont.CP1252, false)
                    val bfNorm = BaseFont.createFont(BaseFont.HELVETICA,      BaseFont.CP1252, false)

                    writer.pageEvent = object : PdfPageEventHelper() {
                        override fun onEndPage(writer: PdfWriter, document: Document) {
                            val cb = writer.directContent
                            val H  = PageSize.A4.height
                            val W  = PageSize.A4.width

                            // Header band
                            cb.saveState()
                            cb.setColorFill(navy)
                            cb.rectangle(0f, H - 72f, W, 72f)
                            cb.fill()

                            // Launcher icon (cream background, full logo, no artefacts)
                            val cy = H - 36f
                            logoImg.setAbsolutePosition(10f, cy - logoSize / 2f)
                            cb.addImage(logoImg)

                            // Brand text
                            cb.beginText()
                            cb.setFontAndSize(bfBold, 21f)
                            cb.setColorFill(BaseColor.WHITE)
                            cb.showTextAligned(Element.ALIGN_LEFT, "EQUIFY", 10f + logoSize + 10f, cy + 5f, 0f)
                            cb.setFontAndSize(bfNorm, 9f)
                            cb.setColorFill(BaseColor(180, 195, 208))
                            cb.showTextAligned(Element.ALIGN_LEFT, "Divide gastos, multiplica momentos", 10f + logoSize + 10f, cy - 13f, 0f)
                            cb.endText()
                            cb.restoreState()

                            // Footer line + labels
                            cb.saveState()
                            cb.setLineWidth(0.5f)
                            cb.setColorStroke(line)
                            val footLineY = document.bottomMargin() - 15f
                            cb.moveTo(document.leftMargin(), footLineY)
                            cb.lineTo(W - document.rightMargin(), footLineY)
                            cb.stroke()
                            cb.beginText()
                            cb.setFontAndSize(bfNorm, 8f)
                            cb.setColorFill(muted)
                            cb.showTextAligned(Element.ALIGN_LEFT,  "Pág ${writer.pageNumber}",          document.leftMargin(),      footLineY - 12f, 0f)
                            cb.showTextAligned(Element.ALIGN_RIGHT, "Generado con Equify · equify.app", W - document.rightMargin(), footLineY - 12f, 0f)
                            cb.endText()
                            cb.restoreState()
                        }
                    }

                    document.open()

                    // Group title + meta
                    document.add(
                        Paragraph(name, Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD, ink))
                            .also { it.spacingAfter = 4f }
                    )
                    document.add(
                        Paragraph("${costs.size} gastos  ·  Generado el $dateStr", Font(Font.FontFamily.HELVETICA, 9f, Font.NORMAL, muted))
                            .also { it.spacingAfter = 16f }
                    )

                    // Hero stats
                    val statsTable = PdfPTable(3)
                    statsTable.widthPercentage = 100f
                    statsTable.setWidths(floatArrayOf(1f, 0.02f, 1f))
                    statsTable.spacingAfter = 22f
                    statsTable.addCell(buildStatCell("Total gastado", "%.2f €".format(totalSpent),  ink,    muted, cream))
                    statsTable.addCell(PdfPCell().apply { backgroundColor = line; border = PdfPCell.NO_BORDER })
                    statsTable.addCell(buildStatCell("A liquidar",    "%.2f €".format(totalSettle), orange, muted, cream))
                    document.add(statsTable)

                    // Lista de gastos
                    document.add(
                        Paragraph("LISTA DE GASTOS", Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD, navy))
                            .also { it.spacingAfter = 6f }
                    )
                    val costsTable = PdfPTable(2)
                    costsTable.widthPercentage = 100f
                    costsTable.setWidths(floatArrayOf(3f, 1f))
                    costsTable.spacingAfter = 20f
                    costsTable.addCell(buildTableHead("Descripción", Element.ALIGN_LEFT,  muted, navy))
                    costsTable.addCell(buildTableHead("Importe",     Element.ALIGN_RIGHT, muted, navy))
                    if (costs.isEmpty()) {
                        costsTable.addCell(
                            PdfPCell(Phrase("Sin gastos registrados", Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, muted))).apply {
                                colspan = 2; border = PdfPCell.NO_BORDER; setPadding(10f)
                                horizontalAlignment = Element.ALIGN_CENTER
                            }
                        )
                    } else {
                        costs.forEachIndexed { i, cost ->
                            val bg = if (i % 2 == 1) rowAlt else BaseColor.WHITE
                            costsTable.addCell(
                                PdfPCell(Phrase(cost.description, Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, ink))).apply {
                                    border = PdfPCell.NO_BORDER; backgroundColor = bg; setPadding(8f)
                                }
                            )
                            costsTable.addCell(
                                PdfPCell(Phrase("%.2f €".format(cost.amount), Font(Font.FontFamily.HELVETICA, 10f, Font.BOLD, ink))).apply {
                                    border = PdfPCell.NO_BORDER; backgroundColor = bg; setPadding(8f)
                                    horizontalAlignment = Element.ALIGN_RIGHT
                                }
                            )
                        }
                    }
                    document.add(costsTable)

                    // Pagos para liquidar
                    document.add(
                        Paragraph("PAGOS PARA LIQUIDAR", Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD, navy))
                            .also { it.spacingAfter = 6f }
                    )
                    val paymentsTable = PdfPTable(3)
                    paymentsTable.widthPercentage = 100f
                    paymentsTable.setWidths(floatArrayOf(2f, 2f, 1f))
                    paymentsTable.spacingAfter = 20f
                    paymentsTable.addCell(buildTableHead("Paga",     Element.ALIGN_LEFT,  muted, navy))
                    paymentsTable.addCell(buildTableHead("Recibe",   Element.ALIGN_LEFT,  muted, navy))
                    paymentsTable.addCell(buildTableHead("Cantidad", Element.ALIGN_RIGHT, muted, navy))
                    if (payments.isEmpty()) {
                        paymentsTable.addCell(
                            PdfPCell(Phrase("Sin deudas pendientes", Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, muted))).apply {
                                colspan = 3; border = PdfPCell.NO_BORDER; setPadding(10f)
                                horizontalAlignment = Element.ALIGN_CENTER
                            }
                        )
                    } else {
                        payments.forEachIndexed { i, p ->
                            val bg = if (i % 2 == 1) rowAlt else BaseColor.WHITE
                            paymentsTable.addCell(
                                PdfPCell(Phrase(p.namePersonWhoPay, Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, ink))).apply {
                                    border = PdfPCell.NO_BORDER; backgroundColor = bg; setPadding(8f)
                                }
                            )
                            paymentsTable.addCell(
                                PdfPCell(Phrase(p.namePersonWhoReceive, Font(Font.FontFamily.HELVETICA, 10f, Font.NORMAL, ink))).apply {
                                    border = PdfPCell.NO_BORDER; backgroundColor = bg; setPadding(8f)
                                }
                            )
                            paymentsTable.addCell(
                                PdfPCell(Phrase("%.2f €".format(p.amount), Font(Font.FontFamily.HELVETICA, 11f, Font.BOLD, orange))).apply {
                                    border = PdfPCell.NO_BORDER; backgroundColor = bg; setPadding(8f)
                                    horizontalAlignment = Element.ALIGN_RIGHT
                                }
                            )
                        }
                    }
                    document.add(paymentsTable)

                    document.close()
                }
                _pdfReady.value = uri
            } catch (_: Exception) { }
        }
    }

    fun onPdfOpened() {
        _pdfReady.value = null
    }

    private fun buildStatCell(
        label: String, value: String,
        valueColor: BaseColor, labelColor: BaseColor, bgColor: BaseColor
    ): PdfPCell = PdfPCell().apply {
        backgroundColor = bgColor
        border = PdfPCell.NO_BORDER
        setPadding(14f)
        addElement(
            Paragraph(label, Font(Font.FontFamily.HELVETICA, 9f, Font.NORMAL, labelColor))
                .also { it.spacingAfter = 5f }
        )
        addElement(Paragraph(value, Font(Font.FontFamily.HELVETICA, 18f, Font.BOLD, valueColor)))
    }

    private fun buildTableHead(
        text: String, align: Int,
        textColor: BaseColor, borderColor: BaseColor
    ): PdfPCell = PdfPCell(Phrase(text, Font(Font.FontFamily.HELVETICA, 8f, Font.BOLD, textColor))).apply {
        border = PdfPCell.NO_BORDER
        borderWidthBottom = 1.5f
        borderColorBottom = borderColor
        setPadding(8f)
        horizontalAlignment = align
    }
}
