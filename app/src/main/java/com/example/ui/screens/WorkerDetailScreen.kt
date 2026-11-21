package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.StarHalf
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Flag
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.RateReview
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material.icons.outlined.StarOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.model.PakistanLocations
import com.example.data.model.ReviewEntity
import com.example.data.model.ServiceCategoryEntity
import com.example.data.model.WorkerProfileEntity
import com.example.data.model.WorkerWithStats
import com.example.i18n.LocalAppLanguage
import com.example.i18n.LocalAppStrings
import com.example.ui.components.SamplePreviewChip
import com.example.ui.components.VerificationBadgeChip
import com.example.ui.components.WorkerAvatar
import com.example.ui.components.launchDialer
import com.example.ui.components.launchWhatsApp
import com.example.ui.theme.AlertRed
import com.example.ui.theme.MintContainer
import com.example.ui.theme.PakistanGreenDark
import com.example.ui.theme.PakistanGreenDeep
import com.example.ui.theme.PakistanGreenLight
import com.example.ui.theme.PureWhite
import com.example.ui.theme.VerifiedEmerald
import com.example.ui.theme.WarmGold
import com.example.ui.theme.WarmGoldContainer
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.floor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun WorkerDetailScreen(
    workerWithStats: WorkerWithStats,
    categories: List<ServiceCategoryEntity>,
    reviews: List<ReviewEntity>,
    onBack: () -> Unit,
    onSubmitRequest: (
        worker: WorkerProfileEntity,
        categoryKey: String,
        area: String,
        description: String,
        offeredPricePkr: Int,
        preferredTime: String
    ) -> Unit,
    onSubmitReview: (workerId: String, rating: Int, comment: String) -> Unit,
    onSubmitReport: (worker: WorkerProfileEntity, reason: String, details: String) -> Unit,
    onOpenChat: (WorkerProfileEntity) -> Unit = {},
    onFeedback: (String) -> Unit
) {
    BackHandler(onBack = onBack)

    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    val context = LocalContext.current
    val worker = workerWithStats.worker

    var showRequestDialog by remember { mutableStateOf(false) }
    var showReviewDialog by remember { mutableStateOf(false) }
    var initialReviewRating by remember { mutableIntStateOf(5) }
    var showReportDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(worker.fullName, maxLines = 1) },
                navigationIcon = {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("worker_detail_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = strings.cancel
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = { showReportDialog = true },
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("report_worker_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Flag,
                            contentDescription = strings.reportWorker,
                            tint = AlertRed
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .testTag("worker_detail_list"),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Card
            item {
                Card(
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(22.dp))
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            WorkerAvatar(
                                photoUri = worker.profilePhotoUri,
                                name = worker.fullName,
                                sizeDp = 72
                            )
                            Column(modifier = Modifier.weight(1f)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Text(
                                        text = worker.fullName,
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                    if (worker.isDemoData) SamplePreviewChip()
                                }
                                Spacer(modifier = Modifier.height(4.dp))
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.LocationOn,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = PakistanLocations.formatWorkerLocation(
                                            provinceId = worker.provinceId,
                                            districtId = worker.districtId,
                                            talukaId = worker.talukaId,
                                            areaFallback = worker.area,
                                            cityFallback = worker.city,
                                            language = language
                                        ),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Spacer(modifier = Modifier.height(6.dp))
                                VerificationBadgeChip(statusName = worker.cnicVerificationStatus)
                            }
                        }

                        FlowRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            worker.categoryKeys.forEach { key ->
                                val catName = categories.firstOrNull { it.key == key }
                                    ?.localizedName(language) ?: key
                                Surface(
                                    shape = RoundedCornerShape(50),
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Text(
                                        text = catName,
                                        style = MaterialTheme.typography.labelMedium,
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
                                    )
                                }
                            }
                        }

                        // Metrics Row
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(14.dp))
                                .background(MaterialTheme.colorScheme.secondaryContainer)
                                .padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(
                                    text = "${strings.pkrPrefix} ${worker.basePricePkr}",
                                    style = MaterialTheme.typography.titleMedium,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = strings.perVisit,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = "${worker.experienceYears} ${strings.yearsExpSuffix}",
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    text = if (worker.isAvailable) strings.availableNow else strings.busyUnavailable,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (worker.isAvailable) VerifiedEmerald else MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Column(horizontalAlignment = Alignment.End) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.Star,
                                        contentDescription = null,
                                        tint = if (workerWithStats.reviewCount > 0) WarmGold else MaterialTheme.colorScheme.outline,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(3.dp))
                                    Text(
                                        text = if (workerWithStats.reviewCount > 0) {
                                            "${"%.1f".format(workerWithStats.averageRating)}"
                                        } else "-",
                                        style = MaterialTheme.typography.titleSmall
                                    )
                                }
                                Text(
                                    text = if (workerWithStats.reviewCount > 0) {
                                        "${workerWithStats.reviewCount} ${strings.reviewsCountSuffix}"
                                    } else {
                                        strings.noReviewsYet
                                    },
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        // Privacy Notice
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f))
                                .padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Lock,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = "${strings.privacyProtectedNote} (${worker.phoneMasked})",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Call & WhatsApp Buttons
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = {
                                    launchDialer(context, worker.dialablePhone) {
                                        onFeedback(strings.dialerUnavailable)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PakistanGreenDark),
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                                    .testTag("call_worker_btn")
                            ) {
                                Icon(Icons.Filled.Call, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(strings.callNow)
                            }
                            Button(
                                onClick = {
                                    launchWhatsApp(context, worker.dialablePhone, worker.fullName) {
                                        onFeedback(strings.whatsappUnavailable)
                                    }
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = PakistanGreenLight),
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                                    .testTag("whatsapp_worker_btn")
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.Chat,
                                    contentDescription = null,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(strings.whatsapp)
                            }
                        }

                        // In-App Chat (opens Messages thread with address & GPS pin sharing)
                        OutlinedButton(
                            onClick = { onOpenChat(worker) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .minimumInteractiveComponentSize()
                                .testTag("open_chat_worker_btn")
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.Chat,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(strings.chatWithWorker)
                        }

                        // Request Service & Leave Review
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { showRequestDialog = true },
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                                    .testTag("open_request_dialog_btn")
                            ) {
                                Icon(Icons.Filled.WorkOutline, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(strings.requestService, maxLines = 1)
                            }
                            OutlinedButton(
                                onClick = {
                                    initialReviewRating = 5
                                    showReviewDialog = true
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .minimumInteractiveComponentSize()
                                    .testTag("open_review_dialog_btn")
                            ) {
                                Icon(Icons.Filled.RateReview, contentDescription = null, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(strings.leaveReview, maxLines = 1)
                            }
                        }
                    }
                }
            }

            // Skills Card
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(text = strings.skillsTitle, style = MaterialTheme.typography.titleMedium)
                        Text(
                            text = worker.skillsSummary.ifBlank { "-" },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Portfolio Photos
            if (worker.portfolioUris.isNotEmpty()) {
                item {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        Text(text = strings.portfolioPhotos, style = MaterialTheme.typography.titleMedium)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            items(worker.portfolioUris) { uri ->
                                AsyncImage(
                                    model = uri,
                                    contentDescription = strings.portfolioPhotos,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier
                                        .size(140.dp)
                                        .clip(RoundedCornerShape(16.dp))
                                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
                                )
                            }
                        }
                    }
                }
            }

            // Star Rating Summary & Distribution Section
            item {
                Text(
                    text = strings.averageRatingTitle,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            item {
                WorkerRatingSummaryCard(
                    averageRating = workerWithStats.averageRating,
                    reviews = reviews,
                    onQuickStarSelected = { star ->
                        initialReviewRating = star
                        showReviewDialog = true
                    },
                    onOpenReviewDialog = {
                        initialReviewRating = 5
                        showReviewDialog = true
                    }
                )
            }

            // Reviews Section
            item {
                Text(
                    text = "${strings.customerReviews} (${reviews.size})",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.testTag("worker_reviews_section_header")
                )
            }

            if (reviews.isEmpty()) {
                item {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.surface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                    ) {
                        Text(
                            text = strings.noReviewsYet,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                }
            } else {
                items(reviews, key = { it.id }) { rev ->
                    WorkerReviewItemCard(review = rev)
                }
            }
        }
    }

    if (showRequestDialog) {
        SubmitRequestDialog(
            worker = worker,
            onDismiss = { showRequestDialog = false },
            onSubmit = { area, desc, price, time ->
                onSubmitRequest(
                    worker,
                    worker.categoryKeys.firstOrNull() ?: "other",
                    area,
                    desc,
                    price,
                    time
                )
                showRequestDialog = false
            }
        )
    }

    if (showReviewDialog) {
        LeaveReviewDialog(
            initialRating = initialReviewRating,
            onDismiss = { showReviewDialog = false },
            onSubmit = { rating, comment ->
                onSubmitReview(worker.id, rating, comment)
                showReviewDialog = false
            }
        )
    }

    if (showReportDialog) {
        ReportWorkerDialog(
            onDismiss = { showReportDialog = false },
            onSubmit = { reason, details ->
                onSubmitReport(worker, reason, details)
                showReportDialog = false
            }
        )
    }
}

@Composable
private fun SubmitRequestDialog(
    worker: WorkerProfileEntity,
    onDismiss: () -> Unit,
    onSubmit: (area: String, desc: String, price: Int, time: String) -> Unit
) {
    val strings = LocalAppStrings.current
    var area by remember { mutableStateOf(worker.area) }
    var description by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf(worker.basePricePkr.toString()) }
    var preferredTime by remember { mutableStateOf("Today, 5:00 PM") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(strings.submitRequestTitle) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = area,
                    onValueChange = { area = it },
                    label = { Text(strings.areaAddress) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(strings.describeWorkNeeded) },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth().testTag("request_desc_input")
                )
                OutlinedTextField(
                    value = budget,
                    onValueChange = { budget = it.filter { c -> c.isDigit() } },
                    label = { Text(strings.offeredBudgetPkr) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = preferredTime,
                    onValueChange = { preferredTime = it },
                    label = { Text(strings.preferredTimeSlot) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val price = budget.toIntOrNull() ?: worker.basePricePkr
                    if (description.isNotBlank()) {
                        onSubmit(area, description, price, preferredTime)
                    }
                },
                modifier = Modifier.testTag("confirm_submit_request_btn")
            ) {
                Text(strings.submit)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(strings.cancel) }
        }
    )
}

@Composable
private fun WorkerRatingSummaryCard(
    averageRating: Double,
    reviews: List<ReviewEntity>,
    onQuickStarSelected: (Int) -> Unit,
    onOpenReviewDialog: () -> Unit
) {
    val strings = LocalAppStrings.current
    val totalCount = reviews.size
    val displayAvg = if (totalCount > 0) "%.1f".format(averageRating) else "0.0"

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
            .testTag("rating_summary_card")
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Average rating score + visual 5-star row
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier.width(110.dp)
                ) {
                    Text(
                        text = displayAvg,
                        style = MaterialTheme.typography.displaySmall.copy(fontWeight = FontWeight.ExtraBold),
                        color = if (totalCount > 0) PakistanGreenDark else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.testTag("worker_avg_rating_text")
                    )
                    StarDisplayBar(rating = averageRating, starSizeDp = 16)
                    Text(
                        text = "$totalCount ${strings.reviewsCountSuffix}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.testTag("worker_review_count_text")
                    )
                }

                // 5-to-1 star distribution bars
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    (5 downTo 1).forEach { star ->
                        val count = reviews.count { it.rating == star }
                        val ratio = if (totalCount > 0) count.toFloat() / totalCount.toFloat() else 0f
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Text(
                                text = "$star★",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.width(22.dp)
                            )
                            LinearProgressIndicator(
                                progress = { ratio },
                                color = WarmGold,
                                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                                strokeCap = StrokeCap.Round,
                                modifier = Modifier
                                    .weight(1f)
                                    .height(7.dp)
                                    .clip(RoundedCornerShape(50))
                            )
                            Text(
                                text = count.toString(),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.width(18.dp)
                            )
                        }
                    }
                }
            }

            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.45f))

            // Quick tap-a-star prompt + Review button
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = strings.tapStarToRate,
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.weight(1f)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onOpenReviewDialog,
                        colors = ButtonDefaults.buttonColors(containerColor = PakistanGreenDark),
                        modifier = Modifier
                            .minimumInteractiveComponentSize()
                            .testTag("rate_worker_summary_btn")
                    ) {
                        Text(strings.leaveReview, maxLines = 1)
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { star ->
                        IconButton(
                            onClick = { onQuickStarSelected(star) },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("quick_rate_star_$star")
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.StarOutline,
                                contentDescription = "${strings.ratingLabel}: $star",
                                tint = WarmGold,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StarDisplayBar(
    rating: Double,
    starSizeDp: Int = 16
) {
    val fullStars = floor(rating).toInt()
    val hasHalf = (rating - fullStars) >= 0.4
    Row(
        horizontalArrangement = Arrangement.spacedBy(2.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        (1..5).forEach { index ->
            val icon = when {
                index <= fullStars -> Icons.Filled.Star
                index == fullStars + 1 && hasHalf -> Icons.AutoMirrored.Filled.StarHalf
                else -> Icons.Outlined.StarOutline
            }
            val tint = if (index <= fullStars || (index == fullStars + 1 && hasHalf)) {
                WarmGold
            } else {
                MaterialTheme.colorScheme.outline
            }
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = tint,
                modifier = Modifier.size(starSizeDp.dp)
            )
        }
    }
}

@Composable
private fun WorkerReviewItemCard(review: ReviewEntity) {
    val formattedDate = remember(review.createdAt) {
        SimpleDateFormat("dd MMM yyyy", Locale.getDefault()).format(Date(review.createdAt))
    }
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
            .testTag("review_card_${review.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = review.customerName, style = MaterialTheme.typography.titleSmall)
                    Text(
                        text = formattedDate,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    StarDisplayBar(rating = review.rating.toDouble(), starSizeDp = 15)
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = WarmGoldContainer
                    ) {
                        Text(
                            text = "${review.rating}.0",
                            style = MaterialTheme.typography.labelSmall,
                            color = PakistanGreenDeep,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                        )
                    }
                }
            }
            if (review.comment.isNotBlank()) {
                Text(
                    text = review.comment,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun LeaveReviewDialog(
    initialRating: Int = 5,
    onDismiss: () -> Unit,
    onSubmit: (rating: Int, comment: String) -> Unit
) {
    val strings = LocalAppStrings.current
    var rating by remember(initialRating) { mutableIntStateOf(initialRating.coerceIn(1, 5)) }
    var comment by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(strings.leaveReview) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = strings.ratingLabel, style = MaterialTheme.typography.labelLarge)
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = MintContainer,
                        modifier = Modifier.testTag("dialog_selected_rating_badge")
                    ) {
                        Text(
                            text = "$rating / 5",
                            style = MaterialTheme.typography.labelMedium,
                            color = PakistanGreenDeep,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(2.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { star ->
                        IconButton(
                            onClick = { rating = star },
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("star_rating_$star")
                        ) {
                            Icon(
                                imageVector = if (star <= rating) Icons.Filled.Star else Icons.Outlined.StarOutline,
                                contentDescription = "$star",
                                tint = if (star <= rating) WarmGold else MaterialTheme.colorScheme.outline,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
                OutlinedTextField(
                    value = comment,
                    onValueChange = { comment = it },
                    label = { Text(strings.writeReviewHint) },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth().testTag("review_comment_input")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onSubmit(rating, comment) },
                modifier = Modifier.testTag("confirm_review_btn")
            ) {
                Text(strings.submit)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(strings.cancel) }
        }
    )
}

@Composable
private fun ReportWorkerDialog(
    onDismiss: () -> Unit,
    onSubmit: (reason: String, details: String) -> Unit
) {
    val strings = LocalAppStrings.current
    var reason by remember { mutableStateOf("Overcharging / Price Issue") }
    var details by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(strings.reportWorker) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = reason,
                    onValueChange = { reason = it },
                    label = { Text(strings.reportReasonLabel) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = details,
                    onValueChange = { details = it },
                    label = { Text(strings.reportDetailsHint) },
                    minLines = 2,
                    modifier = Modifier.fillMaxWidth().testTag("report_details_input")
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (reason.isNotBlank()) {
                        onSubmit(reason, details)
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = AlertRed, contentColor = PureWhite),
                modifier = Modifier.testTag("confirm_report_btn")
            ) {
                Text(strings.submit)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(strings.cancel) }
        }
    )
}
