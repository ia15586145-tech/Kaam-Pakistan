package com.example

import com.example.data.model.PakistanLocations
import com.example.i18n.AppLanguage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class LocationDataUnitTest {

    @Test
    fun sindhContainsAll30DistrictsAndAllTalukasWithTrilingualNames() {
        val sindh = PakistanLocations.provinceById("sindh")
        assertNotNull(sindh)
        assertEquals(30, sindh!!.districts.size)

        val expectedDistrictNames = listOf(
            "Dadu", "Jamshoro", "Hyderabad", "Matiari", "Tando Allahyar",
            "Tando Muhammad Khan", "Badin", "Thatta", "Sujawal", "Mirpurkhas",
            "Umerkot", "Tharparkar", "Shaheed Benazirabad / Nawabshah", "Sanghar",
            "Naushahro Feroze", "Sukkur", "Khairpur", "Ghotki", "Larkana",
            "Qambar Shahdadkot", "Shikarpur", "Jacobabad", "Kashmore",
            "Karachi Central", "Karachi East", "Karachi South", "Karachi West",
            "Keamari", "Korangi", "Malir"
        )
        assertEquals(expectedDistrictNames, sindh.districts.map { it.en })

        sindh.districts.forEach { district ->
            assertTrue("District ${district.en} has empty Sindhi", district.sd.isNotBlank())
            assertTrue("District ${district.en} has empty Urdu", district.ur.isNotBlank())
            assertTrue("District ${district.en} has no talukas", district.talukas.isNotEmpty())
            district.talukas.forEach { taluka ->
                assertTrue("Taluka ${taluka.en} has empty Sindhi", taluka.sd.isNotBlank())
                assertTrue("Taluka ${taluka.en} has empty Urdu", taluka.ur.isNotBlank())
            }
        }

        val totalSindhTalukas = sindh.districts.sumOf { it.talukas.size }
        assertEquals(139, totalSindhTalukas)
        assertEquals(6, sindh.divisions.size)
    }

    @Test
    fun punjabContainsAll10Divisions41DistrictsAndAllTehsilsWithTrilingualNames() {
        val punjab = PakistanLocations.provinceById("punjab")
        assertNotNull(punjab)
        assertEquals(10, punjab!!.divisions.size)
        assertEquals(41, punjab.districts.size)

        val expectedDivisionNames = listOf(
            "Lahore Division",
            "Rawalpindi Division",
            "Gujranwala Division",
            "Gujrat Division",
            "Faisalabad Division",
            "Sargodha Division",
            "Multan Division",
            "Sahiwal Division",
            "Bahawalpur Division",
            "Dera Ghazi Khan Division"
        )
        assertEquals(expectedDivisionNames, punjab.divisions.map { it.en })

        val expectedDistrictNames = listOf(
            // 1. Lahore Division
            "Lahore", "Kasur", "Sheikhupura", "Nankana Sahib",
            // 2. Rawalpindi Division
            "Rawalpindi", "Murree", "Attock", "Jhelum", "Chakwal", "Talagang",
            // 3. Gujranwala Division
            "Gujranwala", "Sialkot", "Narowal",
            // 4. Gujrat Division
            "Gujrat", "Mandi Bahauddin", "Hafizabad", "Wazirabad",
            // 5. Faisalabad Division
            "Faisalabad", "Jhang", "Toba Tek Singh", "Chiniot",
            // 6. Sargodha Division
            "Sargodha", "Khushab", "Mianwali", "Bhakkar",
            // 7. Multan Division
            "Multan", "Khanewal", "Lodhran", "Vehari",
            // 8. Sahiwal Division
            "Sahiwal", "Okara", "Pakpattan",
            // 9. Bahawalpur Division
            "Bahawalpur", "Bahawalnagar", "Rahim Yar Khan",
            // 10. Dera Ghazi Khan Division
            "Dera Ghazi Khan", "Taunsa", "Muzaffargarh", "Kot Addu", "Rajanpur", "Layyah"
        )
        assertEquals(expectedDistrictNames, punjab.districts.map { it.en })

        val seenDivisionIds = mutableSetOf<String>()
        val seenDistrictIds = mutableSetOf<String>()
        val seenTehsilIds = mutableSetOf<String>()

        punjab.divisions.forEach { division ->
            assertTrue("Duplicate division id ${division.id}", seenDivisionIds.add(division.id))
            assertTrue("Division ${division.en} has empty Sindhi", division.sd.isNotBlank())
            assertTrue("Division ${division.en} has empty Urdu", division.ur.isNotBlank())
            assertTrue("Division ${division.en} has no districts", division.districts.isNotEmpty())

            division.districts.forEach { district ->
                assertTrue("Duplicate district id ${district.id}", seenDistrictIds.add(district.id))
                assertTrue("District ${district.en} has empty Sindhi", district.sd.isNotBlank())
                assertTrue("District ${district.en} has empty Urdu", district.ur.isNotBlank())
                assertTrue("District ${district.en} has no tehsils", district.talukas.isNotEmpty())

                district.talukas.forEach { tehsil ->
                    assertTrue("Duplicate tehsil id ${tehsil.id}", seenTehsilIds.add(tehsil.id))
                    assertTrue("Tehsil ${tehsil.en} has empty Sindhi", tehsil.sd.isNotBlank())
                    assertTrue("Tehsil ${tehsil.en} has empty Urdu", tehsil.ur.isNotBlank())
                }
            }
        }

        val totalPunjabTehsils = punjab.districts.sumOf { it.talukas.size }
        assertEquals(151, totalPunjabTehsils)
    }

    @Test
    fun fourLevelHierarchyLookupsResolveCorrectly() {
        val div = PakistanLocations.divisionForDistrict("punjab_rahim_yar_khan")
        assertNotNull(div)
        assertEquals("punjab_div_bahawalpur", div!!.id)
        assertEquals("punjab", PakistanLocations.provinceForDivision(div.id)?.id)
        assertEquals("punjab", PakistanLocations.provinceForDistrict("punjab_taunsa")?.id)
        assertEquals("Chowk Sarwar Shaheed", PakistanLocations.talukaById("punjab_kot_addu", "kad_chowk_sarwar_shaheed")?.en)
    }

    @Test
    fun trilingualSearchMatchesEnglishSindhiAndUrdu() {
        assertTrue(PakistanLocations.matchesLocationQuery("sindh", "sindh_dadu", "dadu_johi", "Johi"))
        assertTrue(PakistanLocations.matchesLocationQuery("sindh", "sindh_dadu", "dadu_johi", "جوھي"))
        assertTrue(PakistanLocations.matchesLocationQuery("sindh", "sindh_dadu", "dadu_johi", "جوہی"))

        assertTrue(
            PakistanLocations.matchesLocationQuery(
                provinceId = "punjab",
                districtId = "punjab_rawalpindi",
                talukaId = "rwp_gujar_khan",
                query = "گوجر خان",
                divisionId = "punjab_div_rawalpindi"
            )
        )
        assertTrue(
            PakistanLocations.matchesLocationQuery(
                provinceId = "punjab",
                districtId = "punjab_dg_khan",
                talukaId = "dgk_de_excluded",
                query = "ڊيره غازي خان"
            )
        )
        assertTrue(
            PakistanLocations.matchesLocationQuery(
                provinceId = "punjab",
                districtId = "punjab_multan",
                talukaId = "mlt_city",
                query = "Multan Division"
            )
        )

        val formattedSd = PakistanLocations.formatWorkerLocation(
            "sindh", "sindh_dadu", "dadu_johi", "Johi", "Dadu", AppLanguage.SINDHI
        )
        assertEquals("جوھي, دادو", formattedSd)

        val formattedUrPunjab = PakistanLocations.formatWorkerLocation(
            "punjab", "punjab_lahore", "lhr_model_town", "Model Town", "Lahore", AppLanguage.URDU
        )
        assertEquals("ماڈل ٹاؤن, لاہور", formattedUrPunjab)

        assertTrue(
            PakistanLocations.matchesLocationQuery(
                provinceId = "balochistan",
                districtId = "bl_gwadar",
                talukaId = "gwd_ormara",
                query = "اورماڙه",
                divisionId = "bl_div_makran"
            )
        )
        assertTrue(
            PakistanLocations.matchesLocationQuery(
                provinceId = "balochistan",
                districtId = "bl_hub",
                talukaId = "hub_gadani",
                query = "گڈانی"
            )
        )
        val formattedSdBalochistan = PakistanLocations.formatWorkerLocation(
            "balochistan", "bl_hub", "hub_gadani", "Gadani", "Hub", AppLanguage.SINDHI
        )
        assertEquals("گڏاڻي, حب", formattedSdBalochistan)
    }

    @Test
    fun balochistanContainsAll8Divisions36DistrictsAndAllTehsilsWithTrilingualNames() {
        val balochistan = PakistanLocations.provinceById("balochistan")
        assertNotNull(balochistan)
        assertEquals(8, balochistan!!.divisions.size)
        assertEquals(36, balochistan.districts.size)

        val expectedDivisionNames = listOf(
            "Quetta Division",
            "Kalat Division",
            "Makran Division",
            "Nasirabad Division",
            "Sibi Division",
            "Zhob Division",
            "Loralai Division",
            "Rakhshan Division"
        )
        assertEquals(expectedDivisionNames, balochistan.divisions.map { it.en })

        val expectedDistrictNames = listOf(
            // 1. Quetta Division
            "Quetta", "Pishin", "Killa Abdullah", "Chaman",
            // 2. Kalat Division
            "Kalat", "Mastung", "Khuzdar", "Awaran", "Lasbela", "Hub", "Surab",
            // 3. Makran Division
            "Kech / Turbat", "Gwadar", "Panjgur",
            // 4. Nasirabad Division
            "Nasirabad", "Jaffarabad", "Usta Muhammad", "Sohbatpur", "Kachhi / Bolan", "Jhal Magsi",
            // 5. Sibi Division
            "Sibi", "Harnai", "Ziarat", "Kohlu", "Dera Bugti",
            // 6. Zhob Division
            "Zhob", "Sherani", "Killa Saifullah",
            // 7. Loralai Division
            "Loralai", "Barkhan", "Musakhel", "Duki",
            // 8. Rakhshan Division
            "Chagai", "Nushki", "Kharan", "Washuk"
        )
        assertEquals(expectedDistrictNames, balochistan.districts.map { it.en })

        val seenDivisionIds = mutableSetOf<String>()
        val seenDistrictIds = mutableSetOf<String>()
        val seenTehsilIds = mutableSetOf<String>()

        balochistan.divisions.forEach { division ->
            assertTrue("Duplicate division id ${division.id}", seenDivisionIds.add(division.id))
            assertTrue("Division ${division.en} has empty Sindhi", division.sd.isNotBlank())
            assertTrue("Division ${division.en} has empty Urdu", division.ur.isNotBlank())
            assertTrue("Division ${division.en} has no districts", division.districts.isNotEmpty())

            division.districts.forEach { district ->
                assertTrue("Duplicate district id ${district.id}", seenDistrictIds.add(district.id))
                assertTrue("District ${district.en} has empty Sindhi", district.sd.isNotBlank())
                assertTrue("District ${district.en} has empty Urdu", district.ur.isNotBlank())
                assertTrue("District ${district.en} has no tehsils", district.talukas.isNotEmpty())

                district.talukas.forEach { tehsil ->
                    assertTrue("Duplicate tehsil id ${tehsil.id}", seenTehsilIds.add(tehsil.id))
                    assertTrue("Tehsil ${tehsil.en} has empty Sindhi", tehsil.sd.isNotBlank())
                    assertTrue("Tehsil ${tehsil.en} has empty Urdu", tehsil.ur.isNotBlank())
                }
            }
        }

        val totalBalochistanTehsils = balochistan.districts.sumOf { it.talukas.size }
        assertEquals(155, totalBalochistanTehsils)

        assertEquals("bl_div_rakhshan", PakistanLocations.divisionForDistrict("bl_washuk")?.id)
        assertEquals("balochistan", PakistanLocations.provinceForDistrict("bl_surab")?.id)
        assertEquals("Sonmiani / Winder", PakistanLocations.talukaById("bl_lasbela", "lsb_sonmiani_winder")?.en)
    }

    @Test
    fun kpContainsAll7Divisions37ListedDistrictsAndAllTehsilsWithTrilingualNames() {
        val kp = PakistanLocations.provinceById("kp")
        assertNotNull(kp)
        assertEquals("خيبر پختونخواهه", kp!!.sd)
        assertEquals(7, kp.divisions.size)
        assertEquals(37, kp.districts.size)

        val expectedDivisionNames = listOf(
            "Peshawar Division",
            "Mardan Division",
            "Hazara Division",
            "Malakand Division",
            "Kohat Division",
            "Bannu Division",
            "Dera Ismail Khan Division"
        )
        assertEquals(expectedDivisionNames, kp.divisions.map { it.en })

        val expectedDistrictNames = listOf(
            // 1. Peshawar Division
            "Peshawar", "Charsadda", "Nowshera", "Khyber", "Mohmand",
            // 2. Mardan Division
            "Mardan", "Swabi",
            // 3. Hazara Division
            "Abbottabad", "Mansehra", "Haripur", "Battagram", "Torghar",
            "Upper Kohistan", "Lower Kohistan", "Kolai-Palas",
            // 4. Malakand Division
            "Swat", "Buner", "Shangla", "Malakand", "Lower Dir", "Upper Dir",
            "Central Dir", "Lower Chitral", "Upper Chitral", "Bajaur",
            // 5. Kohat Division
            "Kohat", "Karak", "Hangu", "Kurram", "Orakzai",
            // 6. Bannu Division
            "Bannu", "Lakki Marwat", "North Waziristan",
            // 7. Dera Ismail Khan Division
            "Dera Ismail Khan", "Tank", "Upper South Waziristan", "Lower South Waziristan"
        )
        assertEquals(expectedDistrictNames, kp.districts.map { it.en })

        val seenDivisionIds = mutableSetOf<String>()
        val seenDistrictIds = mutableSetOf<String>()
        val seenTehsilIds = mutableSetOf<String>()

        kp.divisions.forEach { division ->
            assertTrue("Duplicate division id ${division.id}", seenDivisionIds.add(division.id))
            assertTrue("Division ${division.en} has empty Sindhi", division.sd.isNotBlank())
            assertTrue("Division ${division.en} has empty Urdu", division.ur.isNotBlank())
            assertTrue("Division ${division.en} has no districts", division.districts.isNotEmpty())

            division.districts.forEach { district ->
                assertTrue("Duplicate district id ${district.id}", seenDistrictIds.add(district.id))
                assertTrue("District ${district.en} has empty Sindhi", district.sd.isNotBlank())
                assertTrue("District ${district.en} has empty Urdu", district.ur.isNotBlank())
                assertTrue("District ${district.en} has no tehsils", district.talukas.isNotEmpty())

                district.talukas.forEach { tehsil ->
                    assertTrue("Duplicate tehsil id ${tehsil.id}", seenTehsilIds.add(tehsil.id))
                    assertTrue("Tehsil ${tehsil.en} has empty Sindhi", tehsil.sd.isNotBlank())
                    assertTrue("Tehsil ${tehsil.en} has empty Urdu", tehsil.ur.isNotBlank())
                }
            }
        }

        val totalKpTehsils = kp.districts.sumOf { it.talukas.size }
        assertEquals(163, totalKpTehsils)

        assertEquals("kp_div_malakand", PakistanLocations.divisionForDistrict("kp_bajaur")?.id)
        assertEquals("kp", PakistanLocations.provinceForDistrict("kp_lower_south_waziristan")?.id)
        assertEquals("Babuzai / Mingora", PakistanLocations.talukaById("kp_swat", "swt_babuzai_mingora")?.en)

        assertTrue(
            PakistanLocations.matchesLocationQuery(
                provinceId = "kp",
                districtId = "kp_swat",
                talukaId = "swt_babuzai_mingora",
                query = "مينگوره",
                divisionId = "kp_div_malakand"
            )
        )
        assertTrue(
            PakistanLocations.matchesLocationQuery(
                provinceId = "kp",
                districtId = "kp_abbottabad",
                talukaId = "abt_havelian",
                query = "حویلیاں"
            )
        )
        val formattedUrKp = PakistanLocations.formatWorkerLocation(
            "kp", "kp_peshawar", "psh_city", "Peshawar City", "Peshawar", AppLanguage.URDU
        )
        assertEquals("پشاور سٹی, پشاور", formattedUrKp)
    }

    @Test
    fun remainingTerritoriesContainAllDivisionsDistrictsAndTehsilsWithTrilingualNames() {
        // 1. Gilgit-Baltistan: 3 Divisions, 13 Districts, 31 Tehsils
        val gb = PakistanLocations.provinceById("gb")
        assertNotNull(gb)
        assertEquals(3, gb!!.divisions.size)
        assertEquals(13, gb.districts.size)
        assertEquals(
            listOf("Gilgit Division", "Baltistan Division", "Diamer Division"),
            gb.divisions.map { it.en }
        )
        assertEquals(
            listOf(
                "Gilgit", "Hunza", "Nagar", "Ghizer", "Gupis-Yasin",
                "Skardu", "Ghanche", "Shigar", "Kharmang",
                "Diamer", "Astore", "Darel", "Tangir"
            ),
            gb.districts.map { it.en }
        )
        assertEquals(31, gb.districts.sumOf { it.talukas.size })

        // 2. Azad Jammu & Kashmir: 3 Divisions, 10 Districts, 33 Tehsils
        val ajk = PakistanLocations.provinceById("ajk")
        assertNotNull(ajk)
        assertEquals(3, ajk!!.divisions.size)
        assertEquals(10, ajk.districts.size)
        assertEquals(
            listOf("Muzaffarabad Division", "Poonch Division", "Mirpur Division"),
            ajk.divisions.map { it.en }
        )
        assertEquals(
            listOf(
                "Muzaffarabad", "Hattian Bala", "Neelum",
                "Poonch / Rawalakot", "Bagh", "Haveli", "Sudhanoti",
                "Mirpur", "Bhimber", "Kotli"
            ),
            ajk.districts.map { it.en }
        )
        assertEquals(33, ajk.districts.sumOf { it.talukas.size })

        // 3. Islamabad Capital Territory: 1 Division, 1 District, 7 Tehsils/Sub-divisions
        val ict = PakistanLocations.provinceById("ict")
        assertNotNull(ict)
        assertEquals(1, ict!!.divisions.size)
        assertEquals(1, ict.districts.size)
        assertEquals(7, ict.districts.sumOf { it.talukas.size })

        // Global check across all 7 provinces/territories
        assertEquals(7, PakistanLocations.provinces.size)
        val allDivIds = mutableSetOf<String>()
        val allDistIds = mutableSetOf<String>()
        val allTalukaIds = mutableSetOf<String>()

        PakistanLocations.provinces.forEach { province ->
            assertTrue(province.en.isNotBlank() && province.sd.isNotBlank() && province.ur.isNotBlank())
            province.divisions.forEach { division ->
                assertTrue("Duplicate division id ${division.id}", allDivIds.add(division.id))
                assertTrue(division.en.isNotBlank() && division.sd.isNotBlank() && division.ur.isNotBlank())
                division.districts.forEach { district ->
                    assertTrue("Duplicate district id ${district.id}", allDistIds.add(district.id))
                    assertTrue(district.en.isNotBlank() && district.sd.isNotBlank() && district.ur.isNotBlank())
                    district.talukas.forEach { taluka ->
                        assertTrue("Duplicate taluka id ${taluka.id}", allTalukaIds.add(taluka.id))
                        assertTrue(taluka.en.isNotBlank() && taluka.sd.isNotBlank() && taluka.ur.isNotBlank())
                    }
                }
            }
        }

        assertTrue(
            PakistanLocations.matchesLocationQuery(
                provinceId = "gb",
                districtId = "gb_hunza",
                talukaId = "hnz_aliabad",
                query = "علي آباد",
                divisionId = "gb_div_gilgit"
            )
        )
        assertTrue(
            PakistanLocations.matchesLocationQuery(
                provinceId = "ajk",
                districtId = "ajk_kotli",
                talukaId = "ktl_nakyal",
                query = "نکیال"
            )
        )
        assertEquals(
            "روات, اسلام آباد",
            PakistanLocations.formatWorkerLocation(
                "ict", "ict_islamabad", "isb_rawat", "Rawat", "Islamabad", AppLanguage.URDU
            )
        )
    }

    @Test
    fun providerInstanceExposesCascadeHelpersStatsAndStrictResolution() {
        val provider = PakistanLocations()

        assertEquals(7, provider.stats.provinceCount)
        assertEquals(38, provider.stats.divisionCount)
        assertEquals(168, provider.stats.districtCount)
        assertEquals(679, provider.stats.talukaCount)

        // Cascading hierarchy queries
        assertEquals(10, provider.getDivisions("punjab").size)
        assertEquals(6, provider.getDistricts("punjab", "punjab_div_dgk").size)
        assertEquals(11, provider.getTehsils("balochistan", "bl_div_kalat", "bl_khuzdar").size)

        // Strict cascade fallback when switching province without matching child IDs
        val resolved = provider.resolveStrictCascade(
            provinceId = "gb",
            divisionId = "sindh_div_karachi",
            districtId = "sindh_dadu",
            talukaId = "dadu_johi"
        )
        assertEquals("gb", resolved.province.id)
        assertEquals("gb_div_gilgit", resolved.division.id)
        assertEquals("gb_gilgit", resolved.district.id)
        assertEquals("glg_gilgit", resolved.taluka.id)

        // Trilingual provider search methods
        assertTrue(provider.searchDivisions("مڪران", "balochistan").any { it.id == "bl_div_makran" })
        assertTrue(provider.searchDistricts("ہنزہ", "gb").any { it.id == "gb_hunza" })
        assertTrue(provider.searchTehsils("مينگوره", "kp").any { it.second.id == "swt_babuzai_mingora" })

        assertEquals(
            "گوپس • گوپس ياسين • گلگت ڊويزن • گلگت بلتستان",
            provider.formatFullHierarchy("gb", "gb_div_gilgit", "gb_gupis_yasin", "gpy_gupis", AppLanguage.SINDHI)
        )
    }
}
