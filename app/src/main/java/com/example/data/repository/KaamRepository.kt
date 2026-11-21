package com.example.data.repository

import com.example.data.local.KaamDao
import com.example.data.local.SampleSeedData
import com.example.data.model.ApprovalStatus
import com.example.data.model.ChatConversationEntity
import com.example.data.model.ChatMessageEntity
import com.example.data.model.ChatMessageType
import com.example.data.model.CustomerProfileEntity
import com.example.data.model.ReportEntity
import com.example.data.model.ReportStatus
import com.example.data.model.RequestStatus
import com.example.data.model.ReviewEntity
import com.example.data.model.ServiceCategoryEntity
import com.example.data.model.ServiceRequestEntity
import com.example.data.model.UserEntity
import com.example.data.model.UserRole
import com.example.data.model.VerificationRecordEntity
import com.example.data.model.VerificationStatus
import com.example.data.model.WorkerProfileEntity
import com.example.data.model.WorkerWithStats
import com.example.data.util.PrivacyUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.util.UUID

class KaamRepository(private val dao: KaamDao) {

    val categories: Flow<List<ServiceCategoryEntity>> = dao.observeAllCategories()
    val users: Flow<List<UserEntity>> = dao.observeAllUsers()
    val customers: Flow<List<CustomerProfileEntity>> = dao.observeAllCustomers()
    val requests: Flow<List<ServiceRequestEntity>> = dao.observeAllRequests()
    val reviews: Flow<List<ReviewEntity>> = dao.observeAllReviews()
    val verifications: Flow<List<VerificationRecordEntity>> = dao.observeAllVerifications()
    val reports: Flow<List<ReportEntity>> = dao.observeAllReports()
    val conversations: Flow<List<ChatConversationEntity>> = dao.observeAllConversations()
    val chatMessages: Flow<List<ChatMessageEntity>> = dao.observeAllMessages()

    val workersWithStats: Flow<List<WorkerWithStats>> = combine(
        dao.observeAllWorkers(),
        dao.observeAllReviews()
    ) { workers, allReviews ->
        val reviewsByWorker = allReviews.groupBy { it.workerId }
        workers.map { worker ->
            val workerReviews = reviewsByWorker[worker.id].orEmpty()
            val avg = if (workerReviews.isEmpty()) 0.0
            else workerReviews.map { it.rating }.average()
            WorkerWithStats(
                worker = worker,
                averageRating = avg,
                reviewCount = workerReviews.size
            )
        }
    }

    suspend fun ensureSeeded() {
        if (dao.getCategoryCount() == 0) {
            dao.insertCategories(SampleSeedData.defaultCategories)
            dao.insertUsersIgnore(SampleSeedData.demoUsers)
            dao.insertCustomersIgnore(SampleSeedData.demoCustomers)
            dao.insertWorkersIgnore(SampleSeedData.demoWorkers)
            dao.insertVerificationsIgnore(SampleSeedData.demoVerifications)
            dao.insertRequestsIgnore(SampleSeedData.demoRequests)
            dao.insertReportsIgnore(SampleSeedData.demoReports)
        }
        if (dao.getConversationCount() == 0) {
            dao.insertConversationsIgnore(SampleSeedData.demoConversations)
            dao.insertMessagesIgnore(SampleSeedData.demoMessages)
        }
    }

    suspend fun saveCustomerProfile(
        customerId: String,
        fullName: String,
        rawPhone: String,
        provinceId: String,
        divisionId: String,
        districtId: String,
        talukaId: String,
        city: String,
        area: String
    ) {
        val masked = PrivacyUtils.maskPhone(rawPhone)
        dao.upsertUser(
            UserEntity(
                id = customerId,
                fullName = fullName.trim(),
                phoneMasked = masked,
                role = UserRole.CUSTOMER.name,
                provinceId = provinceId,
                divisionId = divisionId,
                districtId = districtId,
                talukaId = talukaId,
                city = city.trim(),
                area = area.trim(),
                isPhoneVerified = false,
                isDemoData = false
            )
        )
        dao.upsertCustomer(
            CustomerProfileEntity(
                id = customerId,
                userId = customerId,
                fullName = fullName.trim(),
                phoneMasked = masked,
                provinceId = provinceId,
                divisionId = divisionId,
                districtId = districtId,
                talukaId = talukaId,
                city = city.trim(),
                area = area.trim(),
                isDemoData = false
            )
        )
    }

    suspend fun registerOrUpdateWorker(
        existingWorkerId: String?,
        userId: String,
        fullName: String,
        rawPhone: String,
        rawCnic: String,
        provinceId: String,
        divisionId: String,
        districtId: String,
        talukaId: String,
        city: String,
        area: String,
        categoryKeys: List<String>,
        experienceYears: Int,
        skillsSummary: String,
        basePricePkr: Int,
        profilePhotoUri: String,
        portfolioUris: List<String>,
        isAvailable: Boolean
    ): String {
        val workerId = existingWorkerId ?: "wrk_${UUID.randomUUID().toString().take(8)}"
        val maskedPhone = PrivacyUtils.maskPhone(rawPhone)
        val maskedCnic = if (rawCnic.isNotBlank()) PrivacyUtils.maskCnic(rawCnic) else ""
        val verificationStatus = if (maskedCnic.isNotBlank()) {
            VerificationStatus.PENDING_REVIEW.name
        } else {
            VerificationStatus.UNVERIFIED.name
        }

        dao.upsertUser(
            UserEntity(
                id = userId,
                fullName = fullName.trim(),
                phoneMasked = maskedPhone,
                role = UserRole.WORKER.name,
                provinceId = provinceId,
                divisionId = divisionId,
                districtId = districtId,
                talukaId = talukaId,
                city = city.trim(),
                area = area.trim(),
                isPhoneVerified = false,
                isDemoData = false
            )
        )

        val worker = WorkerProfileEntity(
            id = workerId,
            userId = userId,
            fullName = fullName.trim(),
            phoneMasked = maskedPhone,
            dialablePhone = rawPhone.filter { it.isDigit() },
            provinceId = provinceId,
            divisionId = divisionId,
            districtId = districtId,
            talukaId = talukaId,
            city = city.trim(),
            area = area.trim(),
            categoryKeysCsv = categoryKeys.joinToString(","),
            experienceYears = experienceYears.coerceAtLeast(0),
            skillsSummary = skillsSummary.trim(),
            basePricePkr = basePricePkr.coerceAtLeast(0),
            profilePhotoUri = profilePhotoUri,
            portfolioUrisCsv = portfolioUris.joinToString("|"),
            isAvailable = isAvailable,
            approvalStatus = ApprovalStatus.APPROVED.name, // Visible in directory; CNIC stays unverified/pending until Admin verifies
            cnicVerificationStatus = verificationStatus,
            cnicMasked = maskedCnic,
            isFeatured = false,
            isDemoData = false
        )
        dao.upsertWorker(worker)

        if (maskedCnic.isNotBlank()) {
            dao.upsertVerification(
                VerificationRecordEntity(
                    id = "ver_${workerId}",
                    workerId = workerId,
                    workerName = fullName.trim(),
                    cnicMasked = maskedCnic,
                    note = "Submitted via worker registration",
                    status = VerificationStatus.PENDING_REVIEW.name,
                    isDemoData = false
                )
            )
        }
        return workerId
    }

    suspend fun toggleWorkerAvailability(workerId: String, available: Boolean) {
        dao.updateWorkerAvailability(workerId, available)
    }

    suspend fun submitServiceRequest(
        customerId: String,
        customerName: String,
        worker: WorkerProfileEntity,
        categoryKey: String,
        area: String,
        description: String,
        offeredPricePkr: Int,
        preferredTime: String
    ) {
        dao.upsertRequest(
            ServiceRequestEntity(
                id = "req_${UUID.randomUUID().toString().take(8)}",
                customerId = customerId,
                customerName = customerName,
                workerId = worker.id,
                workerName = worker.fullName,
                categoryKey = categoryKey,
                provinceId = worker.provinceId,
                divisionId = worker.divisionId,
                districtId = worker.districtId,
                talukaId = worker.talukaId,
                city = worker.city,
                area = area.ifBlank { worker.area },
                description = description.trim(),
                offeredPricePkr = offeredPricePkr,
                preferredTime = preferredTime.trim(),
                status = RequestStatus.PENDING.name,
                isDemoData = false
            )
        )
    }

    suspend fun updateRequestStatus(requestId: String, status: RequestStatus) {
        dao.updateRequestStatus(requestId, status.name)
    }

    suspend fun submitReview(
        workerId: String,
        customerId: String,
        customerName: String,
        rating: Int,
        comment: String
    ) {
        dao.insertReview(
            ReviewEntity(
                id = "rev_${UUID.randomUUID().toString().take(8)}",
                workerId = workerId,
                customerId = customerId,
                customerName = customerName,
                rating = rating.coerceIn(1, 5),
                comment = comment.trim(),
                isDemoData = false
            )
        )
    }

    suspend fun submitReport(
        reporterUserId: String,
        reporterName: String,
        worker: WorkerProfileEntity,
        reason: String,
        details: String
    ) {
        dao.insertReport(
            ReportEntity(
                id = "rep_${UUID.randomUUID().toString().take(8)}",
                reporterUserId = reporterUserId,
                reporterName = reporterName,
                targetWorkerId = worker.id,
                targetWorkerName = worker.fullName,
                reason = reason.trim(),
                details = details.trim(),
                status = ReportStatus.OPEN.name,
                isDemoData = false
            )
        )
    }

    // Admin actions
    suspend fun adminApproveWorker(workerId: String, verifyCnic: Boolean) {
        val verStatus = if (verifyCnic) VerificationStatus.VERIFIED.name else VerificationStatus.UNVERIFIED.name
        dao.updateWorkerApprovalAndVerification(
            workerId = workerId,
            approvalStatus = ApprovalStatus.APPROVED.name,
            verificationStatus = verStatus
        )
        if (verifyCnic) {
            dao.updateVerificationStatusByWorker(
                workerId = workerId,
                status = VerificationStatus.VERIFIED.name,
                reviewedAt = System.currentTimeMillis()
            )
        }
    }

    suspend fun adminRejectWorker(workerId: String) {
        dao.updateWorkerApprovalAndVerification(
            workerId = workerId,
            approvalStatus = ApprovalStatus.REJECTED.name,
            verificationStatus = VerificationStatus.REJECTED.name
        )
        dao.updateVerificationStatusByWorker(
            workerId = workerId,
            status = VerificationStatus.REJECTED.name,
            reviewedAt = System.currentTimeMillis()
        )
    }

    suspend fun adminToggleFeatured(workerId: String, featured: Boolean) {
        dao.setWorkerFeatured(workerId, featured)
    }

    suspend fun adminToggleCategory(key: String, enabled: Boolean) {
        dao.setCategoryEnabled(key, enabled)
    }

    suspend fun adminAddCategory(nameEn: String, nameUr: String, nameSd: String) {
        val key = nameEn.lowercase().replace(Regex("[^a-z0-9]+"), "_").trim('_')
            .ifBlank { "cat_${UUID.randomUUID().toString().take(4)}" }
        dao.upsertCategory(
            ServiceCategoryEntity(
                key = key,
                nameEn = nameEn.trim(),
                nameUr = nameUr.trim().ifBlank { nameEn.trim() },
                nameSd = nameSd.trim().ifBlank { nameEn.trim() },
                iconKey = "handyman",
                isEnabled = true,
                sortOrder = 99
            )
        )
    }

    suspend fun adminUpdateReportStatus(reportId: String, status: ReportStatus) {
        dao.updateReportStatus(reportId, status.name)
    }

    // In-App Messages & Location Pin / Address Sharing
    suspend fun markConversationRead(conversationId: String) {
        dao.clearConversationUnread(conversationId)
        dao.markMessagesRead(conversationId)
    }

    suspend fun getOrCreateConversationWithWorker(
        customerId: String,
        customerName: String,
        worker: WorkerProfileEntity
    ): String {
        val existing = dao.getConversationForPair(customerId, worker.id)
        if (existing != null) {
            dao.clearConversationUnread(existing.id)
            dao.markMessagesRead(existing.id)
            return existing.id
        }
        val convId = "conv_${UUID.randomUUID().toString().take(8)}"
        val now = System.currentTimeMillis()
        val conv = ChatConversationEntity(
            id = convId,
            customerId = customerId,
            customerName = customerName,
            workerId = worker.id,
            workerName = worker.fullName,
            categoryKey = worker.categoryKeys.firstOrNull() ?: "other",
            locationLabel = listOf(worker.area, worker.city).filter { it.isNotBlank() }.joinToString(", "),
            lastMessagePreview = "Chat started with ${worker.fullName}",
            lastMessageAt = now,
            unreadCount = 0,
            hasSharedAddress = false,
            hasSharedGpsPin = false,
            isDemoData = false
        )
        dao.upsertConversation(conv)
        return convId
    }

    suspend fun sendChatTextMessage(
        conversationId: String,
        senderRole: UserRole,
        senderName: String,
        body: String
    ) {
        val trimmed = body.trim()
        if (trimmed.isEmpty()) return
        val existing = dao.getConversationById(conversationId) ?: return
        val now = System.currentTimeMillis()
        dao.insertMessage(
            ChatMessageEntity(
                id = "msg_${UUID.randomUUID().toString().take(8)}",
                conversationId = conversationId,
                senderRole = senderRole.name,
                senderName = senderName,
                messageType = ChatMessageType.TEXT.name,
                body = trimmed,
                isRead = true,
                isDemoData = false,
                createdAt = now
            )
        )
        dao.upsertConversation(
            existing.copy(
                lastMessagePreview = trimmed,
                lastMessageAt = now,
                unreadCount = 0
            )
        )
    }

    suspend fun shareHomeAddressInChat(
        conversationId: String,
        senderRole: UserRole,
        senderName: String,
        streetAddress: String,
        landmark: String,
        cityArea: String,
        note: String
    ) {
        if (streetAddress.isBlank() && cityArea.isBlank()) return
        val existing = dao.getConversationById(conversationId) ?: return
        val now = System.currentTimeMillis()
        val preview = listOf(streetAddress.trim(), cityArea.trim()).filter { it.isNotEmpty() }.joinToString(", ")
        dao.insertMessage(
            ChatMessageEntity(
                id = "msg_${UUID.randomUUID().toString().take(8)}",
                conversationId = conversationId,
                senderRole = senderRole.name,
                senderName = senderName,
                messageType = ChatMessageType.HOME_ADDRESS.name,
                body = note.trim(),
                addressStreet = streetAddress.trim(),
                addressLandmark = landmark.trim(),
                addressCityArea = cityArea.trim(),
                isRead = true,
                isDemoData = false,
                createdAt = now
            )
        )
        dao.upsertConversation(
            existing.copy(
                lastMessagePreview = preview.ifBlank { "Shared Home Address" },
                lastMessageAt = now,
                unreadCount = 0,
                hasSharedAddress = true
            )
        )
    }

    suspend fun shareGpsPinInChat(
        conversationId: String,
        senderRole: UserRole,
        senderName: String,
        latitude: Double,
        longitude: Double,
        cityArea: String,
        note: String
    ) {
        val existing = dao.getConversationById(conversationId) ?: return
        val now = System.currentTimeMillis()
        val label = note.trim().ifBlank { "GPS Location Pin" }
        dao.insertMessage(
            ChatMessageEntity(
                id = "msg_${UUID.randomUUID().toString().take(8)}",
                conversationId = conversationId,
                senderRole = senderRole.name,
                senderName = senderName,
                messageType = ChatMessageType.GPS_PIN.name,
                body = label,
                addressCityArea = cityArea.trim(),
                latitude = latitude,
                longitude = longitude,
                isRead = true,
                isDemoData = false,
                createdAt = now
            )
        )
        dao.upsertConversation(
            existing.copy(
                lastMessagePreview = "$label (${"%.4f".format(latitude)}, ${"%.4f".format(longitude)})",
                lastMessageAt = now,
                unreadCount = 0,
                hasSharedGpsPin = true
            )
        )
    }
}
