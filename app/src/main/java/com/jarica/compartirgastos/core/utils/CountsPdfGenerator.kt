package com.jarica.compartirgastos.core.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.pdf.PdfDocument
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.domain.models.CostModel
import com.jarica.compartirgastos.core.domain.models.PaymentsToDoCountsModel
import java.io.OutputStream
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Genera el PDF de "hacer cuentas" usando el [PdfDocument] nativo de Android
 * (licencia Apache, sin dependencias externas). Reproduce el diseño de marca:
 * banda de cabecera + pie repetidos por página, tablas con filas alternas y
 * paginación automática cuando el contenido desborda.
 */
class CountsPdfGenerator(
    private val context: Context,
    private val groupName: String,
    private val costs: List<CostModel>,
    private val payments: List<PaymentsToDoCountsModel>,
) {

    // --- Dimensiones de página (A4 en puntos, 1/72") ---
    private val pageW = 595f
    private val pageH = 842f
    private val marginLeft = 40f
    private val marginRight = 40f
    private val contentLeft = marginLeft
    private val contentRight = pageW - marginRight
    private val contentWidth = contentRight - contentLeft
    private val contentTop = 84f      // deja hueco bajo la banda de cabecera (72) + 12
    private val contentBottom = pageH - 60f

    // --- Paleta ---
    private val navy = Color.rgb(53, 82, 106)
    private val orange = Color.rgb(228, 86, 55)
    private val ink = Color.rgb(31, 42, 51)
    private val muted = Color.rgb(107, 122, 134)
    private val lineColor = Color.rgb(230, 228, 222)
    private val cream = Color.rgb(246, 241, 232)
    private val rowAlt = Color.rgb(248, 250, 251)
    private val brandSub = Color.rgb(180, 195, 208)

    private val sans = Typeface.create("sans-serif", Typeface.NORMAL)
    private val sansBold = Typeface.create("sans-serif", Typeface.BOLD)

    private fun textPaint(size: Float, color: Int, tf: Typeface) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = size; this.color = color; typeface = tf
    }

    private fun fillPaint(color: Int) = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        this.color = color; style = Paint.Style.FILL
    }

    // Texto
    private val pTitle = textPaint(18f, ink, sansBold)
    private val pMeta = textPaint(9f, muted, sans)
    private val pSection = textPaint(8f, navy, sansBold)
    private val pStatLabel = textPaint(9f, muted, sans)
    private val pStatValueInk = textPaint(18f, ink, sansBold)
    private val pStatValueOrange = textPaint(18f, orange, sansBold)
    private val pHead = textPaint(8f, muted, sansBold)
    private val pCell = textPaint(10f, ink, sans)
    private val pCellBold = textPaint(10f, ink, sansBold)
    private val pAmount = textPaint(11f, orange, sansBold)
    private val pEmpty = textPaint(10f, muted, sans)
    private val pBrand = textPaint(21f, Color.WHITE, sansBold)
    private val pBrandSub = textPaint(9f, brandSub, sans)
    private val pFooter = textPaint(8f, muted, sans)

    // Relleno / líneas
    private val fillCream = fillPaint(cream)
    private val fillRowAlt = fillPaint(rowAlt)
    private val fillLine = fillPaint(lineColor)
    private val fillNavy = fillPaint(navy)
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE }
    private val bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply { isFilterBitmap = true }

    private val logo: Bitmap? = BitmapFactory.decodeResource(
        context.resources, R.drawable.equify_icon,
        BitmapFactory.Options().apply { inPreferredConfig = Bitmap.Config.ARGB_8888 }
    )

    private val totalSpent = costs.sumOf { it.amount }
    private val totalSettle = payments.sumOf { it.amount }

    private val pdf = PdfDocument()
    private var pageNumber = 0
    private lateinit var page: PdfDocument.Page
    private lateinit var canvas: Canvas
    private var y = 0f

    fun writeTo(out: OutputStream) {
        try {
            startPage()
            drawIntro()
            drawStats()
            drawCostsTable()
            drawPaymentsTable()
            pdf.finishPage(page)
            pdf.writeTo(out)
        } finally {
            pdf.close()
            logo?.recycle()
        }
    }

    // --- Gestión de páginas ---

    private fun startPage() {
        pageNumber++
        val info = PdfDocument.PageInfo.Builder(pageW.toInt(), pageH.toInt(), pageNumber).create()
        page = pdf.startPage(info)
        canvas = page.canvas
        drawHeaderBand()
        drawFooter()
        y = contentTop
    }

    private fun ensureSpace(needed: Float, onNewPage: (() -> Unit)? = null) {
        if (y + needed > contentBottom) {
            pdf.finishPage(page)
            startPage()
            onNewPage?.invoke()
        }
    }

    private fun lineHeight(p: Paint): Float = p.fontMetrics.let { it.bottom - it.top }

    /** Dibuja [text] con la parte superior en [y] y devuelve la y tras la línea. */
    private fun textLine(text: String, x: Float, paint: Paint, align: Paint.Align): Float {
        paint.textAlign = align
        canvas.drawText(text, x, y - paint.fontMetrics.top, paint)
        return y + lineHeight(paint)
    }

    /** Recorta [text] con elipsis para que quepa en [maxW]. */
    private fun fit(text: String, paint: Paint, maxW: Float): String {
        if (paint.measureText(text) <= maxW) return text
        var end = text.length
        while (end > 0 && paint.measureText(text.substring(0, end) + "…") > maxW) end--
        return text.substring(0, end) + "…"
    }

    // --- Cabecera y pie (por página) ---

    private fun drawHeaderBand() {
        canvas.drawRect(0f, 0f, pageW, 72f, fillNavy)
        logo?.let { canvas.drawBitmap(it, null, RectF(10f, 10f, 62f, 62f), bitmapPaint) }
        val textX = 10f + 52f + 10f
        pBrand.textAlign = Paint.Align.LEFT
        canvas.drawText("EQUIFY", textX, 31f, pBrand)
        pBrandSub.textAlign = Paint.Align.LEFT
        canvas.drawText(context.getString(R.string.pdf_tagline), textX, 49f, pBrandSub)
    }

    private fun drawFooter() {
        val footLineY = contentBottom + 15f
        borderPaint.color = lineColor
        borderPaint.strokeWidth = 0.5f
        canvas.drawLine(contentLeft, footLineY, contentRight, footLineY, borderPaint)
        val baseline = footLineY + 12f
        pFooter.textAlign = Paint.Align.LEFT
        canvas.drawText(context.getString(R.string.pdf_page, pageNumber), contentLeft, baseline, pFooter)
        pFooter.textAlign = Paint.Align.RIGHT
        canvas.drawText(context.getString(R.string.pdf_footer_generated), contentRight, baseline, pFooter)
    }

    // --- Secciones ---

    private fun drawIntro() {
        y = textLine(groupName, contentLeft, pTitle, Paint.Align.LEFT)
        y += 4f
        val dateStr = SimpleDateFormat("d MMM yyyy", Locale.getDefault()).format(Date())
        val countStr = context.resources.getQuantityString(R.plurals.pdf_costs_count, costs.size, costs.size)
        val generatedOn = context.getString(R.string.pdf_generated_on, dateStr)
        y = textLine("$countStr  ·  $generatedOn", contentLeft, pMeta, Paint.Align.LEFT)
        y += 16f
    }

    private fun drawStats() {
        val pad = 14f
        val statH = pad + lineHeight(pStatLabel) + 5f + lineHeight(pStatValueInk) + pad
        ensureSpace(statH + 22f)
        val divW = contentWidth * (0.02f / 2.02f)
        val boxW = contentWidth * (1f / 2.02f)
        drawStatBox(contentLeft, boxW, statH, pad, context.getString(R.string.pdf_total_spent), totalSpent.toMoneyDisplay(), pStatValueInk)
        canvas.drawRect(contentLeft + boxW, y, contentLeft + boxW + divW, y + statH, fillLine)
        drawStatBox(contentLeft + boxW + divW, boxW, statH, pad, context.getString(R.string.pdf_to_settle), totalSettle.toMoneyDisplay(), pStatValueOrange)
        y += statH + 22f
    }

    private fun drawStatBox(x: Float, w: Float, h: Float, pad: Float, label: String, value: String, valuePaint: Paint) {
        canvas.drawRect(x, y, x + w, y + h, fillCream)
        var top = y + pad
        pStatLabel.textAlign = Paint.Align.LEFT
        canvas.drawText(label, x + pad, top - pStatLabel.fontMetrics.top, pStatLabel)
        top += lineHeight(pStatLabel) + 5f
        valuePaint.textAlign = Paint.Align.LEFT
        canvas.drawText(value, x + pad, top - valuePaint.fontMetrics.top, valuePaint)
    }

    private fun drawSectionLabel(text: String) {
        y = textLine(text, contentLeft, pSection, Paint.Align.LEFT)
        y += 6f
    }

    // --- Tabla de gastos (2 columnas 3:1) ---

    private val costCol1W = contentWidth * 3f / 4f

    private fun drawCostsTable() {
        val rowH = 16f + lineHeight(pCell)
        ensureSpace(lineHeight(pSection) + 6f + headRowHeight() + rowH)
        drawSectionLabel(context.getString(R.string.pdf_costs_title))
        drawCostsHead()
        if (costs.isEmpty()) {
            drawEmptyRow(context.getString(R.string.pdf_no_costs))
        } else {
            costs.forEachIndexed { i, c ->
                ensureSpace(rowH) { drawCostsHead() }
                if (i % 2 == 1) canvas.drawRect(contentLeft, y, contentRight, y + rowH, fillRowAlt)
                val baseline = y + 8f - pCell.fontMetrics.top
                pCell.textAlign = Paint.Align.LEFT
                canvas.drawText(fit(c.description, pCell, costCol1W - 16f), contentLeft + 8f, baseline, pCell)
                pCellBold.textAlign = Paint.Align.RIGHT
                canvas.drawText(c.amount.toMoneyDisplay(), contentRight - 8f, baseline, pCellBold)
                y += rowH
            }
        }
        y += 20f
    }

    private fun drawCostsHead() {
        val h = headRowHeight()
        val baseline = y + 8f - pHead.fontMetrics.top
        pHead.textAlign = Paint.Align.LEFT
        canvas.drawText(context.getString(R.string.pdf_col_description), contentLeft + 8f, baseline, pHead)
        pHead.textAlign = Paint.Align.RIGHT
        canvas.drawText(context.getString(R.string.pdf_col_amount), contentRight - 8f, baseline, pHead)
        borderPaint.color = navy
        borderPaint.strokeWidth = 1.5f
        canvas.drawLine(contentLeft, y + h, contentRight, y + h, borderPaint)
        y += h
    }

    // --- Tabla de pagos (3 columnas 2:2:1) ---

    private val payCol1W = contentWidth * 2f / 5f
    private val payCol2W = contentWidth * 2f / 5f

    private fun drawPaymentsTable() {
        val rowH = 16f + lineHeight(pCell)
        ensureSpace(lineHeight(pSection) + 6f + headRowHeight() + rowH)
        drawSectionLabel(context.getString(R.string.pdf_payments_title))
        drawPaymentsHead()
        if (payments.isEmpty()) {
            drawEmptyRow(context.getString(R.string.pdf_no_debts))
        } else {
            payments.forEachIndexed { i, p ->
                ensureSpace(rowH) { drawPaymentsHead() }
                if (i % 2 == 1) canvas.drawRect(contentLeft, y, contentRight, y + rowH, fillRowAlt)
                val baseline = y + 8f - pCell.fontMetrics.top
                pCell.textAlign = Paint.Align.LEFT
                canvas.drawText(fit(p.namePersonWhoPay, pCell, payCol1W - 16f), contentLeft + 8f, baseline, pCell)
                canvas.drawText(fit(p.namePersonWhoReceive, pCell, payCol2W - 16f), contentLeft + payCol1W + 8f, baseline, pCell)
                pAmount.textAlign = Paint.Align.RIGHT
                canvas.drawText(p.amount.toMoneyDisplay(), contentRight - 8f, baseline, pAmount)
                y += rowH
            }
        }
        y += 20f
    }

    private fun drawPaymentsHead() {
        val h = headRowHeight()
        val baseline = y + 8f - pHead.fontMetrics.top
        pHead.textAlign = Paint.Align.LEFT
        canvas.drawText(context.getString(R.string.pdf_col_pays), contentLeft + 8f, baseline, pHead)
        canvas.drawText(context.getString(R.string.pdf_col_receives), contentLeft + payCol1W + 8f, baseline, pHead)
        pHead.textAlign = Paint.Align.RIGHT
        canvas.drawText(context.getString(R.string.pdf_col_quantity), contentRight - 8f, baseline, pHead)
        borderPaint.color = navy
        borderPaint.strokeWidth = 1.5f
        canvas.drawLine(contentLeft, y + h, contentRight, y + h, borderPaint)
        y += h
    }

    // --- Auxiliares de fila ---

    private fun headRowHeight(): Float = 16f + lineHeight(pHead)

    private fun drawEmptyRow(text: String) {
        val h = 20f + lineHeight(pEmpty)
        ensureSpace(h)
        pEmpty.textAlign = Paint.Align.CENTER
        canvas.drawText(text, (contentLeft + contentRight) / 2f, y + 10f - pEmpty.fontMetrics.top, pEmpty)
        y += h
    }
}
