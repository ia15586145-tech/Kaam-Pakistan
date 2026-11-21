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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VerifiedUser
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.ApprovalStatus
import com.example.data.model.PakistanLocations
import com.example.data.model.ReportStatus
import com.example.data.model.VerificationStatus
import com.example.i18n.LocalAppLanguage
import com.example.i18n.LocalAppStrings
import com.example.ui.KaamUiState
import com.example.ui.components.SamplePreviewChip
import com.example.ui.components.VerificationBadgeChip
import com.example.ui.theme.AlertRed
import com.example.ui.theme.VerifiedEmerald
import com.example.ui.theme.WarmGold

@Composable
fun AdminPanelScreen(
    uiState: KaamUiState,
    onApproveWorker: (workerId: String, verifyCnic: Boolean) -> Unit,
    onRejectWorker: (workerId: String) -> Unit,
    onToggleFeatured: (workerId: String, featured: Boolean) -> Unit,
    onToggleCategory: (key: String, enabled: Boolean) -> Unit,
    onAddCategory: (en: String, ur: String, sd: String) -> Unit,
    onUpdateReportStatus: (reportId: String, status: ReportStatus) -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    var adminTab by remember { mutableIntStateOf(0) }

    val pendingWorkersCount = uiState.allWorkers.count {
        it.worker.approvalStatus == ApprovalStatus.PENDING.name ||
            it.worker.cnicVerificationStatus == VerificationStatus.PENDING_REVIEW.name
    }
    val openReportsCount = uiState.reports.count {
        it.status == ReportStatus.OPEN.name || it.status == ReportStatus.INVESTIGATING.name
    }

    Column(modifier = modifier.fillMaxSize()) {
        Text(
            text = strings.adminPanelTitle,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)
        )

        // Statistics Grid
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatTile(strings.statCustomers, uiState.customers.size.toString(), Modifier.weight(1f))
            StatTile(strings.statWorkers, uiState.allWorkers.size.toString(), Modifier.weight(1f))
            StatTile(strings.statPendingApprovals, pendingWorkersCount.toString(), Modifier.weight(1f))
            StatTile(strings.statOpenReports, openReportsCount.toString(), Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(10.dp))

        ScrollableTabRow(selectedTabIndex = adminTab, edgePadding = 12.dp) {
            Tab(
                selected = adminTab == 0,
                onClick = { adminTab = 0 },
                text = { Text(strings.adminTabApprovals) },
                modifier = Modifier.testTag("admin_tab_approvals")
            )
            Tab(
                selected = adminTab == 1,
                onClick = { adminTab = 1 },
                text = { Text(strings.adminTabCategories) },
                modifier = Modifier.testTag("admin_tab_categories")
            )
            Tab(
                selected = adminTab == 2,
                onClick = { adminTab = 2 },
                text = { Text(strings.adminTabReports) },
                modifier = Modifier.testTag("admin_tab_reports")
            )
            Tab(
                selected = adminTab == 3,
                onClick = { adminTab = 3 },
                text = { Text(strings.adminTabUsers) },
                modifier = Modifier.testTag("admin_tab_users")
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            when (adminTab) {
                0 -> {
                    items(uiState.allWorkers, key = { "adm_${it.worker.id}" }) { item ->
                        val w = item.worker
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(18.dp))
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
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Text(text = w.fullName, style = MaterialTheme.typography.titleMedium)
                                        if (w.isDemoData) SamplePreviewChip()
                                    }
                                    FilterChip(
                                        selected = w.isFeatured,
                                        onClick = { onToggleFeatured(w.id, !w.isFeatured) },
                                        label = { Text(strings.featuredToggle) },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = Icons.Filled.Star,
                                                contentDescription = null,
                                                tint = if (w.isFeatured) WarmGold else MaterialTheme.colorScheme.outline,
                                                modifier = Modifier.size(16.dp)
                                            )
                                        },
                                        modifier = Modifier.testTag("admin_feature_${w.id}")
                                    )
                                }

                                Text(
                                    text = "${PakistanLocations.formatWorkerLocation(w.provinceId, w.districtId, w.talukaId, w.area, w.city, language)} • ${w.phoneMasked}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )

                                if (w.cnicMasked.isNotBlank()) {
                                    Text(
                                        text = "CNIC: ${w.cnicMasked}",
                                        style = MaterialTheme.typography.labelMedium
                                    )
                                }

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    VerificationBadgeChip(statusName = w.cnicVerificationStatus)
                                    Surface(
                                        shape = RoundedCornerShape(50),
                                        color = MaterialTheme.colorScheme.surfaceVariant
                                    ) {
                                        Text(
                                            text = w.approvalStatus,
                                            style = MaterialTheme.typography.labelSmall,
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                        )
                                    }
                                }

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Button(
                                        onClick = { onApproveWorker(w.id, true) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .minimumInteractiveComponentSize()
                                            .testTag("admin_verify_${w.id}")
                                    ) {
                                        Icon(Icons.Filled.VerifiedUser, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(strings.approveAndVerify, maxLines = 1)
                                    }
                                    OutlinedButton(
                                        onClick = { onRejectWorker(w.id) },
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = AlertRed),
                                        modifier = Modifier
                                            .minimumInteractiveComponentSize()
                                            .testTag("admin_reject_${w.id}")
                                    ) {
                                        Icon(Icons.Filled.Close, contentDescription = null, modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(strings.rejectProfile)
                                    }
                                }
                            }
                        }
                    }
                }

                1 -> {
                    item {
                        AddCategoryCard(onAddCategory = onAddCategory)
                    }
                    items(uiState.categories, key = { "cat_adm_${it.key}" }) { cat ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surface,
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
                                    Text(text = cat.localizedName(language), style = MaterialTheme.typography.titleSmall)
                                    Text(
                                        text = "${cat.nameEn} • ${cat.nameUr} • ${cat.nameSd}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                                Switch(
                                    checked = cat.isEnabled,
                                    onCheckedChange = { onToggleCategory(cat.key, it) }
                                )
                            }
                        }
                    }
                }

                2 -> {
                    items(uiState.reports, key = { "rep_${it.id}" }) { rep ->
                        Card(
                            shape = RoundedCornerShape(18.dp),
                            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(18.dp))
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
                                    Text(text = rep.targetWorkerName, style = MaterialTheme.typography.titleMedium)
                                    if (rep.isDemoData) SamplePreviewChip()
                                }
                                Text(
                                    text = "${rep.reason} • ${rep.status}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = AlertRed
                                )
                                Text(text = rep.details, style = MaterialTheme.typography.bodyMedium)
                                if (rep.status == ReportStatus.OPEN.name) {
                                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                        Button(
                                            onClick = { onUpdateReportStatus(rep.id, ReportStatus.RESOLVED) },
                                            modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                                        ) {
                                            Icon(Icons.Filled.CheckCircle, contentDescription = null, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text(strings.resolveReport)
                                        }
                                        OutlinedButton(
                                            onClick = { onUpdateReportStatus(rep.id, ReportStatus.DISMISSED) },
                                            modifier = Modifier.weight(1f).minimumInteractiveComponentSize()
                                        ) {
                                            Text(strings.dismissReport)
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                else -> {
                    items(uiState.allUsers, key = { "usr_${it.id}" }) { user ->
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.surface,
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
                        ) {
                            Row(
                                modifier = Modifier.padding(14.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                                    ) {
                                        Text(text = user.fullName, style = MaterialTheme.typography.titleSmall)
                                        if (user.isDemoData) SamplePreviewChip()
                                    }
                                    Text(
                                        text = "${user.role} • ${PakistanLocations.formatWorkerLocation(user.provinceId, user.districtId, user.talukaId, user.area, user.city, language)} • ${user.phoneMasked}",
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun StatTile(
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        modifier = modifier.border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = value,
                style = MaterialTheme.typography.titleLarge,
                color = VerifiedEmerald,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun AddCategoryCard(
    onAddCategory: (en: String, ur: String, sd: String) -> Unit
) {
    val strings = LocalAppStrings.current
    var nameEn by remember { mutableStateOf("") }
    var nameUr by remember { mutableStateOf("") }
    var nameSd by remember { mutableStateOf("") }

    Card(
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(18.dp))
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(text = strings.addCategoryTitle, style = MaterialTheme.typography.titleSmall)
            OutlinedTextField(
                value = nameEn,
                onValueChange = { nameEn = it },
                label = { Text(strings.categoryNameEn) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = nameUr,
                onValueChange = { nameUr = it },
                label = { Text(strings.categoryNameUr) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = nameSd,
                onValueChange = { nameSd = it },
                label = { Text(strings.categoryNameSd) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = {
                    if (nameEn.isNotBlank()) {
                        onAddCategory(nameEn, nameUr, nameSd)
                        nameEn = ""
                        nameUr = ""
                        nameSd = ""
                    }
                },
                modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize()
            ) {
                Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text(strings.submit)
            }
        }
    }
}
