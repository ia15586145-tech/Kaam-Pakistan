package com.example.data.model

internal object BalochistanLocationsData {

    val divisions: List<DivisionInfo> = listOf(
        // === 1. QUETTA DIVISION ===
        DivisionInfo(
            id = "bl_div_quetta",
            en = "Quetta Division",
            sd = "ڪوئيٽا ڊويزن",
            ur = "کوئٹہ ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "bl_quetta",
                    en = "Quetta",
                    sd = "ڪوئيٽا",
                    ur = "کوئٹہ",
                    talukas = listOf(
                        TalukaInfo("qta_city", "Quetta City", "ڪوئيٽا سٽي", "کوئٹہ سٹی"),
                        TalukaInfo("qta_saddar", "Quetta Saddar", "ڪوئيٽا صدر", "کوئٹہ صدر"),
                        TalukaInfo("qta_panjpai", "Panjpai", "پنجپائي", "پنجپائی")
                    )
                ),
                DistrictInfo(
                    id = "bl_pishin",
                    en = "Pishin",
                    sd = "پشين",
                    ur = "پشین",
                    talukas = listOf(
                        TalukaInfo("psn_pishin", "Pishin", "پشين", "پشین"),
                        TalukaInfo("psn_barshore", "Barshore", "برشور", "برشور"),
                        TalukaInfo("psn_hurramzai", "Hurramzai", "حرم زئي", "حرم زئی"),
                        TalukaInfo("psn_karezat", "Karezat", "ڪاريزات", "کاریزات"),
                        TalukaInfo("psn_saranan", "Saranan", "سرانان", "سرانان")
                    )
                ),
                DistrictInfo(
                    id = "bl_killa_abdullah",
                    en = "Killa Abdullah",
                    sd = "قلعه عبدالله",
                    ur = "قلعہ عبداللہ",
                    talukas = listOf(
                        TalukaInfo("kab_killa_abdullah", "Killa Abdullah", "قلعه عبدالله", "قلعہ عبداللہ"),
                        TalukaInfo("kab_gulistan", "Gulistan", "گلستان", "گلستان"),
                        TalukaInfo("kab_dobandi", "Dobandi", "دوبندي", "دوبندی")
                    )
                ),
                DistrictInfo(
                    id = "bl_chaman",
                    en = "Chaman",
                    sd = "چمن",
                    ur = "چمن",
                    talukas = listOf(
                        TalukaInfo("cmn_chaman", "Chaman", "چمن", "چمن"),
                        TalukaInfo("cmn_saddar_chaman", "Saddar Chaman", "صدر چمن", "صدر چمن")
                    )
                )
            )
        ),

        // === 2. KALAT DIVISION ===
        DivisionInfo(
            id = "bl_div_kalat",
            en = "Kalat Division",
            sd = "قلات ڊويزن",
            ur = "قلات ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "bl_kalat",
                    en = "Kalat",
                    sd = "قلات",
                    ur = "قلات",
                    talukas = listOf(
                        TalukaInfo("klt_kalat", "Kalat", "قلات", "قلات"),
                        TalukaInfo("klt_mangochar", "Mangochar", "منگوچر", "منگوچر"),
                        TalukaInfo("klt_gazg", "Gazg", "گزگ", "گزگ"),
                        TalukaInfo("klt_johan", "Johan", "جوھان", "جوہان")
                    )
                ),
                DistrictInfo(
                    id = "bl_mastung",
                    en = "Mastung",
                    sd = "مستونگ",
                    ur = "مستونگ",
                    talukas = listOf(
                        TalukaInfo("mst_mastung", "Mastung", "مستونگ", "مستونگ"),
                        TalukaInfo("mst_dasht", "Dasht", "دشت", "دشت"),
                        TalukaInfo("mst_kardigap", "Kardigap", "ڪردگاپ", "کردگاپ"),
                        TalukaInfo("mst_khad_koocha", "Khad Koocha", "کڊ ڪوچه", "کھڈ کوچہ")
                    )
                ),
                DistrictInfo(
                    id = "bl_khuzdar",
                    en = "Khuzdar",
                    sd = "خضدار",
                    ur = "خضدار",
                    talukas = listOf(
                        TalukaInfo("khz_khuzdar", "Khuzdar", "خضدار", "خضدار"),
                        TalukaInfo("khz_nal", "Nal", "نال", "نال"),
                        TalukaInfo("khz_wadh", "Wadh", "وڍ", "وڈھ"),
                        TalukaInfo("khz_zehri", "Zehri", "زھري", "زہری"),
                        TalukaInfo("khz_baghbana", "Baghbana", "باغبانه", "باغبانہ"),
                        TalukaInfo("khz_aranji", "Aranji", "آرنجي", "آرنجی"),
                        TalukaInfo("khz_gresha", "Gresha", "گريشه", "گریشہ"),
                        TalukaInfo("khz_karkh", "Karkh", "ڪرخ", "کرخ"),
                        TalukaInfo("khz_moola", "Moola", "مولا", "مولا"),
                        TalukaInfo("khz_ornach", "Ornach", "اورناچ", "اورناچ"),
                        TalukaInfo("khz_saroona", "Saroona", "سارونه", "سارونہ")
                    )
                ),
                DistrictInfo(
                    id = "bl_awaran",
                    en = "Awaran",
                    sd = "آواران",
                    ur = "آواران",
                    talukas = listOf(
                        TalukaInfo("awr_awaran", "Awaran", "آواران", "آواران"),
                        TalukaInfo("awr_mashkai", "Mashkai", "مشڪئي", "مشکے"),
                        TalukaInfo("awr_jhal_jhao", "Jhal Jhao", "جھل جھاؤ", "جھل جھاؤ"),
                        TalukaInfo("awr_gishkore", "Gishkore", "گشڪور", "گشکور"),
                        TalukaInfo("awr_korak_jahoo", "Korak Jahoo", "ڪورڪ جاھو", "کورک جاہو")
                    )
                ),
                DistrictInfo(
                    id = "bl_lasbela",
                    en = "Lasbela",
                    sd = "لسٻيلا",
                    ur = "لسبیلہ",
                    talukas = listOf(
                        TalukaInfo("lsb_uthal", "Uthal", "اٿل", "اوتھل"),
                        TalukaInfo("lsb_bela", "Bela", "ٻيلا", "بیلہ"),
                        TalukaInfo("lsb_kanraj", "Kanraj", "ڪنراج", "کنراج"),
                        TalukaInfo("lsb_lakhra", "Lakhra", "لاکهڙا", "لاکھڑا"),
                        TalukaInfo("lsb_liari", "Liari", "لياري", "لیاری"),
                        TalukaInfo("lsb_sonmiani_winder", "Sonmiani / Winder", "سونمياڻي / ونڊر", "سونمیانی / وندر")
                    )
                ),
                DistrictInfo(
                    id = "bl_hub",
                    en = "Hub",
                    sd = "حب",
                    ur = "حب",
                    talukas = listOf(
                        TalukaInfo("hub_hub", "Hub", "حب", "حب"),
                        TalukaInfo("hub_gadani", "Gadani", "گڏاڻي", "گڈانی"),
                        TalukaInfo("hub_dureji", "Dureji", "دريجي", "دریجی"),
                        TalukaInfo("hub_sakran", "Sakran", "سڪران", "سکران")
                    )
                ),
                DistrictInfo(
                    id = "bl_surab",
                    en = "Surab",
                    sd = "سوراب",
                    ur = "سوراب",
                    talukas = listOf(
                        TalukaInfo("srb_surab", "Surab", "سوراب", "سوراب"),
                        TalukaInfo("srb_dasht_e_goran", "Dasht-e-Goran", "دشت گوران", "دشت گوران"),
                        TalukaInfo("srb_gidar", "Gidar", "گدر", "گدر"),
                        TalukaInfo("srb_shaheed_sikandarabad", "Shaheed Sikandarabad", "شھيد سڪندرآباد", "شہید سکندرآباد")
                    )
                )
            )
        ),

        // === 3. MAKRAN DIVISION ===
        DivisionInfo(
            id = "bl_div_makran",
            en = "Makran Division",
            sd = "مڪران ڊويزن",
            ur = "مکران ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "bl_kech",
                    en = "Kech / Turbat",
                    sd = "ڪيچ / تربت",
                    ur = "کیچ / تربت",
                    talukas = listOf(
                        TalukaInfo("kch_turbat", "Turbat", "تربت", "تربت"),
                        TalukaInfo("kch_tump", "Tump", "تمپ", "تمپ"),
                        TalukaInfo("kch_mand", "Mand", "مند", "مند"),
                        TalukaInfo("kch_buleda", "Buleda", "بليده", "بلیدہ"),
                        TalukaInfo("kch_dasht", "Dasht", "دشت", "دشت"),
                        TalukaInfo("kch_hoshab", "Hoshab", "ھوشاب", "ہوشاب"),
                        TalukaInfo("kch_zamuran", "Zamuran", "زامران", "زامران"),
                        TalukaInfo("kch_balnigor", "Balnigor", "بالنگور", "بالنگور"),
                        TalukaInfo("kch_gayab", "Gayab", "گياب", "گیاب"),
                        TalukaInfo("kch_solband", "Solband", "سولبند", "سولبند")
                    )
                ),
                DistrictInfo(
                    id = "bl_gwadar",
                    en = "Gwadar",
                    sd = "گوادر",
                    ur = "گوادر",
                    talukas = listOf(
                        TalukaInfo("gwd_gwadar", "Gwadar", "گوادر", "گوادر"),
                        TalukaInfo("gwd_pasni", "Pasni", "پسني", "پسنی"),
                        TalukaInfo("gwd_ormara", "Ormara", "اورماڙه", "اورماڑہ"),
                        TalukaInfo("gwd_jiwani", "Jiwani", "جيواني", "جیوانی"),
                        TalukaInfo("gwd_suntsar", "Suntsar", "سنتسر", "سنتسر")
                    )
                ),
                DistrictInfo(
                    id = "bl_panjgur",
                    en = "Panjgur",
                    sd = "پنجگور",
                    ur = "پنجگور",
                    talukas = listOf(
                        TalukaInfo("pjg_panjgur", "Panjgur", "پنجگور", "پنجگور"),
                        TalukaInfo("pjg_gowargo", "Gowargo", "گوارگو", "گوارگو"),
                        TalukaInfo("pjg_paroom", "Paroom", "پروم", "پروم"),
                        TalukaInfo("pjg_gichk", "Gichk", "گچڪ", "گچک")
                    )
                )
            )
        ),

        // === 4. NASIRABAD DIVISION ===
        DivisionInfo(
            id = "bl_div_nasirabad",
            en = "Nasirabad Division",
            sd = "نصيرآباد ڊويزن",
            ur = "نصیرآباد ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "bl_nasirabad",
                    en = "Nasirabad",
                    sd = "نصيرآباد",
                    ur = "نصیرآباد",
                    talukas = listOf(
                        TalukaInfo("nsr_dera_murad_jamali", "Dera Murad Jamali", "ڊيره مراد جمالي", "ڈیرہ مراد جمالی"),
                        TalukaInfo("nsr_tamboo", "Tamboo", "تمبو", "تمبو"),
                        TalukaInfo("nsr_chattar", "Chattar", "ڇتر", "چھتر"),
                        TalukaInfo("nsr_baba_kot", "Baba Kot", "بابا ڪوٽ", "بابا کوٹ"),
                        TalukaInfo("nsr_mir_hassan", "Mir Hassan", "مير حسن", "میر حسن"),
                        TalukaInfo("nsr_landhi", "Landhi", "لانڍي", "لانڈھی")
                    )
                ),
                DistrictInfo(
                    id = "bl_jaffarabad",
                    en = "Jaffarabad",
                    sd = "جعفرآباد",
                    ur = "جعفرآباد",
                    talukas = listOf(
                        TalukaInfo("jfb_dera_allah_yar", "Dera Allah Yar", "ڊيره الھيار", "ڈیرہ اللہ یار"),
                        TalukaInfo("jfb_gandakha", "Gandakha", "گنداڪه", "گنداخہ"),
                        TalukaInfo("jfb_jhatpat", "Jhatpat", "جھٽ پٽ", "جھٹ پٹ")
                    )
                ),
                DistrictInfo(
                    id = "bl_usta_muhammad",
                    en = "Usta Muhammad",
                    sd = "اوستا محمد",
                    ur = "اوستہ محمد",
                    talukas = listOf(
                        TalukaInfo("ust_usta_muhammad", "Usta Muhammad", "اوستا محمد", "اوستہ محمد"),
                        TalukaInfo("ust_gandakha_bhagh_head", "Gandakha / Bhagh Head", "ڀاڳ ھيڊ", "بھاگ ہیڈ")
                    )
                ),
                DistrictInfo(
                    id = "bl_sohbatpur",
                    en = "Sohbatpur",
                    sd = "صحبت پور",
                    ur = "صحبت پور",
                    talukas = listOf(
                        TalukaInfo("sbt_sohbatpur", "Sohbatpur", "صحبت پور", "صحبت پور"),
                        TalukaInfo("sbt_manjhipur", "Manjhipur", "مانجھي پور", "مانجھی پور"),
                        TalukaInfo("sbt_hairdin", "Hairdin", "حيردين", "حیردین"),
                        TalukaInfo("sbt_faridabad", "Faridabad", "فريدآباد", "فریدآباد"),
                        TalukaInfo("sbt_sanhri", "Sanhri", "سنھري", "سنہری")
                    )
                ),
                DistrictInfo(
                    id = "bl_kachhi",
                    en = "Kachhi / Bolan",
                    sd = "ڪڇي / بولان",
                    ur = "کچھی / بولان",
                    talukas = listOf(
                        TalukaInfo("kch_dhadar", "Dhadar", "ڍاڍر", "ڈھاڈر"),
                        TalukaInfo("kch_bhag", "Bhag", "ڀاڳ", "بھاگ"),
                        TalukaInfo("kch_mach", "Mach", "مڇ", "مچھ"),
                        TalukaInfo("kch_sanni", "Sanni", "سني", "سنی"),
                        TalukaInfo("kch_khatan", "Khatan", "ختن", "ختن"),
                        TalukaInfo("kch_balanari", "Balanari", "بالاناڙي", "بالاناڑی")
                    )
                ),
                DistrictInfo(
                    id = "bl_jhal_magsi",
                    en = "Jhal Magsi",
                    sd = "جھل مگسي",
                    ur = "جھل مگسی",
                    talukas = listOf(
                        TalukaInfo("jmg_gandawah", "Jhal Magsi / Gandawah", "گنداوه", "گنداوہ"),
                        TalukaInfo("jmg_mirpur", "Mirpur", "ميرپور", "میرپور"),
                        TalukaInfo("jmg_saifabad", "Saifabad", "سيف آباد", "سیف آباد")
                    )
                )
            )
        ),

        // === 5. SIBI DIVISION ===
        DivisionInfo(
            id = "bl_div_sibi",
            en = "Sibi Division",
            sd = "سبي ڊويزن",
            ur = "سبی ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "bl_sibi",
                    en = "Sibi",
                    sd = "سبي",
                    ur = "سبی",
                    talukas = listOf(
                        TalukaInfo("sbi_sibi", "Sibi", "سبي", "سبی"),
                        TalukaInfo("sbi_lehri", "Lehri", "لھڙي", "لہڑی"),
                        TalukaInfo("sbi_kutmandai", "Kutmandai", "ڪٽمنڊائي", "کٹمنڈائی"),
                        TalukaInfo("sbi_sangan", "Sangan", "سنگان", "سنگان")
                    )
                ),
                DistrictInfo(
                    id = "bl_harnai",
                    en = "Harnai",
                    sd = "ھرنائي",
                    ur = "ہرنائی",
                    talukas = listOf(
                        TalukaInfo("hrn_harnai", "Harnai", "ھرنائي", "ہرنائی"),
                        TalukaInfo("hrn_shahrig", "Shahrig", "شاھرگ", "شاہرگ"),
                        TalukaInfo("hrn_khoast", "Khoast", "خوست", "خوست")
                    )
                ),
                DistrictInfo(
                    id = "bl_ziarat",
                    en = "Ziarat",
                    sd = "زيارت",
                    ur = "زیارت",
                    talukas = listOf(
                        TalukaInfo("zrt_ziarat", "Ziarat", "زيارت", "زیارت"),
                        TalukaInfo("zrt_sinjavi", "Sinjavi", "سنجاوي", "سنجاوی")
                    )
                ),
                DistrictInfo(
                    id = "bl_kohlu",
                    en = "Kohlu",
                    sd = "ڪوھلو",
                    ur = "کوہلو",
                    talukas = listOf(
                        TalukaInfo("khl_kohlu", "Kohlu", "ڪوھلو", "کوہلو"),
                        TalukaInfo("khl_kahan", "Kahan", "ڪاھان", "کاہان"),
                        TalukaInfo("khl_maiwand", "Maiwand", "ميوند", "میوند"),
                        TalukaInfo("khl_grisani", "Mawand / Grisani", "گريساني", "گریسانہ"),
                        TalukaInfo("khl_shaheed_jahangirabad", "Tamboo / Shaheed Jahangirabad", "شھيد جھانگيرآباد", "شہید جہانگیرآباد")
                    )
                ),
                DistrictInfo(
                    id = "bl_dera_bugti",
                    en = "Dera Bugti",
                    sd = "ڊيره بگٽي",
                    ur = "ڈیرہ بگٹی",
                    talukas = listOf(
                        TalukaInfo("dbg_dera_bugti", "Dera Bugti", "ڊيره بگٽي", "ڈیرہ بگٹی"),
                        TalukaInfo("dbg_sui", "Sui", "سوئي", "سوئی"),
                        TalukaInfo("dbg_phelawagh", "Phelawagh", "ڦيلاوغ", "پھیلاوغ"),
                        TalukaInfo("dbg_baiker", "Baiker", "بيڪڙ", "بیکڑ"),
                        TalukaInfo("dbg_loti", "Loti", "لوٽي", "لوٹی"),
                        TalukaInfo("dbg_malam", "Malam", "مالم", "مالم"),
                        TalukaInfo("dbg_pir_koh", "Pir Koh", "پير ڪوھه", "پیر کوہ"),
                        TalukaInfo("dbg_sangsillah", "Sangsillah", "سنگسيلا", "سنگسیلہ")
                    )
                )
            )
        ),

        // === 6. ZHOB DIVISION ===
        DivisionInfo(
            id = "bl_div_zhob",
            en = "Zhob Division",
            sd = "زوب ڊويزن",
            ur = "ژوب ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "bl_zhob",
                    en = "Zhob",
                    sd = "زوب",
                    ur = "ژوب",
                    talukas = listOf(
                        TalukaInfo("zhb_zhob", "Zhob", "زوب", "ژوب"),
                        TalukaInfo("zhb_qamar_din_karez", "Qamar Din Karez", "قمر دين ڪاريز", "قمر دین کاریز"),
                        TalukaInfo("zhb_ashwat", "Ashwat", "اشوات", "اشوات"),
                        TalukaInfo("zhb_kashatu", "Kashatu", "ڪشاتو", "کشاتو"),
                        TalukaInfo("zhb_sambaza", "Sambaza", "سمبازه", "سمبازہ")
                    )
                ),
                DistrictInfo(
                    id = "bl_sherani",
                    en = "Sherani",
                    sd = "شيراني",
                    ur = "شیرانی",
                    talukas = listOf(
                        TalukaInfo("shr_sherani", "Sherani", "شيراني", "شیرانی")
                    )
                ),
                DistrictInfo(
                    id = "bl_killa_saifullah",
                    en = "Killa Saifullah",
                    sd = "قلعه سيف الله",
                    ur = "قلعہ سیف اللہ",
                    talukas = listOf(
                        TalukaInfo("ksf_killa_saifullah", "Killa Saifullah", "قلعه سيف الله", "قلعہ سیف اللہ"),
                        TalukaInfo("ksf_muslim_bagh", "Muslim Bagh", "مسلم باغ", "مسلم باغ"),
                        TalukaInfo("ksf_loiband", "Loiband", "لوئي بند", "لوئی بند"),
                        TalukaInfo("ksf_badini", "Badini", "باديني", "بادینی"),
                        TalukaInfo("ksf_kanmetharzai", "Kanmetharzai", "ڪان ميھترزئي", "کان مہترزئی"),
                        TalukaInfo("ksf_shinki", "Shinki", "شنڪي", "شنکی")
                    )
                )
            )
        ),

        // === 7. LORALAI DIVISION ===
        DivisionInfo(
            id = "bl_div_loralai",
            en = "Loralai Division",
            sd = "لورالائي ڊويزن",
            ur = "لورالائی ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "bl_loralai",
                    en = "Loralai",
                    sd = "لورالائي",
                    ur = "لورالائی",
                    talukas = listOf(
                        TalukaInfo("lrl_loralai", "Loralai", "لورالائي", "لورالائی"),
                        TalukaInfo("lrl_bori", "Bori", "بوري", "بوری"),
                        TalukaInfo("lrl_mekhtar", "Mekhtar", "ميختر", "میختر")
                    )
                ),
                DistrictInfo(
                    id = "bl_barkhan",
                    en = "Barkhan",
                    sd = "بارکان",
                    ur = "بارکھان",
                    talukas = listOf(
                        TalukaInfo("brk_barkhan", "Barkhan", "بارکان", "بارکھان")
                    )
                ),
                DistrictInfo(
                    id = "bl_musakhel",
                    en = "Musakhel",
                    sd = "موسيٰ خيل",
                    ur = "موسیٰ خیل",
                    talukas = listOf(
                        TalukaInfo("msk_musakhel", "Musakhel", "موسيٰ خيل", "موسیٰ خیل"),
                        TalukaInfo("msk_kingri", "Kingri", "ڪنگري", "کنگری"),
                        TalukaInfo("msk_drug", "Drug", "ڊرگ", "ڈرگ"),
                        TalukaInfo("msk_toisar", "Toisar", "توئيسر", "توئیسر")
                    )
                ),
                DistrictInfo(
                    id = "bl_duki",
                    en = "Duki",
                    sd = "دڪي",
                    ur = "دکی",
                    talukas = listOf(
                        TalukaInfo("dki_duki", "Duki", "دڪي", "دکی"),
                        TalukaInfo("dki_luni", "Luni", "لوني", "لونی"),
                        TalukaInfo("dki_thal_chotiali", "Thal Chotiali", "ٿل چوٽيالي", "تھل چوٹیالی")
                    )
                )
            )
        ),

        // === 8. RAKHSHAN DIVISION ===
        DivisionInfo(
            id = "bl_div_rakhshan",
            en = "Rakhshan Division",
            sd = "رخشان ڊويزن",
            ur = "رخشان ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "bl_chagai",
                    en = "Chagai",
                    sd = "چاغي",
                    ur = "چاغی",
                    talukas = listOf(
                        TalukaInfo("chg_dalbandin", "Dalbandin", "دالبندين", "دالبندین"),
                        TalukaInfo("chg_taftan", "Taftan", "تفتان", "تفتان"),
                        TalukaInfo("chg_nokundi", "Nokundi", "نوڪنڊي", "نوکنڈی"),
                        TalukaInfo("chg_chagai", "Chagai", "چاغي", "چاغی"),
                        TalukaInfo("chg_amuri", "Amuri", "آموري", "آموری")
                    )
                ),
                DistrictInfo(
                    id = "bl_nushki",
                    en = "Nushki",
                    sd = "نوشڪي",
                    ur = "نوشکی",
                    talukas = listOf(
                        TalukaInfo("nsk_nushki", "Nushki", "نوشڪي", "نوشکی"),
                        TalukaInfo("nsk_dak", "Dak", "ڊاڪ", "ڈاک")
                    )
                ),
                DistrictInfo(
                    id = "bl_kharan",
                    en = "Kharan",
                    sd = "خاران",
                    ur = "خاران",
                    talukas = listOf(
                        TalukaInfo("khr_kharan", "Kharan", "خاران", "خاران"),
                        TalukaInfo("khr_sar_kharan", "Sar-Kharan", "سرخاران", "سرخاران"),
                        TalukaInfo("khr_tohmulk", "Tohmulk", "توھملڪ", "توہملک")
                    )
                ),
                DistrictInfo(
                    id = "bl_washuk",
                    en = "Washuk",
                    sd = "واشڪ",
                    ur = "واشک",
                    talukas = listOf(
                        TalukaInfo("wsk_washuk", "Washuk", "واشڪ", "واشک"),
                        TalukaInfo("wsk_besima", "Besima", "بسيمه", "بسیمہ"),
                        TalukaInfo("wsk_mashkel", "Mashkel", "ماشڪيل", "ماشکیل"),
                        TalukaInfo("wsk_nag", "Nag", "ناگ", "ناگ"),
                        TalukaInfo("wsk_shahgori", "Shahgori", "شاھگوري", "شاہگوری")
                    )
                )
            )
        )
    )
}
