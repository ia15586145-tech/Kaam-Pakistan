package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.ui.theme.EmblemAccentEmerald
import com.example.ui.theme.EmblemGlowMint
import com.example.ui.theme.PakistanGreenDark
import com.example.ui.theme.PakistanGreenDeep
import com.example.ui.theme.PureWhite

@Composable
fun SplashScreen(modifier: Modifier = Modifier) {
    var animateIn by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        animateIn = true
    }

    val emblemScale by animateFloatAsState(
        targetValue = if (animateIn) 1f else 0.90f,
        animationSpec = tween(durationMillis = 550, easing = FastOutSlowInEasing),
        label = "splash_emblem_scale"
    )
    val emblemAlpha by animateFloatAsState(
        targetValue = if (animateIn) 1f else 0f,
        animationSpec = tween(durationMillis = 450),
        label = "splash_emblem_alpha"
    )

    BoxWithConstraints(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF04331A),
                        PakistanGreenDeep,
                        PakistanGreenDark,
                        PakistanGreenDeep
                    )
                )
            )
            .windowInsetsPadding(WindowInsets.safeDrawing)
            .padding(24.dp)
            .testTag("splash_screen"),
        contentAlignment = Alignment.Center
    ) {
        val emblemSize = minOf(maxWidth * 0.66f, 260.dp)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .scale(emblemScale)
                    .alpha(emblemAlpha)
            ) {
                // Soft ambient radial glow behind the emblem
                Box(
                    modifier = Modifier
                        .size(emblemSize + 26.dp)
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    EmblemGlowMint.copy(alpha = 0.22f),
                                    Color.Transparent
                                )
                            )
                        )
                )
                Image(
                    painter = painterResource(id = R.drawable.img_kaam_logo_fg),
                    contentDescription = "KAAM PAKISTAN — Find Work. Find Workers.",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(emblemSize)
                        .clip(RoundedCornerShape(36.dp))
                        .testTag("splash_emblem")
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Gracefully centered loading progress indicator beneath emblem
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(14.dp),
                modifier = Modifier
                    .alpha(emblemAlpha)
                    .testTag("splash_loading_indicator")
            ) {
                CircularProgressIndicator(
                    color = EmblemGlowMint,
                    trackColor = PureWhite.copy(alpha = 0.18f),
                    strokeWidth = 3.dp,
                    modifier = Modifier.size(30.dp)
                )
                LinearProgressIndicator(
                    color = EmblemAccentEmerald,
                    trackColor = PureWhite.copy(alpha = 0.16f),
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .width(136.dp)
                        .height(4.dp)
                        .clip(RoundedCornerShape(50))
                )
            }
        }
    }
}
