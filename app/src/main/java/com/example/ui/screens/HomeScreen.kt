package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.R
import com.example.data.model.ServiceCategoryEntity
import com.example.data.model.WorkerWithStats
import com.example.i18n.AppLanguage
import com.example.i18n.LocalAppLanguage
import com.example.i18n.LocalAppStrings
import com.example.ui.KaamUiState
import com.example.ui.components.CascadingLocationBar
import com.example.ui.components.FavoriteHeartToggleButton
import com.example.ui.components.LanguageSelectorPill
import com.example.ui.components.SamplePreviewChip
import com.example.ui.components.VerificationBadgeChip
import com.example.ui.components.WorkerAvatar
import com.example.ui.components.WorkerListCard
import com.example.ui.components.categoryIconFor
import com.example.ui.theme.AlertRed
import com.example.ui.theme.AlertRedContainer
import com.example.ui.theme.EmblemAccentEmerald
import com.example.ui.theme.EmblemGlowMint
import com.example.ui.theme.MintContainer
import com.example.ui.theme.PakistanGreenDark
import com.example.ui.theme.PakistanGreenDeep
import com.example.ui.theme.PureWhite
import com.example.ui.theme.WarmGold

@Composable
fun HomeScreen(
    uiState: KaamUiState,
    onLanguageSelected: (AppLanguage) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onLocationFilterSelected: (provinceId: String?, divisionId: String?, districtId: String?, talukaId: String?) -> Unit,
    onCategorySelected: (String?) -> Unit,
    onClearAllFilters: () -> Unit = {},
    onToggleSamplePreview: (Boolean) -> Unit,
    onToggleFavorite: (String) -> Unit = {},
    onToggleFavoritesOnly: (Boolean) -> Unit = {},
    onWorkerClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    val enabledCategories = uiState.categories.filter { it.isEnabled }
    val favoriteIdsSet = remember(uiState.favoriteWorkerIds) { uiState.favoriteWorkerIds.toSet() }
    var showSkillPickerModal by remember { mutableStateOf(false) }
    var isFilterBarExpanded by remember { mutableStateOf(true) }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("home_feed_list"),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Hero Header Banner + Language Switcher
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PakistanGreenDeep, PakistanGreenDark)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                        val wideHeader = maxWidth >= 520.dp
                        if (wideHeader) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                BrandHeaderIdentity(
                                    tagline = strings.tagline,
                                    modifier = Modifier.weight(1f)
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                LanguageSelectorPill(
                                    selectedLanguage = language,
                                    onSelect = onLanguageSelected
                                )
                            }
                        } else {
                            Column(
                                modifier = Modifier.fillMaxWidth(),
                                verticalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                BrandHeaderIdentity(
                                    tagline = strings.tagline,
                                    modifier = Modifier.fillMaxWidth()
                                )
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    LanguageSelectorPill(
                                        selectedLanguage = language,
                                        onSelect = onLanguageSelected
                                    )
                                }
                            }
                        }
                    }

                    // Illustrated Hero Card with glowing emblem accent border
                    Card(
                        shape = RoundedCornerShape(20.dp),
                        colors = CardDefaults.cardColors(containerColor = PureWhite),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.5.dp,
                                brush = Brush.linearGradient(
                                    colors = listOf(EmblemGlowMint.copy(alpha = 0.85f), PureWhite.copy(alpha = 0.85f))
                                ),
                                shape = RoundedCornerShape(20.dp)
                            )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_hero_services),
                            contentDescription = strings.appName,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(144.dp)
                        )
                    }

                }
            }
        }

        // Unified Search & Filter Bar (Skill Category + Favorites + 4-Level Cascading Location Filter)
        item {
            HomeSearchAndFilterBar(
                uiState = uiState,
                enabledCategories = enabledCategories,
                isExpanded = isFilterBarExpanded,
                onToggleExpanded = { isFilterBarExpanded = !isFilterBarExpanded },
                onSearchQueryChange = onSearchQueryChange,
                onOpenSkillPicker = { showSkillPickerModal = true },
                onToggleFavoritesOnly = { onToggleFavoritesOnly(!uiState.showFavoritesOnly) },
                onLocationFilterSelected = onLocationFilterSelected,
                onClearAllFilters = {
                    onClearAllFilters()
                },
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Service Categories Section
        item {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = strings.serviceCategories,
                        style = MaterialTheme.typography.titleLarge
                    )
                    if (uiState.selectedCategoryKey != null) {
                        Text(
                            text = strings.allCategories,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { onCategorySelected(null) }
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }

                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(enabledCategories, key = { it.key }) { cat ->
                        CategoryIconCard(
                            category = cat,
                            selected = uiState.selectedCategoryKey == cat.key,
                            onClick = {
                                val next = if (uiState.selectedCategoryKey == cat.key) null else cat.key
                                onCategorySelected(next)
                            }
                        )
                    }
                }
            }
        }

        // Honest Preview Data Separation Banner
        item {
            Surface(
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = strings.sampleDataBannerTitle,
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Text(
                            text = strings.sampleDataBannerDesc,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Switch(
                        checked = uiState.preferences.showSamplePreviewData,
                        onCheckedChange = onToggleSamplePreview,
                        modifier = Modifier.testTag("toggle_sample_preview_home")
                    )
                }
            }
        }

        // Saved Favorites Quick-Access Section (Persisted Local Favorites)
        if (uiState.favoriteWorkers.isNotEmpty()) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.testTag("saved_favorites_section")
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = null,
                                tint = AlertRed,
                                modifier = Modifier.size(20.dp)
                            )
                            Text(
                                text = "${strings.savedFavoritesTitle} (${uiState.favoriteWorkers.size})",
                                style = MaterialTheme.typography.titleLarge,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        FilterChip(
                            selected = uiState.showFavoritesOnly,
                            onClick = { onToggleFavoritesOnly(!uiState.showFavoritesOnly) },
                            label = { Text(strings.favoritesFilterChip, maxLines = 1) },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PakistanGreenDark,
                                selectedLabelColor = PureWhite
                            ),
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("saved_favorites_filter_shortcut")
                        )
                    }
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.testTag("saved_favorites_row")
                    ) {
                        items(uiState.favoriteWorkers, key = { "fav_${it.worker.id}" }) { item ->
                            SavedFavoriteQuickCard(
                                item = item,
                                categories = enabledCategories,
                                onToggleFavorite = { onToggleFavorite(item.worker.id) },
                                onClick = { onWorkerClick(item.worker.id) }
                            )
                        }
                    }
                }
            }
        }

        // Featured Workers Carousel
        if (uiState.featuredWorkers.isNotEmpty() && !uiState.showFavoritesOnly) {
            item {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = strings.featuredWorkers,
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(uiState.featuredWorkers, key = { "feat_${it.worker.id}" }) { item ->
                            FeaturedWorkerCard(
                                item = item,
                                categories = enabledCategories,
                                isFavorite = item.worker.id in favoriteIdsSet,
                                onToggleFavorite = { onToggleFavorite(item.worker.id) },
                                locationFormatter = { w ->
                                    uiState.locationProvider.formatWorkerLocation(
                                        provinceId = w.provinceId,
                                        districtId = w.districtId,
                                        talukaId = w.talukaId,
                                        areaFallback = w.area,
                                        cityFallback = w.city,
                                        language = language
                                    )
                                },
                                onClick = { onWorkerClick(item.worker.id) }
                            )
                        }
                    }
                }
            }
        }

        // Nearby Workers / Filtered Favorites Header
        item {
            Text(
                text = if (uiState.showFavoritesOnly) {
                    "${strings.savedFavoritesTitle} (${uiState.filteredWorkers.size})"
                } else {
                    "${strings.nearbyWorkers} (${uiState.filteredWorkers.size})"
                },
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }

        // Nearby Workers List or Responsive Grid
        if (uiState.filteredWorkers.isEmpty()) {
            item {
                EmptyWorkersView(
                    title = if (uiState.showFavoritesOnly) strings.emptyFavoritesTitle else strings.emptyWorkersTitle,
                    subtitle = if (uiState.showFavoritesOnly) strings.emptyFavoritesSubtitle else strings.emptyWorkersSubtitle,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        } else {
            item {
                BoxWithConstraints(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    val columns = if (maxWidth >= 600.dp) 2 else 1
                    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        uiState.filteredWorkers.chunked(columns).forEach { rowItems ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {
                                rowItems.forEach { item ->
                                    WorkerListCard(
                                        item = item,
                                        categories = enabledCategories,
                                        isFavorite = item.worker.id in favoriteIdsSet,
                                        onToggleFavorite = { onToggleFavorite(item.worker.id) },
                                        onClick = { onWorkerClick(item.worker.id) },
                                        modifier = Modifier.weight(1f)
                                    )
                                }
                                if (rowItems.size < columns) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (showSkillPickerModal) {
        SkillCategoryPickerModal(
            categories = enabledCategories,
            selectedCategoryKey = uiState.selectedCategoryKey,
            onDismiss = { showSkillPickerModal = false },
            onSelectCategory = { catKey ->
                onCategorySelected(catKey)
                showSkillPickerModal = false
            }
        )
    }
}

@Composable
private fun CategoryIconCard(
    category: ServiceCategoryEntity,
    selected: Boolean,
    onClick: () -> Unit
) {
    val language = LocalAppLanguage.current
    val bg = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    val fg = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.primary
    val textColor = if (selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface

    Surface(
        shape = RoundedCornerShape(18.dp),
        color = bg,
        tonalElevation = if (selected) 4.dp else 1.dp,
        modifier = Modifier
            .width(104.dp)
            .border(
                width = 1.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(18.dp)
            )
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .testTag("category_card_${category.key}")
    ) {
        Column(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(CircleShape)
                    .background(
                        if (selected) PureWhite.copy(alpha = 0.2f)
                        else MaterialTheme.colorScheme.primaryContainer
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = categoryIconFor(category.iconKey),
                    contentDescription = category.localizedName(language),
                    tint = fg,
                    modifier = Modifier.size(22.dp)
                )
            }
            Text(
                text = category.localizedName(language),
                style = MaterialTheme.typography.labelMedium,
                color = textColor,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun SavedFavoriteQuickCard(
    item: WorkerWithStats,
    categories: List<ServiceCategoryEntity>,
    onToggleFavorite: () -> Unit,
    onClick: () -> Unit
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    val worker = item.worker
    val primaryCat = worker.categoryKeys.firstOrNull()?.let { key ->
        categories.firstOrNull { it.key == key }?.localizedName(language)
    }.orEmpty()

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .width(220.dp)
            .border(1.5.dp, AlertRed.copy(alpha = 0.28f), RoundedCornerShape(18.dp))
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick)
            .testTag("favorite_quick_card_${worker.id}")
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                WorkerAvatar(photoUri = worker.profilePhotoUri, name = worker.fullName, sizeDp = 42)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = worker.fullName,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = primaryCat,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                FavoriteHeartToggleButton(
                    isFavorite = true,
                    onToggle = onToggleFavorite,
                    tag = "quick_favorite_toggle_${worker.id}"
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${strings.pkrPrefix} ${worker.basePricePkr} ${strings.perVisit}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = if (item.reviewCount > 0) WarmGold else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(13.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = if (item.reviewCount > 0) "${"%.1f".format(item.averageRating)}" else "-",
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Composable
private fun FeaturedWorkerCard(
    item: WorkerWithStats,
    categories: List<ServiceCategoryEntity>,
    isFavorite: Boolean,
    onToggleFavorite: () -> Unit,
    locationFormatter: (com.example.data.model.WorkerProfileEntity) -> String,
    onClick: () -> Unit
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    val worker = item.worker
    val primaryCat = worker.categoryKeys.firstOrNull()?.let { key ->
        categories.firstOrNull { it.key == key }?.localizedName(language)
    }.orEmpty()

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = Modifier
            .width(256.dp)
            .border(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.25f), RoundedCornerShape(20.dp))
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .testTag("featured_worker_${worker.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                WorkerAvatar(photoUri = worker.profilePhotoUri, name = worker.fullName, sizeDp = 48)
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = worker.fullName,
                        style = MaterialTheme.typography.titleSmall,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = primaryCat,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                FavoriteHeartToggleButton(
                    isFavorite = isFavorite,
                    onToggle = onToggleFavorite,
                    tag = "featured_favorite_toggle_${worker.id}"
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                VerificationBadgeChip(statusName = worker.cnicVerificationStatus)
                if (worker.isDemoData) {
                    SamplePreviewChip()
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = locationFormatter(worker),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        tint = if (item.reviewCount > 0) WarmGold else MaterialTheme.colorScheme.outline,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = if (item.reviewCount > 0) "${"%.1f".format(item.averageRating)}" else "-",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }

            Text(
                text = "${strings.pkrPrefix} ${worker.basePricePkr} ${strings.perVisit}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun EmptyWorkersView(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_empty_requests),
                contentDescription = title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(140.dp)
                    .clip(RoundedCornerShape(16.dp))
            )
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun BrandHeaderIdentity(
    tagline: String,
    modifier: Modifier = Modifier
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Row(
            modifier = modifier.testTag("home_brand_header"),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_kaam_badge_circle),
                contentDescription = "KAAM PAKISTAN",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.5.dp,
                        brush = Brush.linearGradient(colors = listOf(EmblemGlowMint, PureWhite)),
                        shape = CircleShape
                    )
                    .testTag("home_brand_badge")
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "ڪام پاڪستان | KAAM PAKISTAN",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.ExtraBold),
                    color = PureWhite,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.testTag("home_brand_title")
                )
                Text(
                    text = tagline,
                    style = MaterialTheme.typography.bodySmall,
                    color = EmblemGlowMint.copy(alpha = 0.92f),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun HomeSearchAndFilterBar(
    uiState: KaamUiState,
    enabledCategories: List<ServiceCategoryEntity>,
    isExpanded: Boolean,
    onToggleExpanded: () -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onOpenSkillPicker: () -> Unit,
    onToggleFavoritesOnly: () -> Unit,
    onLocationFilterSelected: (provinceId: String?, divisionId: String?, districtId: String?, talukaId: String?) -> Unit,
    onClearAllFilters: () -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current

    val activeFilterCount = listOfNotNull(
        uiState.selectedCategoryKey,
        uiState.selectedProvinceId,
        uiState.selectedDivisionId,
        uiState.selectedDistrictId,
        uiState.selectedTalukaId
    ).size + (if (uiState.searchQuery.isNotBlank()) 1 else 0) + (if (uiState.showFavoritesOnly) 1 else 0)

    val selectedCategory = uiState.selectedCategoryKey?.let { key ->
        enabledCategories.firstOrNull { it.key == key }
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
            .testTag("home_search_filter_bar")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Row 1: Search Input + Filter Panel Toggle Button
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.searchQuery,
                    onValueChange = onSearchQueryChange,
                    placeholder = {
                        Text(
                            text = strings.searchPlaceholder,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = strings.searchPlaceholder,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        if (uiState.searchQuery.isNotEmpty()) {
                            IconButton(
                                onClick = { onSearchQueryChange("") },
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("clear_search_query_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = strings.cancel
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.background,
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedBorderColor = EmblemAccentEmerald,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.7f)
                    ),
                    modifier = Modifier
                        .weight(1f)
                        .testTag("home_search_input")
                )

                BadgedBox(
                    badge = {
                        if (activeFilterCount > 0) {
                            Badge(
                                containerColor = PakistanGreenDark,
                                contentColor = PureWhite,
                                modifier = Modifier.testTag("home_filter_active_count")
                            ) {
                                Text(activeFilterCount.toString())
                            }
                        }
                    }
                ) {
                    FilledTonalIconButton(
                        onClick = onToggleExpanded,
                        colors = IconButtonDefaults.filledTonalIconButtonColors(
                            containerColor = if (isExpanded || activeFilterCount > 0) MintContainer else MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = PakistanGreenDeep
                        ),
                        modifier = Modifier
                            .size(52.dp)
                            .testTag("home_filter_toggle_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Tune,
                            contentDescription = strings.filterBarTitle
                        )
                    }
                }
            }

            // Expandable Skill Category + Favorites + Cascading Location Controls
            AnimatedVisibility(visible = isExpanded) {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.45f))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = strings.filterBarTitle,
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.onSurface,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = MintContainer,
                                modifier = Modifier.testTag("filter_results_count_badge")
                            ) {
                                Text(
                                    text = "${uiState.filteredWorkers.size} ${strings.resultsCountLabel}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = PakistanGreenDeep,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                                )
                            }
                        }

                        if (activeFilterCount > 0) {
                            TextButton(
                                onClick = onClearAllFilters,
                                modifier = Modifier
                                    .minimumInteractiveComponentSize()
                                    .testTag("clear_all_filters_btn")
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Clear,
                                    contentDescription = null,
                                    modifier = Modifier.size(15.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(strings.clearAllFilters)
                            }
                        }
                    }

                    // Skill Category + Favorites Quick Filter Chips inside Filter Bar
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        FilterChip(
                            selected = selectedCategory != null,
                            onClick = onOpenSkillPicker,
                            leadingIcon = {
                                Icon(
                                    imageVector = selectedCategory?.let { categoryIconFor(it.iconKey) } ?: Icons.Filled.Build,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            trailingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.KeyboardArrowDown,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            label = {
                                Text(
                                    text = "${strings.skillCategoryLabel}: ${selectedCategory?.localizedName(language) ?: strings.allCategories}",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = PakistanGreenDark,
                                selectedLabelColor = PureWhite,
                                selectedLeadingIconColor = PureWhite,
                                selectedTrailingIconColor = PureWhite
                            ),
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("filter_skill_category_chip")
                        )

                        FilterChip(
                            selected = uiState.showFavoritesOnly,
                            onClick = onToggleFavoritesOnly,
                            leadingIcon = {
                                Icon(
                                    imageVector = if (uiState.showFavoritesOnly) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                    contentDescription = null,
                                    tint = if (uiState.showFavoritesOnly) PureWhite else AlertRed,
                                    modifier = Modifier.size(16.dp)
                                )
                            },
                            label = {
                                val countSuffix = if (uiState.favoriteWorkers.isNotEmpty()) {
                                    " (${uiState.favoriteWorkers.size})"
                                } else ""
                                Text(
                                    text = "${strings.favoritesFilterChip}$countSuffix",
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                            },
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = AlertRed,
                                selectedLabelColor = PureWhite,
                                selectedLeadingIconColor = PureWhite,
                                containerColor = if (uiState.favoriteWorkers.isNotEmpty()) AlertRedContainer.copy(alpha = 0.45f) else MaterialTheme.colorScheme.surface
                            ),
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("filter_favorites_chip")
                        )
                    }

                    // 4-Level Cascading Location Filter (Province -> Division -> District -> Taluka/Tehsil)
                    CascadingLocationBar(
                        selectedProvinceId = uiState.selectedProvinceId,
                        selectedDivisionId = uiState.selectedDivisionId,
                        selectedDistrictId = uiState.selectedDistrictId,
                        selectedTalukaId = uiState.selectedTalukaId,
                        allowAllOption = true,
                        onLocationChanged = onLocationFilterSelected,
                        locationProvider = uiState.locationProvider
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SkillCategoryPickerModal(
    categories: List<ServiceCategoryEntity>,
    selectedCategoryKey: String?,
    onDismiss: () -> Unit,
    onSelectCategory: (String?) -> Unit
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .testTag("skill_category_picker_sheet")
        ) {
            Text(
                text = strings.serviceCategories,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )
            LazyColumn(
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    val allSelected = selectedCategoryKey == null
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (allSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = if (allSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { onSelectCategory(null) }
                            .testTag("skill_opt_all")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Build,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = strings.allCategories,
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = if (allSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                            if (allSelected) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }

                items(categories, key = { it.key }) { cat ->
                    val isSelected = selectedCategoryKey == cat.key
                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = if (isSelected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                                shape = RoundedCornerShape(14.dp)
                            )
                            .clip(RoundedCornerShape(14.dp))
                            .clickable { onSelectCategory(cat.key) }
                            .testTag("skill_opt_${cat.key}")
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(
                                    imageVector = categoryIconFor(cat.iconKey),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = cat.localizedName(language),
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                                )
                            }
                            if (isSelected) {
                                Icon(
                                    imageVector = Icons.Filled.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


