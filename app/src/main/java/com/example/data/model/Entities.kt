package com.example.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.i18n.AppLanguage

enum class UserRole {
    CUSTOMER,
    WORKER,
    ADMIN
}

enum class ApprovalStatus {
    PENDING,
    APPROVED,
    REJECTED
}

enum class VerificationStatus {
    UNVERIFIED,
    PENDING_REVIEW,
    VERIFIED,
    REJECTED
}

enum class RequestStatus {
    PENDING,
    ACCEPTED,
    REJECTED,
    COMPLETED,
    CANCELLED
}

enum class ReportStatus {
    OPEN,
    INVESTIGATING,
    RESOLVED,
    DISMISSED
}

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val fullName: String,
    val phoneMasked: String,
    val role: String, // UserRole.name
    val provinceId: String = "sindh",
    val divisionId: String = "sindh_div_karachi",
    val districtId: String = "sindh_karachi_east",
    val talukaId: String = "ke_gulshan_e_iqbal",
    val city: String,
    val area: String,
    val languageCode: String = AppLanguage.ENGLISH.code,
    val isPhoneVerified: Boolean = false,
    val isDemoData: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "worker_profiles")
data class WorkerProfileEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val fullName: String,
    val phoneMasked: String,
    val dialablePhone: String, // Used strictly for ACTION_DIAL / WhatsApp intent, masked in UI
    val provinceId: String = "sindh",
    val divisionId: String = "sindh_div_karachi",
    val districtId: String = "sindh_karachi_east",
    val talukaId: String = "ke_gulshan_e_iqbal",
    val city: String,
    val area: String,
    val categoryKeysCsv: String,
    val experienceYears: Int,
    val skillsSummary: String,
    val basePricePkr: Int,
    val profilePhotoUri: String = "",
    val portfolioUrisCsv: String = "",
    val isAvailable: Boolean = true,
    val approvalStatus: String = ApprovalStatus.PENDING.name,
    val cnicVerificationStatus: String = VerificationStatus.UNVERIFIED.name,
    val cnicMasked: String = "",
    val isFeatured: Boolean = false,
    val isDemoData: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
) {
    val categoryKeys: List<String>
        get() = categoryKeysCsv.split(",").map { it.trim() }.filter { it.isNotEmpty() }

    val portfolioUris: List<String>
        get() = portfolioUrisCsv.split("|").map { it.trim() }.filter { it.isNotEmpty() }
}

@Entity(tableName = "customer_profiles")
data class CustomerProfileEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val fullName: String,
    val phoneMasked: String,
    val provinceId: String = "sindh",
    val divisionId: String = "sindh_div_karachi",
    val districtId: String = "sindh_karachi_east",
    val talukaId: String = "ke_gulshan_e_iqbal",
    val city: String,
    val area: String,
    val isDemoData: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "service_categories")
data class ServiceCategoryEntity(
    @PrimaryKey val key: String,
    val nameEn: String,
    val nameUr: String,
    val nameSd: String,
    val iconKey: String,
    val isEnabled: Boolean = true,
    val sortOrder: Int = 0
) {
    fun localizedName(language: AppLanguage): String = when (language) {
        AppLanguage.ENGLISH -> nameEn
        AppLanguage.URDU -> nameUr
        AppLanguage.SINDHI -> nameSd
    }
}

@Entity(tableName = "service_requests")
data class ServiceRequestEntity(
    @PrimaryKey val id: String,
    val customerId: String,
    val customerName: String,
    val workerId: String,
    val workerName: String,
    val categoryKey: String,
    val provinceId: String = "sindh",
    val divisionId: String = "sindh_div_karachi",
    val districtId: String = "sindh_karachi_east",
    val talukaId: String = "ke_gulshan_e_iqbal",
    val city: String,
    val area: String,
    val description: String,
    val offeredPricePkr: Int,
    val preferredTime: String,
    val status: String = RequestStatus.PENDING.name,
    val isDemoData: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "reviews")
data class ReviewEntity(
    @PrimaryKey val id: String,
    val workerId: String,
    val customerId: String,
    val customerName: String,
    val rating: Int,
    val comment: String,
    val isDemoData: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "verification_records")
data class VerificationRecordEntity(
    @PrimaryKey val id: String,
    val workerId: String,
    val workerName: String,
    val cnicMasked: String,
    val note: String,
    val status: String = VerificationStatus.PENDING_REVIEW.name,
    val isDemoData: Boolean = false,
    val submittedAt: Long = System.currentTimeMillis(),
    val reviewedAt: Long? = null
)

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey val id: String,
    val reporterUserId: String,
    val reporterName: String,
    val targetWorkerId: String,
    val targetWorkerName: String,
    val reason: String,
    val details: String,
    val status: String = ReportStatus.OPEN.name,
    val isDemoData: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

enum class ChatMessageType {
    TEXT,
    HOME_ADDRESS,
    GPS_PIN
}

@Entity(tableName = "chat_conversations")
data class ChatConversationEntity(
    @PrimaryKey val id: String,
    val customerId: String,
    val customerName: String,
    val workerId: String,
    val workerName: String,
    val categoryKey: String,
    val locationLabel: String,
    val lastMessagePreview: String,
    val lastMessageAt: Long = System.currentTimeMillis(),
    val unreadCount: Int = 0,
    val hasSharedAddress: Boolean = false,
    val hasSharedGpsPin: Boolean = false,
    val isDemoData: Boolean = false
)

@Entity(tableName = "chat_messages")
data class ChatMessageEntity(
    @PrimaryKey val id: String,
    val conversationId: String,
    val senderRole: String,
    val senderName: String,
    val messageType: String = ChatMessageType.TEXT.name,
    val body: String,
    val addressStreet: String = "",
    val addressLandmark: String = "",
    val addressCityArea: String = "",
    val latitude: Double? = null,
    val longitude: Double? = null,
    val isRead: Boolean = false,
    val isDemoData: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

data class WorkerWithStats(
    val worker: WorkerProfileEntity,
    val averageRating: Double,
    val reviewCount: Int
)
