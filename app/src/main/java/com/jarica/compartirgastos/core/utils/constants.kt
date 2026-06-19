package com.jarica.compartirgastos.core.utils

import com.jarica.compartirgastos.BuildConfig

const val ID_GROUP_SAVED = "idGroupSaved"
const val PREFERENCES_NAME = "preferences_name"
const val TAG = "PdfCreator"

//email
const val EMAIL_DIRECTION = "jarica.games@gmail.com"
const val EMAIL_SUBJECT = "FeedBack"

//Table Names
const val PEOPLE_TABLE = "peopleTable"
const val GROUPS_NAME_TABLE = "groupNameTable"
const val COSTS_TABLE = "costsTable"
const val PAYMENTS_TABLE = "paymentsTable"
const val DISTRIBUTION_COST_TABLE = "distributionCostTable"
const val DISTRIBUTION_PAYMENT_TABLE = "distributionPaymentCostTable"
const val PAYMENTS_BETWEEN_PERSONS_TABLE = "PaymentsBetweenPersonsTable"

//Admob
const val TAG_ADMOB = "AdViewModel"

/**
 * IDs de unidades de anuncio de AdMob.
 *
 * En debug se usan SIEMPRE los IDs de prueba oficiales de Google para no generar
 * tráfico inválido (que puede suspender la cuenta de AdMob). En release se usan
 * los IDs reales.
 *
 * Doc: https://developers.google.com/admob/android/test-ads
 */
object AdIds {
    // IDs de prueba oficiales de Google (no tocar).
    private const val TEST_BANNER = "ca-app-pub-3940256099942544/6300978111"
    private const val TEST_INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712"

    // IDs reales de producción.
    private const val PROD_BANNER = "ca-app-pub-4979320410432560/4688560090"
    private const val PROD_BANNER_MAIN_SCREEN = "ca-app-pub-4979320410432560/4377528313"
    private const val PROD_BANNER_GROUPS_SCREEN = "ca-app-pub-4979320410432560/4688560090"
    private const val PROD_INTERSTITIAL = "ca-app-pub-4979320410432560/2157781438"

    val banner: String get() = if (BuildConfig.DEBUG) TEST_BANNER else PROD_BANNER
    val bannerMainScreen: String get() = if (BuildConfig.DEBUG) TEST_BANNER else PROD_BANNER_MAIN_SCREEN
    val bannerGroupsScreen: String get() = if (BuildConfig.DEBUG) TEST_BANNER else PROD_BANNER_GROUPS_SCREEN
    val interstitial: String get() = if (BuildConfig.DEBUG) TEST_INTERSTITIAL else PROD_INTERSTITIAL
}

//SplashScreen
const val SPLASHSCREEN_DURATION = 1500L

// UI Constants
const val HEADER_WEIGHT = 0.15f

