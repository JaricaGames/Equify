package com.jarica.compartirgastos.presentation.mainViewsScreens.doTheCountsScreen

import android.content.ContentResolver
import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
import com.jarica.compartirgastos.domain.models.CostModel
import com.jarica.compartirgastos.domain.models.PersonModel
import com.jarica.compartirgastos.domain.peopleUseCases.UpdatePersonUseCase
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.groupNameCompanionObject
import com.jarica.compartirgastos.presentation.mainViewsScreens.mainScreen.MainScreenViewModel.Companion.iDGroupName
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellow2RGB
import com.jarica.compartirgastos.presentation.ui.theme.DarkYellowRGB
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlin.math.absoluteValue

@HiltViewModel
class DoTheCountsScreenViewModel @Inject constructor(
    private val updatePersonUseCase: UpdatePersonUseCase
) : ViewModel() {


    private val _listOfPayments = MutableLiveData<ArrayList<PaymentsToCountsModel>>()
    val listOfPayments: LiveData<ArrayList<PaymentsToCountsModel>> = _listOfPayments

    private val _listOfPersons = MutableLiveData<List<PersonModel>>()
    //val listOfPersons: LiveData<List<PersonModel>> = _listOfPersons

    fun doTheCounts(peopleList: List<PersonModel>) {

//Array de pagos
        val arrayPaymentsToDoTheCounts = ArrayList<PaymentsToCountsModel>(emptyList())

        // Copia las personas para no modificar la lista original.
        val mutablePeopleList = peopleList.map { it.copy() }
        _listOfPersons.value = peopleList

        run personWhoPay@{
            for (personWhoPay in mutablePeopleList) {

                //Compruebo si el equity es menor que 0, en ese caso tiene que pagar

                if (personWhoPay.equity.toFloat() < 0 && personWhoPay.idGroupName == iDGroupName) {

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

        // PONE TODOS LOS EQUITY EN 0
        /*        for (persons in peopleList) {

            if (persons.idGroupName == iDGroupName) {
                viewModelScope.launch(Dispatchers.IO) {
                    updatePersonUseCase(personModel = persons.copy(equity = "0"))
                }
            }
        }*/
        for (item in arrayPaymentsToDoTheCounts) {
            Log.d(
                "Nono",
                item.namePersonWhoPay + " paga a " + item.namePersonWhoReceive + " la cantidad de = " + item.amount + " €"
            )
        }
        _listOfPayments.value = arrayPaymentsToDoTheCounts


    }

    fun putEverythingToZero() {

        _listOfPersons.value!!.forEach { person ->
            if (person.idGroupName == iDGroupName) {
                viewModelScope.launch(Dispatchers.IO) {
                    updatePersonUseCase(personModel = person.copy(equity = "0"))
                }
            }
        }
    }


    //Crea el PDF
    fun createPdf(
        contentResolver: ContentResolver,
        uri: Uri,
        listOfPayments: ArrayList<PaymentsToCountsModel>,
        costsList: List<CostModel>,
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
            val paragraph = Paragraph("$groupNameCompanionObject   -   Fecha: $currentDate", font)
            document.add(paragraph)

            //RELLENAR TABLA DE GASTOS
            document.add(fillInPayTable(costsList))

            //RELENAMOS TABLA DE PAGOS ENTRE PERSONAS
            document.add(fillInPaymentsToDoTheCounts(listOfPayments))

            //CERRAMOS DOCUMENTO
            document.close()
        }

    }

    private fun fillInPaymentsToDoTheCounts(listOfPayments: ArrayList<PaymentsToCountsModel>): PdfPTable {

        // PAGOS PARA AJUSTES //
        // Crear tabla con 4columnas
        val table = PdfPTable(4)
        //ENCABEZADO
        val boldFont = Font(Font.FontFamily.HELVETICA, 16f, Font.BOLD, BaseColor.BLACK)
        val header = PdfPCell(Phrase("PAGOS PARA AJUSTES", boldFont))

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

                val cell2 = PdfPCell(Phrase("le paga a"))
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

                val cell2 = PdfPCell(Phrase("le paga a"))
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
    }

    private fun fillInPayTable(costsList: List<CostModel>): PdfPTable {

        // LISTA DE GASTOS //
        // Crear tabla con 3 columnas
        val table = PdfPTable(3)

        //ENCABEZADO LISTA DE GASTOS

        val boldFont = Font(Font.FontFamily.HELVETICA, 16f, Font.BOLD, BaseColor.BLACK)
        val header = PdfPCell(Phrase("LISTA DE GASTOS", boldFont))

        header.colspan = 3
        header.horizontalAlignment = Element.ALIGN_CENTER
        header.backgroundColor = DarkYellowRGB
        header.borderWidthBottom = 3f
        header.borderColorBottom = BaseColor.BLACK
        header.setPadding(12f)
        table.addCell(header)

        // ENCABEZADOS TITULOS ED COLUMNAS

        val cell1 = PdfPCell(Phrase("Título"))
        cell1.horizontalAlignment = PdfPCell.ALIGN_CENTER
        cell1.verticalAlignment = PdfPCell.ALIGN_MIDDLE
        cell1.setPadding(8f)
        cell1.border = PdfPCell.NO_BORDER
        cell1.backgroundColor = DarkYellow2RGB
        cell1.borderWidthBottom = 1f
        cell1.borderColorBottom = BaseColor.BLACK
        table.addCell(cell1)

        val cell2 = PdfPCell(Phrase("Cantidad"))
        cell2.horizontalAlignment = PdfPCell.ALIGN_CENTER
        cell2.verticalAlignment = PdfPCell.ALIGN_MIDDLE
        cell2.setPadding(8f)
        cell2.border = PdfPCell.NO_BORDER
        cell2.backgroundColor = DarkYellow2RGB
        cell2.borderWidthBottom = 1f
        cell2.borderColorBottom = BaseColor.BLACK
        table.addCell(cell2)

        val cell3 = PdfPCell(Phrase("De"))
        cell3.horizontalAlignment = PdfPCell.ALIGN_CENTER
        cell3.verticalAlignment = PdfPCell.ALIGN_MIDDLE
        cell3.setPadding(8f)
        cell3.border = PdfPCell.NO_BORDER
        cell3.backgroundColor = DarkYellow2RGB
        cell3.borderWidthBottom = 1f
        cell3.borderColorBottom = BaseColor.BLACK
        table.addCell(cell3)


        // Rellenar filas desde la lista
        costsList.forEachIndexed() { index, cost ->
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

                val personWhoPayCells = PdfPCell(Phrase(cost.personString))
                personWhoPayCells.horizontalAlignment = PdfPCell.ALIGN_CENTER
                personWhoPayCells.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                personWhoPayCells.setPadding(8f)
                personWhoPayCells.border = PdfPCell.NO_BORDER
                personWhoPayCells.borderWidthBottom = 1f
                personWhoPayCells.borderColorBottom = BaseColor.BLACK
                table.addCell(personWhoPayCells)

            } else {

                val cell1 = PdfPCell(Phrase(cost.description))
                cell1.horizontalAlignment = PdfPCell.ALIGN_CENTER
                cell1.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                cell1.setPadding(8f)
                cell1.border = PdfPCell.NO_BORDER
                cell1.borderWidthBottom = 3f
                cell1.borderColorBottom = BaseColor.BLACK
                table.addCell(cell1)

                val cell2 = PdfPCell(Phrase(cost.amount.toString() + " €"))
                cell2.horizontalAlignment = PdfPCell.ALIGN_CENTER
                cell2.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                cell2.setPadding(8f)
                cell2.border = PdfPCell.NO_BORDER
                cell2.borderWidthBottom = 3f
                cell2.borderColorBottom = BaseColor.BLACK
                table.addCell(cell2)

                val cell3 = PdfPCell(Phrase(cost.personString))
                cell3.horizontalAlignment = PdfPCell.ALIGN_CENTER
                cell3.verticalAlignment = PdfPCell.ALIGN_MIDDLE
                cell3.setPadding(8f)
                cell3.border = PdfPCell.NO_BORDER
                cell3.borderWidthBottom = 3f
                cell3.borderColorBottom = BaseColor.BLACK
                table.addCell(cell3)


            }
        }
        table.spacingBefore = 40f
        return table
    }

}

data class PaymentsToCountsModel(
    val amount: String,
    val namePersonWhoPay: String,
    val namePersonWhoReceive: String,

    )

