package com.example.data.model

internal object KpLocationsData {

    val divisions: List<DivisionInfo> = listOf(
        // === 1. PESHAWAR DIVISION ===
        DivisionInfo(
            id = "kp_div_peshawar",
            en = "Peshawar Division",
            sd = "پشاور ڊويزن",
            ur = "پشاور ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "kp_peshawar",
                    en = "Peshawar",
                    sd = "پشاور",
                    ur = "پشاور",
                    talukas = listOf(
                        TalukaInfo("psh_city", "Peshawar City", "پشاور سٽي", "پشاور سٹی"),
                        TalukaInfo("psh_saddar_shah_alam", "Saddar / Shah Alam", "شاھه عالم", "شاہ عالم"),
                        TalukaInfo("psh_mathra", "Mathra", "مٿره", "متھرہ"),
                        TalukaInfo("psh_chamkani", "Chamkani", "چمڪني", "چمکنی"),
                        TalukaInfo("psh_badbher", "Badbher", "بڊھ بير", "بڈھ بیر"),
                        TalukaInfo("psh_pishtakhara", "Pishtakhara", "پشتخره", "پشتخرہ"),
                        TalukaInfo("psh_hassan_khel", "Hassan Khel", "حسن خيل", "حسن خیل")
                    )
                ),
                DistrictInfo(
                    id = "kp_charsadda",
                    en = "Charsadda",
                    sd = "چارسده",
                    ur = "چارسدہ",
                    talukas = listOf(
                        TalukaInfo("chs_charsadda", "Charsadda", "چارسده", "چارسدہ"),
                        TalukaInfo("chs_tangi", "Tangi", "ٽنگي", "تنگی"),
                        TalukaInfo("chs_shabqadar", "Shabqadar", "شبقدر", "شبقدر")
                    )
                ),
                DistrictInfo(
                    id = "kp_nowshera",
                    en = "Nowshera",
                    sd = "نوشھره",
                    ur = "نوشہرہ",
                    talukas = listOf(
                        TalukaInfo("nws_nowshera", "Nowshera", "نوشھره", "نوشہرہ"),
                        TalukaInfo("nws_pabbi", "Pabbi", "پبي", "پبی"),
                        TalukaInfo("nws_jehangira", "Jehangira", "جھانگيره", "جہانگیرہ")
                    )
                ),
                DistrictInfo(
                    id = "kp_khyber",
                    en = "Khyber",
                    sd = "خيبر",
                    ur = "خیبر",
                    talukas = listOf(
                        TalukaInfo("khy_jamrud", "Jamrud", "جمرود", "جمرود"),
                        TalukaInfo("khy_landi_kotal", "Landi Kotal", "لنڊي ڪوتل", "لنڈی کوتل"),
                        TalukaInfo("khy_bara", "Bara", "باڙه", "باڑہ"),
                        TalukaInfo("khy_mula_gori", "Mula Gori", "ملا گوري", "ملا گوری")
                    )
                ),
                DistrictInfo(
                    id = "kp_mohmand",
                    en = "Mohmand",
                    sd = "مھمند",
                    ur = "مہمند",
                    talukas = listOf(
                        TalukaInfo("mhm_upper_ghalanai", "Upper Mohmand / Ghalanai", "غلنئي", "غلنئی"),
                        TalukaInfo("mhm_lower_yake_ghund", "Lower Mohmand / Yake Ghund", "يڪه غنڊ", "یکہ غنڈ"),
                        TalukaInfo("mhm_halimzai", "Halimzai", "حليم زئي", "حلیم زئی"),
                        TalukaInfo("mhm_pindiali", "Pindiali", "پنڊيالي", "پنڈیالی"),
                        TalukaInfo("mhm_prang_ghar", "Prang Ghar", "پرانگ غار", "پرانگ غار"),
                        TalukaInfo("mhm_safi", "Safi", "صافي", "صافی"),
                        TalukaInfo("mhm_ambar_utman_khel", "Ambar Utman Khel", "انبار اتمان خيل", "انبار اتمان خیل")
                    )
                )
            )
        ),

        // === 2. MARDAN DIVISION ===
        DivisionInfo(
            id = "kp_div_mardan",
            en = "Mardan Division",
            sd = "مردان ڊويزن",
            ur = "مردان ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "kp_mardan",
                    en = "Mardan",
                    sd = "مردان",
                    ur = "مردان",
                    talukas = listOf(
                        TalukaInfo("mrd_mardan", "Mardan", "مردان", "مردان"),
                        TalukaInfo("mrd_takht_bhai", "Takht Bhai", "تخت ڀائي", "تخت بھائی"),
                        TalukaInfo("mrd_katlang", "Katlang", "ڪاٽلنگ", "کاٹلنگ"),
                        TalukaInfo("mrd_rustam", "Rustam", "رستم", "رستم"),
                        TalukaInfo("mrd_garhi_kapura", "Garhi Kapura", "ڳڙھي ڪپوره", "گڑھی کپورہ")
                    )
                ),
                DistrictInfo(
                    id = "kp_swabi",
                    en = "Swabi",
                    sd = "صوابي",
                    ur = "صوابی",
                    talukas = listOf(
                        TalukaInfo("swb_swabi", "Swabi", "صوابي", "صوابی"),
                        TalukaInfo("swb_topi", "Topi", "ٽوپي", "ٹوپی"),
                        TalukaInfo("swb_lahor", "Lahor", "لاھور / ڇوٽا لاھور", "چھوٹا لاہور"),
                        TalukaInfo("swb_razar", "Razar", "رزڙ", "رزڑ")
                    )
                )
            )
        ),

        // === 3. HAZARA DIVISION ===
        DivisionInfo(
            id = "kp_div_hazara",
            en = "Hazara Division",
            sd = "هزاره ڊويزن",
            ur = "ہزارہ ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "kp_abbottabad",
                    en = "Abbottabad",
                    sd = "ايبٽ آباد",
                    ur = "ایبٹ آباد",
                    talukas = listOf(
                        TalukaInfo("abt_abbottabad", "Abbottabad", "ايبٽ آباد", "ایبٹ آباد"),
                        TalukaInfo("abt_havelian", "Havelian", "حويليان", "حویلیاں"),
                        TalukaInfo("abt_lora", "Lora", "لورا", "لورا"),
                        TalukaInfo("abt_lower_tanawal", "Lower Tanawal", "لوئر تناول", "لوئر تناول")
                    )
                ),
                DistrictInfo(
                    id = "kp_mansehra",
                    en = "Mansehra",
                    sd = "مانسھره",
                    ur = "مانسہرہ",
                    talukas = listOf(
                        TalukaInfo("mns_mansehra", "Mansehra", "مانسھره", "مانسہرہ"),
                        TalukaInfo("mns_balakot", "Balakot", "بالاڪوٽ", "بالاکوٹ"),
                        TalukaInfo("mns_oghi", "Oghi", "اوگھي", "اوگھی"),
                        TalukaInfo("mns_baffa_pakhal", "Baffa Pakhal", "بفه پکل", "بفہ پکھل"),
                        TalukaInfo("mns_darband", "Darband", "دربند", "دربند"),
                        TalukaInfo("mns_tanawal", "Tanawal", "تناول", "تناول")
                    )
                ),
                DistrictInfo(
                    id = "kp_haripur",
                    en = "Haripur",
                    sd = "ھري پور",
                    ur = "ہری پور",
                    talukas = listOf(
                        TalukaInfo("hrp_haripur", "Haripur", "ھري پور", "ہری پور"),
                        TalukaInfo("hrp_ghazi", "Ghazi", "غازي", "غازی"),
                        TalukaInfo("hrp_khanpur", "Khanpur", "خانپور", "خانپور")
                    )
                ),
                DistrictInfo(
                    id = "kp_battagram",
                    en = "Battagram",
                    sd = "بٽگرام",
                    ur = "بٹگرام",
                    talukas = listOf(
                        TalukaInfo("btg_battagram", "Battagram", "بٽگرام", "بٹگرام"),
                        TalukaInfo("btg_allai", "Allai", "الائي", "آلائی")
                    )
                ),
                DistrictInfo(
                    id = "kp_torghar",
                    en = "Torghar",
                    sd = "تورغر",
                    ur = "تورغر",
                    talukas = listOf(
                        TalukaInfo("tgr_judba", "Judba", "جدبا", "جدبا"),
                        TalukaInfo("tgr_khander_hassanzai", "Khander Hassanzai", "کنڊر حسن زئي", "کھنڈر حسن زئی"),
                        TalukaInfo("tgr_daur_maira", "Daur Maira", "دور ميرا", "دور میرا")
                    )
                ),
                DistrictInfo(
                    id = "kp_upper_kohistan",
                    en = "Upper Kohistan",
                    sd = "اپر ڪوھستان",
                    ur = "اپر کوہستان",
                    talukas = listOf(
                        TalukaInfo("ukh_dassu", "Dassu", "داسو", "داسو"),
                        TalukaInfo("ukh_kandia", "Kandia", "ڪنديا", "کندیا"),
                        TalukaInfo("ukh_harban_basha", "Harban Basha", "ھربن باشا", "ہربن باشا"),
                        TalukaInfo("ukh_seo", "Seo", "سيو", "سیو")
                    )
                ),
                DistrictInfo(
                    id = "kp_lower_kohistan",
                    en = "Lower Kohistan",
                    sd = "لوئر ڪوھستان",
                    ur = "لوئر کوہستان",
                    talukas = listOf(
                        TalukaInfo("lkh_pattan", "Pattan", "پٽن", "پٹن"),
                        TalukaInfo("lkh_bankad", "Bankad", "بنڪڊ", "بنکڈ"),
                        TalukaInfo("lkh_ranolia", "Ranolia", "رنوليه", "رنولیہ")
                    )
                ),
                DistrictInfo(
                    id = "kp_kolai_palas",
                    en = "Kolai-Palas",
                    sd = "ڪولائي پالس",
                    ur = "کولائی پالس",
                    talukas = listOf(
                        TalukaInfo("klp_palas", "Palas", "پالس", "پالس"),
                        TalukaInfo("klp_bataira_kolai", "Bataira / Kolai", "ڪولائي", "کولائی")
                    )
                )
            )
        ),

        // === 4. MALAKAND DIVISION ===
        DivisionInfo(
            id = "kp_div_malakand",
            en = "Malakand Division",
            sd = "مالاڪنڊ ڊويزن",
            ur = "مالاکنڈ ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "kp_swat",
                    en = "Swat",
                    sd = "سوات",
                    ur = "سوات",
                    talukas = listOf(
                        TalukaInfo("swt_babuzai_mingora", "Babuzai / Mingora", "مينگوره", "مینگورہ"),
                        TalukaInfo("swt_matta", "Matta", "مته", "متہ"),
                        TalukaInfo("swt_khwazakhela", "Khwazakhela", "خوازه خيله", "خوازہ خیلہ"),
                        TalukaInfo("swt_barikot", "Barikot", "بريڪوٽ", "بریکوٹ"),
                        TalukaInfo("swt_kabal", "Kabal", "ڪبل", "کبل"),
                        TalukaInfo("swt_charbagh", "Charbagh", "چارباغ", "چارباغ"),
                        TalukaInfo("swt_bahrain", "Bahrain", "بحرين", "بحرین")
                    )
                ),
                DistrictInfo(
                    id = "kp_buner",
                    en = "Buner",
                    sd = "بونير",
                    ur = "بونیر",
                    talukas = listOf(
                        TalukaInfo("bnr_daggar", "Daggar", "ڊگر", "ڈگر"),
                        TalukaInfo("bnr_gagra", "Gagra", "گاگرا", "گاگرا"),
                        TalukaInfo("bnr_khudu_khel", "Khudu Khel", "خدو خيل", "خدو خیل"),
                        TalukaInfo("bnr_mandanr", "Mandanr", "مندڻڙ", "مندنڑ"),
                        TalukaInfo("bnr_chagharzai", "Chagharzai", "چغرزئي", "چغرزئی"),
                        TalukaInfo("bnr_gadezai", "Gadezai", "گديزئي", "گدیزئی")
                    )
                ),
                DistrictInfo(
                    id = "kp_shangla",
                    en = "Shangla",
                    sd = "شانگله",
                    ur = "شانگلہ",
                    talukas = listOf(
                        TalukaInfo("shg_alpuri", "Alpuri", "الپوري", "الپوری"),
                        TalukaInfo("shg_besham", "Besham", "بشام", "بشام"),
                        TalukaInfo("shg_puran", "Puran", "پوريان / پوران", "پوران"),
                        TalukaInfo("shg_chakesar", "Chakesar", "چڪيسر", "چکیسر"),
                        TalukaInfo("shg_martung", "Martung", "مرتنگ", "مرتنگ"),
                        TalukaInfo("shg_makhuzai", "Makhuzai", "مخوزئي", "مخوزئی"),
                        TalukaInfo("shg_shahpur", "Shahpur", "شاھپور", "شاہ پور")
                    )
                ),
                DistrictInfo(
                    id = "kp_malakand",
                    en = "Malakand",
                    sd = "مالاڪنڊ",
                    ur = "مالاکنڈ",
                    talukas = listOf(
                        TalukaInfo("mlk_batkhela", "Batkhela", "بٽ خيله", "بٹ خیلہ"),
                        TalukaInfo("mlk_dargai", "Dargai", "درگئي", "درگئی"),
                        TalukaInfo("mlk_thana_baizai", "Thana Baizai", "ٿاڻه بائيزئي", "تھانہ بائیزئی"),
                        TalukaInfo("mlk_sam_ranizai", "Sam Ranizai", "سم راڻيزئي", "سم رانیزئی")
                    )
                ),
                DistrictInfo(
                    id = "kp_lower_dir",
                    en = "Lower Dir",
                    sd = "لوئر دير",
                    ur = "لوئر دیر",
                    talukas = listOf(
                        TalukaInfo("ldr_timergara", "Timergara", "تيمرگره", "تیمرگرہ"),
                        TalukaInfo("ldr_adenzai", "Adenzai", "ادينزئي", "ادینزئی"),
                        TalukaInfo("ldr_lal_qila", "Lal Qila", "لعل قلعه", "لعل قلعہ"),
                        TalukaInfo("ldr_samarbagh", "Samarbagh", "ثمر باغ", "ثمر باغ"),
                        TalukaInfo("ldr_munda", "Munda", "منڊا", "منڈا"),
                        TalukaInfo("ldr_balambat", "Balambat", "بالمبٽ", "بالمبٹ"),
                        TalukaInfo("ldr_khall", "Khall", "کل", "کھل")
                    )
                ),
                DistrictInfo(
                    id = "kp_upper_dir",
                    en = "Upper Dir",
                    sd = "اپر دير",
                    ur = "اپر دیر",
                    talukas = listOf(
                        TalukaInfo("udr_dir", "Dir", "دير", "دیر"),
                        TalukaInfo("udr_sharingal", "Sharingal", "شرينگل", "شرینگل"),
                        TalukaInfo("udr_wari", "Wari", "واڙي", "واڑی"),
                        TalukaInfo("udr_barawal", "Barawal", "براول", "براول"),
                        TalukaInfo("udr_kalkot", "Kalkot", "ڪالڪوٽ", "کالکوٹ"),
                        TalukaInfo("udr_lar_jam", "Lar Jam", "لار جام", "لار جام")
                    )
                ),
                DistrictInfo(
                    id = "kp_central_dir",
                    en = "Central Dir",
                    sd = "سينٽرل دير",
                    ur = "سینٹرل دیر",
                    talukas = listOf(
                        TalukaInfo("cdr_wari_akhagram", "Wari / Akhagram", "آخاگرام", "آخاگرام"),
                        TalukaInfo("cdr_nehag_dara", "Nehag Dara", "نھاگ دره", "نہاگ درہ")
                    )
                ),
                DistrictInfo(
                    id = "kp_lower_chitral",
                    en = "Lower Chitral",
                    sd = "لوئر چترال",
                    ur = "لوئر چترال",
                    talukas = listOf(
                        TalukaInfo("lch_chitral", "Chitral", "چترال", "چترال"),
                        TalukaInfo("lch_drosh", "Drosh", "دروش", "دروش")
                    )
                ),
                DistrictInfo(
                    id = "kp_upper_chitral",
                    en = "Upper Chitral",
                    sd = "اپر چترال",
                    ur = "اپر چترال",
                    talukas = listOf(
                        TalukaInfo("uch_mastuj", "Mastuj", "مستوج", "مستوج"),
                        TalukaInfo("uch_mulkhow_torkhow", "Mulkhow / Torkhow", "ملکو / تورکو", "ملکھو / تورکھو")
                    )
                ),
                DistrictInfo(
                    id = "kp_bajaur",
                    en = "Bajaur",
                    sd = "باجوڙ",
                    ur = "باجوڑ",
                    talukas = listOf(
                        TalukaInfo("bjr_khar_bajaur", "Khar Bajaur", "خار باجوڙ", "خار باجوڑ"),
                        TalukaInfo("bjr_nawagai", "Nawagai", "نواگئي", "نواگئی"),
                        TalukaInfo("bjr_mamund", "Mamund", "ماموند", "ماموند"),
                        TalukaInfo("bjr_salarzai", "Salarzai", "سالارزئي", "سالارزئی"),
                        TalukaInfo("bjr_utman_khel", "Utman Khel", "اتمان خيل", "اتمان خیل"),
                        TalukaInfo("bjr_barang", "Barang", "برنگ", "برنگ"),
                        TalukaInfo("bjr_bar_chamer_kand", "Bar Chamer Kand", "بر چمرڪنڊ", "بر چمرکند")
                    )
                )
            )
        ),

        // === 5. KOHAT DIVISION ===
        DivisionInfo(
            id = "kp_div_kohat",
            en = "Kohat Division",
            sd = "ڪوهاٽ ڊويزن",
            ur = "کوہاٹ ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "kp_kohat",
                    en = "Kohat",
                    sd = "ڪوهاٽ",
                    ur = "کوہاٹ",
                    talukas = listOf(
                        TalukaInfo("kht_kohat", "Kohat", "ڪوهاٽ", "کوہاٹ"),
                        TalukaInfo("kht_lachi", "Lachi", "لاچي", "لاچی"),
                        TalukaInfo("kht_gumbat", "Gumbat", "گمبٽ", "گمبٹ"),
                        TalukaInfo("kht_dara_adam_khel", "Dara Adam Khel", "دره آدم خيل", "درہ آدم خیل")
                    )
                ),
                DistrictInfo(
                    id = "kp_karak",
                    en = "Karak",
                    sd = "ڪرڪ",
                    ur = "کرک",
                    talukas = listOf(
                        TalukaInfo("krk_karak", "Karak", "ڪرڪ", "کرک"),
                        TalukaInfo("krk_banda_daud_shah", "Banda Daud Shah", "بانڊه دائود شاھه", "بانڈہ داؤد شاہ"),
                        TalukaInfo("krk_takht_e_nasrati", "Takht-e-Nasrati", "تخت نصرتي", "تخت نصرتی")
                    )
                ),
                DistrictInfo(
                    id = "kp_hangu",
                    en = "Hangu",
                    sd = "هنگو",
                    ur = "ہنگو",
                    talukas = listOf(
                        TalukaInfo("hng_hangu", "Hangu", "هنگو", "ہنگو"),
                        TalukaInfo("hng_tall", "Tall", "ٽل", "ٹل")
                    )
                ),
                DistrictInfo(
                    id = "kp_kurram",
                    en = "Kurram",
                    sd = "ڪرم",
                    ur = "کرم",
                    talukas = listOf(
                        TalukaInfo("krm_upper_parachinar", "Upper Kurram / Parachinar", "پاراچنار", "پاراچنار"),
                        TalukaInfo("krm_lower_sadda", "Lower Kurram / Sadda", "صده", "صدہ"),
                        TalukaInfo("krm_central", "Central Kurram", "سينٽرل ڪرم", "سینٹرل کرم")
                    )
                ),
                DistrictInfo(
                    id = "kp_orakzai",
                    en = "Orakzai",
                    sd = "اورڪزئي",
                    ur = "اورکزئی",
                    talukas = listOf(
                        TalukaInfo("ork_upper", "Upper Orakzai", "اپر اورڪزئي", "اپر اورکزئی"),
                        TalukaInfo("ork_lower_kalaya", "Lower Orakzai / Kalaya", "ڪلايا", "کلایا"),
                        TalukaInfo("ork_central", "Central Orakzai", "سينٽرل اورڪزئي", "سینٹرل اورکزئی"),
                        TalukaInfo("ork_ismail_zai", "Ismail Zai", "اسماعيل زئي", "اسماعیل زئی")
                    )
                )
            )
        ),

        // === 6. BANNU DIVISION ===
        DivisionInfo(
            id = "kp_div_bannu",
            en = "Bannu Division",
            sd = "بنون ڊويزن",
            ur = "بنوں ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "kp_bannu",
                    en = "Bannu",
                    sd = "بنون",
                    ur = "بنوں",
                    talukas = listOf(
                        TalukaInfo("bnu_bannu", "Bannu", "بنون", "بنوں"),
                        TalukaInfo("bnu_domel", "Domel", "ڊوميل", "ڈومیل"),
                        TalukaInfo("bnu_kakki", "Kakki", "ڪڪي", "ککی"),
                        TalukaInfo("bnu_baka_khel", "Baka Khel", "بڪه خيل", "بکہ خیل"),
                        TalukaInfo("bnu_miryan", "Miryan", "مريان", "میریان"),
                        TalukaInfo("bnu_wazir", "Wazir", "وزير", "وزیر")
                    )
                ),
                DistrictInfo(
                    id = "kp_lakki_marwat",
                    en = "Lakki Marwat",
                    sd = "لڪي مروت",
                    ur = "لکی مروت",
                    talukas = listOf(
                        TalukaInfo("lkm_lakki_marwat", "Lakki Marwat", "لڪي مروت", "لکی مروت"),
                        TalukaInfo("lkm_sarai_naurang", "Sarai Naurang", "سرائي نورنگ", "سرائے نورنگ"),
                        TalukaInfo("lkm_ghazni_khel", "Ghazni Khel", "غزني خيل", "غزنی خیل"),
                        TalukaInfo("lkm_bettani", "Bettani", "بيٽني", "بیٹنی")
                    )
                ),
                DistrictInfo(
                    id = "kp_north_waziristan",
                    en = "North Waziristan",
                    sd = "اتر وزيرستان",
                    ur = "شمالی وزیرستان",
                    talukas = listOf(
                        TalukaInfo("nwz_miranshah", "Miranshah", "ميرانشاھه", "میرانشاہ"),
                        TalukaInfo("nwz_mir_ali", "Mir Ali", "مير علي", "میر علی"),
                        TalukaInfo("nwz_razmak", "Razmak", "رزمڪ", "رزمک"),
                        TalukaInfo("nwz_datta_khel", "Datta Khel", "دته خيل", "دتہ خیل"),
                        TalukaInfo("nwz_spinwam", "Spinwam", "سپين وام", "اسپین وام"),
                        TalukaInfo("nwz_shewa", "Shewa", "شيوه", "شیوہ"),
                        TalukaInfo("nwz_dossali", "Dossali", "دوسلي", "دوسلی"),
                        TalukaInfo("nwz_gharyum", "Gharyum", "غريوم", "غریوم"),
                        TalukaInfo("nwz_shawal", "Shawal", "شوال", "شوال")
                    )
                )
            )
        ),

        // === 7. DERA ISMAIL KHAN DIVISION ===
        DivisionInfo(
            id = "kp_div_dik",
            en = "Dera Ismail Khan Division",
            sd = "ڊيره اسماعيل خان ڊويزن",
            ur = "ڈیرہ اسماعیل خان ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "kp_dera_ismail_khan",
                    en = "Dera Ismail Khan",
                    sd = "ڊيره اسماعيل خان",
                    ur = "ڈیرہ اسماعیل خان",
                    talukas = listOf(
                        TalukaInfo("dik_dera_ismail_khan", "Dera Ismail Khan", "ڊيره اسماعيل خان", "ڈیرہ اسماعیل خان"),
                        TalukaInfo("dik_paharpur", "Paharpur", "پھاڙپور", "پہاڑپور"),
                        TalukaInfo("dik_kulachi", "Kulachi", "ڪلاچي", "کلاچی"),
                        TalukaInfo("dik_paroa", "Paroa", "پروآ", "پروا"),
                        TalukaInfo("dik_daraban", "Daraban", "درابن", "درابن"),
                        TalukaInfo("dik_darazinda", "Darazinda", "درازنده", "درازندہ")
                    )
                ),
                DistrictInfo(
                    id = "kp_tank",
                    en = "Tank",
                    sd = "ٽانڪ",
                    ur = "ٹانک",
                    talukas = listOf(
                        TalukaInfo("tnk_tank", "Tank", "ٽانڪ", "ٹانک"),
                        TalukaInfo("tnk_jandola", "Jandola", "جنڊوله", "جنڈولہ")
                    )
                ),
                DistrictInfo(
                    id = "kp_upper_south_waziristan",
                    en = "Upper South Waziristan",
                    sd = "اپر ڏکڻ وزيرستان",
                    ur = "اپر جنوبی وزیرستان",
                    talukas = listOf(
                        TalukaInfo("usw_ladha", "Ladha", "لڌا", "لدھا"),
                        TalukaInfo("usw_makin", "Makin", "مڪين", "مکین"),
                        TalukaInfo("usw_sararogha", "Sararogha", "سراروغه", "سراروغہ"),
                        TalukaInfo("usw_tiarza", "Tiarza", "تيارزه", "تیارزہ"),
                        TalukaInfo("usw_shaktoi", "Shaktoi", "شڪتوئي", "شکتوئی"),
                        TalukaInfo("usw_shawal", "Shawal", "شوال", "شوال")
                    )
                ),
                DistrictInfo(
                    id = "kp_lower_south_waziristan",
                    en = "Lower South Waziristan",
                    sd = "لوئر ڏکڻ وزيرستان",
                    ur = "لوئر جنوبی وزیرستان",
                    talukas = listOf(
                        TalukaInfo("lsw_wana", "Wana", "واڻه", "وانہ"),
                        TalukaInfo("lsw_birmil", "Birmil", "برمل", "برمل"),
                        TalukaInfo("lsw_toi_khulla", "Toi Khulla", "توئي خله", "توئی خلہ"),
                        TalukaInfo("lsw_shakai", "Shakai", "شڪئي", "شکئی")
                    )
                )
            )
        )
    )
}
