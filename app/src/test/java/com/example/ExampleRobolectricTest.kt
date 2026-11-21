package com.example

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.KaamDatabase
import com.example.data.prefs.KaamPrefsStore
import com.example.ui.KaamViewModel
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Before
  fun setUp() {
    KaamDatabase.resetForTests()
    KaamPrefsStore.resetForTests()
  }

  @After
  fun tearDown() {
    KaamDatabase.resetForTests()
    KaamPrefsStore.resetForTests()
  }

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("Kaam Pakistan", appName)
  }

  @Test
  fun `viewModel exposes PakistanLocations provider and cascading helpers`() {
    val app = ApplicationProvider.getApplicationContext<Application>()
    val viewModel = KaamViewModel(app)

    assertEquals(7, viewModel.locationsProvider.stats.provinceCount)
    assertEquals(38, viewModel.locationsProvider.stats.divisionCount)
    assertEquals(168, viewModel.locationsProvider.stats.districtCount)
    assertEquals(679, viewModel.locationsProvider.stats.talukaCount)

    assertTrue(viewModel.divisionsFor("sindh").any { it.id == "sindh_div_karachi" })
    assertTrue(viewModel.districtsFor("punjab", "punjab_div_lahore").any { it.id == "punjab_lahore" })
    assertTrue(viewModel.tehsilsFor("kp", "kp_div_malakand", "kp_swat").any { it.second.id == "swt_babuzai_mingora" })

    val resolved = viewModel.resolveStrictLocation("balochistan", null, null, null)
    assertEquals("balochistan", resolved.province.id)
    assertEquals("bl_div_quetta", resolved.division.id)
    assertEquals("bl_quetta", resolved.district.id)
    assertEquals("qta_city", resolved.taluka.id)
  }
}
