package com.jarica.compartirgastos.features.balances.domain.balancesUseCases

import com.jarica.compartirgastos.core.domain.models.PaymentsToDoCountsModel
import com.jarica.compartirgastos.core.domain.models.PersonBalance
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests del algoritmo de liquidación de deudas.
 *
 * Convención de signos: balance > 0 => la persona paga (deudor);
 * balance < 0 => la persona cobra (acreedor). Todos los importes en céntimos (Long).
 */
class DoTheCountsUseCaseTest {

    private val useCase = DoTheCountsUseCase()

    private fun person(id: String, balance: Long) =
        PersonBalance(idPerson = id, balance = balance, name = id)

    /** Suma de todo lo que cada persona paga/cobra según la lista de pagos generada. */
    private fun netPerPerson(payments: List<PaymentsToDoCountsModel>): Map<String, Long> {
        val net = mutableMapOf<String, Long>()
        payments.forEach { p ->
            net[p.idPersonWhoPay] = (net[p.idPersonWhoPay] ?: 0L) + p.amount
            net[p.idPersonWhoReceive] = (net[p.idPersonWhoReceive] ?: 0L) - p.amount
        }
        return net
    }

    @Test
    fun `lista vacia no genera pagos`() {
        assertTrue(useCase(emptyList()).isEmpty())
    }

    @Test
    fun `todos los balances a cero no genera pagos`() {
        val balances = listOf(person("A", 0L), person("B", 0L), person("C", 0L))
        assertTrue(useCase(balances).isEmpty())
    }

    @Test
    fun `dos personas se liquidan con un solo pago`() {
        val balances = listOf(person("A", 1000L), person("B", -1000L))

        val payments = useCase(balances)

        assertEquals(1, payments.size)
        val p = payments.first()
        assertEquals("A", p.idPersonWhoPay)
        assertEquals("B", p.idPersonWhoReceive)
        assertEquals(1000L, p.amount)
    }

    @Test
    fun `un deudor frente a dos acreedores reparte el importe exacto`() {
        val balances = listOf(person("A", 1000L), person("B", -600L), person("C", -400L))

        val payments = useCase(balances)

        // A debe pagar 1000 en total, repartido entre B (600) y C (400).
        assertEquals(1000L, payments.sumOf { it.amount })
        assertTrue(payments.all { it.idPersonWhoPay == "A" })
        assertEquals(600L, payments.first { it.idPersonWhoReceive == "B" }.amount)
        assertEquals(400L, payments.first { it.idPersonWhoReceive == "C" }.amount)
    }

    @Test
    fun `varios deudores y acreedores quedan completamente liquidados`() {
        val balances = listOf(
            person("A", 700L),
            person("B", 300L),
            person("C", -600L),
            person("D", -400L),
        )

        val payments = useCase(balances)

        // Cada persona acaba con su deuda saldada: lo que paga (o cobra) iguala su balance.
        val net = netPerPerson(payments)
        balances.forEach { b ->
            assertEquals("Persona ${b.idPerson} mal liquidada", b.balance, net[b.idPerson] ?: 0L)
        }
    }

    @Test
    fun `el total pagado iguala la deuda total`() {
        val balances = listOf(
            person("A", 1250L),
            person("B", 750L),
            person("C", -1000L),
            person("D", -1000L),
        )

        val payments = useCase(balances)

        val totalDeuda = balances.filter { it.balance > 0 }.sumOf { it.balance }
        assertEquals(totalDeuda, payments.sumOf { it.amount })
    }

    @Test
    fun `el numero de pagos nunca supera n menos 1`() {
        val balances = listOf(
            person("A", 500L),
            person("B", 300L),
            person("C", 200L),
            person("D", -400L),
            person("E", -600L),
        )

        val payments = useCase(balances)

        // Propiedad del algoritmo greedy: en cada paso se salda al menos una persona.
        assertTrue(payments.size <= balances.size - 1)
        val net = netPerPerson(payments)
        balances.forEach { b -> assertEquals(b.balance, net[b.idPerson] ?: 0L) }
    }

    @Test
    fun `ignora personas con balance cero`() {
        val balances = listOf(
            person("A", 1000L),
            person("B", 0L),
            person("C", -1000L),
        )

        val payments = useCase(balances)

        assertTrue(payments.none { it.idPersonWhoPay == "B" || it.idPersonWhoReceive == "B" })
        val net = netPerPerson(payments)
        assertEquals(1000L, net["A"])
        assertEquals(-1000L, net["C"])
    }
}
