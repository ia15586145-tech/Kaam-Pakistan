package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
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

@Database(
    entities = [
        UserEntity::class,
        WorkerProfileEntity::class,
        CustomerProfileEntity::class,
        ServiceCategoryEntity::class,
        ServiceRequestEntity::class,
        ReviewEntity::class,
        VerificationRecordEntity::class,
        ReportEntity::class,
        ChatConversationEntity::class,
        ChatMessageEntity::class
    ],
    version = 8,
    exportSchema = false
)
abstract class KaamDatabase : RoomDatabase() {
    abstract fun kaamDao(): KaamDao

    companion object {
        @Volatile
        private var INSTANCE: KaamDatabase? = null

        fun getInstance(context: Context): KaamDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    KaamDatabase::class.java,
                    "kaam_pakistan.db"
                ).fallbackToDestructiveMigration(dropAllTables = true)
                    .build().also { INSTANCE = it }
            }
        }

        internal fun resetForTests() {
            synchronized(this) {
                INSTANCE?.close()
                INSTANCE = null
            }
        }
    }
}
