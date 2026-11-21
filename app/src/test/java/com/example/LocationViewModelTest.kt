package com.example

import com.example.data.local.PreviewWorkerMockDataSource
import com.example.data.local.SampleSeedData
import com.example.data.model.ApprovalStatus
import com.example.data.model.VerificationStatus
import com.example.ui.LocationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LocationViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `PreviewWorkerMockDataSource provides 14 approved workers across all 4 provinces and all 11 categories`() {
        val previewWorkers = PreviewWorkerMockDataSource.getPreviewWorkers()
        assertEquals(14, previewWorkers.size)

        val byProvince = previewWorkers.groupingBy { it.worker.provinceId }.eachCount()
        assertEquals(4, byProvince["sindh"])
        assertEquals(4, byProvince["punjab"])
        assertEquals(3, byProvince["balochistan"])
        assertEquals(3, byProvince["kp"])

        val coveredCategories = previewWorkers.flatMap { it.worker.categoryKeys }.toSet()
        val allCategories = SampleSeedData.defaultCategories.map { it.key }.toSet()
        assertEquals(allCategories, coveredCategories)

        // Integrity checks: no fake verified badges, no fake ratings, demo flag set, masked phones
        previewWorkers.forEach { item ->
            assertTrue(item.worker.isDemoData)
            assertEquals(ApprovalStatus.APPROVED.name, item.worker.approvalStatus)
            assertFalse(item.worker.cnicVerificationStatus == VerificationStatus.VERIFIED.name)
            assertEquals(0.0, item.averageRating, 0.0)
            assertEquals(0, item.reviewCount)
            assertTrue(item.worker.phoneMasked.contains("•"))
        }
    }

    @Test
    fun `SampleSeedData has unique user and worker IDs when combined with PreviewWorkerMockDataSource`() {
        val userIds = SampleSeedData.demoUsers.map { it.id }
        assertEquals(userIds.size, userIds.distinct().size)

        val workerIds = SampleSeedData.demoWorkers.map { it.id }
        assertEquals(workerIds.size, workerIds.distinct().size)
    }

    @Test
    fun `LocationViewModel filters preview workers across Province Division District and Tehsil`() {
        val viewModel = LocationViewModel()
        assertEquals(14, viewModel.uiState.value.previewWorkers.size)
        assertEquals(14, viewModel.uiState.value.filteredPreviewWorkers.size)

        // Province filter: Sindh -> 4
        viewModel.selectLocation("sindh", null, null, null)
        assertEquals(4, viewModel.uiState.value.filteredPreviewWorkers.size)

        // Division filter: Hyderabad Division -> Sajid (Hyderabad) & Ghulam Qadir (Dadu)
        viewModel.selectLocation("sindh", "sindh_div_hyderabad", null, null)
        assertEquals(2, viewModel.uiState.value.filteredPreviewWorkers.size)

        // District filter: Dadu -> Ghulam Qadir Jamali
        viewModel.selectLocation("sindh", "sindh_div_hyderabad", "sindh_dadu", null)
        assertEquals(listOf("wrk_demo_dadu"), viewModel.uiState.value.filteredPreviewWorkers.map { it.worker.id })

        // Tehsil/Taluka filter: Punjab -> Lahore -> Lahore -> Model Town
        viewModel.selectLocation("punjab", "punjab_div_lahore", "punjab_lahore", "lhr_model_town")
        assertEquals(listOf("wrk_demo_3"), viewModel.uiState.value.filteredPreviewWorkers.map { it.worker.id })

        // Balochistan: Makran -> Gwadar
        viewModel.selectLocation("balochistan", "bl_div_makran", "bl_gwadar", "gwd_gwadar")
        assertEquals(listOf("wrk_demo_gwadar"), viewModel.uiState.value.filteredPreviewWorkers.map { it.worker.id })

        // KP: Malakand -> Swat -> Babuzai / Mingora
        viewModel.selectLocation("kp", "kp_div_malakand", "kp_swat", "swt_babuzai_mingora")
        assertEquals(listOf("wrk_demo_swat"), viewModel.uiState.value.filteredPreviewWorkers.map { it.worker.id })

        viewModel.clearFilters()
        assertEquals(14, viewModel.uiState.value.filteredPreviewWorkers.size)
    }

    @Test
    fun `LocationViewModel filters by category trilingual search and preview toggle`() {
        val viewModel = LocationViewModel()

        // Filter by province + category: Punjab + solar_tech -> Rawalpindi worker
        viewModel.selectLocation("punjab", null, null, null)
        viewModel.selectCategory("solar_tech")
        assertEquals(listOf("wrk_demo_rwp"), viewModel.uiState.value.filteredPreviewWorkers.map { it.worker.id })

        viewModel.clearFilters()

        // Trilingual Urdu location search: کوئٹہ (Quetta)
        viewModel.updateSearchQuery("کوئٹہ")
        assertEquals(listOf("wrk_demo_quetta"), viewModel.uiState.value.filteredPreviewWorkers.map { it.worker.id })

        // Trilingual Sindhi location search: دادو (Dadu)
        viewModel.updateSearchQuery("دادو")
        assertEquals(listOf("wrk_demo_dadu"), viewModel.uiState.value.filteredPreviewWorkers.map { it.worker.id })

        // Trilingual Urdu category search: پلمبر (Plumber)
        viewModel.updateSearchQuery("پلمبر")
        val plumberIds = viewModel.uiState.value.filteredPreviewWorkers.map { it.worker.id }.toSet()
        assertTrue(plumberIds.containsAll(setOf("wrk_demo_3", "wrk_demo_quetta", "wrk_demo_abbottabad")))

        // Turning off preview mode hides all mock workers
        viewModel.clearFilters()
        viewModel.setPreviewMode(false)
        assertTrue(viewModel.uiState.value.previewWorkers.isEmpty())
        assertTrue(viewModel.uiState.value.filteredPreviewWorkers.isEmpty())

        // Re-enabling restores all 14 preview workers
        viewModel.setPreviewMode(true)
        assertEquals(14, viewModel.uiState.value.filteredPreviewWorkers.size)
    }
}
