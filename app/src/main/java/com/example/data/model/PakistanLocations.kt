package com.example.data.model

import com.example.i18n.AppLanguage

data class TalukaInfo(
    val id: String,
    val en: String,
    val sd: String,
    val ur: String
) {
    fun localizedName(language: AppLanguage): String = when (language) {
        AppLanguage.ENGLISH -> en
        AppLanguage.SINDHI -> sd
        AppLanguage.URDU -> ur
    }

    fun matches(query: String): Boolean {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return true
        return en.lowercase().contains(q) || sd.contains(q) || ur.contains(q)
    }
}

data class DistrictInfo(
    val id: String,
    val en: String,
    val sd: String,
    val ur: String,
    val talukas: List<TalukaInfo>
) {
    fun localizedName(language: AppLanguage): String = when (language) {
        AppLanguage.ENGLISH -> en
        AppLanguage.SINDHI -> sd
        AppLanguage.URDU -> ur
    }

    fun matches(query: String): Boolean {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return true
        return en.lowercase().contains(q) || sd.contains(q) || ur.contains(q)
    }
}

data class DivisionInfo(
    val id: String,
    val en: String,
    val sd: String,
    val ur: String,
    val districts: List<DistrictInfo>
) {
    fun localizedName(language: AppLanguage): String = when (language) {
        AppLanguage.ENGLISH -> en
        AppLanguage.SINDHI -> sd
        AppLanguage.URDU -> ur
    }

    fun matches(query: String): Boolean {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return true
        return en.lowercase().contains(q) || sd.contains(q) || ur.contains(q)
    }
}

data class ProvinceInfo(
    val id: String,
    val en: String,
    val sd: String,
    val ur: String,
    val divisions: List<DivisionInfo>
) {
    val districts: List<DistrictInfo>
        get() = divisions.flatMap { it.districts }

    fun localizedName(language: AppLanguage): String = when (language) {
        AppLanguage.ENGLISH -> en
        AppLanguage.SINDHI -> sd
        AppLanguage.URDU -> ur
    }

    fun matches(query: String): Boolean {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return true
        return en.lowercase().contains(q) || sd.contains(q) || ur.contains(q)
    }
}

data class ResolvedLocationSelection(
    val province: ProvinceInfo,
    val division: DivisionInfo,
    val district: DistrictInfo,
    val taluka: TalukaInfo
)

data class PakistanLocationStats(
    val provinceCount: Int,
    val divisionCount: Int,
    val districtCount: Int,
    val talukaCount: Int
)

private fun buildAllPakistanProvinces(): List<ProvinceInfo> = listOf(
    ProvinceInfo(
        id = "sindh",
        en = "Sindh",
        sd = "سنڌ",
        ur = "سندھ",
        divisions = SindhLocationsData.divisions
    ),
    ProvinceInfo(
        id = "punjab",
        en = "Punjab",
        sd = "پنجاب",
        ur = "پنجاب",
        divisions = PunjabLocationsData.divisions
    ),
    ProvinceInfo(
        id = "kp",
        en = "Khyber Pakhtunkhwa",
        sd = "خيبر پختونخواهه",
        ur = "خیبر پختونخوا",
        divisions = KpLocationsData.divisions
    ),
    ProvinceInfo(
        id = "balochistan",
        en = "Balochistan",
        sd = "بلوچستان",
        ur = "بلوچستان",
        divisions = BalochistanLocationsData.divisions
    ),
    ProvinceInfo(
        id = "gb",
        en = "Gilgit-Baltistan",
        sd = "گلگت بلتستان",
        ur = "گلگت بلتستان",
        divisions = TerritoriesLocationsData.gilgitBaltistanDivisions
    ),
    ProvinceInfo(
        id = "ajk",
        en = "Azad Jammu & Kashmir",
        sd = "آزاد ڄمون ۽ ڪشمير",
        ur = "آزاد جموں و کشمیر",
        divisions = TerritoriesLocationsData.ajkDivisions
    ),
    ProvinceInfo(
        id = "ict",
        en = "Islamabad Capital Territory",
        sd = "اسلام آباد",
        ur = "اسلام آباد",
        divisions = TerritoriesLocationsData.ictDivisions
    )
)

/**
 * Data provider encapsulating Pakistan's exhaustive 4-level administrative hierarchy
 * (Province/Territory -> Division -> District -> Tehsil/Taluka) with trilingual support
 * across English, Sindhi (سنڌي), and Urdu (اردو).
 */
open class PakistanLocations(
    val provinces: List<ProvinceInfo> = buildAllPakistanProvinces()
) {
    private val provincesById: Map<String, ProvinceInfo> = provinces.associateBy { it.id }

    private val divisionToProvince: Map<String, ProvinceInfo> = buildMap {
        provinces.forEach { province ->
            province.divisions.forEach { division ->
                put(division.id, province)
            }
        }
    }

    private val divisionsById: Map<String, DivisionInfo> = buildMap {
        provinces.forEach { province ->
            province.divisions.forEach { division ->
                put(division.id, division)
            }
        }
    }

    private val districtToDivision: Map<String, DivisionInfo> = buildMap {
        provinces.forEach { province ->
            province.divisions.forEach { division ->
                division.districts.forEach { district ->
                    put(district.id, division)
                }
            }
        }
    }

    private val districtToProvince: Map<String, ProvinceInfo> = buildMap {
        provinces.forEach { province ->
            province.divisions.forEach { division ->
                division.districts.forEach { district ->
                    put(district.id, province)
                }
            }
        }
    }

    private val districtsById: Map<String, DistrictInfo> = buildMap {
        provinces.forEach { province ->
            province.districts.forEach { district ->
                put(district.id, district)
            }
        }
    }

    private val talukasById: Map<String, TalukaInfo> = buildMap {
        provinces.forEach { province ->
            province.districts.forEach { district ->
                district.talukas.forEach { taluka ->
                    put(taluka.id, taluka)
                }
            }
        }
    }

    val stats: PakistanLocationStats = PakistanLocationStats(
        provinceCount = provinces.size,
        divisionCount = divisionsById.size,
        districtCount = districtsById.size,
        talukaCount = talukasById.size
    )

    fun getDivisions(provinceId: String? = null): List<DivisionInfo> {
        val province = provinceById(provinceId)
        return province?.divisions ?: provinces.flatMap { it.divisions }
    }

    fun getDistricts(provinceId: String? = null, divisionId: String? = null): List<DistrictInfo> {
        val division = divisionById(provinceId, divisionId)
        if (division != null) return division.districts
        val province = provinceById(provinceId)
        return province?.districts ?: provinces.flatMap { it.districts }
    }

    fun getTehsils(
        provinceId: String? = null,
        divisionId: String? = null,
        districtId: String? = null
    ): List<Pair<DistrictInfo, TalukaInfo>> {
        val district = districtById(provinceId, districtId)
        if (district != null) {
            return district.talukas.map { district to it }
        }
        return getDistricts(provinceId, divisionId).flatMap { d ->
            d.talukas.map { t -> d to t }
        }
    }

    fun provinceById(id: String?): ProvinceInfo? {
        if (id.isNullOrBlank()) return null
        return provincesById[id]
    }

    fun divisionById(provinceId: String?, divisionId: String?): DivisionInfo? {
        if (divisionId.isNullOrBlank()) return null
        return if (!provinceId.isNullOrBlank()) {
            provinceById(provinceId)?.divisions?.firstOrNull { it.id == divisionId }
        } else {
            divisionsById[divisionId]
        }
    }

    fun provinceForDivision(divisionId: String?): ProvinceInfo? {
        if (divisionId.isNullOrBlank()) return null
        return divisionToProvince[divisionId]
    }

    fun divisionForDistrict(districtId: String?): DivisionInfo? {
        if (districtId.isNullOrBlank()) return null
        return districtToDivision[districtId]
    }

    fun provinceForDistrict(districtId: String?): ProvinceInfo? {
        if (districtId.isNullOrBlank()) return null
        return districtToProvince[districtId]
    }

    fun districtById(provinceId: String?, districtId: String?): DistrictInfo? {
        if (districtId.isNullOrBlank()) return null
        return if (!provinceId.isNullOrBlank()) {
            provinceById(provinceId)?.districts?.firstOrNull { it.id == districtId }
        } else {
            districtsById[districtId]
        }
    }

    fun talukaById(districtId: String?, talukaId: String?): TalukaInfo? {
        if (talukaId.isNullOrBlank()) return null
        return if (!districtId.isNullOrBlank()) {
            districtById(null, districtId)?.talukas?.firstOrNull { it.id == talukaId }
        } else {
            talukasById[talukaId]
        }
    }

    /**
     * Resolves a strict, complete 4-level cascade selection (never null at any level),
     * automatically falling back to valid child selections when a parent changes.
     */
    fun resolveStrictCascade(
        provinceId: String?,
        divisionId: String?,
        districtId: String?,
        talukaId: String?,
        fallbackProvinceId: String = "sindh"
    ): ResolvedLocationSelection {
        val province = provinceById(provinceId)
            ?: provinceById(fallbackProvinceId)
            ?: provinces.first()
        val division = divisionById(province.id, divisionId)
            ?: province.divisions.first()
        val district = division.districts.firstOrNull { it.id == districtId }
            ?: division.districts.first()
        val taluka = district.talukas.firstOrNull { it.id == talukaId }
            ?: district.talukas.first()
        return ResolvedLocationSelection(
            province = province,
            division = division,
            district = district,
            taluka = taluka
        )
    }

    fun searchDivisions(query: String, provinceId: String? = null): List<DivisionInfo> {
        val divisions = getDivisions(provinceId)
        if (query.isBlank()) return divisions
        return divisions.filter { it.matches(query) }
    }

    fun searchDistricts(
        query: String,
        provinceId: String? = null,
        divisionId: String? = null
    ): List<DistrictInfo> {
        val districts = getDistricts(provinceId, divisionId)
        if (query.isBlank()) return districts
        return districts.filter { it.matches(query) }
    }

    fun searchTehsils(
        query: String,
        provinceId: String? = null,
        divisionId: String? = null,
        districtId: String? = null
    ): List<Pair<DistrictInfo, TalukaInfo>> {
        val tehsils = getTehsils(provinceId, divisionId, districtId)
        if (query.isBlank()) return tehsils
        return tehsils.filter { (dist, tal) -> tal.matches(query) || dist.matches(query) }
    }

    fun formatWorkerLocation(
        provinceId: String,
        districtId: String,
        talukaId: String,
        areaFallback: String,
        cityFallback: String,
        language: AppLanguage
    ): String {
        val talukaName = talukaById(districtId, talukaId)?.localizedName(language)
        val districtName = districtById(provinceId, districtId)?.localizedName(language)
        val parts = listOfNotNull(
            talukaName?.takeIf { it.isNotBlank() } ?: areaFallback.takeIf { it.isNotBlank() },
            districtName?.takeIf { it.isNotBlank() } ?: cityFallback.takeIf { it.isNotBlank() }
        )
        return parts.joinToString(", ")
    }

    fun formatFullHierarchy(
        provinceId: String?,
        divisionId: String?,
        districtId: String?,
        talukaId: String?,
        language: AppLanguage
    ): String {
        val prov = provinceById(provinceId)
        val div = divisionById(provinceId, divisionId) ?: divisionForDistrict(districtId)
        val dist = districtById(provinceId, districtId)
        val tal = talukaById(districtId, talukaId)
        return listOfNotNull(
            tal?.localizedName(language),
            dist?.localizedName(language),
            div?.localizedName(language),
            prov?.localizedName(language)
        ).joinToString(" • ")
    }

    fun matchesLocationQuery(
        provinceId: String,
        districtId: String,
        talukaId: String,
        query: String,
        divisionId: String = ""
    ): Boolean {
        val q = query.trim().lowercase()
        if (q.isEmpty()) return true
        val prov = provinceById(provinceId)
        val div = if (divisionId.isNotBlank()) divisionById(provinceId, divisionId) else divisionForDistrict(districtId)
        val dist = districtById(provinceId, districtId)
        val tal = talukaById(districtId, talukaId)
        return (prov != null && prov.matches(q)) ||
            (div != null && div.matches(q)) ||
            (dist != null && dist.matches(q)) ||
            (tal != null && tal.matches(q))
    }

    companion object : PakistanLocations(buildAllPakistanProvinces())
}
