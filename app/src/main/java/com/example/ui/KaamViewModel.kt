package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.auth.OtpChannel
import com.example.data.auth.OtpGateway
import com.example.data.auth.OtpGatewayResult
import com.example.data.auth.UnconfiguredBackendOtpGateway
import com.example.data.local.KaamDatabase
import com.example.data.model.ApprovalStatus
import com.example.data.model.ChatConversationEntity
import com.example.data.model.ChatMessageEntity
import com.example.data.model.CustomerProfileEntity
import com.example.data.model.DistrictInfo
import com.example.data.model.DivisionInfo
import com.example.data.model.PakistanLocationStats
import com.example.data.model.PakistanLocations
import com.example.data.model.ProvinceInfo
import com.example.data.model.ReportEntity
import com.example.data.model.ReportStatus
import com.example.data.model.RequestStatus
import com.example.data.model.ResolvedLocationSelection
import com.example.data.model.ReviewEntity
import com.example.data.model.ServiceCategoryEntity
import com.example.data.model.ServiceRequestEntity
import com.example.data.model.TalukaInfo
import com.example.data.model.UserEntity
import com.example.data.model.UserRole
import com.example.data.model.VerificationRecordEntity
import com.example.data.model.WorkerProfileEntity
import com.example.data.model.WorkerWithStats
import com.example.data.prefs.AppUserPreferences
import com.example.data.prefs.UserPreferencesRepository
import com.example.data.repository.KaamRepository
import com.example.i18n.AppLanguage
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class OtpUiState(
    val phoneInput: String = "",
    val selectedChannel: OtpChannel = OtpChannel.SMS,
    val otpCodeInput: String = "",
    val challengeId: String? = null,
    val statusNotice: String? = null
)

data class KaamUiState(
    val preferences: AppUserPreferences = AppUserPreferences(),
    val locationProvider: PakistanLocations = PakistanLocations,
    val provinces: List<ProvinceInfo> = PakistanLocations.provinces,
    val locationStats: PakistanLocationStats = PakistanLocations.stats,
    val categories: List<ServiceCategoryEntity> = emptyList(),
    val allWorkers: List<WorkerWithStats> = emptyList(),
    val filteredWorkers: List<WorkerWithStats> = emptyList(),
    val featuredWorkers: List<WorkerWithStats> = emptyList(),
    val favoriteWorkerIds: List<String> = emptyList(),
    val favoriteWorkers: List<WorkerWithStats> = emptyList(),
    val showFavoritesOnly: Boolean = false,
    val allUsers: List<UserEntity> = emptyList(),
    val customers: List<CustomerProfileEntity> = emptyList(),
    val requests: List<ServiceRequestEntity> = emptyList(),
    val reviews: List<ReviewEntity> = emptyList(),
    val verifications: List<VerificationRecordEntity> = emptyList(),
    val reports: List<ReportEntity> = emptyList(),
    val conversations: List<ChatConversationEntity> = emptyList(),
    val chatMessages: List<ChatMessageEntity> = emptyList(),
    val selectedConversationId: String? = null,
    val unreadMessagesCount: Int = 0,
    val searchQuery: String = "",
    val selectedProvinceId: String? = null,
    val selectedDivisionId: String? = null,
    val selectedDistrictId: String? = null,
    val selectedTalukaId: String? = null,
    val selectedCategoryKey: String? = null,
    val otpState: OtpUiState = OtpUiState(),
    val isLoaded: Boolean = false
)

class KaamViewModel(application: Application) : AndroidViewModel(application) {

    private val database = KaamDatabase.getInstance(application)
    private val repository = KaamRepository(database.kaamDao())
    private val prefsRepo = UserPreferencesRepository(application)
    private val otpGateway: OtpGateway = UnconfiguredBackendOtpGateway()

    val locationsProvider: PakistanLocations = PakistanLocations()

    private val searchQueryFlow = MutableStateFlow("")
    private val selectedProvinceFlow = MutableStateFlow<String?>(null)
    private val selectedDivisionFlow = MutableStateFlow<String?>(null)
    private val selectedDistrictFlow = MutableStateFlow<String?>(null)
    private val selectedTalukaFlow = MutableStateFlow<String?>(null)
    private val selectedCategoryFlow = MutableStateFlow<String?>(null)
    private val selectedConversationFlow = MutableStateFlow<String?>(null)
    private val localFavoriteIdsFlow = MutableStateFlow<List<String>?>(null)
    private val showFavoritesOnlyFlow = MutableStateFlow(false)
    private val otpUiFlow = MutableStateFlow(OtpUiState())

    private val _events = MutableSharedFlow<String>(extraBufferCapacity = 8)
    val events = _events.asSharedFlow()

    init {
        viewModelScope.launch {
            repository.ensureSeeded()
        }
    }

    private data class LocationSelection(
        val provinceId: String?,
        val divisionId: String?,
        val districtId: String?,
        val talukaId: String?
    )

    private val locationFilterFlow = combine(
        selectedProvinceFlow,
        selectedDivisionFlow,
        selectedDistrictFlow,
        selectedTalukaFlow
    ) { prov, div, dist, tal ->
        LocationSelection(prov, div, dist, tal)
    }

    private val favoritesControlFlow = combine(
        localFavoriteIdsFlow,
        showFavoritesOnlyFlow
    ) { localIds, favoritesOnly ->
        localIds to favoritesOnly
    }

    private val filterFlow = combine(
        searchQueryFlow,
        locationFilterFlow,
        selectedCategoryFlow,
        otpUiFlow,
        favoritesControlFlow
    ) { query, loc, catKey, otp, favControl ->
        FilterBundle(
            query = query,
            provinceId = loc.provinceId,
            divisionId = loc.divisionId,
            districtId = loc.districtId,
            talukaId = loc.talukaId,
            catKey = catKey,
            otp = otp,
            localFavoriteIds = favControl.first,
            favoritesOnly = favControl.second
        )
    }

    private val coreDataFlow = combine(
        prefsRepo.preferencesFlow,
        repository.categories,
        repository.workersWithStats,
        repository.requests,
        repository.reviews
    ) { prefs, cats, workers, reqs, revs ->
        CoreDataBundle(prefs, cats, workers, reqs, revs)
    }

    private val adminDataFlow = combine(
        repository.users,
        repository.customers,
        repository.verifications,
        repository.reports
    ) { users, custs, vers, reps ->
        AdminDataBundle(users, custs, vers, reps)
    }

    private val chatDataFlow = combine(
        repository.conversations,
        repository.chatMessages,
        selectedConversationFlow
    ) { convs, msgs, selectedId ->
        ChatDataBundle(convs, msgs, selectedId)
    }

    val uiState: StateFlow<KaamUiState> = combine(
        coreDataFlow,
        adminDataFlow,
        filterFlow,
        chatDataFlow
    ) { core, admin, filter, chat ->
        val visibleWorkers = core.workers.filter { item ->
            val w = item.worker
            val demoAllowed = core.prefs.showSamplePreviewData || !w.isDemoData
            val approved = w.approvalStatus == ApprovalStatus.APPROVED.name
            demoAllowed && approved
        }

        val activeFavoriteIds = (filter.localFavoriteIds ?: core.prefs.favoriteWorkerIds).distinct()
        val activeFavoriteSet = activeFavoriteIds.toSet()

        val q = filter.query.trim().lowercase()
        val filtered = visibleWorkers.filter { item ->
            val w = item.worker
            val matchesProv = filter.provinceId == null || w.provinceId == filter.provinceId
            val matchesDiv = filter.divisionId == null || w.divisionId == filter.divisionId
            val matchesDist = filter.districtId == null || w.districtId == filter.districtId
            val matchesTal = filter.talukaId == null || w.talukaId == filter.talukaId
            val matchesCat = filter.catKey == null || w.categoryKeys.contains(filter.catKey)
            val matchesFav = !filter.favoritesOnly || w.id in activeFavoriteSet
            val matchesQuery = q.isBlank() ||
                w.fullName.lowercase().contains(q) ||
                w.city.lowercase().contains(q) ||
                w.area.lowercase().contains(q) ||
                w.skillsSummary.lowercase().contains(q) ||
                locationsProvider.matchesLocationQuery(w.provinceId, w.districtId, w.talukaId, q, w.divisionId) ||
                w.categoryKeys.any { key ->
                    val cat = core.categories.firstOrNull { it.key == key }
                    val aliases = categorySearchAliases(key)
                    key.contains(q) ||
                        aliases.any { it.contains(q) || q.contains(it) } ||
                        (cat?.nameEn?.lowercase()?.contains(q) == true) ||
                        (cat?.nameUr?.contains(q) == true) ||
                        (cat?.nameSd?.contains(q) == true)
                }
            matchesProv && matchesDiv && matchesDist && matchesTal && matchesCat && matchesFav && matchesQuery
        }

        val featured = visibleWorkers.filter { it.worker.isFeatured }

        val visibleConversations = chat.conversations.filter {
            core.prefs.showSamplePreviewData || !it.isDemoData
        }
        val visibleChatMessages = chat.chatMessages.filter {
            core.prefs.showSamplePreviewData || !it.isDemoData
        }
        val totalUnread = visibleConversations.sumOf { it.unreadCount }

        val visibleReviews = core.reviews.filter { core.prefs.showSamplePreviewData || !it.isDemoData }
        val reviewsByWorker = visibleReviews.groupBy { it.workerId }
        val allVisibleWorkers = core.workers
            .filter { core.prefs.showSamplePreviewData || !it.worker.isDemoData }
            .map { item ->
                val workerReviews = reviewsByWorker[item.worker.id].orEmpty()
                val avg = if (workerReviews.isEmpty()) 0.0 else workerReviews.map { it.rating }.average()
                item.copy(averageRating = avg, reviewCount = workerReviews.size)
            }
        val visibleWorkersWithStats = visibleWorkers.map { item ->
            val workerReviews = reviewsByWorker[item.worker.id].orEmpty()
            val avg = if (workerReviews.isEmpty()) 0.0 else workerReviews.map { it.rating }.average()
            item.copy(averageRating = avg, reviewCount = workerReviews.size)
        }
        val workersById = visibleWorkersWithStats.associateBy { it.worker.id }
        val favoriteWorkersList = activeFavoriteIds.mapNotNull { id -> workersById[id] }

        KaamUiState(
            preferences = core.prefs,
            locationProvider = locationsProvider,
            provinces = locationsProvider.provinces,
            locationStats = locationsProvider.stats,
            categories = core.categories,
            allWorkers = allVisibleWorkers,
            filteredWorkers = filtered.map { item ->
                val workerReviews = reviewsByWorker[item.worker.id].orEmpty()
                val avg = if (workerReviews.isEmpty()) 0.0 else workerReviews.map { it.rating }.average()
                item.copy(averageRating = avg, reviewCount = workerReviews.size)
            },
            featuredWorkers = featured.map { item ->
                val workerReviews = reviewsByWorker[item.worker.id].orEmpty()
                val avg = if (workerReviews.isEmpty()) 0.0 else workerReviews.map { it.rating }.average()
                item.copy(averageRating = avg, reviewCount = workerReviews.size)
            },
            favoriteWorkerIds = activeFavoriteIds,
            favoriteWorkers = favoriteWorkersList,
            showFavoritesOnly = filter.favoritesOnly,
            allUsers = admin.users.filter { core.prefs.showSamplePreviewData || !it.isDemoData },
            customers = admin.customers.filter { core.prefs.showSamplePreviewData || !it.isDemoData },
            requests = core.requests.filter { core.prefs.showSamplePreviewData || !it.isDemoData },
            reviews = visibleReviews,
            verifications = admin.verifications.filter { core.prefs.showSamplePreviewData || !it.isDemoData },
            reports = admin.reports.filter { core.prefs.showSamplePreviewData || !it.isDemoData },
            conversations = visibleConversations,
            chatMessages = visibleChatMessages,
            selectedConversationId = chat.selectedConversationId,
            unreadMessagesCount = totalUnread,
            searchQuery = filter.query,
            selectedProvinceId = filter.provinceId,
            selectedDivisionId = filter.divisionId,
            selectedDistrictId = filter.districtId,
            selectedTalukaId = filter.talukaId,
            selectedCategoryKey = filter.catKey,
            otpState = filter.otp,
            isLoaded = true
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = KaamUiState()
    )

    fun setLanguage(language: AppLanguage) {
        viewModelScope.launch { prefsRepo.setLanguage(language) }
    }

    fun setActiveRole(role: UserRole) {
        viewModelScope.launch { prefsRepo.setActiveRole(role) }
    }

    fun setShowSamplePreviewData(show: Boolean) {
        viewModelScope.launch { prefsRepo.setShowSamplePreviewData(show) }
    }

    fun setDarkTheme(dark: Boolean) {
        viewModelScope.launch { prefsRepo.setDarkTheme(dark) }
    }

    fun updateSearchQuery(query: String) {
        searchQueryFlow.value = query
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

    fun selectLocationFilter(
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

    fun toggleFavoriteWorker(workerId: String) {
        val currentList = localFavoriteIdsFlow.value ?: uiState.value.preferences.favoriteWorkerIds
        val updated = if (workerId in currentList) {
            currentList.filterNot { it == workerId }
        } else {
            listOf(workerId) + currentList
        }
        localFavoriteIdsFlow.value = updated
        viewModelScope.launch {
            prefsRepo.setFavoriteWorkerIds(updated)
        }
    }

    fun setFavoritesOnlyFilter(enabled: Boolean) {
        showFavoritesOnlyFlow.value = enabled
    }

    fun clearAllFilters() {
        searchQueryFlow.value = ""
        selectedProvinceFlow.value = null
        selectedDivisionFlow.value = null
        selectedDistrictFlow.value = null
        selectedTalukaFlow.value = null
        selectedCategoryFlow.value = null
        showFavoritesOnlyFlow.value = false
    }

    fun updateOtpPhone(phone: String) {
        otpUiFlow.value = otpUiFlow.value.copy(phoneInput = phone, statusNotice = null)
    }

    fun selectOtpChannel(channel: OtpChannel) {
        otpUiFlow.value = otpUiFlow.value.copy(selectedChannel = channel, statusNotice = null)
    }

    fun updateOtpCode(code: String) {
        val digitsOnly = code.filter { it.isDigit() }.take(6)
        otpUiFlow.value = otpUiFlow.value.copy(otpCodeInput = digitsOnly, statusNotice = null)
    }

    fun requestSixDigitOtp() {
        val state = otpUiFlow.value
        val digits = state.phoneInput.filter { it.isDigit() }
        if (digits.length < 10) {
            otpUiFlow.value = state.copy(statusNotice = "Enter a valid Pakistani mobile number (e.g., 03001234567).")
            return
        }
        viewModelScope.launch {
            when (val res = otpGateway.requestSixDigitOtp(digits, state.selectedChannel)) {
                is OtpGatewayResult.GatewayNotConfigured -> {
                    otpUiFlow.value = otpUiFlow.value.copy(
                        statusNotice = "Prepared ${res.channel.name} OTP request for ${res.normalizedPhone}. Connect a backend OTP endpoint (with server-side credentials) to send real 6-digit codes."
                    )
                }
                is OtpGatewayResult.ChallengeSent -> {
                    otpUiFlow.value = otpUiFlow.value.copy(
                        challengeId = res.challengeId,
                        statusNotice = "6-digit OTP sent via ${res.channel.name}."
                    )
                }
                is OtpGatewayResult.VerificationFailed -> {
                    otpUiFlow.value = otpUiFlow.value.copy(statusNotice = res.reason)
                }
                is OtpGatewayResult.Verified -> Unit
            }
        }
    }

    fun verifySixDigitOtp() {
        val state = otpUiFlow.value
        if (state.otpCodeInput.length != 6) {
            otpUiFlow.value = state.copy(statusNotice = "Please enter all 6 digits.")
            return
        }
        viewModelScope.launch {
            val res = otpGateway.verifySixDigitOtp(state.challengeId.orEmpty(), state.otpCodeInput)
            if (res is OtpGatewayResult.VerificationFailed) {
                otpUiFlow.value = otpUiFlow.value.copy(statusNotice = res.reason)
            }
        }
    }

    fun saveCustomerProfile(
        fullName: String,
        rawPhone: String,
        provinceId: String,
        divisionId: String,
        districtId: String,
        talukaId: String,
        areaDetail: String,
        successMessage: String
    ) {
        if (fullName.isBlank()) return
        val resolved = locationsProvider.resolveStrictCascade(provinceId, divisionId, districtId, talukaId)
        val distName = resolved.district.en
        val talName = resolved.taluka.en
        val resolvedArea = areaDetail.ifBlank { talName }
        viewModelScope.launch {
            val customerId = uiState.value.preferences.activeCustomerId
            repository.saveCustomerProfile(
                customerId = customerId,
                fullName = fullName,
                rawPhone = rawPhone,
                provinceId = resolved.province.id,
                divisionId = resolved.division.id,
                districtId = resolved.district.id,
                talukaId = resolved.taluka.id,
                city = distName,
                area = resolvedArea
            )
            _events.tryEmit(successMessage)
        }
    }

    fun saveWorkerProfile(
        existingWorkerId: String?,
        fullName: String,
        rawPhone: String,
        rawCnic: String,
        provinceId: String,
        divisionId: String,
        districtId: String,
        talukaId: String,
        areaDetail: String,
        categoryKeys: List<String>,
        experienceYears: Int,
        skillsSummary: String,
        basePricePkr: Int,
        profilePhotoUri: String,
        portfolioUris: List<String>,
        isAvailable: Boolean,
        successMessage: String
    ) {
        if (fullName.isBlank() || categoryKeys.isEmpty()) return
        val resolved = locationsProvider.resolveStrictCascade(provinceId, divisionId, districtId, talukaId)
        val distName = resolved.district.en
        val talName = resolved.taluka.en
        val resolvedArea = areaDetail.ifBlank { talName }
        viewModelScope.launch {
            val userId = "wrk_user_local"
            repository.registerOrUpdateWorker(
                existingWorkerId = existingWorkerId,
                userId = userId,
                fullName = fullName,
                rawPhone = rawPhone,
                rawCnic = rawCnic,
                provinceId = resolved.province.id,
                divisionId = resolved.division.id,
                districtId = resolved.district.id,
                talukaId = resolved.taluka.id,
                city = distName,
                area = resolvedArea,
                categoryKeys = categoryKeys,
                experienceYears = experienceYears,
                skillsSummary = skillsSummary,
                basePricePkr = basePricePkr,
                profilePhotoUri = profilePhotoUri,
                portfolioUris = portfolioUris,
                isAvailable = isAvailable
            )
            _events.tryEmit(successMessage)
        }
    }

    fun toggleWorkerAvailability(workerId: String, available: Boolean) {
        viewModelScope.launch {
            repository.toggleWorkerAvailability(workerId, available)
        }
    }

    fun submitServiceRequest(
        worker: WorkerProfileEntity,
        categoryKey: String,
        area: String,
        description: String,
        offeredPricePkr: Int,
        preferredTime: String,
        successMessage: String
    ) {
        viewModelScope.launch {
            val customer = uiState.value.customers.firstOrNull {
                it.id == uiState.value.preferences.activeCustomerId
            }
            repository.submitServiceRequest(
                customerId = customer?.id ?: "cust_local_1",
                customerName = customer?.fullName ?: "Customer",
                worker = worker,
                categoryKey = categoryKey,
                area = area,
                description = description,
                offeredPricePkr = offeredPricePkr,
                preferredTime = preferredTime
            )
            _events.tryEmit(successMessage)
        }
    }

    fun updateRequestStatus(requestId: String, status: RequestStatus) {
        viewModelScope.launch {
            repository.updateRequestStatus(requestId, status)
        }
    }

    fun submitReview(
        workerId: String,
        rating: Int,
        comment: String,
        successMessage: String
    ) {
        viewModelScope.launch {
            val customer = uiState.value.customers.firstOrNull {
                it.id == uiState.value.preferences.activeCustomerId
            }
            repository.submitReview(
                workerId = workerId,
                customerId = customer?.id ?: "cust_local_1",
                customerName = customer?.fullName ?: "Customer",
                rating = rating,
                comment = comment
            )
            _events.tryEmit(successMessage)
        }
    }

    fun submitReport(
        worker: WorkerProfileEntity,
        reason: String,
        details: String,
        successMessage: String
    ) {
        viewModelScope.launch {
            val customer = uiState.value.customers.firstOrNull {
                it.id == uiState.value.preferences.activeCustomerId
            }
            repository.submitReport(
                reporterUserId = customer?.id ?: "cust_local_1",
                reporterName = customer?.fullName ?: "Customer",
                worker = worker,
                reason = reason,
                details = details
            )
            _events.tryEmit(successMessage)
        }
    }

    fun adminApproveWorker(workerId: String, verifyCnic: Boolean) {
        viewModelScope.launch {
            repository.adminApproveWorker(workerId, verifyCnic)
        }
    }

    fun adminRejectWorker(workerId: String) {
        viewModelScope.launch {
            repository.adminRejectWorker(workerId)
        }
    }

    fun adminToggleFeatured(workerId: String, featured: Boolean) {
        viewModelScope.launch {
            repository.adminToggleFeatured(workerId, featured)
        }
    }

    fun adminToggleCategory(key: String, enabled: Boolean) {
        viewModelScope.launch {
            repository.adminToggleCategory(key, enabled)
        }
    }

    fun adminAddCategory(nameEn: String, nameUr: String, nameSd: String, successMessage: String) {
        if (nameEn.isBlank()) return
        viewModelScope.launch {
            repository.adminAddCategory(nameEn, nameUr, nameSd)
            _events.tryEmit(successMessage)
        }
    }

    fun adminUpdateReportStatus(reportId: String, status: ReportStatus) {
        viewModelScope.launch {
            repository.adminUpdateReportStatus(reportId, status)
        }
    }

    // In-App Messages Actions
    fun openConversation(conversationId: String?) {
        selectedConversationFlow.value = conversationId
        if (conversationId != null) {
            viewModelScope.launch {
                repository.markConversationRead(conversationId)
            }
        }
    }

    fun startOrOpenChatWithWorker(worker: WorkerProfileEntity, onOpened: () -> Unit = {}) {
        viewModelScope.launch {
            val customer = uiState.value.customers.firstOrNull {
                it.id == uiState.value.preferences.activeCustomerId
            }
            val convId = repository.getOrCreateConversationWithWorker(
                customerId = customer?.id ?: "cust_local_1",
                customerName = customer?.fullName ?: "Bilal Ahmed",
                worker = worker
            )
            selectedConversationFlow.value = convId
            onOpened()
        }
    }

    fun sendChatTextMessage(conversationId: String, body: String) {
        val role = uiState.value.preferences.activeRole
        val conv = uiState.value.conversations.firstOrNull { it.id == conversationId }
        val senderName = when (role) {
            UserRole.WORKER -> conv?.workerName ?: "Worker"
            UserRole.ADMIN -> "Kaam Pakistan Admin"
            UserRole.CUSTOMER -> {
                uiState.value.customers.firstOrNull {
                    it.id == uiState.value.preferences.activeCustomerId
                }?.fullName ?: conv?.customerName ?: "Customer"
            }
        }
        viewModelScope.launch {
            repository.sendChatTextMessage(
                conversationId = conversationId,
                senderRole = role,
                senderName = senderName,
                body = body
            )
        }
    }

    fun shareHomeAddressInChat(
        conversationId: String,
        streetAddress: String,
        landmark: String,
        cityArea: String,
        note: String
    ) {
        val role = uiState.value.preferences.activeRole
        val conv = uiState.value.conversations.firstOrNull { it.id == conversationId }
        val senderName = if (role == UserRole.WORKER) {
            conv?.workerName ?: "Worker"
        } else {
            uiState.value.customers.firstOrNull {
                it.id == uiState.value.preferences.activeCustomerId
            }?.fullName ?: conv?.customerName ?: "Customer"
        }
        viewModelScope.launch {
            repository.shareHomeAddressInChat(
                conversationId = conversationId,
                senderRole = role,
                senderName = senderName,
                streetAddress = streetAddress,
                landmark = landmark,
                cityArea = cityArea,
                note = note
            )
        }
    }

    fun shareGpsPinInChat(
        conversationId: String,
        latitude: Double,
        longitude: Double,
        cityArea: String,
        note: String
    ) {
        val role = uiState.value.preferences.activeRole
        val conv = uiState.value.conversations.firstOrNull { it.id == conversationId }
        val senderName = if (role == UserRole.WORKER) {
            conv?.workerName ?: "Worker"
        } else {
            uiState.value.customers.firstOrNull {
                it.id == uiState.value.preferences.activeCustomerId
            }?.fullName ?: conv?.customerName ?: "Customer"
        }
        viewModelScope.launch {
            repository.shareGpsPinInChat(
                conversationId = conversationId,
                senderRole = role,
                senderName = senderName,
                latitude = latitude,
                longitude = longitude,
                cityArea = cityArea,
                note = note
            )
        }
    }

    private data class FilterBundle(
        val query: String,
        val provinceId: String?,
        val divisionId: String?,
        val districtId: String?,
        val talukaId: String?,
        val catKey: String?,
        val otp: OtpUiState,
        val localFavoriteIds: List<String>?,
        val favoritesOnly: Boolean
    )

    private data class CoreDataBundle(
        val prefs: AppUserPreferences,
        val categories: List<ServiceCategoryEntity>,
        val workers: List<WorkerWithStats>,
        val requests: List<ServiceRequestEntity>,
        val reviews: List<ReviewEntity>
    )

    private data class AdminDataBundle(
        val users: List<UserEntity>,
        val customers: List<CustomerProfileEntity>,
        val verifications: List<VerificationRecordEntity>,
        val reports: List<ReportEntity>
    )

    private data class ChatDataBundle(
        val conversations: List<ChatConversationEntity>,
        val chatMessages: List<ChatMessageEntity>,
        val selectedConversationId: String?
    )
}
