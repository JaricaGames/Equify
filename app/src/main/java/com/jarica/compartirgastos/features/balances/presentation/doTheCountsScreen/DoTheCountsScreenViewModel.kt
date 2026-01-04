package com.jarica.compartirgastos.features.balances.presentation.doTheCountsScreen

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itextpdf.text.BaseColor
import com.itextpdf.text.Element
import com.itextpdf.text.Font
import com.itextpdf.text.Phrase
import com.itextpdf.text.pdf.PdfPCell
import com.itextpdf.text.pdf.PdfPTable
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.PaymentsToDoCountsModel
import com.jarica.compartirgastos.core.domain.models.PersonModel
import com.jarica.compartirgastos.core.presentation.ui.amountText
import com.jarica.compartirgastos.core.presentation.ui.costListText
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkYellow2RGB
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkYellowRGB
import com.jarica.compartirgastos.core.presentation.ui.titleText
import com.jarica.compartirgastos.core.presentation.ui.toText
import com.jarica.compartirgastos.features.balances.domain.balancesUseCases.DoTheCountsUseCase
import com.jarica.compartirgastos.features.balances.domain.balancesUseCases.GetBalancesByGroupUseCase
import com.jarica.compartirgastos.features.people.domain.peopleUseCases.UpdatePersonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoTheCountsScreenViewModel @Inject constructor(
    private val updatePersonUseCase: UpdatePersonUseCase,
    private val getBalancesByGroupUseCase: GetBalancesByGroupUseCase,
    private val doTheCountsUseCase: DoTheCountsUseCase,
    @ApplicationContext private val context: Context
) : ViewModel() {

/*    private val _listOfPayments = MutableLiveData<ArrayList<PaymentsToCountsModel>>()
    val listOfPayments: LiveData<ArrayList<PaymentsToCountsModel>> = _listOfPayments*/

    private val _listOfPersons = MutableLiveData<List<PersonModel>>()
    //val listOfPersons: LiveData<List<PersonModel>> = _listOfPersons

    private val _paymentsState = MutableStateFlow<List<PaymentsToDoCountsModel>>(emptyList())
    val paymentsState: StateFlow<List<PaymentsToDoCountsModel>> = _paymentsState

    fun calculatePayments(groupId: String?) {
        viewModelScope.launch {

            try {
                val balances = getBalancesByGroupUseCase(groupId).first()
                val result = doTheCountsUseCase(balances)
                _paymentsState.value = result

            } catch (e: Exception) {
                _paymentsState.value = emptyList()
            }
        }
    }

    fun doTheCounts(peopleList: List<PersonModel>) {

//Array de pagos
        /*val arrayPaymentsToDoTheCounts = ArrayList<PaymentsToCountsModel>(emptyList())

        // Copia las personas para no modificar la lista original.
        val mutablePeopleList = peopleList.map { it.copy() }
        _listOfPersons.value = peopleList

        run personWhoPay@{
            for (personWhoPay in mutablePeopleList) {

                //Compruebo si el equity es menor que 0, en ese caso tiene que pagar

                *//*if (personWhoPay.equity.toFloat() < 0 && personWhoPay.idGroupName == iDGroupName) {

                    //Calculo a quien le tiene que pagar
                    run personWhoReceive@{

                        for (personWhoReceive in mutablePeopleList) {

                            //Si lo que tiene que pagar es menor que lo que tiene recibe la otra persona
                            if (personWhoPay.equity.toFloat().absoluteValue <= personWhoReceive.equity.toFloat() && personWhoReceive.idPerson != personWhoPay.idPerson && personWhoReceive.equity.toFloat() > 0 && personWhoReceive.idGroupName == iDGroupName) {

                                arrayPaymentsToDoTheCounts.add(
                                    PaymentsToCountsModel(
                                        amount = personWhoPay.equity.toFloat().absoluteValue.toString(),
                                        namePersonWhoPay = personWhoPay.name,
                                        namePersonWhoReceive = personWhoReceive.name,
                                    )
                                )

                                personWhoPay.equity = "0"
                                personWhoReceive.equity =
                                    (personWhoReceive.equity.toFloat().absoluteValue - personWhoPay.equity.toFloat().absoluteValue).toString()

                                return@personWhoReceive
                            }

                            //Si lo que tiene que pagar es mayor que lo que tiene recibe la otra persona
                            if (personWhoPay.equity.toFloat().absoluteValue >= personWhoReceive.equity.toFloat() && personWhoReceive.idPerson != personWhoPay.idPerson && personWhoReceive.equity.toFloat() > 0 && personWhoReceive.idGroupName == iDGroupName) {

                                arrayPaymentsToDoTheCounts.add(
                                    PaymentsToCountsModel(
                                        amount = personWhoReceive.equity,
                                        namePersonWhoPay = personWhoPay.name,
                                        namePersonWhoReceive = personWhoReceive.name,
                                    )
                                )

                                personWhoPay.equity =
                                    "-" + (personWhoPay.equity.toFloat().absoluteValue - personWhoReceive.equity.toFloat()).toString()
                                personWhoReceive.equity = "0"

                            }
                        }
                    }
                }

            }
        }

        _listOfPayments.value = arrayPaymentsToDoTheCounts*/


    }

/*    fun putEverythingToZero() {
        _listOfPersons.value!!.forEach { person ->
            if (person.idGroupName == iDGroupName) {
                viewModelScope.launch(Dispatchers.IO) {
                   // updatePersonUseCase(personModel = person.copy(equity = "0"))
                }
            }
        }
    }*/


    //Crea el PDF
    /*fun createPdf(
        contentResolver: ContentResolver,
        uri: Uri,
        listOfPayments: List<PaymentsToDoCountsModel>,
        costsList: List<CostModel>
    ) {
        val document = Document()

        contentResolver.openOutputStream(uri)?.use { outputStream: OutputStream ->

            PdfWriter.getInstance(document, outputStream)

            //ABRIMOS EL DOCUMENTO
            document.open()

            //PONEMOS EL TÍTULO
            // Crear formato de fecha
            // Formato con mes en letras y según idioma del sistema
            val locale = Locale.getDefault()
            val pattern = if (locale.language == "es") {
                "dd 'de' MMMM 'de' yyyy" // español
            } else {
                "dd MMMM yyyy" // otros idiomas
            }
            val sdf = SimpleDateFormat(pattern, locale)
            val currentDate = sdf.format(Date())
            val font = Font(Font.FontFamily.HELVETICA, 24f, Font.BOLD, BaseColor.BLACK)
            val paragraph = Paragraph("$groupNameCompanionObject   -   $dateText: $currentDate", font)
            document.add(paragraph)

            //RELLENAR TABLA DE GASTOS
            document.add(fillInPayTable(costsList))

            //RELENAMOS TABLA DE PAGOS ENTRE PERSONAS
            //document.add(fillInPaymentsToDoTheCounts(listOfPayments))

            //CERRAMOS DOCUMENTO
            document.close()
        }

    }*/

    /*private fun fillInPaymentsToDoTheCounts(listOfPayments: ArrayList<PaymentsToCountsModel>): PdfPTable {

        // PAGOS PARA AJUSTES //
        // Crear tabla con 4columnas
        val table = PdfPTable(4)
        //ENCABEZADO
        val boldFont = Font(Font.FontFamily.HELVETICA, 16f, Font.BOLD, BaseColor.BLACK)
        val header = PdfPCell(Phrase(payForText, boldFont))

        header.colspan = 4
        header.horizontalAlignment = Element.ALIGN_CENTER
        header.backgroundColor = DarkYellowRGB
        header.borderWidthBottom = 3f
        header.borderColorBottom = BaseColor.BLACK
        header.setPadding(12f)
        table.addCell(header)


        // Rellenar filas desde la lista
        listOfPayments.forEachIndexed() { index, pago ->
            // PAra hacer el borde a la fila de abajo comparamos el ultima indice del array
            if (index != listOfPayments.lastIndex) {
                val cell1 = PdfPCell(Phrase(pago.namePersonWhoPay))
                cell1.horizontalAlignment = PdfPCell.ALIGN_CENTER
                cell1.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                cell1.setPadding(8f)
                cell1.border = PdfPCell.NO_BORDER
                cell1.borderWidthBottom = 1f
                cell1.borderColorBottom = BaseColor.BLACK
                table.addCell(cell1)

                val cell2 = PdfPCell(Phrase(oweToText))
                cell2.horizontalAlignment = PdfPCell.ALIGN_CENTER
                cell2.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                cell2.setPadding(8f)
                cell2.border = PdfPCell.NO_BORDER
                cell2.borderWidthBottom = 1f
                cell2.borderColorBottom = BaseColor.BLACK
                table.addCell(cell2)

                val cell3 = PdfPCell(Phrase(pago.namePersonWhoReceive))
                cell3.horizontalAlignment = PdfPCell.ALIGN_CENTER
                cell3.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                cell3.setPadding(8f)
                cell3.border = PdfPCell.NO_BORDER
                cell3.borderWidthBottom = 1f
                cell3.borderColorBottom = BaseColor.BLACK
                table.addCell(cell3)


                val boldFontAmount =
                    Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD, BaseColor.BLACK)
                val cell4 =
                    PdfPCell(Phrase("%.2f".format(pago.amount.toFloat()) + " €", boldFontAmount))
                // val cell4 = PdfPCell(Phrase(pago.amount + " €", boldFontAmount))
                cell4.horizontalAlignment = PdfPCell.ALIGN_CENTER
                cell4.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                cell3.setPadding(8f)
                cell4.border = PdfPCell.NO_BORDER
                cell4.borderWidthBottom = 1f
                cell4.borderColorBottom = BaseColor.BLACK
                cell4.backgroundColor = DarkYellow2RGB
                table.addCell(cell4)
            } else {
                val cell1 = PdfPCell(Phrase(pago.namePersonWhoPay))
                cell1.horizontalAlignment = PdfPCell.ALIGN_CENTER
                cell1.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                cell1.setPadding(8f)
                cell1.border = PdfPCell.NO_BORDER
                cell1.borderWidthBottom = 3f
                cell1.borderColorBottom = BaseColor.BLACK
                table.addCell(cell1)

                val cell2 = PdfPCell(Phrase(oweToText))
                cell2.horizontalAlignment = PdfPCell.ALIGN_CENTER
                cell2.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                cell2.setPadding(8f)
                cell2.border = PdfPCell.NO_BORDER
                cell2.borderWidthBottom = 3f
                cell2.borderColorBottom = BaseColor.BLACK
                table.addCell(cell2)

                val cell3 = PdfPCell(Phrase(pago.namePersonWhoReceive))
                cell3.horizontalAlignment = PdfPCell.ALIGN_CENTER
                cell3.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                cell3.setPadding(8f)
                cell3.border = PdfPCell.NO_BORDER
                cell3.borderWidthBottom = 3f
                cell3.borderColorBottom = BaseColor.BLACK
                table.addCell(cell3)

                val boldFontAmount =
                    Font(Font.FontFamily.HELVETICA, 12f, Font.BOLD, BaseColor.BLACK)
                val cell4 =
                    PdfPCell(Phrase("%.2f".format(pago.amount.toFloat()) + " €", boldFontAmount))
                cell4.horizontalAlignment = PdfPCell.ALIGN_CENTER
                cell4.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                cell4.setPadding(8f)
                cell4.border = PdfPCell.NO_BORDER
                cell4.borderWidthBottom = 3f
                cell4.borderColorBottom = BaseColor.BLACK
                cell4.backgroundColor = DarkYellow2RGB
                table.addCell(cell4)
            }
        }
        table.spacingBefore = 40f
        return table
    }*/

    private fun fillInPayTable(costsList: List<CostModel>): PdfPTable {

        // LISTA DE GASTOS //
        // Crear tabla con 3 columnas
        val table = PdfPTable(3)

        //ENCABEZADO LISTA DE GASTOS

        val boldFont = Font(Font.FontFamily.HELVETICA, 16f, Font.BOLD, BaseColor.BLACK)
        val header = PdfPCell(Phrase(costListText, boldFont))

        header.colspan = 3
        header.horizontalAlignment = Element.ALIGN_CENTER
        header.backgroundColor = DarkYellowRGB
        header.borderWidthBottom = 3f
        header.borderColorBottom = BaseColor.BLACK
        header.setPadding(12f)
        table.addCell(header)

        // ENCABEZADOS TITULOS ED COLUMNAS

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

        val toCell = PdfPCell(Phrase(toText))
        toCell.horizontalAlignment = PdfPCell.ALIGN_CENTER
        toCell.verticalAlignment = PdfPCell.ALIGN_MIDDLE
        toCell.setPadding(8f)
        toCell.border = PdfPCell.NO_BORDER
        toCell.backgroundColor = DarkYellow2RGB
        toCell.borderWidthBottom = 1f
        toCell.borderColorBottom = BaseColor.BLACK
        table.addCell(toCell)


        // Rellenar filas desde la lista
        costsList.forEachIndexed { index, cost ->
            // PAra hacer el borde a la fila de abajo comparamos el ultima indice del array
            if (index != costsList.lastIndex) {
                val titleCells = PdfPCell(Phrase(cost.description))
                titleCells.horizontalAlignment = PdfPCell.ALIGN_CENTER
                titleCells.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                titleCells.setPadding(8f)
                titleCells.border = PdfPCell.NO_BORDER
                titleCells.borderWidthBottom = 1f
                titleCells.borderColorBottom = BaseColor.BLACK
                table.addCell(titleCells)

                val amountCells = PdfPCell(Phrase(cost.amount.toString() + " €"))
                amountCells.horizontalAlignment = PdfPCell.ALIGN_CENTER
                amountCells.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                amountCells.setPadding(8f)
                amountCells.border = PdfPCell.NO_BORDER
                amountCells.borderWidthBottom = 1f
                amountCells.borderColorBottom = BaseColor.BLACK
                table.addCell(amountCells)

                /*val personWhoPayCells = PdfPCell(Phrase(cost.personString))
                personWhoPayCells.horizontalAlignment = PdfPCell.ALIGN_CENTER
                personWhoPayCells.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                personWhoPayCells.setPadding(8f)
                personWhoPayCells.border = PdfPCell.NO_BORDER
                personWhoPayCells.borderWidthBottom = 1f
                personWhoPayCells.borderColorBottom = BaseColor.BLACK
                table.addCell(personWhoPayCells)*/

            } else {

                val titleCells = PdfPCell(Phrase(cost.description))
                titleCells.horizontalAlignment = PdfPCell.ALIGN_CENTER
                titleCells.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                titleCells.setPadding(8f)
                titleCells.border = PdfPCell.NO_BORDER
                titleCells.borderWidthBottom = 3f
                titleCells.borderColorBottom = BaseColor.BLACK
                table.addCell(titleCells)

                val amountCells = PdfPCell(Phrase(cost.amount.toString() + " €"))
                amountCells.horizontalAlignment = PdfPCell.ALIGN_CENTER
                amountCells.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                amountCells.setPadding(8f)
                amountCells.border = PdfPCell.NO_BORDER
                amountCells.borderWidthBottom = 3f
                amountCells.borderColorBottom = BaseColor.BLACK
                table.addCell(amountCells)

                /*val personWhoPayCells = PdfPCell(Phrase(cost.personString))
                personWhoPayCells.horizontalAlignment = PdfPCell.ALIGN_CENTER
                personWhoPayCells.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                personWhoPayCells.setPadding(8f)
                personWhoPayCells.border = PdfPCell.NO_BORDER
                personWhoPayCells.borderWidthBottom = 3f
                personWhoPayCells.borderColorBottom = BaseColor.BLACK
                table.addCell(personWhoPayCells)*/


            }
        }
        table.spacingBefore = 40f
        return table
    }


}




