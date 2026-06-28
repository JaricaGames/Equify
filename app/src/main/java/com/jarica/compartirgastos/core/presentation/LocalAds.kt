package com.jarica.compartirgastos.core.presentation

import androidx.compose.runtime.compositionLocalOf

/**
 * Indica si el usuario ha comprado "quitar anuncios".
 *
 * Se provee una vez en la raíz de la navegación (NavigationWrapper) a partir del
 * estado de [com.jarica.compartirgastos.core.billing.BillingManager], y lo leen
 * todos los composables de anuncios para decidir si pintarse o no.
 *
 * Por defecto `false` (se muestran anuncios) hasta que se conozca el estado real.
 */
val LocalAdsRemoved = compositionLocalOf { false }
