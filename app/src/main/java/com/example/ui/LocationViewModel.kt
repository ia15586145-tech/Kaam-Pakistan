package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.PreviewWorkerMockDataSource
import com.example.data.local.SampleSeedData
import com.example.data.model.DistrictInfo
import com.example.data.model.DivisionInfo
import com.example.data.model.PakistanLocations
import com.example.data.model.ProvinceInfo
import com.example.data.model.ResolvedLocationSelection
import com.example.data.model.ServiceCategoryEntity
import com.example.data.model.TalukaInfo
import com.example.data.model.WorkerWithStats
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class LocationUiState(
    val locationProvider: PakistanLocations = PakistanLocations,
    val provinces: List<ProvinceInfo> = PakistanLocations.provinces,
    val selectedProvinceId: String? = null,
    val selectedDivisionId: String? = null,
    val selectedDistrictId: String? = null,
    val selectedTalukaId: String? = null,
    val selectedCategoryKey: String? = null,
    val searchQuery: String = "",
    val previewModeEnabled: Boolean = true,
    val previewWorkers: List<WorkerWithStats> = emptyList(),
    val filteredPreviewWorkers: List<WorkerWithStats> = emptyList()
)

/**
 * Dedicated ViewModel encapsulating Pakistan's 4-level location provider (`PakistanLocations`)
 * and the `PreviewWorkerMockDataSource` for standalone and app-wide preview location filtering.
 */
class LocationViewModel(
    val locationsProvider: PakistanLocations = PakistanLocations(),
    private val mockDataSource: PreviewWorkerMockDataSource = PreviewWorkerMockDataSource(),
    private val categories: List<ServiceCategoryEntity> = SampleSeedData.defaultCategories
) : ViewModel() {

    private val selectedProvinceFlow = MutableStateFlow<String?>(null)
    private val selectedDivisionFlow = MutableStateFlow<String?>(null)
    private val selectedDistrictFlow = MutableStateFlow<String?>(null)
    private val selectedTalukaFlow = MutableStateFlow<String?>(null)
    private val selectedCategoryFlow = MutableStateFlow<String?>(null)
    private val searchQueryFlow = MutableStateFlow("")
    private val previewModeFlow = MutableStateFlow(true)

    private val locationSelectionFlow = combine(
        selectedProvinceFlow,
        selectedDivisionFlow,
        selectedDistrictFlow,
        selectedTalukaFlow
    ) { prov, div, dist, tal ->
        QuadSelection(prov, div, dist, tal)
    }

    val uiState: StateFlow<LocationUiState> = combine(
        locationSelectionFlow,
        selectedCategoryFlow,
        searchQueryFlow,
        previewModeFlow
    ) { selection, categoryKey, query, previewEnabled ->
        val allPreview = if (previewEnabled) mockDataSource.getPreviewWorkers() else emptyList()
        val q = query.trim().lowercase()

        val filtered = allPreview.filter { item ->
            val w = item.worker
            val matchesProv = selection.provinceId == null || w.provinceId == selection.provinceId
            val matchesDiv = selection.divisionId == null || w.divisionId == selection.divisionId
            val matchesDist = selection.districtId == null || w.districtId == selection.districtId
            val matchesTal = selection.talukaId == null || w.talukaId == selection.talukaId
            val matchesCat = categoryKey == null || w.categoryKeys.contains(categoryKey)
            val matchesQuery = q.isBlank() ||
                w.fullName.lowercase().contains(q) ||
                w.city.lowercase().contains(q) ||
                w.area.lowercase().contains(q) ||
                w.skillsSummary.lowercase().contains(q) ||
                locationsProvider.matchesLocationQuery(w.provinceId, w.districtId, w.talukaId, q, w.divisionId) ||
                w.categoryKeys.any { key ->
                    val cat = categories.firstOrNull { it.key == key }
                    val aliases = categorySearchAliases(key)
                    key.contains(q) ||
                        aliases.any { it.contains(q) || q.contains(it) } ||
                        (cat?.nameEn?.lowercase()?.contains(q) == true) ||
                        (cat?.nameUr?.contains(q) == true) ||
                        (cat?.nameSd?.contains(q) == true)
                }
            matchesProv && matchesDiv && matchesDist && matchesTal && matchesCat && matchesQuery
        }

        LocationUiState(
            locationProvider = locationsProvider,
            provinces = locationsProvider.provinces,
            selectedProvinceId = selection.provinceId,
            selectedDivisionId = selection.divisionId,
            selectedDistrictId = selection.districtId,
            selectedTalukaId = selection.talukaId,
            selectedCategoryKey = categoryKey,
            searchQuery = query,
            previewModeEnabled = previewEnabled,
            previewWorkers = allPreview,
            filteredPreviewWorkers = filtered
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = LocationUiState(
            locationProvider = locationsProvider,
            provinces = locationsProvider.provinces,
            previewWorkers = mockDataSource.getPreviewWorkers(),
            filteredPreviewWorkers = mockDataSource.getPreviewWorkers()
        )
    )

    fun selectLocation(
        provinceId: String?,
        divisionId: String?,
        districtId: String?,
        talukaId: String?
    ) {
        selectedProvinceFlow.value = provinceId
        selectedDivisionFlow.value = divisionId
        selectedDistrictFlow.value = districtId
        selectedTalukaFlow.value = talukaId
    }

    fun selectCategory(categoryKey: String?) {
        selectedCategoryFlow.value = categoryKey
    }

    fun updateSearchQuery(query: String) {
        searchQueryFlow.value = query
    }

    fun setPreviewMode(enabled: Boolean) {
        previewModeFlow.value = enabled
    }

    fun clearFilters() {
        selectedProvinceFlow.value = null
        selectedDivisionFlow.value = null
        selectedDistrictFlow.value = null
        selectedTalukaFlow.value = null
        selectedCategoryFlow.value = null
        searchQueryFlow.value = ""
    }

    fun divisionsFor(provinceId: String? = null): List<DivisionInfo> =
        locationsProvider.getDivisions(provinceId)

    fun districtsFor(provinceId: String? = null, divisionId: String? = null): List<DistrictInfo> =
        locationsProvider.getDistricts(provinceId, divisionId)

    fun tehsilsFor(
        provinceId: String? = null,
        divisionId: String? = null,
        districtId: String? = null
    ): List<Pair<DistrictInfo, TalukaInfo>> =
        locationsProvider.getTehsils(provinceId, divisionId, districtId)

    fun resolveStrictLocation(
        provinceId: String?,
        divisionId: String?,
        districtId: String?,
        talukaId: String?
    ): ResolvedLocationSelection =
        locationsProvider.resolveStrictCascade(provinceId, divisionId, districtId, talukaId)

    private data class QuadSelection(
        val provinceId: String?,
        val divisionId: String?,
        val districtId: String?,
        val talukaId: String?
    )
}

internal fun categorySearchAliases(categoryKey: String): List<String> = when (categoryKey) {
    "electrician" -> listOf("electric", "electrical", "wiring", "bijli")
    "plumber" -> listOf("plumbing", "pipe", "water", "sanitary")
    "ac_tech" -> listOf("ac", "air conditioning", "cooling", "fridge")
    "mason" -> listOf("masonry", "construction", "brick", "tile", "mistri")
    "painter" -> listOf("painting", "paint", "rang", "whitewash")
    "carpenter" -> listOf("carpentry", "wood", "furniture")
    "cleaner" -> listOf("cleaning", "clean", "safai", "wash")
    "appliance_repair" -> listOf("appliance", "washing machine", "microwave", "refrigerator")
    "solar_tech" -> listOf("solar", "inverter", "ups", "panel")
    "mechanic" -> listOf("mechanic", "auto", "car", "bike", "generator")
    else -> emptyList()
}

