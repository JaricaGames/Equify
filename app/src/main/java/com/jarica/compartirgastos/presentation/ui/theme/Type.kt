package com.jarica.compartirgastos.presentation.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jarica.compartirgastos.R


val rubik = FontFamily(
    Font(R.font.rubikmedium, FontWeight.Medium),
    Font(R.font.rubikextrabold, FontWeight.ExtraBold),
    Font(R.font.rubikbold, FontWeight.Bold),
    Font(R.font.rubiklight, FontWeight.Light),
    Font(R.font.rubikregular, FontWeight.Normal),
    Font(R.font.rubiksemibold, FontWeight.SemiBold),
    Font(R.font.rubikmediumitalic, FontWeight.Medium, FontStyle.Italic),
    Font(R.font.rubikextrabolditalic, FontWeight.ExtraBold, FontStyle.Italic),
    Font(R.font.rubikbolditalic, FontWeight.Bold, FontStyle.Italic),
    Font(R.font.rubiklightitalic, FontWeight.Light, FontStyle.Italic),
    Font(R.font.rubiksemibolditalic, FontWeight.SemiBold, FontStyle.Italic)
)


// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = rubik,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )


    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)