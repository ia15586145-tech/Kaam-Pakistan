package com.example.data.local

import com.example.data.model.ApprovalStatus
import com.example.data.model.ChatConversationEntity
import com.example.data.model.ChatMessageEntity
import com.example.data.model.ChatMessageType
import com.example.data.model.CustomerProfileEntity
import com.example.data.model.ReportEntity
import com.example.data.model.ReportStatus
import com.example.data.model.RequestStatus
import com.example.data.model.ServiceCategoryEntity
import com.example.data.model.ServiceRequestEntity
import com.example.data.model.UserEntity
import com.example.data.model.UserRole
import com.example.data.model.VerificationRecordEntity
import com.example.data.model.VerificationStatus
import com.example.data.model.WorkerProfileEntity

object SampleSeedData {

    val defaultCategories = listOf(
        ServiceCategoryEntity("electrician", "Electrician", "الیکٹریشن", "اليڪٽريشن", "bolt", true, 1),
        ServiceCategoryEntity("plumber", "Plumber", "پلمبر", "پلمبر", "plumbing", true, 2),
        ServiceCategoryEntity("ac_tech", "AC Technician", "اے سی ٹیکنیشن", "اي سي ٽيڪنيشن", "ac_unit", true, 3),
        ServiceCategoryEntity("mason", "Mason", "معمار", "رازو", "construction", true, 4),
        ServiceCategoryEntity("painter", "Painter", "پینٹر", "رنگساز", "format_paint", true, 5),
        ServiceCategoryEntity("carpenter", "Carpenter", "بڑھئی", "واڍو", "carpenter", true, 6),
        ServiceCategoryEntity("cleaner", "Cleaner", "صفائی کارکن", "صفائي ڪندڙ", "cleaning_services", true, 7),
        ServiceCategoryEntity("appliance_repair", "Home Appliance Repair", "گھریلو آلات مرمت", "گهريلو سامان مرمت", "kitchen", true, 8),
        ServiceCategoryEntity("solar_tech", "Solar Technician", "سولر ٹیکنیشن", "سولر ٽيڪنيشن", "solar_power", true, 9),
        ServiceCategoryEntity("mechanic", "Mechanic", "مکینک", "مڪينڪ", "build", true, 10),
        ServiceCategoryEntity("other", "Other Services", "دیگر خدمات", "ٻيون خدمتون", "handyman", true, 11)
    )

    val demoUsers = PreviewWorkerMockDataSource.sampleUsers + listOf(
        UserEntity(
            id = "cust_local_1",
            fullName = "Bilal Ahmed",
            phoneMasked = "0300 ••••412",
            role = UserRole.CUSTOMER.name,
            provinceId = "sindh",
            divisionId = "sindh_div_karachi",
            districtId = "sindh_karachi_east",
            talukaId = "ke_gulshan_e_iqbal",
            city = "Karachi East",
            area = "Gulshan-e-Iqbal",
            isPhoneVerified = false,
            isDemoData = true
        ),
        UserEntity(
            id = "wrk_user_larkana",
            fullName = "Altaf Hussain Bhutto",
            phoneMasked = "0344 ••••392",
            role = UserRole.WORKER.name,
            provinceId = "sindh",
            divisionId = "sindh_div_larkana",
            districtId = "sindh_larkana",
            talukaId = "larkana_ratodero",
            city = "Larkana",
            area = "Ratodero",
            isPhoneVerified = false,
            isDemoData = true
        ),
        UserEntity(
            id = "wrk_user_thar",
            fullName = "Ramesh Kumar Maheshwari",
            phoneMasked = "0334 ••••740",
            role = UserRole.WORKER.name,
            provinceId = "sindh",
            divisionId = "sindh_div_mirpurkhas",
            districtId = "sindh_tharparkar",
            talukaId = "thar_mithi",
            city = "Tharparkar",
            area = "Mithi",
            isPhoneVerified = false,
            isDemoData = true
        ),
        UserEntity(
            id = "wrk_user_nawabshah",
            fullName = "Imran Ali Zardari",
            phoneMasked = "0315 ••••128",
            role = UserRole.WORKER.name,
            provinceId = "sindh",
            divisionId = "sindh_div_sba",
            districtId = "sindh_shaheed_benazirabad",
            talukaId = "sba_nawabshah",
            city = "Shaheed Benazirabad / Nawabshah",
            area = "Nawabshah",
            isPhoneVerified = false,
            isDemoData = true
        ),
        // Gilgit-Baltistan, AJK & ICT Sample Users
        UserEntity(
            id = "wrk_user_gilgit",
            fullName = "Zulfiqar Ali Hunzai",
            phoneMasked = "0355 ••••418",
            role = UserRole.WORKER.name,
            provinceId = "gb",
            divisionId = "gb_div_gilgit",
            districtId = "gb_gilgit",
            talukaId = "glg_gilgit",
            city = "Gilgit",
            area = "Gilgit",
            isPhoneVerified = false,
            isDemoData = true
        ),
        UserEntity(
            id = "wrk_user_skardu",
            fullName = "Muhammad Hassan Balti",
            phoneMasked = "0355 ••••902",
            role = UserRole.WORKER.name,
            provinceId = "gb",
            divisionId = "gb_div_baltistan",
            districtId = "gb_skardu",
            talukaId = "skd_skardu",
            city = "Skardu",
            area = "Skardu",
            isPhoneVerified = false,
            isDemoData = true
        ),
        UserEntity(
            id = "wrk_user_muzaffarabad",
            fullName = "Raja Kashif Abbasi",
            phoneMasked = "0341 ••••764",
            role = UserRole.WORKER.name,
            provinceId = "ajk",
            divisionId = "ajk_div_muzaffarabad",
            districtId = "ajk_muzaffarabad",
            talukaId = "mzd_muzaffarabad",
            city = "Muzaffarabad",
            area = "Muzaffarabad",
            isPhoneVerified = false,
            isDemoData = true
        ),
        UserEntity(
            id = "wrk_user_mirpur",
            fullName = "Chaudhry Tariq Mehmood",
            phoneMasked = "0331 ••••325",
            role = UserRole.WORKER.name,
            provinceId = "ajk",
            divisionId = "ajk_div_mirpur",
            districtId = "ajk_mirpur",
            talukaId = "mrp_mirpur",
            city = "Mirpur",
            area = "Mirpur",
            isPhoneVerified = false,
            isDemoData = true
        ),
        UserEntity(
            id = "wrk_user_islamabad",
            fullName = "Faisal Mehmood Qureshi",
            phoneMasked = "0300 ••••651",
            role = UserRole.WORKER.name,
            provinceId = "ict",
            divisionId = "ict_div_islamabad",
            districtId = "ict_islamabad",
            talukaId = "isb_city_sectors",
            city = "Islamabad",
            area = "Islamabad City / Sectors",
            isPhoneVerified = false,
            isDemoData = true
        )
    )

    val demoCustomers = listOf(
        CustomerProfileEntity(
            id = "cust_local_1",
            userId = "cust_local_1",
            fullName = "Bilal Ahmed",
            phoneMasked = "0300 ••••412",
            provinceId = "sindh",
            divisionId = "sindh_div_karachi",
            districtId = "sindh_karachi_east",
            talukaId = "ke_gulshan_e_iqbal",
            city = "Karachi East",
            area = "Gulshan-e-Iqbal",
            isDemoData = true
        )
    )

    val demoWorkers = PreviewWorkerMockDataSource.sampleWorkers + listOf(
        WorkerProfileEntity(
            id = "wrk_demo_larkana",
            userId = "wrk_user_larkana",
            fullName = "Altaf Hussain Bhutto",
            phoneMasked = "0344 ••••392",
            dialablePhone = "03440000392",
            provinceId = "sindh",
            divisionId = "sindh_div_larkana",
            districtId = "sindh_larkana",
            talukaId = "larkana_ratodero",
            city = "Larkana",
            area = "Ratodero",
            categoryKeysCsv = "plumber,mechanic",
            experienceYears = 7,
            skillsSummary = "PPRC plumbing, submersible water pump repair, generator & bike servicing",
            basePricePkr = 1000,
            isAvailable = true,
            approvalStatus = ApprovalStatus.APPROVED.name,
            cnicVerificationStatus = VerificationStatus.PENDING_REVIEW.name,
            cnicMasked = "43203-•••••••-9",
            isFeatured = false,
            isDemoData = true
        ),
        WorkerProfileEntity(
            id = "wrk_demo_thar",
            userId = "wrk_user_thar",
            fullName = "Ramesh Kumar Maheshwari",
            phoneMasked = "0334 ••••740",
            dialablePhone = "03340000740",
            provinceId = "sindh",
            divisionId = "sindh_div_mirpurkhas",
            districtId = "sindh_tharparkar",
            talukaId = "thar_mithi",
            city = "Tharparkar",
            area = "Mithi",
            categoryKeysCsv = "solar_tech,plumber",
            experienceYears = 6,
            skillsSummary = "Off-grid solar panel mounting, RO water filter maintenance, sanitary fitting",
            basePricePkr = 950,
            isAvailable = true,
            approvalStatus = ApprovalStatus.APPROVED.name,
            cnicVerificationStatus = VerificationStatus.UNVERIFIED.name,
            cnicMasked = "",
            isFeatured = false,
            isDemoData = true
        ),
        WorkerProfileEntity(
            id = "wrk_demo_nawabshah",
            userId = "wrk_user_nawabshah",
            fullName = "Imran Ali Zardari",
            phoneMasked = "0315 ••••128",
            dialablePhone = "03150000128",
            provinceId = "sindh",
            divisionId = "sindh_div_sba",
            districtId = "sindh_shaheed_benazirabad",
            talukaId = "sba_nawabshah",
            city = "Shaheed Benazirabad / Nawabshah",
            area = "Nawabshah",
            categoryKeysCsv = "carpenter,painter",
            experienceYears = 10,
            skillsSummary = "Custom wooden doors, kitchen cabinets, weather-shield exterior & distemper paint",
            basePricePkr = 1300,
            isAvailable = false,
            approvalStatus = ApprovalStatus.PENDING.name,
            cnicVerificationStatus = VerificationStatus.PENDING_REVIEW.name,
            cnicMasked = "45402-•••••••-7",
            isFeatured = false,
            isDemoData = true
        ),
        // Gilgit-Baltistan Sample Workers
        WorkerProfileEntity(
            id = "wrk_demo_gilgit",
            userId = "wrk_user_gilgit",
            fullName = "Zulfiqar Ali Hunzai",
            phoneMasked = "0355 ••••418",
            dialablePhone = "03550000418",
            provinceId = "gb",
            divisionId = "gb_div_gilgit",
            districtId = "gb_gilgit",
            talukaId = "glg_gilgit",
            city = "Gilgit",
            area = "Gilgit",
            categoryKeysCsv = "solar_tech,electrician",
            experienceYears = 9,
            skillsSummary = "High-altitude solar PV & lithium backup systems, house wiring, micro-hydro panel setup",
            basePricePkr = 1400,
            isAvailable = true,
            approvalStatus = ApprovalStatus.APPROVED.name,
            cnicVerificationStatus = VerificationStatus.PENDING_REVIEW.name,
            cnicMasked = "71501-•••••••-3",
            isFeatured = true,
            isDemoData = true
        ),
        WorkerProfileEntity(
            id = "wrk_demo_skardu",
            userId = "wrk_user_skardu",
            fullName = "Muhammad Hassan Balti",
            phoneMasked = "0355 ••••902",
            dialablePhone = "03550000902",
            provinceId = "gb",
            divisionId = "gb_div_baltistan",
            districtId = "gb_skardu",
            talukaId = "skd_skardu",
            city = "Skardu",
            area = "Skardu",
            categoryKeysCsv = "plumber,mason",
            experienceYears = 8,
            skillsSummary = "Winterized insulated water piping, stone masonry, solar water heater fitting",
            basePricePkr = 1350,
            isAvailable = true,
            approvalStatus = ApprovalStatus.APPROVED.name,
            cnicVerificationStatus = VerificationStatus.UNVERIFIED.name,
            cnicMasked = "",
            isFeatured = false,
            isDemoData = true
        ),
        // Azad Jammu & Kashmir Sample Workers
        WorkerProfileEntity(
            id = "wrk_demo_muzaffarabad",
            userId = "wrk_user_muzaffarabad",
            fullName = "Raja Kashif Abbasi",
            phoneMasked = "0341 ••••764",
            dialablePhone = "03410000764",
            provinceId = "ajk",
            divisionId = "ajk_div_muzaffarabad",
            districtId = "ajk_muzaffarabad",
            talukaId = "mzd_muzaffarabad",
            city = "Muzaffarabad",
            area = "Muzaffarabad",
            categoryKeysCsv = "electrician,appliance_repair",
            experienceYears = 7,
            skillsSummary = "Home wiring, UPS & inverter setup, washing machine & microwave fault repair",
            basePricePkr = 1200,
            isAvailable = true,
            approvalStatus = ApprovalStatus.APPROVED.name,
            cnicVerificationStatus = VerificationStatus.PENDING_REVIEW.name,
            cnicMasked = "82203-•••••••-5",
            isFeatured = false,
            isDemoData = true
        ),
        WorkerProfileEntity(
            id = "wrk_demo_mirpur",
            userId = "wrk_user_mirpur",
            fullName = "Chaudhry Tariq Mehmood",
            phoneMasked = "0331 ••••325",
            dialablePhone = "03310000325",
            provinceId = "ajk",
            divisionId = "ajk_div_mirpur",
            districtId = "ajk_mirpur",
            talukaId = "mrp_mirpur",
            city = "Mirpur",
            area = "Mirpur",
            categoryKeysCsv = "ac_tech,plumber",
            experienceYears = 10,
            skillsSummary = "Multi-split inverter AC installation, concealed PPRC plumbing, water pressure pumps",
            basePricePkr = 1500,
            isAvailable = true,
            approvalStatus = ApprovalStatus.APPROVED.name,
            cnicVerificationStatus = VerificationStatus.UNVERIFIED.name,
            cnicMasked = "",
            isFeatured = true,
            isDemoData = true
        ),
        // Islamabad Capital Territory Sample Worker
        WorkerProfileEntity(
            id = "wrk_demo_islamabad",
            userId = "wrk_user_islamabad",
            fullName = "Faisal Mehmood Qureshi",
            phoneMasked = "0300 ••••651",
            dialablePhone = "03000000651",
            provinceId = "ict",
            divisionId = "ict_div_islamabad",
            districtId = "ict_islamabad",
            talukaId = "isb_city_sectors",
            city = "Islamabad",
            area = "Islamabad City / Sectors",
            categoryKeysCsv = "ac_tech,solar_tech,electrician",
            experienceYears = 9,
            skillsSummary = "CDA sector residential AC servicing, on-grid net-metering solar & smart DB panels",
            basePricePkr = 1600,
            isAvailable = true,
            approvalStatus = ApprovalStatus.APPROVED.name,
            cnicVerificationStatus = VerificationStatus.PENDING_REVIEW.name,
            cnicMasked = "61101-•••••••-9",
            isFeatured = true,
            isDemoData = true
        )
    )

    val demoVerifications = listOf(
        VerificationRecordEntity(
            id = "ver_demo_1",
            workerId = "wrk_demo_1",
            workerName = "Ustad Tariq Mahmood",
            cnicMasked = "42201-•••••••-5",
            note = "Sample preview ID record awaiting admin verification",
            status = VerificationStatus.PENDING_REVIEW.name,
            isDemoData = true
        ),
        VerificationRecordEntity(
            id = "ver_demo_2",
            workerId = "wrk_demo_2",
            workerName = "Sajid Hussain Soomro",
            cnicMasked = "41304-•••••••-1",
            note = "Sample preview ID record awaiting admin verification",
            status = VerificationStatus.PENDING_REVIEW.name,
            isDemoData = true
        ),
        VerificationRecordEntity(
            id = "ver_demo_dadu",
            workerId = "wrk_demo_dadu",
            workerName = "Ghulam Qadir Jamali",
            cnicMasked = "41203-•••••••-3",
            note = "Sample preview ID record (Johi, Dadu) awaiting admin verification",
            status = VerificationStatus.PENDING_REVIEW.name,
            isDemoData = true
        ),
        VerificationRecordEntity(
            id = "ver_demo_rwp",
            workerId = "wrk_demo_rwp",
            workerName = "Usman Tariq Raja",
            cnicMasked = "37402-•••••••-1",
            note = "Sample preview ID record (Gujar Khan, Rawalpindi) awaiting admin verification",
            status = VerificationStatus.PENDING_REVIEW.name,
            isDemoData = true
        ),
        VerificationRecordEntity(
            id = "ver_demo_quetta",
            workerId = "wrk_demo_quetta",
            workerName = "Mirwais Khan Kakar",
            cnicMasked = "54400-•••••••-3",
            note = "Sample preview ID record (Quetta City, Quetta) awaiting admin verification",
            status = VerificationStatus.PENDING_REVIEW.name,
            isDemoData = true
        ),
        VerificationRecordEntity(
            id = "ver_demo_peshawar",
            workerId = "wrk_demo_peshawar",
            workerName = "Salman Khan Afridi",
            cnicMasked = "17301-•••••••-5",
            note = "Sample preview ID record (Peshawar City, Peshawar) awaiting admin verification",
            status = VerificationStatus.PENDING_REVIEW.name,
            isDemoData = true
        ),
        VerificationRecordEntity(
            id = "ver_demo_islamabad",
            workerId = "wrk_demo_islamabad",
            workerName = "Faisal Mehmood Qureshi",
            cnicMasked = "61101-•••••••-9",
            note = "Sample preview ID record (Islamabad City / Sectors) awaiting admin verification",
            status = VerificationStatus.PENDING_REVIEW.name,
            isDemoData = true
        )
    )

    val demoRequests = listOf(
        ServiceRequestEntity(
            id = "req_demo_1",
            customerId = "cust_local_1",
            customerName = "Bilal Ahmed",
            workerId = "wrk_demo_1",
            workerName = "Ustad Tariq Mahmood",
            categoryKey = "electrician",
            provinceId = "sindh",
            divisionId = "sindh_div_karachi",
            districtId = "sindh_karachi_east",
            talukaId = "ke_gulshan_e_iqbal",
            city = "Karachi East",
            area = "Gulshan-e-Iqbal",
            description = "Main breaker tripping when water pump and AC run together.",
            offeredPricePkr = 1500,
            preferredTime = "Today, 5:00 PM",
            status = RequestStatus.PENDING.name,
            isDemoData = true
        )
    )

    val demoReports = listOf(
        ReportEntity(
            id = "rep_demo_1",
            reporterUserId = "cust_local_1",
            reporterName = "Bilal Ahmed",
            targetWorkerId = "wrk_demo_3",
            targetWorkerName = "Kamran Ali",
            reason = "Price Mismatch",
            details = "Sample preview complaint to test Admin moderation queue.",
            status = ReportStatus.OPEN.name,
            isDemoData = true
        )
    )

    val demoConversations: List<ChatConversationEntity> = run {
        val now = 1_763_680_000_000L
        listOf(
            ChatConversationEntity(
                id = "conv_demo_1",
                customerId = "cust_local_1",
                customerName = "Bilal Ahmed",
                workerId = "wrk_demo_1",
                workerName = "Ustad Tariq Mahmood",
                categoryKey = "electrician",
                locationLabel = "Gulshan-e-Iqbal, Karachi East",
                lastMessagePreview = "Near Hasan Square now, reaching your gate in 20 minutes.",
                lastMessageAt = now - 6 * 60_000L,
                unreadCount = 2,
                hasSharedAddress = true,
                hasSharedGpsPin = true,
                isDemoData = true
            ),
            ChatConversationEntity(
                id = "conv_demo_2",
                customerId = "cust_local_1",
                customerName = "Bilal Ahmed",
                workerId = "wrk_demo_2",
                workerName = "Sajid Hussain Soomro",
                categoryKey = "ac_tech",
                locationLabel = "Qasimabad, Hyderabad",
                lastMessagePreview = "Can visit after 4:30 PM with R-410A gauge and nitrogen test kit.",
                lastMessageAt = now - 42 * 60_000L,
                unreadCount = 1,
                hasSharedAddress = true,
                hasSharedGpsPin = false,
                isDemoData = true
            ),
            ChatConversationEntity(
                id = "conv_demo_3",
                customerId = "cust_local_1",
                customerName = "Bilal Ahmed",
                workerId = "wrk_demo_3",
                workerName = "Kamran Ali",
                categoryKey = "plumber",
                locationLabel = "Model Town, Lahore",
                lastMessagePreview = "Pinned hardware point near Link Road for float valve pickup.",
                lastMessageAt = now - 3 * 3_600_000L,
                unreadCount = 0,
                hasSharedAddress = false,
                hasSharedGpsPin = true,
                isDemoData = true
            )
        )
    }

    val demoMessages: List<ChatMessageEntity> = run {
        val now = 1_763_680_000_000L
        listOf(
            // Conversation 1: Electrician (Home Address + GPS Pin + 2 unread replies)
            ChatMessageEntity(
                id = "msg_demo_1_1",
                conversationId = "conv_demo_1",
                senderRole = UserRole.CUSTOMER.name,
                senderName = "Bilal Ahmed",
                messageType = ChatMessageType.TEXT.name,
                body = "Assalam-o-Alaikum Ustad Tariq, main breaker trips when AC and water pump run together.",
                isRead = true,
                isDemoData = true,
                createdAt = now - 30 * 60_000L
            ),
            ChatMessageEntity(
                id = "msg_demo_1_2",
                conversationId = "conv_demo_1",
                senderRole = UserRole.CUSTOMER.name,
                senderName = "Bilal Ahmed",
                messageType = ChatMessageType.HOME_ADDRESS.name,
                body = "Ring bell at main green gate.",
                addressStreet = "House B-14, Block 13-D",
                addressLandmark = "Near Urdu University Road & Al-Noor Masjid",
                addressCityArea = "Gulshan-e-Iqbal, Karachi East",
                isRead = true,
                isDemoData = true,
                createdAt = now - 28 * 60_000L
            ),
            ChatMessageEntity(
                id = "msg_demo_1_3",
                conversationId = "conv_demo_1",
                senderRole = UserRole.CUSTOMER.name,
                senderName = "Bilal Ahmed",
                messageType = ChatMessageType.GPS_PIN.name,
                body = "Home Entrance Pin",
                addressCityArea = "Gulshan-e-Iqbal, Karachi East",
                latitude = 24.9204,
                longitude = 67.0882,
                isRead = true,
                isDemoData = true,
                createdAt = now - 27 * 60_000L
            ),
            ChatMessageEntity(
                id = "msg_demo_1_4",
                conversationId = "conv_demo_1",
                senderRole = UserRole.WORKER.name,
                senderName = "Ustad Tariq Mahmood",
                messageType = ChatMessageType.TEXT.name,
                body = "Walaikum Assalam Bilal bhai, received your home address and GPS pin. Bringing clamp meter & 32A breaker.",
                isRead = false,
                isDemoData = true,
                createdAt = now - 14 * 60_000L
            ),
            ChatMessageEntity(
                id = "msg_demo_1_5",
                conversationId = "conv_demo_1",
                senderRole = UserRole.WORKER.name,
                senderName = "Ustad Tariq Mahmood",
                messageType = ChatMessageType.TEXT.name,
                body = "Near Hasan Square now, reaching your gate in 20 minutes.",
                isRead = false,
                isDemoData = true,
                createdAt = now - 6 * 60_000L
            ),

            // Conversation 2: AC Technician (Home Address + 1 unread reply)
            ChatMessageEntity(
                id = "msg_demo_2_1",
                conversationId = "conv_demo_2",
                senderRole = UserRole.CUSTOMER.name,
                senderName = "Bilal Ahmed",
                messageType = ChatMessageType.TEXT.name,
                body = "Sajid bhai, 1.5 ton inverter AC is blowing warm air. Can you check gas pressure today?",
                isRead = true,
                isDemoData = true,
                createdAt = now - 60 * 60_000L
            ),
            ChatMessageEntity(
                id = "msg_demo_2_2",
                conversationId = "conv_demo_2",
                senderRole = UserRole.CUSTOMER.name,
                senderName = "Bilal Ahmed",
                messageType = ChatMessageType.HOME_ADDRESS.name,
                body = "Second floor apartment, lift available.",
                addressStreet = "Flat 204, Sindh Terrace, Phase 1",
                addressLandmark = "Opposite Citizen Colony Gate",
                addressCityArea = "Qasimabad, Hyderabad",
                isRead = true,
                isDemoData = true,
                createdAt = now - 56 * 60_000L
            ),
            ChatMessageEntity(
                id = "msg_demo_2_3",
                conversationId = "conv_demo_2",
                senderRole = UserRole.WORKER.name,
                senderName = "Sajid Hussain Soomro",
                messageType = ChatMessageType.TEXT.name,
                body = "Can visit after 4:30 PM with R-410A gauge and nitrogen test kit.",
                isRead = false,
                isDemoData = true,
                createdAt = now - 42 * 60_000L
            ),

            // Conversation 3: Plumber (GPS Pin, all read)
            ChatMessageEntity(
                id = "msg_demo_3_1",
                conversationId = "conv_demo_3",
                senderRole = UserRole.WORKER.name,
                senderName = "Kamran Ali",
                messageType = ChatMessageType.TEXT.name,
                body = "Assalam-o-Alaikum, PPRC elbow and gate valve replacement completed cleanly.",
                isRead = true,
                isDemoData = true,
                createdAt = now - 4 * 3_600_000L
            ),
            ChatMessageEntity(
                id = "msg_demo_3_2",
                conversationId = "conv_demo_3",
                senderRole = UserRole.WORKER.name,
                senderName = "Kamran Ali",
                messageType = ChatMessageType.GPS_PIN.name,
                body = "Pinned hardware point near Link Road for float valve pickup.",
                addressCityArea = "Model Town, Lahore",
                latitude = 31.4805,
                longitude = 74.3239,
                isRead = true,
                isDemoData = true,
                createdAt = now - 3 * 3_600_000L
            )
        )
    }
}
