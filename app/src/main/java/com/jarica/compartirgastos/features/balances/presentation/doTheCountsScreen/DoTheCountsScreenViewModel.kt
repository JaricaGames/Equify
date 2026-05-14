package com.jarica.compartirgastos.features.balances.presentation.doTheCountsScreen

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Document
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Paragraph
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.itextpdf.text.pdf.PdfWriter
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.PaymentsToDoCountsModel
import com.jarica.compartirgastos.core.presentation.ui.amountText
import com.jarica.compartirgastos.core.presentation.ui.costListText
import com.jarica.compartirgastos.core.presentation.ui.dateText
import com.jarica.compartirgastos.core.presentation.ui.oweToText
import com.jarica.compartirgastos.core.presentation.ui.payToDoTheCountsText
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkYellow2RGB
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkYellowRGB
import com.jarica.compartirgastos.core.presentation.ui.titleText
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
import java.io.OutputStream
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

    fun createPdf(contentResolver: ContentResolver, uri: Uri) {
        viewModelScope.launch(Dispatchers.IO) {
            val document = Document()
            contentResolver.openOutputStream(uri)?.use { outputStream: OutputStream ->
                PdfWriter.getInstance(document, outputStream)
                document.open()

                val locale = Locale.getDefault()
                val pattern = if (locale.language == "es") "dd 'de' MMMM 'de' yyyy" else "dd MMMM yyyy"
                val currentDate = SimpleDateFormat(pattern, locale).format(Date())
                val titleFont = Font(Font.FontFamily.HELVETICA, 24f, Font.BOLD, BaseColor.BLACK)
                document.add(Paragraph("${_groupName.value}   -   $dateText: $currentDate", titleFont))

                document.add(fillInPayTable(_costsState.value))
                document.add(fillInPaymentsToDoTheCounts(_paymentsState.value))

                document.close()
            }
            _pdfReady.value = uri
        }
    }

    fun onPdfOpened() {
        _pdfReady.value = null
    }

    private fun fillInPaymentsToDoTheCounts(listOfPayments: List<PaymentsToDoCountsModel>): PdfPTable {
        val table = PdfPTable(4)
        val boldFont = Font(Font.FontFamily.HELVETICA, 16f, Font.BOLD, BaseColor.BLACK)

        val header = PdfPCell(Phrase(payToDoTheCountsText, boldFont))
        header.colspan = 4
        header.horizontalAlignment = Element.ALIGN_CENTER
        header.backgroundColor = DarkYellowRGB
        header.borderWidthBottom = 3f
        header.borderColorBottom = BaseColor.BLACK
        header.setPadding(12f)
        table.addCell(header)

        listOfPayments.forEachIndexed { index, pago ->
            val borderBottom = if (index == listOfPayments.lastIndex) 3f else 1f

            val cell1 = PdfPCell(Phrase(pago.namePersonWhoPay))
            cell1.horizontalAlignment = PdfPCell.ALIGN_CENTER
            cell1.verticalAlignment = PdfPCell.ALIGN_MIDDLE
            cell1.setPadding(8f)
            cell1.border = PdfPCell.NO_BORDER
            cell1.borderWidthBottom = borderBottom
            cell1.borderColorBottom = BaseColor.BLACK
            table.addCell(cell1)

            val cell2 = PdfPCell(Phrase(oweToText))
            cell2.horizontalAlignment = PdfPCell.ALIGN_CENTER
            cell2.verticalAlignment = PdfPCell.ALIGN_MIDDLE
            cell2.setPadding(8f)
            cell2.border = PdfPCell.NO_BORDER
            cell2.borderWidthBottom = borderBottom
            cell2.borderColorBottom = BaseColor.BLACK
            table.addCell(cell2)

            val cell3 = PdfPCell(Phrase(pago.namePersonWhoReceive))
            cell3.horizontalAlignment = PdfPCell.ALIGN_CENTER
            cell3.verticalAlignment = PdfPCell.ALIGN_MIDDLE
            cell3.setPadding(8f)
            cell3.border = PdfPCell.NO_BORDER
            cell3.borderWidthBottom = borderBottom
            cell3.borderColorBottom = BaseColor.BLACK
            table.addCell(cell3)

            val amountFont = Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD, BaseColor.BLACK)
            val cell4 = PdfPCell(Phrase("%.2f".format(pago.amount) + " €", amountFont))
            cell4.horizontalAlignment = PdfPCell.ALIGN_CENTER
            cell4.verticalAlignment = PdfPCell.ALIGN_MIDDLE
            cell4.setPadding(8f)
            cell4.border = PdfPCell.NO_BORDER
            cell4.borderWidthBottom = borderBottom
            cell4.borderColorBottom = BaseColor.BLACK
            cell4.backgroundColor = DarkYellow2RGB
            table.addCell(cell4)
        }
        table.spacingBefore = 40f
        return table
    }

    private fun fillInPayTable(costsList: List<CostModel>): PdfPTable {
        val table = PdfPTable(2)
        val boldFont = Font(Font.FontFamily.HELVETICA, 16f, Font.BOLD, BaseColor.BLACK)

        val header = PdfPCell(Phrase(costListText, boldFont))
        header.colspan = 2
        header.horizontalAlignment = Element.ALIGN_CENTER
        header.backgroundColor = DarkYellowRGB
        header.borderWidthBottom = 3f
        header.borderColorBottom = BaseColor.BLACK
        header.setPadding(12f)
        table.addCell(header)

        val titleCell = PdfPCell(Phrase(titleText))
        titleCell.horizontalAlignment = PdfPCell.ALIGN_CENTER
        titleCell.verticalAlignment = PdfPCell.ALIGN_MIDDLE
        titleCell.setPadding(8f)
        titleCell.border = PdfPCell.NO_BORDER
        titleCell.backgroundColor = DarkYellow2RGB
        titleCell.borderWidthBottom = 1f
        titleCell.borderColorBottom = BaseColor.BLACK
        table.addCell(titleCell)

        val amountCell = PdfPCell(Phrase(amountText))
        amountCell.horizontalAlignment = PdfPCell.ALIGN_CENTER
        amountCell.verticalAlignment = PdfPCell.ALIGN_MIDDLE
        amountCell.setPadding(8f)
        amountCell.border = PdfPCell.NO_BORDER
        amountCell.backgroundColor = DarkYellow2RGB
        amountCell.borderWidthBottom = 1f
        amountCell.borderColorBottom = BaseColor.BLACK
        table.addCell(amountCell)

        costsList.forEachIndexed { index, cost ->
            val borderBottom = if (index == costsList.lastIndex) 3f else 1f

            val titleCells = PdfPCell(Phrase(cost.description))
            titleCells.horizontalAlignment = PdfPCell.ALIGN_CENTER
            titleCells.verticalAlignment = PdfPCell.ALIGN_MIDDLE
            titleCells.setPadding(8f)
            titleCells.border = PdfPCell.NO_BORDER
            titleCells.borderWidthBottom = borderBottom
            titleCells.borderColorBottom = BaseColor.BLACK
            table.addCell(titleCells)

            val amountCells = PdfPCell(Phrase("%.2f".format(cost.amount) + " €"))
            amountCells.horizontalAlignment = PdfPCell.ALIGN_CENTER
            amountCells.verticalAlignment = PdfPCell.ALIGN_MIDDLE
            amountCells.setPadding(8f)
            amountCells.border = PdfPCell.NO_BORDER
            amountCells.borderWidthBottom = borderBottom
            amountCells.borderColorBottom = BaseColor.BLACK
            table.addCell(amountCells)
        }
        table.spacingBefore = 40f
        return table
    }
}
