package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.model.ChatConversationEntity
import com.example.data.model.ChatMessageEntity
import com.example.data.model.CustomerProfileEntity
import com.example.data.model.ReportEntity
import com.example.data.model.ReviewEntity
import com.example.data.model.ServiceCategoryEntity
import com.example.data.model.ServiceRequestEntity
import com.example.data.model.UserEntity
import com.example.data.model.VerificationRecordEntity
import com.example.data.model.WorkerProfileEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface KaamDao {
    // Categories
    @Query("SELECT * FROM service_categories ORDER BY sortOrder ASC")
    fun observeAllCategories(): Flow<List<ServiceCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(categories: List<ServiceCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCategory(category: ServiceCategoryEntity)

    @Query("UPDATE service_categories SET isEnabled = :enabled WHERE key = :key")
    suspend fun setCategoryEnabled(key: String, enabled: Boolean)

    // Users
    @Query("SELECT * FROM users ORDER BY createdAt DESC")
    fun observeAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertUser(user: UserEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUsersIgnore(users: List<UserEntity>)

    // Customers
    @Query("SELECT * FROM customer_profiles ORDER BY createdAt DESC")
    fun observeAllCustomers(): Flow<List<CustomerProfileEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertCustomer(customer: CustomerProfileEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCustomersIgnore(customers: List<CustomerProfileEntity>)

    // Workers
    @Query("SELECT * FROM worker_profiles ORDER BY isFeatured DESC, createdAt DESC")
    fun observeAllWorkers(): Flow<List<WorkerProfileEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWorker(worker: WorkerProfileEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkersIgnore(workers: List<WorkerProfileEntity>)

    @Query("UPDATE worker_profiles SET isAvailable = :available WHERE id = :workerId")
    suspend fun updateWorkerAvailability(workerId: String, available: Boolean)

    @Query("UPDATE worker_profiles SET approvalStatus = :approvalStatus, cnicVerificationStatus = :verificationStatus WHERE id = :workerId")
    suspend fun updateWorkerApprovalAndVerification(
        workerId: String,
        approvalStatus: String,
        verificationStatus: String
    )

    @Query("UPDATE worker_profiles SET isFeatured = :featured WHERE id = :workerId")
    suspend fun setWorkerFeatured(workerId: String, featured: Boolean)

    // Service Requests
    @Query("SELECT * FROM service_requests ORDER BY createdAt DESC")
    fun observeAllRequests(): Flow<List<ServiceRequestEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertRequest(request: ServiceRequestEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRequestsIgnore(requests: List<ServiceRequestEntity>)

    @Query("UPDATE service_requests SET status = :status WHERE id = :requestId")
    suspend fun updateRequestStatus(requestId: String, status: String)

    // Reviews
    @Query("SELECT * FROM reviews ORDER BY createdAt DESC")
    fun observeAllReviews(): Flow<List<ReviewEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReview(review: ReviewEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReviewsIgnore(reviews: List<ReviewEntity>)

    // Verification Records
    @Query("SELECT * FROM verification_records ORDER BY submittedAt DESC")
    fun observeAllVerifications(): Flow<List<VerificationRecordEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertVerification(record: VerificationRecordEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVerificationsIgnore(records: List<VerificationRecordEntity>)

    @Query("UPDATE verification_records SET status = :status, reviewedAt = :reviewedAt WHERE workerId = :workerId")
    suspend fun updateVerificationStatusByWorker(workerId: String, status: String, reviewedAt: Long)

    // Reports
    @Query("SELECT * FROM reports ORDER BY createdAt DESC")
    fun observeAllReports(): Flow<List<ReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ReportEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertReportsIgnore(reports: List<ReportEntity>)

    @Query("UPDATE reports SET status = :status WHERE id = :reportId")
    suspend fun updateReportStatus(reportId: String, status: String)

    // Chat Conversations & Messages
    @Query("SELECT * FROM chat_conversations ORDER BY lastMessageAt DESC")
    fun observeAllConversations(): Flow<List<ChatConversationEntity>>

    @Query("SELECT * FROM chat_conversations WHERE id = :conversationId LIMIT 1")
    suspend fun getConversationById(conversationId: String): ChatConversationEntity?

    @Query("SELECT * FROM chat_conversations WHERE customerId = :customerId AND workerId = :workerId LIMIT 1")
    suspend fun getConversationForPair(customerId: String, workerId: String): ChatConversationEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertConversation(conversation: ChatConversationEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertConversationsIgnore(conversations: List<ChatConversationEntity>)

    @Query("SELECT * FROM chat_messages ORDER BY createdAt ASC")
    fun observeAllMessages(): Flow<List<ChatMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: ChatMessageEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMessagesIgnore(messages: List<ChatMessageEntity>)

    @Query("UPDATE chat_conversations SET unreadCount = 0 WHERE id = :conversationId")
    suspend fun clearConversationUnread(conversationId: String)

    @Query("UPDATE chat_messages SET isRead = 1 WHERE conversationId = :conversationId")
    suspend fun markMessagesRead(conversationId: String)

    @Query("SELECT COUNT(*) FROM chat_conversations")
    suspend fun getConversationCount(): Int

    // Count check for initial seed
    @Query("SELECT COUNT(*) FROM service_categories")
    suspend fun getCategoryCount(): Int
}
