package com.jarica.compartirgastos.core.utils

import kotlin.math.absoluteValue
import kotlin.math.roundToLong

/**
 * El dinero se almacena y opera como [Long] en céntimos (10,50 € -> 1050) para evitar
 * los errores de precisión de Float/Double en operaciones financieras.
 */

/** Parsea texto del usuario ("12,50", "12.5", "12") a céntimos. Devuelve null si no es válido. */
fun String.toCentsOrNull(): Long? {
    val normalized = trim().replace(',', '.')
    if (normalized.isEmpty()) return null
    val value = normalized.toDoubleOrNull() ?: return null
    if (value < 0) return null
    return (value * 100).roundToLong()
}

/** Formatea céntimos a "12,50" (sin símbolo), con coma decimal. */
fun Long.toMoneyString(): String {
    val sign = if (this < 0) "-" else ""
    val abs = this.absoluteValue
    return "%s%d,%02d".format(sign, abs / 100, abs % 100)
}

/** Formatea céntimos a "12,50 €". */
fun Long.toMoneyDisplay(): String = "${toMoneyString()} €"

/**
 * Reparte un total en [n] partes lo más iguales posible distribuyendo el resto:
 * splitEvenly(1000, 3) -> [334, 333, 333]. La suma siempre cuadra con el total exacto.
 */
fun splitEvenly(total: Long, n: Int): List<Long> {
    if (n <= 0) return emptyList()
    val base = total / n
    val remainder = (total % n).toInt()
    return List(n) { index -> base + if (index < remainder) 1 else 0 }
}
