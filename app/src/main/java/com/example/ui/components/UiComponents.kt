package com.example.ui.components

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Bolt
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Carpenter
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Construction
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FormatPaint
import androidx.compose.material.icons.filled.Handyman
import androidx.compose.material.icons.filled.HourglassTop
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Plumbing
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.SolarPower
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.R
import com.example.data.model.PakistanLocations
import com.example.data.model.ServiceCategoryEntity
import com.example.data.model.VerificationStatus
import com.example.data.model.WorkerWithStats
import com.example.data.util.PrivacyUtils
import com.example.i18n.AppLanguage
import com.example.i18n.LocalAppLanguage
import com.example.i18n.LocalAppStrings
import com.example.ui.theme.AlertRed
import com.example.ui.theme.AlertRedContainer
import com.example.ui.theme.EmblemAccentEmerald
import com.example.ui.theme.MintContainer
import com.example.ui.theme.PakistanGreenDeep
import com.example.ui.theme.VerifiedEmerald
import com.example.ui.theme.WarmGold
import com.example.ui.theme.WarmGoldContainer

fun categoryIconFor(iconKey: String): ImageVector = when (iconKey) {
    "bolt" -> Icons.Filled.Bolt
    "plumbing" -> Icons.Filled.Plumbing
    "ac_unit" -> Icons.Filled.AcUnit
    "construction" -> Icons.Filled.Construction
    "format_paint" -> Icons.Filled.FormatPaint
    "carpenter" -> Icons.Filled.Carpenter
    "cleaning_services" -> Icons.Filled.CleaningServices
    "kitchen" -> Icons.Filled.Kitchen
    "solar_power" -> Icons.Filled.SolarPower
    "build" -> Icons.Filled.Build
    else -> Icons.Filled.Handyman
}

@Composable
fun LanguageSelectorPill(
    selectedLanguage: AppLanguage,
    onSelect: (AppLanguage) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 2.dp,
        modifier = modifier
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(50))
            .testTag("language_selector")
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppLanguage.entries.forEach { lang ->
                val isSelected = lang == selectedLanguage
                Surface(
                    shape = RoundedCornerShape(50),
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .clip(RoundedCornerShape(50))
                        .clickable { onSelect(lang) }
                        .testTag("lang_chip_${lang.code}")
                ) {
                    Text(
                        text = lang.nativeLabel,
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun VerificationBadgeChip(
    statusName: String,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    val status = runCatching { VerificationStatus.valueOf(statusName) }.getOrDefault(VerificationStatus.UNVERIFIED)
    val (bg, fg, borderCol, icon, label) = when (status) {
        VerificationStatus.VERIFIED -> Quint(
            MintContainer,
            PakistanGreenDeep,
            EmblemAccentEmerald.copy(alpha = 0.85f),
            Icons.Filled.VerifiedUser,
            strings.verifiedIdBadge
        )
        VerificationStatus.PENDING_REVIEW -> Quint(
            WarmGoldContainer,
            WarmGold,
            WarmGold.copy(alpha = 0.35f),
            Icons.Filled.HourglassTop,
            strings.verificationPendingBadge
        )
        VerificationStatus.REJECTED -> Quint(
            AlertRedContainer,
            AlertRed,
            AlertRed.copy(alpha = 0.35f),
            Icons.Filled.Shield,
            strings.rejectedBadge
        )
        VerificationStatus.UNVERIFIED -> Quint(
            MaterialTheme.colorScheme.surfaceVariant,
            MaterialTheme.colorScheme.onSurfaceVariant,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
            Icons.Outlined.Info,
            strings.unverifiedBadge
        )
    }

    Surface(
        shape = RoundedCornerShape(50),
        color = bg,
        modifier = modifier.border(1.dp, borderCol, RoundedCornerShape(50))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = fg,
                modifier = Modifier.size(13.dp)
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = fg,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun SamplePreviewChip(modifier: Modifier = Modifier) {
    val strings = LocalAppStrings.current
    Surface(
        shape = RoundedCornerShape(50),
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = modifier.border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(50))
    ) {
        Text(
            text = strings.samplePreviewBadge,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

@Composable
fun WorkerAvatar(
    photoUri: String,
    name: String,
    sizeDp: Int = 56,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .size(sizeDp.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(1.5.dp, MaterialTheme.colorScheme.outline, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (photoUri.isNotBlank()) {
            AsyncImage(
                model = photoUri,
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.img_default_avatar),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

@Composable
fun FavoriteHeartToggleButton(
    isFavorite: Boolean,
    onToggle: () -> Unit,
    tag: String,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    val heartScale by animateFloatAsState(
        targetValue = if (isFavorite) 1.12f else 1f,
        animationSpec = spring(dampingRatio = 0.5f, stiffness = 500f),
        label = "favorite_heart_scale"
    )
    IconButton(
        onClick = onToggle,
        modifier = modifier
            .minimumInteractiveComponentSize()
            .testTag(tag)
    ) {
        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .background(
                    if (isFavorite) AlertRedContainer.copy(alpha = 0.85f)
                    else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = if (isFavorite) strings.removeFromFavoritesDesc else strings.saveToFavoritesDesc,
                tint = if (isFavorite) AlertRed else MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier
                    .size(20.dp)
                    .scale(heartScale)
            )
        }
    }
}

@Composable
fun WorkerListCard(
    item: WorkerWithStats,
    categories: List<ServiceCategoryEntity>,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isFavorite: Boolean = false,
    onToggleFavorite: (() -> Unit)? = null
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    val worker = item.worker

    val categoryLabels = worker.categoryKeys.mapNotNull { key ->
        categories.firstOrNull { it.key == key }?.localizedName(language)
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .testTag("worker_card_${worker.id}")
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                WorkerAvatar(photoUri = worker.profilePhotoUri, name = worker.fullName, sizeDp = 56)

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = worker.fullName,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        if (worker.isDemoData) {
                            SamplePreviewChip()
                        }
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = categoryLabels.joinToString(" • "),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(14.dp)
                        )
                        Text(
                            text = PakistanLocations.formatWorkerLocation(
                                provinceId = worker.provinceId,
                                districtId = worker.districtId,
                                talukaId = worker.talukaId,
                                areaFallback = worker.area,
                                cityFallback = worker.city,
                                language = language
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                if (onToggleFavorite != null) {
                    FavoriteHeartToggleButton(
                        isFavorite = isFavorite,
                        onToggle = onToggleFavorite,
                        tag = "favorite_toggle_${worker.id}"
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                VerificationBadgeChip(statusName = worker.cnicVerificationStatus)

                // Honest rating display: never show fake ratings!
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = if (item.reviewCount > 0) WarmGold else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(16.dp)
                    )
                    Text(
                        text = if (item.reviewCount > 0) {
                            "${"%.1f".format(item.averageRating)} (${item.reviewCount})"
                        } else {
                            strings.noReviewsYet
                        },
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val availColor = if (worker.isAvailable) VerifiedEmerald else MaterialTheme.colorScheme.onSurfaceVariant
                    Icon(
                        imageVector = Icons.Filled.CheckCircle,
                        contentDescription = null,
                        tint = availColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = if (worker.isAvailable) strings.availableNow else strings.busyUnavailable,
                        style = MaterialTheme.typography.labelSmall,
                        color = availColor
                    )
                    Text(
                        text = "• ${worker.experienceYears} ${strings.yearsExpSuffix}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Text(
                    text = "${strings.pkrPrefix} ${worker.basePricePkr} ${strings.perVisit}",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

fun launchDialer(context: Context, dialablePhone: String, onUnavailable: () -> Unit) {
    val clean = dialablePhone.filter { it.isDigit() || it == '+' }
    if (clean.isBlank()) {
        onUnavailable()
        return
    }
    try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$clean"))
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        onUnavailable()
    }
}

fun launchWhatsApp(context: Context, dialablePhone: String, workerName: String, onUnavailable: () -> Unit) {
    val intl = PrivacyUtils.toInternationalPkPhone(dialablePhone)
    if (intl.isBlank()) {
        onUnavailable()
        return
    }
    val msg = Uri.encode("Assalam-o-Alaikum $workerName, I found your profile on Kaam Pakistan.")
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$intl?text=$msg"))
        context.startActivity(intent)
    } catch (_: ActivityNotFoundException) {
        onUnavailable()
    }
}

fun launchMapsPin(
    context: Context,
    latitude: Double,
    longitude: Double,
    label: String,
    onUnavailable: () -> Unit
) {
    val encodedLabel = Uri.encode(label.ifBlank { "Kaam Pakistan Location" })
    val geoUri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude($encodedLabel)")
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, geoUri))
    } catch (_: ActivityNotFoundException) {
        try {
            val webUri = Uri.parse("https://maps.google.com/?q=$latitude,$longitude")
            context.startActivity(Intent(Intent.ACTION_VIEW, webUri))
        } catch (_: ActivityNotFoundException) {
            onUnavailable()
        }
    }
}

private data class Quint<A, B, C, D, E>(
    val first: A,
    val second: B,
    val third: C,
    val fourth: D,
    val fifth: E
)
