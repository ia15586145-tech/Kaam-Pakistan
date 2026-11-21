package com.example.ui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.PakistanLocations
import com.example.data.model.RequestStatus
import com.example.data.model.ServiceRequestEntity
import com.example.data.model.UserRole
import com.example.i18n.LocalAppLanguage
import com.example.i18n.LocalAppStrings
import com.example.ui.KaamUiState
import com.example.ui.components.SamplePreviewChip
import com.example.ui.theme.AlertRed
import com.example.ui.theme.AlertRedContainer
import com.example.ui.theme.PureWhite
import com.example.ui.theme.VerifiedEmerald
import com.example.ui.theme.WarmGold
import com.example.ui.theme.WarmGoldContainer

@Composable
fun RequestsScreen(
    uiState: KaamUiState,
    onUpdateStatus: (String, RequestStatus) -> Unit,
    onToggleAvailability: (String, Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    var selectedTab by remember {
        mutableIntStateOf(if (uiState.preferences.activeRole == UserRole.WORKER) 1 else 0)
    }

    LaunchedEffect(uiState.preferences.activeRole) {
        selectedTab = if (uiState.preferences.activeRole == UserRole.WORKER) 1 else 0
    }

    val myLocalWorker = uiState.allWorkers.firstOrNull { !it.worker.isDemoData }?.worker
        ?: uiState.allWorkers.firstOrNull()?.worker

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = strings.requestsTitle,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 14.dp)
        )

        TabRow(selectedTabIndex = selectedTab) {
            Tab(
                selected = selectedTab == 0,
                onClick = { selectedTab = 0 },
                text = { Text(strings.customerRequestsTab) },
                modifier = Modifier.testTag("tab_customer_requests")
            )
            Tab(
                selected = selectedTab == 1,
                onClick = { selectedTab = 1 },
                text = { Text(strings.workerJobsTab) },
                modifier = Modifier.testTag("tab_worker_jobs")
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            if (selectedTab == 1 && myLocalWorker != null) {
                item {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(
                                    text = myLocalWorker.fullName,
                                    style = MaterialTheme.typography.titleSmall
                                )
                                Text(
                                    text = strings.availabilityToggleLabel,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            Switch(
                                checked = myLocalWorker.isAvailable,
                                onCheckedChange = { onToggleAvailability(myLocalWorker.id, it) },
                                modifier = Modifier.testTag("worker_availability_switch")
                            )
                        }
                    }
                }
            }

            if (uiState.requests.isEmpty()) {
                item {
                    EmptyWorkersView(
                        title = strings.noRequestsTitle,
                        subtitle = strings.noRequestsSubtitle
                    )
                }
            } else {
                items(uiState.requests, key = { it.id }) { req ->
                    val categoryName = uiState.categories.firstOrNull { it.key == req.categoryKey }
                        ?.localizedName(language) ?: req.categoryKey
                    RequestCard(
                        request = req,
                        categoryName = categoryName,
                        isWorkerMode = selectedTab == 1,
                        onUpdateStatus = { status -> onUpdateStatus(req.id, status) }
                    )
                }
            }
        }
    }
}

@Composable
private fun RequestCard(
    request: ServiceRequestEntity,
    categoryName: String,
    isWorkerMode: Boolean,
    onUpdateStatus: (RequestStatus) -> Unit
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    val status = runCatching { RequestStatus.valueOf(request.status) }.getOrDefault(RequestStatus.PENDING)
    val (badgeBg, badgeFg, badgeText) = when (status) {
        RequestStatus.PENDING -> Triple(WarmGoldContainer, WarmGold, strings.statusPending)
        RequestStatus.ACCEPTED -> Triple(Color(0xFFD1FAE5), VerifiedEmerald, strings.statusAccepted)
        RequestStatus.COMPLETED -> Triple(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.colorScheme.primary, strings.statusCompleted)
        RequestStatus.REJECTED -> Triple(AlertRedContainer, AlertRed, strings.statusRejected)
        RequestStatus.CANCELLED -> Triple(MaterialTheme.colorScheme.surfaceVariant, MaterialTheme.colorScheme.onSurfaceVariant, strings.statusCancelled)
    }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.6f), RoundedCornerShape(18.dp))
            .testTag("request_card_${request.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = if (isWorkerMode) request.customerName else request.workerName,
                            style = MaterialTheme.typography.titleMedium
                        )
                        if (request.isDemoData) SamplePreviewChip()
                    }
                    Text(
                        text = categoryName,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Surface(shape = RoundedCornerShape(50), color = badgeBg) {
                    Text(
                        text = badgeText,
                        style = MaterialTheme.typography.labelSmall,
                        color = badgeFg,
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }

            Text(
                text = request.description,
                style = MaterialTheme.typography.bodyMedium
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.LocationOn,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = PakistanLocations.formatWorkerLocation(
                            provinceId = request.provinceId,
                            districtId = request.districtId,
                            talukaId = request.talukaId,
                            areaFallback = request.area,
                            cityFallback = request.city,
                            language = language
                        ),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.Schedule,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Text(
                        text = request.preferredTime,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = "${strings.pkrPrefix} ${request.offeredPricePkr}",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            if (isWorkerMode && status == RequestStatus.PENDING) {
                Spacer(modifier = Modifier.height(2.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Button(
                        onClick = { onUpdateStatus(RequestStatus.ACCEPTED) },
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("accept_job_${request.id}")
                    ) {
                        Icon(Icons.Filled.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(strings.acceptJob)
                    }
                    OutlinedButton(
                        onClick = { onUpdateStatus(RequestStatus.REJECTED) },
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AlertRed),
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("reject_job_${request.id}")
                    ) {
                        Icon(Icons.Filled.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(strings.rejectJob)
                    }
                }
            } else if (status == RequestStatus.ACCEPTED) {
                Button(
                    onClick = { onUpdateStatus(RequestStatus.COMPLETED) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                        .testTag("complete_job_${request.id}")
                ) {
                    Icon(Icons.Filled.DoneAll, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(strings.markCompleted)
                }
            } else if (!isWorkerMode && status == RequestStatus.PENDING) {
                OutlinedButton(
                    onClick = { onUpdateStatus(RequestStatus.CANCELLED) },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = AlertRed),
                    modifier = Modifier
                        .fillMaxWidth()
                        .minimumInteractiveComponentSize()
                ) {
                    Text(strings.cancel)
                }
            }
        }
    }
}
