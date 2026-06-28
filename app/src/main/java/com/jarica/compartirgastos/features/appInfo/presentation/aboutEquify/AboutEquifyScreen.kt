package com.jarica.compartirgastos.features.appInfo.presentation.aboutEquify

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R
import com.jarica.compartirgastos.core.presentation.LocalAdsRemoved
import com.jarica.compartirgastos.core.presentation.ui.aboutAuthorMessage
import com.jarica.compartirgastos.core.presentation.ui.aboutAuthorName
import com.jarica.compartirgastos.core.presentation.ui.aboutAuthorRole
import com.jarica.compartirgastos.core.presentation.ui.aboutFeedbackLabel
import com.jarica.compartirgastos.core.presentation.ui.aboutFeedbackSub
import com.jarica.compartirgastos.core.presentation.ui.aboutFooterText
import com.jarica.compartirgastos.core.presentation.ui.aboutImproveEyebrow
import com.jarica.compartirgastos.core.presentation.ui.aboutLegalEyebrow
import com.jarica.compartirgastos.core.presentation.ui.aboutPrivacyLabel
import com.jarica.compartirgastos.core.presentation.ui.aboutProActiveLabel
import com.jarica.compartirgastos.core.presentation.ui.aboutProActiveSub
import com.jarica.compartirgastos.core.presentation.ui.aboutProEyebrow
import com.jarica.compartirgastos.core.presentation.ui.aboutRateGooglePlayText
import com.jarica.compartirgastos.core.presentation.ui.aboutRemoveAdsLabel
import com.jarica.compartirgastos.core.presentation.ui.aboutRemoveAdsSub
import com.jarica.compartirgastos.core.presentation.ui.aboutRestorePurchaseLabel
import com.jarica.compartirgastos.core.presentation.ui.aboutScreenText
import com.jarica.compartirgastos.core.presentation.ui.aboutShareLabel
import com.jarica.compartirgastos.core.presentation.ui.aboutShareSub
import com.jarica.compartirgastos.core.presentation.ui.aboutSubtitle
import com.jarica.compartirgastos.core.presentation.ui.aboutTermsLabel
import com.jarica.compartirgastos.core.presentation.ui.aboutVersionLabel
import com.jarica.compartirgastos.core.presentation.ui.appName
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkBlue
import com.jarica.compartirgastos.core.presentation.ui.theme.DarkOrange
import com.jarica.compartirgastos.core.presentation.ui.theme.White
import com.jarica.compartirgastos.core.presentation.ui.theme.parkinsans
import com.jarica.compartirgastos.core.utils.EMAIL_DIRECTION
import com.jarica.compartirgastos.core.utils.EMAIL_SUBJECT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private val LineColor   = Color(0xFFE6E4DE)
private val MutedColor  = Color(0xFF6B7A86)
private val InkColor    = Color(0xFF1F2A33)
private val CreamBg     = Color(0xFFF6F1E8)
private val CreamBorder = Color(0xFFECE5D6)
private val NavySoft    = Color(0xFFF0F3F6)
private val StarFilled  = Color(0xFFF2C14E)
private val StarEmpty   = Color(0xFFD5D2CB)

@Composable
fun AboutEquifyScreen(
    navigateBack: () -> Unit,
    navigateToPrivacy: () -> Unit,
    navigateToTerms: () -> Unit,
    aboutScreenViewModel: AboutEquifyScreenViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        aboutScreenViewModel.event.collect { event ->
            when (event) {
                AboutEquifyScreenViewModel.UiEvent.SendEmail -> {
                    withContext(Dispatchers.Main) {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf(EMAIL_DIRECTION))
                            putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT)
                            setPackage("com.google.android.gm")
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
    ) {
        AboutHeader(onBack = navigateBack)

        // Hero row: icon + title + version + stars
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp)
                .padding(top = 18.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Image(
                painter            = painterResource(R.drawable.equify_icon),
                contentDescription = null,
                contentScale       = ContentScale.Crop,
                modifier           = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Column(verticalArrangement = Arrangement.spacedBy(3.dp)) {
                Text(
                    text          = appName,
                    fontSize      = 22.sp,
                    fontWeight    = FontWeight.Bold,
                    color         = InkColor,
                    fontFamily    = parkinsans,
                    letterSpacing = (-0.02).em
                )
                Text(
                    text       = aboutVersionLabel,
                    fontSize   = 12.sp,
                    color      = MutedColor,
                    fontFamily = parkinsans
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    repeat(5) { i ->
                        Icon(
                            imageVector        = Icons.Default.Star,
                            contentDescription = null,
                            tint               = StarFilled,
                            modifier           = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }

        // Author note card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 6.dp, bottom = 6.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(CreamBg)
                .border(1.dp, CreamBorder, RoundedCornerShape(18.dp))
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    modifier         = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF4A7DAA)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text       = "J",
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color      = White,
                        fontFamily = parkinsans
                    )
                }
                Column {
                    Text(
                        text       = aboutAuthorName,
                        fontSize   = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = InkColor,
                        fontFamily = parkinsans
                    )
                    Text(
                        text       = aboutAuthorRole,
                        fontSize   = 11.sp,
                        color      = MutedColor,
                        fontFamily = parkinsans
                    )
                }
            }
            Text(
                text       = aboutAuthorMessage,
                fontSize   = 13.sp,
                lineHeight = 20.sp,
                color      = InkColor,
                fontFamily = parkinsans
            )
        }

        // CTA — Play Store rating (link pending)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
                .clip(RoundedCornerShape(18.dp))
                .background(DarkOrange)
                .clickable { openPlayStore(context) }
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    imageVector        = Icons.Default.Star,
                    contentDescription = null,
                    tint               = StarFilled,
                    modifier           = Modifier.size(18.dp)
                )
                Text(
                    text          = aboutRateGooglePlayText,
                    fontSize      = 15.sp,
                    fontWeight    = FontWeight.SemiBold,
                    color         = White,
                    fontFamily    = parkinsans,
                    letterSpacing = (-0.005).em
                )
            }
        }

        ProSection(
            adsRemoved = LocalAdsRemoved.current,
            onRemoveAds = {
                context.findActivity()?.let { aboutScreenViewModel.onRemoveAdsClicked(it) }
            },
            onRestore = { aboutScreenViewModel.onRestorePurchasesClicked() }
        )

        AboutEyebrow(text = aboutImproveEyebrow)
        AboutGroupList {
            AboutRow(
                icon     = R.drawable.feedback,
                iconBg   = Color(0xFFFFE9DD),
                iconTint = Color(0xFFB73A1E),
                title    = aboutFeedbackLabel,
                subtitle = aboutFeedbackSub,
                onClick  = { aboutScreenViewModel.onFeedbackClicked() }
            )
            Box(Modifier.fillMaxWidth().height(1.dp).background(LineColor))
            AboutRow(
                icon     = R.drawable.exit,
                iconBg   = NavySoft,
                iconTint = DarkBlue,
                title    = aboutShareLabel,
                subtitle = aboutShareSub,
                onClick  = { shareApp(context) }
            )
        }

        AboutEyebrow(text = aboutLegalEyebrow)
        AboutGroupList {
            AboutRow(
                icon     = R.drawable.information,
                iconBg   = NavySoft,
                iconTint = DarkBlue,
                title    = aboutPrivacyLabel,
                onClick  = { navigateToPrivacy() }
            )
            Box(Modifier.fillMaxWidth().height(1.dp).background(LineColor))
            AboutRow(
                icon     = R.drawable.list_paper,
                iconBg   = NavySoft,
                iconTint = DarkBlue,
                title    = aboutTermsLabel,
                onClick  = { navigateToTerms() }
            )
        }

        Text(
            text      = aboutFooterText,
            fontSize  = 10.sp,
            color     = MutedColor.copy(alpha = 0.7f),
            fontFamily = parkinsans,
            letterSpacing = 0.08.em,
            textAlign = TextAlign.Center,
            modifier  = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp)
        )

        Spacer(Modifier.navigationBarsPadding())
    }
}

/**
 * Abre la ficha de la app en Google Play. Usa el packageName en tiempo de
 * ejecución. Intenta primero la app de Play Store (market://) y cae al navegador
 * si no está.
 */
private fun openPlayStore(context: Context) {
    val packageName = context.packageName
    val marketIntent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("market://details?id=$packageName")
    )
    try {
        context.startActivity(marketIntent)
    } catch (e: ActivityNotFoundException) {
        context.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

/**
 * Comparte la app vía el selector del sistema (ACTION_SEND). El enlace se
 * construye con el packageName en tiempo de ejecución.
 */
private fun shareApp(context: Context) {
    val url = "https://play.google.com/store/apps/details?id=${context.packageName}"
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, context.getString(R.string.about_share_message, url))
    }
    context.startActivity(
        Intent.createChooser(sendIntent, context.getString(R.string.about_share_chooser))
    )
}

/**
 * Sección de compra "quitar anuncios". Si el usuario ya la compró, muestra un
 * estado de agradecimiento; si no, muestra el CTA de compra y un enlace para
 * restaurar la compra (tras reinstalar o cambiar de dispositivo).
 */
@Composable
private fun ProSection(
    adsRemoved: Boolean,
    onRemoveAds: () -> Unit,
    onRestore: () -> Unit
) {
    AboutEyebrow(text = aboutProEyebrow)

    if (adsRemoved) {
        AboutGroupList {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 13.dp),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color(0xFFFFF3D6)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector        = Icons.Default.Star,
                        contentDescription = null,
                        tint               = StarFilled,
                        modifier           = Modifier.size(18.dp)
                    )
                }
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text       = aboutProActiveLabel,
                        fontSize   = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color      = InkColor,
                        fontFamily = parkinsans
                    )
                    Text(
                        text       = aboutProActiveSub,
                        fontSize   = 12.sp,
                        color      = MutedColor,
                        fontFamily = parkinsans
                    )
                }
            }
        }
        return
    }

    // CTA de compra
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(18.dp))
            .background(DarkBlue)
            .clickable { onRemoveAds() }
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(White.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector        = Icons.Default.Star,
                    contentDescription = null,
                    tint               = StarFilled,
                    modifier           = Modifier.size(20.dp)
                )
            }
            Column(
                modifier            = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Text(
                    text       = aboutRemoveAdsLabel,
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color      = White,
                    fontFamily = parkinsans
                )
                Text(
                    text       = aboutRemoveAdsSub,
                    fontSize   = 12.sp,
                    lineHeight = 16.sp,
                    color      = White.copy(alpha = 0.75f),
                    fontFamily = parkinsans
                )
            }
        }
    }

    // Restaurar compra
    Text(
        text          = aboutRestorePurchaseLabel,
        fontSize      = 12.sp,
        fontWeight    = FontWeight.SemiBold,
        color         = DarkBlue,
        fontFamily    = parkinsans,
        textAlign     = TextAlign.Center,
        modifier      = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
            .clickable { onRestore() }
            .padding(vertical = 6.dp)
    )
}

/** Encuentra la Activity a partir del Context envuelto por Compose. */
private fun Context.findActivity(): Activity? {
    var ctx: Context = this
    while (ctx is ContextWrapper) {
        if (ctx is Activity) return ctx
        ctx = ctx.baseContext
    }
    return null
}

@Composable
private fun AboutHeader(onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 22.dp, bottomEnd = 22.dp))
            .drawBehind {
                drawRect(DarkBlue)
                val side = 140.dp.toPx()
                val half = side / 2f
                val cx   = size.width - 40.dp.toPx()
                val cy   = size.height - 40.dp.toPx()
                withTransform({ rotate(degrees = 45f, pivot = Offset(cx, cy)) }) {
                    drawRoundRect(
                        color        = DarkOrange,
                        topLeft      = Offset(cx - half, cy - half),
                        size         = Size(side, side),
                        cornerRadius = CornerRadius(6.dp.toPx()),
                        alpha        = 0.95f
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
                modifier              = Modifier.fillMaxWidth(),
                verticalAlignment     = Alignment.CenterVertically,
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
                        painter            = painterResource(R.drawable.arrow_back),
                        contentDescription = "",
                        tint               = White,
                        modifier           = Modifier.size(22.dp)
                    )
                }
                Text(
                    text       = appName,
                    fontSize   = 13.sp,
                    color      = White.copy(alpha = 0.7f),
                    fontWeight = FontWeight.Medium,
                    fontFamily = parkinsans
                )
                Spacer(modifier = Modifier.size(36.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text          = aboutScreenText,
                fontSize      = 30.sp,
                fontWeight    = FontWeight.Bold,
                color         = White,
                fontFamily    = parkinsans,
                letterSpacing = (-0.02).em
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text       = aboutSubtitle,
                fontSize   = 13.sp,
                color      = White.copy(alpha = 0.75f),
                fontFamily = parkinsans
            )
        }
    }
}

@Composable
private fun AboutEyebrow(text: String) {
    Text(
        text          = text.uppercase(),
        fontSize      = 11.sp,
        letterSpacing = 0.1.em,
        color         = MutedColor,
        fontWeight    = FontWeight.SemiBold,
        fontFamily    = parkinsans,
        modifier      = Modifier
            .fillMaxWidth()
            .padding(horizontal = 18.dp)
            .padding(top = 18.dp, bottom = 8.dp)
    )
}

@Composable
private fun AboutGroupList(content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, LineColor, RoundedCornerShape(16.dp))
            .background(White),
        content = content
    )
}

@Composable
private fun AboutRow(
    @DrawableRes icon: Int,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 14.dp, vertical = 13.dp),
        verticalAlignment     = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(iconBg),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter            = painterResource(icon),
                contentDescription = null,
                tint               = iconTint,
                modifier           = Modifier.size(16.dp)
            )
        }
        Column(
            modifier            = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text       = title,
                fontSize   = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color      = InkColor,
                fontFamily = parkinsans
            )
            if (subtitle != null) {
                Text(
                    text       = subtitle,
                    fontSize   = 12.sp,
                    color      = MutedColor,
                    fontFamily = parkinsans
                )
            }
        }
        Icon(
            painter            = painterResource(R.drawable.right_arrow),
            contentDescription = null,
            tint               = MutedColor.copy(alpha = 0.4f),
            modifier           = Modifier.size(16.dp)
        )
    }
}
