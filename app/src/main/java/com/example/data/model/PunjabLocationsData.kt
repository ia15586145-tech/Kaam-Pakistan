package com.example.data.model

internal object PunjabLocationsData {

    val divisions: List<DivisionInfo> = listOf(
        // === 1. LAHORE DIVISION ===
        DivisionInfo(
            id = "punjab_div_lahore",
            en = "Lahore Division",
            sd = "لاهور ڊويزن",
            ur = "لاہور ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "punjab_lahore",
                    en = "Lahore",
                    sd = "لاهور",
                    ur = "لاہور",
                    talukas = listOf(
                        TalukaInfo("lhr_city", "Lahore City", "لاهور سٽي", "لاہور سٹی"),
                        TalukaInfo("lhr_cantt", "Lahore Cantt", "لاهور ڪينٽ", "لاہور کینٹ"),
                        TalukaInfo("lhr_model_town", "Model Town", "ماڊل ٽائون", "ماڈل ٹاؤن"),
                        TalukaInfo("lhr_raiwind", "Raiwind", "رائيونڊ", "رائیونڈ"),
                        TalukaInfo("lhr_shalimar", "Shalimar", "شاليمار", "شالیمار")
                    )
                ),
                DistrictInfo(
                    id = "punjab_kasur",
                    en = "Kasur",
                    sd = "قصور",
                    ur = "قصور",
                    talukas = listOf(
                        TalukaInfo("ksr_kasur", "Kasur", "قصور", "قصور"),
                        TalukaInfo("ksr_chunian", "Chunian", "چونيان", "چونیاں"),
                        TalukaInfo("ksr_pattoki", "Pattoki", "پتوڪي", "پتوکی"),
                        TalukaInfo("ksr_kot_radha_kishan", "Kot Radha Kishan", "ڪوٽ راڌا ڪشن", "کوٹ رادھا کشن")
                    )
                ),
                DistrictInfo(
                    id = "punjab_sheikhupura",
                    en = "Sheikhupura",
                    sd = "شيخوپوره",
                    ur = "شیخوپورہ",
                    talukas = listOf(
                        TalukaInfo("skp_sheikhupura", "Sheikhupura", "شيخوپوره", "شیخوپورہ"),
                        TalukaInfo("skp_ferozewala", "Ferozewala", "فيروزوالا", "فیروزوالا"),
                        TalukaInfo("skp_muridke", "Muridke", "مريدڪي", "مریدکے"),
                        TalukaInfo("skp_safdarabad", "Safdarabad", "صفدرآباد", "صفدرآباد"),
                        TalukaInfo("skp_sharakpur", "Sharakpur", "شرقپور", "شرقپور")
                    )
                ),
                DistrictInfo(
                    id = "punjab_nankana_sahib",
                    en = "Nankana Sahib",
                    sd = "ننڪانه صاحب",
                    ur = "ننکانہ صاحب",
                    talukas = listOf(
                        TalukaInfo("nks_nankana_sahib", "Nankana Sahib", "ننڪانه صاحب", "ننکانہ صاحب"),
                        TalukaInfo("nks_sangla_hill", "Sangla Hill", "سانگله هل", "سانگلہ ہل"),
                        TalukaInfo("nks_shah_kot", "Shah Kot", "شاھه ڪوٽ", "شاہ کوٹ")
                    )
                )
            )
        ),

        // === 2. RAWALPINDI DIVISION ===
        DivisionInfo(
            id = "punjab_div_rawalpindi",
            en = "Rawalpindi Division",
            sd = "راولپنڊي ڊويزن",
            ur = "راولپنڈی ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "punjab_rawalpindi",
                    en = "Rawalpindi",
                    sd = "راولپنڊي",
                    ur = "راولپنڈی",
                    talukas = listOf(
                        TalukaInfo("rwp_city", "Rawalpindi City", "راولپنڊي سٽي", "راولپنڈی سٹی"),
                        TalukaInfo("rwp_cantt", "Rawalpindi Cantt", "راولپنڊي ڪينٽ", "راولپنڈی کینٹ"),
                        TalukaInfo("rwp_saddar", "Rawalpindi Saddar", "راولپنڊي صدر", "راولپنڈی صدر"),
                        TalukaInfo("rwp_gujar_khan", "Gujar Khan", "گجر خان", "گوجر خان"),
                        TalukaInfo("rwp_kahuta", "Kahuta", "ڪھوٽا", "کہوٹہ"),
                        TalukaInfo("rwp_kallar_syedan", "Kallar Syedan", "ڪلر سيدان", "کلر سیداں"),
                        TalukaInfo("rwp_taxila", "Taxila", "ٽيڪسلا", "ٹیکسلا")
                    )
                ),
                DistrictInfo(
                    id = "punjab_murree",
                    en = "Murree",
                    sd = "مري",
                    ur = "مری",
                    talukas = listOf(
                        TalukaInfo("mre_murree", "Murree", "مري", "مری"),
                        TalukaInfo("mre_kotli_sattian", "Kotli Sattian", "ڪوٽلي ستيان", "کوٹلی ستیاں")
                    )
                ),
                DistrictInfo(
                    id = "punjab_attock",
                    en = "Attock",
                    sd = "اٽڪ",
                    ur = "اٹک",
                    talukas = listOf(
                        TalukaInfo("atk_attock", "Attock", "اٽڪ", "اٹک"),
                        TalukaInfo("atk_fateh_jang", "Fateh Jang", "فتح جنگ", "فتح جنگ"),
                        TalukaInfo("atk_hassan_abdal", "Hassan Abdal", "حسن ابدال", "حسن ابدال"),
                        TalukaInfo("atk_hazro", "Hazro", "حضرو", "حضرو"),
                        TalukaInfo("atk_jand", "Jand", "جنڊ", "جنڈ"),
                        TalukaInfo("atk_pindi_gheb", "Pindi Gheb", "پنڊي گھيب", "پنڈی گھیب")
                    )
                ),
                DistrictInfo(
                    id = "punjab_jhelum",
                    en = "Jhelum",
                    sd = "جهلم",
                    ur = "جہلم",
                    talukas = listOf(
                        TalukaInfo("jhl_jhelum", "Jhelum", "جهلم", "جہلم"),
                        TalukaInfo("jhl_dina", "Dina", "دينه", "دینہ"),
                        TalukaInfo("jhl_pind_dadan_khan", "Pind Dadan Khan", "پنڊ دادن خان", "پنڈ دادن خان"),
                        TalukaInfo("jhl_sohawa", "Sohawa", "سهاوه", "سوہاوہ")
                    )
                ),
                DistrictInfo(
                    id = "punjab_chakwal",
                    en = "Chakwal",
                    sd = "چڪوال",
                    ur = "چکوال",
                    talukas = listOf(
                        TalukaInfo("ckw_chakwal", "Chakwal", "چڪوال", "چکوال"),
                        TalukaInfo("ckw_choa_saidan_shah", "Choa Saidan Shah", "چوآ سيدن شاھه", "چوآ سیدن شاہ"),
                        TalukaInfo("ckw_kallar_kahar", "Kallar Kahar", "ڪلر ڪھار", "کلر کہار")
                    )
                ),
                DistrictInfo(
                    id = "punjab_talagang",
                    en = "Talagang",
                    sd = "تله گنگ",
                    ur = "تلہ گنگ",
                    talukas = listOf(
                        TalukaInfo("tlg_talagang", "Talagang", "تله گنگ", "تلہ گنگ"),
                        TalukaInfo("tlg_lawa", "Lawa", "لاوا", "لاوا")
                    )
                )
            )
        ),

        // === 3. GUJRANWALA DIVISION ===
        DivisionInfo(
            id = "punjab_div_gujranwala",
            en = "Gujranwala Division",
            sd = "گوجرانواله ڊويزن",
            ur = "گوجرانوالہ ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "punjab_gujranwala",
                    en = "Gujranwala",
                    sd = "گوجرانواله",
                    ur = "گوجرانوالہ",
                    talukas = listOf(
                        TalukaInfo("gjw_city", "Gujranwala City", "گوجرانواله سٽي", "گوجرانوالہ سٹی"),
                        TalukaInfo("gjw_saddar", "Gujranwala Saddar", "گوجرانواله صدر", "گوجرانوالہ صدر"),
                        TalukaInfo("gjw_kamoke", "Kamoke", "ڪاموڪي", "کامونکی"),
                        TalukaInfo("gjw_nowshera_virkan", "Nowshera Virkan", "نوشھره ورڪان", "نوشہرہ ورکاں")
                    )
                ),
                DistrictInfo(
                    id = "punjab_sialkot",
                    en = "Sialkot",
                    sd = "سيالڪوٽ",
                    ur = "سیالکوٹ",
                    talukas = listOf(
                        TalukaInfo("slk_sialkot", "Sialkot", "سيالڪوٽ", "سیالکوٹ"),
                        TalukaInfo("slk_daska", "Daska", "ڊسڪه", "ڈسکہ"),
                        TalukaInfo("slk_pasrur", "Pasrur", "پسرور", "پسرور"),
                        TalukaInfo("slk_sambrial", "Sambrial", "سمبڙيال", "سمبڑیال")
                    )
                ),
                DistrictInfo(
                    id = "punjab_narowal",
                    en = "Narowal",
                    sd = "نارووال",
                    ur = "نارووال",
                    talukas = listOf(
                        TalukaInfo("nrw_narowal", "Narowal", "نارووال", "نارووال"),
                        TalukaInfo("nrw_shakargarh", "Shakargarh", "شڪرڳڙهه", "شکرگڑھ"),
                        TalukaInfo("nrw_zafarwal", "Zafarwal", "ظفروال", "ظفروال")
                    )
                )
            )
        ),

        // === 4. GUJRAT DIVISION ===
        DivisionInfo(
            id = "punjab_div_gujrat",
            en = "Gujrat Division",
            sd = "گجرات ڊويزن",
            ur = "گجرات ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "punjab_gujrat",
                    en = "Gujrat",
                    sd = "گجرات",
                    ur = "گجرات",
                    talukas = listOf(
                        TalukaInfo("gjr_gujrat", "Gujrat", "گجرات", "گجرات"),
                        TalukaInfo("gjr_kharian", "Kharian", "کارياں", "کھاریاں"),
                        TalukaInfo("gjr_sarai_alamgir", "Sarai Alamgir", "سرائي عالمگير", "سرائے عالمگیر"),
                        TalukaInfo("gjr_jalalpur_jattan", "Jalalpur Jattan", "جلالپور جٽان", "جلالپور جٹاں"),
                        TalukaInfo("gjr_kunjah", "Kunjah", "ڪنجاھه", "کنجاہ")
                    )
                ),
                DistrictInfo(
                    id = "punjab_mandi_bahauddin",
                    en = "Mandi Bahauddin",
                    sd = "منڊي بهاؤالدين",
                    ur = "منڈی بہاؤالدین",
                    talukas = listOf(
                        TalukaInfo("mbd_mandi_bahauddin", "Mandi Bahauddin", "منڊي بهاؤالدين", "منڈی بہاؤالدین"),
                        TalukaInfo("mbd_malakwal", "Malakwal", "ملڪوال", "ملکوال"),
                        TalukaInfo("mbd_phalia", "Phalia", "ڦاليه", "پھالیہ")
                    )
                ),
                DistrictInfo(
                    id = "punjab_hafizabad",
                    en = "Hafizabad",
                    sd = "حافظ آباد",
                    ur = "حافظ آباد",
                    talukas = listOf(
                        TalukaInfo("hfz_hafizabad", "Hafizabad", "حافظ آباد", "حافظ آباد"),
                        TalukaInfo("hfz_pindi_bhattian", "Pindi Bhattian", "پنڊي ڀٽيان", "پنڈی بھٹیاں")
                    )
                ),
                DistrictInfo(
                    id = "punjab_wazirabad",
                    en = "Wazirabad",
                    sd = "وزيرآباد",
                    ur = "وزیرآباد",
                    talukas = listOf(
                        TalukaInfo("wzr_wazirabad", "Wazirabad", "وزيرآباد", "وزیرآباد"),
                        TalukaInfo("wzr_alipur_chattha", "Alipur Chattha", "علي پور چٺه", "علی پور چٹھہ")
                    )
                )
            )
        ),

        // === 5. FAISALABAD DIVISION ===
        DivisionInfo(
            id = "punjab_div_faisalabad",
            en = "Faisalabad Division",
            sd = "فيصل آباد ڊويزن",
            ur = "فیصل آباد ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "punjab_faisalabad",
                    en = "Faisalabad",
                    sd = "فيصل آباد",
                    ur = "فیصل آباد",
                    talukas = listOf(
                        TalukaInfo("fsd_city", "Faisalabad City", "فيصل آباد سٽي", "فیصل آباد سٹی"),
                        TalukaInfo("fsd_saddar", "Faisalabad Saddar", "فيصل آباد صدر", "فیصل آباد صدر"),
                        TalukaInfo("fsd_chak_jhumra", "Chak Jhumra", "چڪ جھمره", "چک جھمرہ"),
                        TalukaInfo("fsd_jaranwala", "Jaranwala", "جڙانواله", "جڑانوالہ"),
                        TalukaInfo("fsd_samundri", "Samundri", "سمندري", "سمندری"),
                        TalukaInfo("fsd_tandlianwala", "Tandlianwala", "تاندليانواله", "تاندلیانوالہ")
                    )
                ),
                DistrictInfo(
                    id = "punjab_jhang",
                    en = "Jhang",
                    sd = "جھنگ",
                    ur = "جھنگ",
                    talukas = listOf(
                        TalukaInfo("jhg_jhang", "Jhang", "جھنگ", "جھنگ"),
                        TalukaInfo("jhg_ahmadpur_sial", "Ahmadpur Sial", "احمدپور سيال", "احمد پور سیال"),
                        TalukaInfo("jhg_athara_hazari", "Athara Hazari", "اٺاره هزاري", "اٹھارہ ہزاری"),
                        TalukaInfo("jhg_shorkot", "Shorkot", "شورڪوٽ", "شورکوٹ")
                    )
                ),
                DistrictInfo(
                    id = "punjab_toba_tek_singh",
                    en = "Toba Tek Singh",
                    sd = "ٽوبه ٽيڪ سنگهه",
                    ur = "ٹوبہ ٹیک سنگھ",
                    talukas = listOf(
                        TalukaInfo("tts_toba_tek_singh", "Toba Tek Singh", "ٽوبه ٽيڪ سنگهه", "ٹوبہ ٹیک سنگھ"),
                        TalukaInfo("tts_gojra", "Gojra", "گوجره", "گوجرہ"),
                        TalukaInfo("tts_kamalia", "Kamalia", "ڪماليه", "کمالیہ"),
                        TalukaInfo("tts_pirmahal", "Pirmahal", "پير محل", "پیر محل")
                    )
                ),
                DistrictInfo(
                    id = "punjab_chiniot",
                    en = "Chiniot",
                    sd = "چنيوٽ",
                    ur = "چنیوٹ",
                    talukas = listOf(
                        TalukaInfo("chn_chiniot", "Chiniot", "چنيوٽ", "چنیوٹ"),
                        TalukaInfo("chn_bhawana", "Bhawana", "ڀوانه", "بھوانہ"),
                        TalukaInfo("chn_lalian", "Lalian", "لاليان", "لالیاں")
                    )
                )
            )
        ),

        // === 6. SARGODHA DIVISION ===
        DivisionInfo(
            id = "punjab_div_sargodha",
            en = "Sargodha Division",
            sd = "سرگوڌا ڊويزن",
            ur = "سرگودھا ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "punjab_sargodha",
                    en = "Sargodha",
                    sd = "سرگوڌا",
                    ur = "سرگودھا",
                    talukas = listOf(
                        TalukaInfo("sgd_sargodha", "Sargodha", "سرگوڌا", "سرگودھا"),
                        TalukaInfo("sgd_bhalwal", "Bhalwal", "ڀلوال", "بھلوال"),
                        TalukaInfo("sgd_bhera", "Bhera", "ڀيره", "بھیرہ"),
                        TalukaInfo("sgd_kot_momin", "Kot Momin", "ڪوٽ مومن", "کوٹ مومن"),
                        TalukaInfo("sgd_sahiwal", "Sahiwal", "ساهيوال", "ساہیوال"),
                        TalukaInfo("sgd_shahpur", "Shahpur", "شاھپور", "شاہ پور"),
                        TalukaInfo("sgd_sillanwali", "Sillanwali", "سلانوالي", "سلانوالی")
                    )
                ),
                DistrictInfo(
                    id = "punjab_khushab",
                    en = "Khushab",
                    sd = "خوشاب",
                    ur = "خوشاب",
                    talukas = listOf(
                        TalukaInfo("khs_khushab", "Khushab", "خوشاب", "خوشاب"),
                        TalukaInfo("khs_noorpur_thal", "Noorpur Thal", "نورپور ٿل", "نورپور تھل"),
                        TalukaInfo("khs_quaidabad", "Quaidabad", "قائدآباد", "قائدآباد"),
                        TalukaInfo("khs_naushera", "Naushera", "نوشھره", "نوشہرہ")
                    )
                ),
                DistrictInfo(
                    id = "punjab_mianwali",
                    en = "Mianwali",
                    sd = "ميانوالي",
                    ur = "میانوالی",
                    talukas = listOf(
                        TalukaInfo("mnw_mianwali", "Mianwali", "ميانوالي", "میانوالی"),
                        TalukaInfo("mnw_isakhel", "Isakhel", "عيسيٰ خيل", "عیسیٰ خیل"),
                        TalukaInfo("mnw_piplan", "Piplan", "پپلاں", "پپلاں")
                    )
                ),
                DistrictInfo(
                    id = "punjab_bhakkar",
                    en = "Bhakkar",
                    sd = "ڀڪر",
                    ur = "بھکر",
                    talukas = listOf(
                        TalukaInfo("bhk_bhakkar", "Bhakkar", "ڀڪر", "بھکر"),
                        TalukaInfo("bhk_darya_khan", "Darya Khan", "دريا خان", "دریا خان"),
                        TalukaInfo("bhk_kalur_kot", "Kalur Kot", "ڪلور ڪوٽ", "کلور کوٹ"),
                        TalukaInfo("bhk_mankera", "Mankera", "منڪيره", "منکیرہ")
                    )
                )
            )
        ),

        // === 7. MULTAN DIVISION ===
        DivisionInfo(
            id = "punjab_div_multan",
            en = "Multan Division",
            sd = "ملتان ڊويزن",
            ur = "ملتان ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "punjab_multan",
                    en = "Multan",
                    sd = "ملتان",
                    ur = "ملتان",
                    talukas = listOf(
                        TalukaInfo("mlt_city", "Multan City", "ملتان سٽي", "ملتان سٹی"),
                        TalukaInfo("mlt_saddar", "Multan Saddar", "ملتان صدر", "ملتان صدر"),
                        TalukaInfo("mlt_jalalpur_pirwala", "Jalalpur Pirwala", "جلالپور پيروالا", "جلالپور پیروالا"),
                        TalukaInfo("mlt_shujabad", "Shujabad", "شجاع آباد", "شجاع آباد")
                    )
                ),
                DistrictInfo(
                    id = "punjab_khanewal",
                    en = "Khanewal",
                    sd = "خانيوال",
                    ur = "خانیوال",
                    talukas = listOf(
                        TalukaInfo("knw_khanewal", "Khanewal", "خانيوال", "خانیوال"),
                        TalukaInfo("knw_jahanian", "Jahanian", "جهانيان", "جہانیاں"),
                        TalukaInfo("knw_kabirwala", "Kabirwala", "ڪبيروالا", "کبیروالا"),
                        TalukaInfo("knw_mian_channu", "Mian Channu", "ميان چنون", "میاں چنوں")
                    )
                ),
                DistrictInfo(
                    id = "punjab_lodhran",
                    en = "Lodhran",
                    sd = "لوڌران",
                    ur = "لودھراں",
                    talukas = listOf(
                        TalukaInfo("ldh_lodhran", "Lodhran", "لوڌران", "لودھراں"),
                        TalukaInfo("ldh_dunyapur", "Dunyapur", "دنياپور", "دنیاپور"),
                        TalukaInfo("ldh_kahror_pacca", "Kahror Pacca", "ڪھروڙ پڪا", "کہروڑ پکا")
                    )
                ),
                DistrictInfo(
                    id = "punjab_vehari",
                    en = "Vehari",
                    sd = "وهاڙي",
                    ur = "وہاڑی",
                    talukas = listOf(
                        TalukaInfo("vhr_vehari", "Vehari", "وهاڙي", "وہاڑی"),
                        TalukaInfo("vhr_burewala", "Burewala", "بوريوالا", "بورے والا"),
                        TalukaInfo("vhr_mailsi", "Mailsi", "ميلسي", "میلسی")
                    )
                )
            )
        ),

        // === 8. SAHIWAL DIVISION ===
        DivisionInfo(
            id = "punjab_div_sahiwal",
            en = "Sahiwal Division",
            sd = "ساهيوال ڊويزن",
            ur = "ساہیوال ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "punjab_sahiwal",
                    en = "Sahiwal",
                    sd = "ساهيوال",
                    ur = "ساہیوال",
                    talukas = listOf(
                        TalukaInfo("swl_sahiwal", "Sahiwal", "ساهيوال", "ساہیوال"),
                        TalukaInfo("swl_chichawatni", "Chichawatni", "چيچاوطني", "چیچہ وطنی")
                    )
                ),
                DistrictInfo(
                    id = "punjab_okara",
                    en = "Okara",
                    sd = "اوڪاڙه",
                    ur = "اوکاڑہ",
                    talukas = listOf(
                        TalukaInfo("okr_okara", "Okara", "اوڪاڙه", "اوکاڑہ"),
                        TalukaInfo("okr_depalpur", "Depalpur", "ديپالپور", "دیپالپور"),
                        TalukaInfo("okr_renala_khurd", "Renala Khurd", "ريناله خورد", "رینالہ خورد")
                    )
                ),
                DistrictInfo(
                    id = "punjab_pakpattan",
                    en = "Pakpattan",
                    sd = "پاڪپتن",
                    ur = "پاکپتن",
                    talukas = listOf(
                        TalukaInfo("pkp_pakpattan", "Pakpattan", "پاڪپتن", "پاکپتن"),
                        TalukaInfo("pkp_arifwala", "Arifwala", "عارف والا", "عارف والا")
                    )
                )
            )
        ),

        // === 9. BAHAWALPUR DIVISION ===
        DivisionInfo(
            id = "punjab_div_bahawalpur",
            en = "Bahawalpur Division",
            sd = "بهاولپور ڊويزن",
            ur = "بہاولپور ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "punjab_bahawalpur",
                    en = "Bahawalpur",
                    sd = "بهاولپور",
                    ur = "بہاولپور",
                    talukas = listOf(
                        TalukaInfo("bwp_city", "Bahawalpur City", "بهاولپور سٽي", "بہاولپور سٹی"),
                        TalukaInfo("bwp_saddar", "Bahawalpur Saddar", "بهاولپور صدر", "بہاولپور صدر"),
                        TalukaInfo("bwp_ahmadpur_east", "Ahmadpur East", "احمدپور اوڀر", "احمد پور شرقیہ"),
                        TalukaInfo("bwp_hasilpur", "Hasilpur", "حاصل پور", "حاصل پور"),
                        TalukaInfo("bwp_khairpur_tamewali", "Khairpur Tamewali", "خيرپور ٽاميوالي", "خیرپور ٹامیوالی"),
                        TalukaInfo("bwp_yazman", "Yazman", "يزمان", "یزمان")
                    )
                ),
                DistrictInfo(
                    id = "punjab_bahawalnagar",
                    en = "Bahawalnagar",
                    sd = "بهاولنگر",
                    ur = "بہاولنگر",
                    talukas = listOf(
                        TalukaInfo("bwn_bahawalnagar", "Bahawalnagar", "بهاولنگر", "بہاولنگر"),
                        TalukaInfo("bwn_chishtian", "Chishtian", "چشتيان", "چشتیاں"),
                        TalukaInfo("bwn_fort_abbas", "Fort Abbas", "فورٽ عباس", "فورٹ عباس"),
                        TalukaInfo("bwn_haroonabad", "Haroonabad", "هارون آباد", "ہارون آباد"),
                        TalukaInfo("bwn_minchinabad", "Minchinabad", "منچن آباد", "منچن آباد")
                    )
                ),
                DistrictInfo(
                    id = "punjab_rahim_yar_khan",
                    en = "Rahim Yar Khan",
                    sd = "رحيم يار خان",
                    ur = "رحیم یار خان",
                    talukas = listOf(
                        TalukaInfo("ryk_rahim_yar_khan", "Rahim Yar Khan", "رحيم يار خان", "رحیم یار خان"),
                        TalukaInfo("ryk_khanpur", "Khanpur", "خانپور", "خانپور"),
                        TalukaInfo("ryk_liaquatpur", "Liaquatpur", "لياقت پور", "لیاقت پور"),
                        TalukaInfo("ryk_sadiqabad", "Sadiqabad", "صادق آباد", "صادق آباد")
                    )
                )
            )
        ),

        // === 10. DERA GHAZI KHAN DIVISION ===
        DivisionInfo(
            id = "punjab_div_dgk",
            en = "Dera Ghazi Khan Division",
            sd = "ڊيره غازي خان ڊويزن",
            ur = "ڈیرہ غازی خان ڈویژن",
            districts = listOf(
                DistrictInfo(
                    id = "punjab_dg_khan",
                    en = "Dera Ghazi Khan",
                    sd = "ڊيره غازي خان",
                    ur = "ڈیرہ غازی خان",
                    talukas = listOf(
                        TalukaInfo("dgk_dg_khan", "Dera Ghazi Khan", "ڊيره غازي خان", "ڈیرہ غازی خان"),
                        TalukaInfo("dgk_de_excluded", "De-Excluded Area D.G. Khan", "ڊي ايڪسڪلوڊڊ ايريا ڊي جي خان", "ڈی ایکسکلوڈڈ ایریا ڈی جی خان")
                    )
                ),
                DistrictInfo(
                    id = "punjab_taunsa",
                    en = "Taunsa",
                    sd = "تونسہ",
                    ur = "تونسہ",
                    talukas = listOf(
                        TalukaInfo("tns_taunsa", "Taunsa", "تونسہ", "تونسہ"),
                        TalukaInfo("tns_koh_e_suleman", "Koh-e-Suleman", "ڪوھه سليمان", "کوہ سلیمان"),
                        TalukaInfo("tns_vehova", "Vehova", "وھووا", "وہووا")
                    )
                ),
                DistrictInfo(
                    id = "punjab_muzaffargarh",
                    en = "Muzaffargarh",
                    sd = "مظفرڳڙهه",
                    ur = "مظفرگڑھ",
                    talukas = listOf(
                        TalukaInfo("mzg_muzaffargarh", "Muzaffargarh", "مظفرڳڙهه", "مظفرگڑھ"),
                        TalukaInfo("mzg_alipur", "Alipur", "علي پور", "علی پور"),
                        TalukaInfo("mzg_jatoi", "Jatoi", "جتوئي", "جتوئی")
                    )
                ),
                DistrictInfo(
                    id = "punjab_kot_addu",
                    en = "Kot Addu",
                    sd = "ڪوٽ ادو",
                    ur = "کوٹ ادو",
                    talukas = listOf(
                        TalukaInfo("kad_kot_addu", "Kot Addu", "ڪوٽ ادو", "کوٹ ادو"),
                        TalukaInfo("kad_chowk_sarwar_shaheed", "Chowk Sarwar Shaheed", "چوڪ سرور شھيد", "چوک سرور شہید")
                    )
                ),
                DistrictInfo(
                    id = "punjab_rajanpur",
                    en = "Rajanpur",
                    sd = "راجن پور",
                    ur = "راجن پور",
                    talukas = listOf(
                        TalukaInfo("rjp_rajanpur", "Rajanpur", "راجن پور", "راجن پور"),
                        TalukaInfo("rjp_jampur", "Jampur", "ڄامپور", "جام پور"),
                        TalukaInfo("rjp_rojhan", "Rojhan", "روجھاڻ", "روجھان")
                    )
                ),
                DistrictInfo(
                    id = "punjab_layyah",
                    en = "Layyah",
                    sd = "ليه",
                    ur = "لیہ",
                    talukas = listOf(
                        TalukaInfo("lyh_layyah", "Layyah", "ليه", "لیہ"),
                        TalukaInfo("lyh_chaubara", "Chaubara", "چوباره", "چوبارہ"),
                        TalukaInfo("lyh_karor_lal_esan", "Karor Lal Esan", "ڪروڙ لعل عيسن", "کروڑ لعل عیسن")
                    )
                )
            )
        )
    )
}
