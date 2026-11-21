package com.example.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.data.model.DistrictInfo
import com.example.data.model.DivisionInfo
import com.example.data.model.PakistanLocations
import com.example.data.model.TalukaInfo
import com.example.i18n.LocalAppLanguage
import com.example.i18n.LocalAppStrings

private enum class PickerStep(val index: Int) {
    PROVINCE(0),
    DIVISION(1),
    DISTRICT(2),
    TALUKA(3)
}

/**
 * Reusable 4-level cascading location bar (Province -> Division -> District -> Taluka/Tehsil)
 * backed by a searchable bottom sheet picker with full English, Sindhi, and Urdu support.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CascadingLocationBar(
    selectedProvinceId: String?,
    selectedDivisionId: String?,
    selectedDistrictId: String?,
    selectedTalukaId: String?,
    allowAllOption: Boolean,
    onLocationChanged: (provinceId: String?, divisionId: String?, districtId: String?, talukaId: String?) -> Unit,
    modifier: Modifier = Modifier,
    locationProvider: PakistanLocations = PakistanLocations
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    var pickerInitialStep by remember { mutableStateOf<PickerStep?>(null) }

    val selectedProvince = locationProvider.provinceById(selectedProvinceId)
    val selectedDivision = locationProvider.divisionById(selectedProvinceId, selectedDivisionId)
    val selectedDistrict = locationProvider.districtById(selectedProvinceId, selectedDistrictId)
    val selectedTaluka = locationProvider.talukaById(selectedDistrictId, selectedTalukaId)

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            LocationStepChip(
                prefix = strings.provinceLabel,
                value = selectedProvince?.localizedName(language) ?: strings.allProvinces,
                isActive = selectedProvince != null,
                onClick = { pickerInitialStep = PickerStep.PROVINCE },
                testTag = "cascading_province_chip"
            )

            LocationStepChip(
                prefix = strings.divisionLabel,
                value = selectedDivision?.localizedName(language) ?: strings.allDivisions,
                isActive = selectedDivision != null,
                onClick = { pickerInitialStep = PickerStep.DIVISION },
                testTag = "cascading_division_chip"
            )

            LocationStepChip(
                prefix = strings.districtLabel,
                value = selectedDistrict?.localizedName(language) ?: strings.allDistricts,
                isActive = selectedDistrict != null,
                onClick = { pickerInitialStep = PickerStep.DISTRICT },
                testTag = "cascading_district_chip"
            )

            LocationStepChip(
                prefix = strings.talukaLabel,
                value = selectedTaluka?.localizedName(language) ?: strings.allTalukas,
                isActive = selectedTaluka != null,
                onClick = { pickerInitialStep = PickerStep.TALUKA },
                testTag = "cascading_taluka_chip"
            )

            if (allowAllOption && (selectedProvinceId != null || selectedDivisionId != null || selectedDistrictId != null || selectedTalukaId != null)) {
                TextButton(
                    onClick = { onLocationChanged(null, null, null, null) },
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("clear_location_filter_btn")
                ) {
                    Icon(Icons.Filled.Clear, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(strings.clearLocationFilter)
                }
            }
        }
    }

    pickerInitialStep?.let { step ->
        LocationPickerModal(
            initialStep = step,
            selectedProvinceId = selectedProvinceId,
            selectedDivisionId = selectedDivisionId,
            selectedDistrictId = selectedDistrictId,
            selectedTalukaId = selectedTalukaId,
            allowAllOption = allowAllOption,
            locationProvider = locationProvider,
            onDismiss = { pickerInitialStep = null },
            onLocationSelected = { prov, div, dist, tal ->
                onLocationChanged(prov, div, dist, tal)
                pickerInitialStep = null
            }
        )
    }
}

@Composable
private fun LocationStepChip(
    prefix: String,
    value: String,
    isActive: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    FilterChip(
        selected = isActive,
        onClick = onClick,
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.LocationOn,
                contentDescription = null,
                modifier = Modifier.size(15.dp)
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
                text = "$prefix: $value",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTrailingIconColor = MaterialTheme.colorScheme.onPrimary
        ),
        modifier = Modifier
            .minimumInteractiveComponentSize()
            .testTag(testTag)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LocationPickerModal(
    initialStep: PickerStep,
    selectedProvinceId: String?,
    selectedDivisionId: String?,
    selectedDistrictId: String?,
    selectedTalukaId: String?,
    allowAllOption: Boolean,
    locationProvider: PakistanLocations,
    onDismiss: () -> Unit,
    onLocationSelected: (provinceId: String?, divisionId: String?, districtId: String?, talukaId: String?) -> Unit
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    var tempProvId by remember { mutableStateOf(selectedProvinceId ?: if (!allowAllOption) "sindh" else null) }
    var tempDivId by remember { mutableStateOf(selectedDivisionId) }
    var tempDistId by remember { mutableStateOf(selectedDistrictId) }
    var tempTalId by remember { mutableStateOf(selectedTalukaId) }
    var activeTab by remember { mutableIntStateOf(initialStep.index) }
    var searchQuery by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    LaunchedEffect(activeTab) {
        listState.scrollToItem(0)
    }

    val activeProvince = locationProvider.provinceById(tempProvId)
    val availableDivisions: List<DivisionInfo> = remember(tempProvId, locationProvider) {
        locationProvider.getDivisions(tempProvId)
    }
    val activeDivision = locationProvider.divisionById(tempProvId, tempDivId)
        ?: availableDivisions.firstOrNull { !allowAllOption }

    val availableDistricts: List<DistrictInfo> = remember(tempProvId, tempDivId, locationProvider) {
        locationProvider.getDistricts(tempProvId, activeDivision?.id ?: tempDivId)
    }
    val activeDistrict = locationProvider.districtById(tempProvId, tempDistId)
        ?: availableDistricts.firstOrNull { !allowAllOption }

    val availableTalukas: List<Pair<DistrictInfo, TalukaInfo>> = remember(tempProvId, tempDivId, tempDistId, locationProvider) {
        locationProvider.getTehsils(tempProvId, activeDivision?.id ?: tempDivId, activeDistrict?.id ?: tempDistId)
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.86f)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = strings.selectLocationTitle,
                    style = MaterialTheme.typography.titleLarge
                )
                if (allowAllOption) {
                    TextButton(
                        onClick = { onLocationSelected(null, null, null, null) },
                        modifier = Modifier.testTag("picker_reset_all")
                    ) {
                        Text(strings.clearLocationFilter)
                    }
                }
            }

            // 4-step cascading tabs
            ScrollableTabRow(selectedTabIndex = activeTab, edgePadding = 12.dp) {
                Tab(
                    selected = activeTab == 0,
                    onClick = {
                        activeTab = 0
                        searchQuery = ""
                    },
                    text = { Text("1. ${strings.provinceLabel}", maxLines = 1) },
                    modifier = Modifier.testTag("picker_tab_province")
                )
                Tab(
                    selected = activeTab == 1,
                    onClick = {
                        activeTab = 1
                        searchQuery = ""
                    },
                    text = { Text("2. ${strings.divisionLabel}", maxLines = 1) },
                    modifier = Modifier.testTag("picker_tab_division")
                )
                Tab(
                    selected = activeTab == 2,
                    onClick = {
                        activeTab = 2
                        searchQuery = ""
                    },
                    text = { Text("3. ${strings.districtLabel}", maxLines = 1) },
                    modifier = Modifier.testTag("picker_tab_district")
                )
                Tab(
                    selected = activeTab == 3,
                    onClick = {
                        activeTab = 3
                        searchQuery = ""
                    },
                    text = { Text("4. ${strings.talukaLabel}", maxLines = 1) },
                    modifier = Modifier.testTag("picker_tab_taluka")
                )
            }

            // Fast search bar inside Division, District, and Taluka/Tehsil tabs
            if (activeTab >= 1) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(strings.searchDistrictOrTalukaHint) },
                    leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(Icons.Filled.Clear, contentDescription = strings.cancel)
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .testTag("location_picker_search_input")
                )
            }

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .testTag("location_picker_list"),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                when (activeTab) {
                    // STEP 1: PROVINCE
                    0 -> {
                        if (allowAllOption) {
                            item {
                                LocationOptionRow(
                                    title = strings.allProvinces,
                                    subtitle = null,
                                    selected = tempProvId == null,
                                    onClick = { onLocationSelected(null, null, null, null) },
                                    testTag = "province_opt_all"
                                )
                            }
                        }
                        items(locationProvider.provinces, key = { it.id }) { prov ->
                            LocationOptionRow(
                                title = prov.localizedName(language),
                                subtitle = "${prov.divisions.size} ${strings.divisionLabel} • ${prov.districts.size} ${strings.districtLabel}",
                                selected = tempProvId == prov.id,
                                onClick = {
                                    tempProvId = prov.id
                                    tempDivId = null
                                    tempDistId = null
                                    tempTalId = null
                                    searchQuery = ""
                                    activeTab = 1 // Cascade to Division
                                },
                                testTag = "province_opt_${prov.id}"
                            )
                        }
                    }

                    // STEP 2: DIVISION
                    1 -> {
                        if (allowAllOption && searchQuery.isBlank()) {
                            item {
                                LocationOptionRow(
                                    title = strings.allDivisions,
                                    subtitle = activeProvince?.localizedName(language) ?: strings.allProvinces,
                                    selected = tempDivId == null,
                                    onClick = { onLocationSelected(tempProvId, null, null, null) },
                                    testTag = "division_opt_all"
                                )
                            }
                        }
                        val filteredDivs = locationProvider.searchDivisions(searchQuery, tempProvId)
                        items(filteredDivs, key = { it.id }) { div ->
                            val parentProv = locationProvider.provinceForDivision(div.id)
                            LocationOptionRow(
                                title = div.localizedName(language),
                                subtitle = "${parentProv?.localizedName(language).orEmpty()} • ${div.districts.size} ${strings.districtLabel}",
                                selected = tempDivId == div.id,
                                onClick = {
                                    tempProvId = parentProv?.id ?: tempProvId
                                    tempDivId = div.id
                                    tempDistId = null
                                    tempTalId = null
                                    searchQuery = ""
                                    activeTab = 2 // Cascade to District
                                },
                                testTag = "division_opt_${div.id}"
                            )
                        }
                    }

                    // STEP 3: DISTRICT (with fast search & direct Tehsil/Taluka quick-jump)
                    2 -> {
                        if (allowAllOption && searchQuery.isBlank()) {
                            item {
                                LocationOptionRow(
                                    title = strings.allDistricts,
                                    subtitle = activeDivision?.localizedName(language) ?: activeProvince?.localizedName(language) ?: strings.allProvinces,
                                    selected = tempDistId == null,
                                    onClick = { onLocationSelected(tempProvId, tempDivId, null, null) },
                                    testTag = "district_opt_all"
                                )
                            }
                        }

                        val filteredDistricts = availableDistricts.filter { it.matches(searchQuery) }
                        items(filteredDistricts, key = { it.id }) { dist ->
                            val parentProv = locationProvider.provinceForDistrict(dist.id)
                            val parentDiv = locationProvider.divisionForDistrict(dist.id)
                            LocationOptionRow(
                                title = dist.localizedName(language),
                                subtitle = "${parentDiv?.localizedName(language).orEmpty()} • ${dist.talukas.size} ${strings.talukaLabel}",
                                selected = tempDistId == dist.id,
                                onClick = {
                                    tempProvId = parentProv?.id ?: tempProvId
                                    tempDivId = parentDiv?.id ?: tempDivId
                                    tempDistId = dist.id
                                    tempTalId = null
                                    searchQuery = ""
                                    activeTab = 3 // Cascade to Taluka/Tehsil
                                },
                                testTag = "district_opt_${dist.id}"
                            )
                        }

                        if (searchQuery.isNotBlank()) {
                            val matchingTalukas = availableDistricts.flatMap { d ->
                                d.talukas.filter { it.matches(searchQuery) }.map { t -> d to t }
                            }
                            if (matchingTalukas.isNotEmpty()) {
                                item {
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = strings.talukaLabel,
                                        style = MaterialTheme.typography.labelLarge,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                                items(matchingTalukas, key = { "quick_${it.second.id}" }) { (dist, tal) ->
                                    val parentProv = locationProvider.provinceForDistrict(dist.id)
                                    val parentDiv = locationProvider.divisionForDistrict(dist.id)
                                    LocationOptionRow(
                                        title = tal.localizedName(language),
                                        subtitle = "${dist.localizedName(language)} • ${parentDiv?.localizedName(language).orEmpty()}",
                                        selected = tempTalId == tal.id,
                                        onClick = {
                                            onLocationSelected(parentProv?.id, parentDiv?.id, dist.id, tal.id)
                                        },
                                        testTag = "taluka_quick_opt_${tal.id}"
                                    )
                                }
                            }
                        }
                    }

                    // STEP 4: TALUKA / TEHSIL
                    else -> {
                        if (allowAllOption && searchQuery.isBlank()) {
                            item {
                                LocationOptionRow(
                                    title = strings.allTalukas,
                                    subtitle = activeDistrict?.localizedName(language) ?: strings.allDistricts,
                                    selected = tempTalId == null,
                                    onClick = { onLocationSelected(tempProvId, tempDivId, tempDistId, null) },
                                    testTag = "taluka_opt_all"
                                )
                            }
                        }

                        val filteredTalukas = availableTalukas.filter { (dist, tal) ->
                            tal.matches(searchQuery) || dist.matches(searchQuery)
                        }
                        items(filteredTalukas, key = { it.second.id }) { (dist, tal) ->
                            val parentProv = locationProvider.provinceForDistrict(dist.id)
                            val parentDiv = locationProvider.divisionForDistrict(dist.id)
                            LocationOptionRow(
                                title = tal.localizedName(language),
                                subtitle = "${dist.localizedName(language)} • ${parentDiv?.localizedName(language).orEmpty()}",
                                selected = tempTalId == tal.id,
                                onClick = {
                                    onLocationSelected(parentProv?.id, parentDiv?.id, dist.id, tal.id)
                                },
                                testTag = "taluka_opt_${tal.id}"
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationOptionRow(
    title: String,
    subtitle: String?,
    selected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = RoundedCornerShape(14.dp)
            )
            .clip(RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .testTag(testTag)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                    color = if (selected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurface
                )
                if (!subtitle.isNullOrBlank()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
            if (selected) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
