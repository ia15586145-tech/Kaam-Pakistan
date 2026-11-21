package com.example.data.model

internal object TerritoriesLocationsData {

    val gilgitBaltistanDivisions: List<DivisionInfo> = listOf(
        // === GILGIT DIVISION ===
        DivisionInfo(
            id = "gb_div_gilgit",
            en = "Gilgit Division",
            sd = "گلگت ڊويزن",
            ur = "گلگت ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "gb_gilgit",
                    en = "Gilgit",
                    sd = "گلگت",
                    ur = "گلگت",
                    talukas = listOf(
                        TalukaInfo("glg_gilgit", "Gilgit", "گلگت", "گلگت"),
                        TalukaInfo("glg_danyor", "Danyor", "دنيور", "دنیور"),
                        TalukaInfo("glg_juglot", "Juglot", "جگلوٽ", "جگلوٹ")
                    )
                ),
                DistrictInfo(
                    id = "gb_hunza",
                    en = "Hunza",
                    sd = "هنزه",
                    ur = "ہنزہ",
                    talukas = listOf(
                        TalukaInfo("hnz_aliabad", "Aliabad", "علي آباد", "علی آباد"),
                        TalukaInfo("hnz_gojal_upper_hunza", "Gojal / Upper Hunza", "گوجال / اپر هنزه", "گوجال / اپر ہنزہ")
                    )
                ),
                DistrictInfo(
                    id = "gb_nagar",
                    en = "Nagar",
                    sd = "نگر",
                    ur = "نگر",
                    talukas = listOf(
                        TalukaInfo("ngr_nagar_1", "Nagar-I", "نگر 1", "نگر 1"),
                        TalukaInfo("ngr_nagar_2_chalt", "Nagar-II / Chalt", "چلت", "چلت")
                    )
                ),
                DistrictInfo(
                    id = "gb_ghizer",
                    en = "Ghizer",
                    sd = "غذر",
                    ur = "غذر",
                    talukas = listOf(
                        TalukaInfo("ghz_punial_gahkuch", "Punial / Gahkuch", "گاھڪوچ", "گاہکوچ"),
                        TalukaInfo("ghz_ishkoman", "Ishkoman", "اشڪومن", "اشکومن")
                    )
                ),
                DistrictInfo(
                    id = "gb_gupis_yasin",
                    en = "Gupis-Yasin",
                    sd = "گوپس ياسين",
                    ur = "گوپس یاسین",
                    talukas = listOf(
                        TalukaInfo("gpy_gupis", "Gupis", "گوپس", "گوپس"),
                        TalukaInfo("gpy_yasin", "Yasin", "ياسين", "یاسین"),
                        TalukaInfo("gpy_phander", "Phander", "ڦنڊر", "پھنڈر")
                    )
                )
            )
        ),

        // === BALTISTAN DIVISION ===
        DivisionInfo(
            id = "gb_div_baltistan",
            en = "Baltistan Division",
            sd = "بلتستان ڊويزن",
            ur = "بلتستان ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "gb_skardu",
                    en = "Skardu",
                    sd = "اسڪردو",
                    ur = "اسکردو",
                    talukas = listOf(
                        TalukaInfo("skd_skardu", "Skardu", "اسڪردو", "اسکردو"),
                        TalukaInfo("skd_gultari", "Gultari", "گلتري", "گلتری"),
                        TalukaInfo("skd_rondu", "Rondu", "روندو", "روندو")
                    )
                ),
                DistrictInfo(
                    id = "gb_ghanche",
                    en = "Ghanche",
                    sd = "گھانچي",
                    ur = "گانچھے",
                    talukas = listOf(
                        TalukaInfo("gnc_khaplu", "Khaplu", "خپلو", "خپلو"),
                        TalukaInfo("gnc_mashabrum", "Mashabrum", "ماشابرم", "ماشابرم"),
                        TalukaInfo("gnc_chorbat", "Chorbat", "چوربٽ", "چوربٹ"),
                        TalukaInfo("gnc_daghoni", "Daghoni", "ڊاگھوني", "ڈاغونی"),
                        TalukaInfo("gnc_haldi", "Haldi", "ھلدي", "ہلدی"),
                        TalukaInfo("gnc_keris", "Keris", "ڪيرس", "کیرس")
                    )
                ),
                DistrictInfo(
                    id = "gb_shigar",
                    en = "Shigar",
                    sd = "شگر",
                    ur = "شگر",
                    talukas = listOf(
                        TalukaInfo("sgr_shigar", "Shigar", "شگر", "شگر"),
                        TalukaInfo("sgr_gulabpur", "Gulabpur", "گلاب پور", "گلاب پور")
                    )
                ),
                DistrictInfo(
                    id = "gb_kharmang",
                    en = "Kharmang",
                    sd = "کرمنگ",
                    ur = "کھرمنگ",
                    talukas = listOf(
                        TalukaInfo("khm_kharmang_tolti", "Kharmang / Tolti", "کرمنگ / ٽولٽي", "کھرمنگ / ٹولٹی")
                    )
                )
            )
        ),

        // === DIAMER DIVISION ===
        DivisionInfo(
            id = "gb_div_diamer",
            en = "Diamer Division",
            sd = "ديامير ڊويزن",
            ur = "دیامر ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "gb_diamer",
                    en = "Diamer",
                    sd = "ديامير",
                    ur = "دیامر",
                    talukas = listOf(
                        TalukaInfo("dmr_chilas", "Chilas", "چلاس", "چلاس"),
                        TalukaInfo("dmr_darel", "Darel", "داريل", "داریل"),
                        TalukaInfo("dmr_tangir", "Tangir", "تانگير", "تانگیر")
                    )
                ),
                DistrictInfo(
                    id = "gb_astore",
                    en = "Astore",
                    sd = "استور",
                    ur = "استور",
                    talukas = listOf(
                        TalukaInfo("ast_eidgah_astore", "Eidgah / Astore", "استور", "استور"),
                        TalukaInfo("ast_shounter", "Shounter", "شونٽر", "شونٹر")
                    )
                ),
                DistrictInfo(
                    id = "gb_darel",
                    en = "Darel",
                    sd = "داريل",
                    ur = "داریل",
                    talukas = listOf(
                        TalukaInfo("drl_darel", "Darel", "داريل", "داریل")
                    )
                ),
                DistrictInfo(
                    id = "gb_tangir",
                    en = "Tangir",
                    sd = "تانگير",
                    ur = "تانگیر",
                    talukas = listOf(
                        TalukaInfo("tng_tangir", "Tangir", "تانگير", "تانگیر")
                    )
                )
            )
        )
    )

    val ajkDivisions: List<DivisionInfo> = listOf(
        // === MUZAFFARABAD DIVISION ===
        DivisionInfo(
            id = "ajk_div_muzaffarabad",
            en = "Muzaffarabad Division",
            sd = "مظفرآباد ڊويزن",
            ur = "مظفرآباد ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "ajk_muzaffarabad",
                    en = "Muzaffarabad",
                    sd = "مظفرآباد",
                    ur = "مظفرآباد",
                    talukas = listOf(
                        TalukaInfo("mzd_muzaffarabad", "Muzaffarabad", "مظفرآباد", "مظفرآباد"),
                        TalukaInfo("mzd_naseerabad_pattika", "Naseerabad / Pattika", "پٽيڪه", "پٹیکہ")
                    )
                ),
                DistrictInfo(
                    id = "ajk_hattian_bala",
                    en = "Hattian Bala",
                    sd = "هٽيان بالا",
                    ur = "ہٹیاں بالا",
                    talukas = listOf(
                        TalukaInfo("htb_hattian_bala", "Hattian Bala", "هٽيان بالا", "ہٹیاں بالا"),
                        TalukaInfo("htb_chikkar", "Chikkar", "چڪار", "چکار"),
                        TalukaInfo("htb_leepa", "Leepa", "ليپه", "لیپہ")
                    )
                ),
                DistrictInfo(
                    id = "ajk_neelum",
                    en = "Neelum",
                    sd = "نيلم",
                    ur = "نیلم",
                    talukas = listOf(
                        TalukaInfo("nlm_athmuqam", "Athmuqam", "آٺ مقام", "آٹھ مقام"),
                        TalukaInfo("nlm_sharda", "Sharda", "شاردا", "شاردا")
                    )
                )
            )
        ),

        // === POONCH DIVISION ===
        DivisionInfo(
            id = "ajk_div_poonch",
            en = "Poonch Division",
            sd = "پونڇ ڊويزن",
            ur = "پونچھ ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "ajk_poonch",
                    en = "Poonch / Rawalakot",
                    sd = "پونڇ / راولاڪوٽ",
                    ur = "پونچھ / راولاکوٹ",
                    talukas = listOf(
                        TalukaInfo("pnc_rawalakot", "Rawalakot", "راولاڪوٽ", "راولاکوٹ"),
                        TalukaInfo("pnc_hajira", "Hajira", "ھجيره", "ہجیرہ"),
                        TalukaInfo("pnc_abbaspur", "Abbaspur", "عباس پور", "عباس پور"),
                        TalukaInfo("pnc_thorar", "Thorar", "ٿوراڙ", "تھوراڑ")
                    )
                ),
                DistrictInfo(
                    id = "ajk_bagh",
                    en = "Bagh",
                    sd = "باغ",
                    ur = "باغ",
                    talukas = listOf(
                        TalukaInfo("bgh_bagh", "Bagh", "باغ", "باغ"),
                        TalukaInfo("bgh_dhirkot", "Dhirkot", "ڌيرڪوٽ", "دھیرکوٹ"),
                        TalukaInfo("bgh_hari_ghel", "Hari Ghel", "ھري گھل", "ہری گھل")
                    )
                ),
                DistrictInfo(
                    id = "ajk_haveli",
                    en = "Haveli",
                    sd = "حويلي",
                    ur = "حویلی",
                    talukas = listOf(
                        TalukaInfo("hvl_forward_kahuta", "Forward Kahuta", "فارورڊ ڪھوٽه", "فارورڈ کہوٹہ"),
                        TalukaInfo("hvl_khurshidabad", "Khurshidabad", "خورشيدآباد", "خورشید آباد"),
                        TalukaInfo("hvl_mumtazabad", "Mumtazabad", "ممتازآباد", "ممتاز آباد")
                    )
                ),
                DistrictInfo(
                    id = "ajk_sudhanoti",
                    en = "Sudhanoti",
                    sd = "سڌنوتي",
                    ur = "سدھنوتی",
                    talukas = listOf(
                        TalukaInfo("sdn_pallandri", "Pallandri", "پلندري", "پلندری"),
                        TalukaInfo("sdn_mang", "Mang", "منگ", "منگ"),
                        TalukaInfo("sdn_trarkhel", "Trarkhel", "ترار کل", "تراڑ کھل"),
                        TalukaInfo("sdn_baloch", "Baloch", "بلوچ", "بلوچ")
                    )
                )
            )
        ),

        // === MIRPUR DIVISION ===
        DivisionInfo(
            id = "ajk_div_mirpur",
            en = "Mirpur Division",
            sd = "ميرپور ڊويزن",
            ur = "میرپور ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "ajk_mirpur",
                    en = "Mirpur",
                    sd = "ميرپور",
                    ur = "میرپور",
                    talukas = listOf(
                        TalukaInfo("mrp_mirpur", "Mirpur", "ميرپور", "میرپور"),
                        TalukaInfo("mrp_dadyal", "Dadyal", "ڊڊيال", "ڈڈیال"),
                        TalukaInfo("mrp_islamgarh", "Islamgarh", "اسلام ڳڙهه", "اسلام گڑھ")
                    )
                ),
                DistrictInfo(
                    id = "ajk_bhimber",
                    en = "Bhimber",
                    sd = "ڀمبر",
                    ur = "بھمبر",
                    talukas = listOf(
                        TalukaInfo("bmb_bhimber", "Bhimber", "ڀمبر", "بھمبر"),
                        TalukaInfo("bmb_barnala", "Barnala", "برناله", "برنالہ"),
                        TalukaInfo("bmb_samahni", "Samahni", "سماھني", "سماہنی")
                    )
                ),
                DistrictInfo(
                    id = "ajk_kotli",
                    en = "Kotli",
                    sd = "ڪوٽلي",
                    ur = "کوٹلی",
                    talukas = listOf(
                        TalukaInfo("ktl_kotli", "Kotli", "ڪوٽلي", "کوٹلی"),
                        TalukaInfo("ktl_nakyal", "Fatehpur Thakiala / Nakyal", "نڪيال", "نکیال"),
                        TalukaInfo("ktl_khoiratta", "Khoiratta", "کوئي رٽه", "کھوئی رٹہ"),
                        TalukaInfo("ktl_sehnsa", "Sehnsa", "سھنسه", "سہنسہ"),
                        TalukaInfo("ktl_charhoi", "Charhoi", "چڙھوئي", "چڑھوئی"),
                        TalukaInfo("ktl_duliah_jattan", "Duliah Jattan", "دوليه جٽان", "دولیہ جٹاں")
                    )
                )
            )
        )
    )

    val ictDivisions: List<DivisionInfo> = listOf(
        // === ISLAMABAD DIVISION ===
        DivisionInfo(
            id = "ict_div_islamabad",
            en = "Islamabad Division",
            sd = "اسلام آباد ڊويزن",
            ur = "اسلام آباد ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "ict_islamabad",
                    en = "Islamabad",
                    sd = "اسلام آباد",
                    ur = "اسلام آباد",
                    talukas = listOf(
                        TalukaInfo("isb_city_sectors", "Islamabad City / Sectors", "اسلام آباد سٽي / سيڪٽرز", "اسلام آباد سٹی / سیکٹرز"),
                        TalukaInfo("isb_bara_kahu", "Bara Kahu", "باره ڪھو", "بارہ کہو"),
                        TalukaInfo("isb_tarnol", "Tarnol", "ترنول", "ترنول"),
                        TalukaInfo("isb_sihala", "Sihala", "سھاله", "سہالہ"),
                        TalukaInfo("isb_nilore", "Nilore", "نيلور", "نیلور"),
                        TalukaInfo("isb_murree_road", "Bhara Kahu / Murree Road", "مري روڊ", "مری روڈ"),
                        TalukaInfo("isb_rawat", "Rawat", "روات", "روات")
                    )
                )
            )
        )
    )
}
