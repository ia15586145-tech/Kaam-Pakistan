package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

private val LightColorScheme = lightColorScheme(
    primary = PakistanGreenDark,
    onPrimary = PureWhite,
    primaryContainer = MintContainer,
    onPrimaryContainer = PakistanGreenDeep,
    secondary = PakistanGreenLight,
    onSecondary = PureWhite,
    secondaryContainer = MintSurface,
    onSecondaryContainer = PakistanGreenDark,
    tertiary = WarmGold,
    onTertiary = PureWhite,
    tertiaryContainer = WarmGoldContainer,
    onTertiaryContainer = Color(0xFF78350F),
    background = MintSurface,
    onBackground = InkPrimary,
    surface = PureWhite,
    onSurface = InkPrimary,
    surfaceVariant = MintContainer,
    onSurfaceVariant = InkSecondary,
    outline = OutlineMint,
    error = AlertRed,
    onError = PureWhite,
    errorContainer = AlertRedContainer,
    onErrorContainer = Color(0xFF7F1D1D)
)

private val DarkColorScheme = darkColorScheme(
    primary = DarkMintAccent,
    onPrimary = PakistanGreenDeep,
    primaryContainer = DarkMintContainer,
    onPrimaryContainer = MintContainer,
    secondary = EmblemAccentEmerald,
    onSecondary = PakistanGreenDeep,
    secondaryContainer = DarkSurfaceCard,
    onSecondaryContainer = DarkMintAccent,
    tertiary = Color(0xFFFBBF24),
    onTertiary = Color(0xFF451A03),
    background = DarkSurfaceBg,
    onBackground = PureWhite,
    surface = DarkSurfaceCard,
    onSurface = PureWhite,
    surfaceVariant = DarkMintContainer,
    onSurfaceVariant = Color(0xFFBDF2CF),
    outline = DarkOutline,
    error = Color(0xFFF87171),
    onError = Color(0xFF450A0A)
)

val KaamShapes = Shapes(
    small = RoundedCornerShape(10.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(28.dp)
)

@Composable
fun KaamPakistanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        shapes = KaamShapes,
        content = content
    )
}
