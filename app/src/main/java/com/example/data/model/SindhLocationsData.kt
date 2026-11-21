package com.example.data.model

internal object SindhLocationsData {

    val divisions: List<DivisionInfo> = listOf(
        DivisionInfo(
            id = "sindh_div_hyderabad",
            en = "Hyderabad Division",
            sd = "حيدرآباد ڊويزن",
            ur = "حیدرآباد ڈویژن",
            districts = listOf(
                // 1. Dadu
                DistrictInfo(
                    id = "sindh_dadu",
                    en = "Dadu",
                    sd = "دادو",
                    ur = "دادو",
                    talukas = listOf(
                        TalukaInfo("dadu_dadu", "Dadu", "دادو", "دادو"),
                        TalukaInfo("dadu_johi", "Johi", "جوھي", "جوہی"),
                        TalukaInfo("dadu_mehar", "Mehar", "ميھڙ", "میہڑ"),
                        TalukaInfo("dadu_kn_shah", "Khairpur Nathan Shah", "خيرپور ناٿن شاھه", "خیرپور ناتھن شاہ")
                    )
                ),
                // 2. Jamshoro
                DistrictInfo(
                    id = "sindh_jamshoro",
                    en = "Jamshoro",
                    sd = "ڄامشورو",
                    ur = "جامشورو",
                    talukas = listOf(
                        TalukaInfo("jamshoro_kotri", "Kotri", "ڪوٽڙي", "کوٹری"),
                        TalukaInfo("jamshoro_sehwan", "Sehwan", "سيوھڻ", "سیہون"),
                        TalukaInfo("jamshoro_thano_bula_khan", "Thano Bula Khan", "ٿاڻو بولا خان", "تھانہ بولا خان"),
                        TalukaInfo("jamshoro_manjhand", "Manjhand", "مانجھند", "مانجھند")
                    )
                ),
                // 3. Hyderabad
                DistrictInfo(
                    id = "sindh_hyderabad",
                    en = "Hyderabad",
                    sd = "حيدرآباد",
                    ur = "حیدرآباد",
                    talukas = listOf(
                        TalukaInfo("hyderabad_city", "Hyderabad City", "حيدرآباد سٽي", "حیدرآباد سٹی"),
                        TalukaInfo("hyderabad_rural", "Hyderabad Rural", "حيدرآباد ٻھراڙي", "حیدرآباد دیہی"),
                        TalukaInfo("hyderabad_latifabad", "Latifabad", "لطيف آباد", "لطیف آباد"),
                        TalukaInfo("hyderabad_qasimabad", "Qasimabad", "قاسم آباد", "قاسم آباد")
                    )
                ),
                // 4. Matiari
                DistrictInfo(
                    id = "sindh_matiari",
                    en = "Matiari",
                    sd = "مٽياري",
                    ur = "مٹیاری",
                    talukas = listOf(
                        TalukaInfo("matiari_matiari", "Matiari", "مٽياري", "مٹیاری"),
                        TalukaInfo("matiari_hala", "Hala", "ھالا", "ہالا"),
                        TalukaInfo("matiari_saeedabad", "Saeedabad", "سعيد آباد", "سعید آباد")
                    )
                ),
                // 5. Tando Allahyar
                DistrictInfo(
                    id = "sindh_tando_allahyar",
                    en = "Tando Allahyar",
                    sd = "ٽنڊو الھيار",
                    ur = "ٹنڈو الہیار",
                    talukas = listOf(
                        TalukaInfo("ta_tando_allahyar", "Tando Allahyar", "ٽنڊو الھيار", "ٹنڈو الہیار"),
                        TalukaInfo("ta_jhando_mari", "Jhando Mari", "جھنڊو مري", "جھنڈو مری"),
                        TalukaInfo("ta_chamber", "Chamber", "چمبڙ", "چمبڑ")
                    )
                ),
                // 6. Tando Muhammad Khan
                DistrictInfo(
                    id = "sindh_tando_muhammad_khan",
                    en = "Tando Muhammad Khan",
                    sd = "ٽنڊو محمد خان",
                    ur = "ٹنڈو محمد خان",
                    talukas = listOf(
                        TalukaInfo("tmk_tando_muhammad_khan", "Tando Muhammad Khan", "ٽنڊو محمد خان", "ٹنڈو محمد خان"),
                        TalukaInfo("tmk_bulri_shah_karim", "Bulri Shah Karim", "بلڙي شاھه ڪريم", "بلڑی شاہ کریم"),
                        TalukaInfo("tmk_tando_ghulam_hyder", "Tando Ghulam Hyder", "ٽنڊو غلام حيدر", "ٹنڈو غلام حیدر")
                    )
                ),
                // 7. Badin
                DistrictInfo(
                    id = "sindh_badin",
                    en = "Badin",
                    sd = "بدين",
                    ur = "بدین",
                    talukas = listOf(
                        TalukaInfo("badin_badin", "Badin", "بدين", "بدین"),
                        TalukaInfo("badin_matli", "Matli", "ماتلي", "ماتلی"),
                        TalukaInfo("badin_golarchi", "Golarchi / Shaheed Fazil Rahu", "گولاڙچي", "گولاڑچی"),
                        TalukaInfo("badin_talhar", "Talhar", "تلھار", "تلہار"),
                        TalukaInfo("badin_tando_bago", "Tando Bago", "ٽنڊو باگو", "ٹنڈو باگو")
                    )
                ),
                // 8. Thatta
                DistrictInfo(
                    id = "sindh_thatta",
                    en = "Thatta",
                    sd = "ٺٽو",
                    ur = "ٹھٹھہ",
                    talukas = listOf(
                        TalukaInfo("thatta_thatta", "Thatta", "ٺٽو", "ٹھٹھہ"),
                        TalukaInfo("thatta_mirpur_sakro", "Mirpur Sakro", "ميرپور ساڪرو", "میرپور ساکرو"),
                        TalukaInfo("thatta_ghorabari", "Ghorabari", "گھوڙاٻاري", "گھوڑاباری"),
                        TalukaInfo("thatta_keti_bunder", "Keti Bunder", "ڪيٽي بندر", "کیٹی بندر")
                    )
                ),
                // 9. Sujawal
                DistrictInfo(
                    id = "sindh_sujawal",
                    en = "Sujawal",
                    sd = "سجاول",
                    ur = "سجاول",
                    talukas = listOf(
                        TalukaInfo("sujawal_sujawal", "Sujawal", "سجاول", "سجاول"),
                        TalukaInfo("sujawal_mirpur_bathoro", "Mirpur Bathoro", "ميرپور بٺورو", "میرپور بٹھورو"),
                        TalukaInfo("sujawal_jati", "Jati", "جاتي", "جاتی"),
                        TalukaInfo("sujawal_shah_bunder", "Shah Bunder", "شاھه بندر", "شاہ بندر"),
                        TalukaInfo("sujawal_kharo_chan", "Kharo Chan", "کارو ڇاڻ", "کھارو چھان")
                    )
                )
            )
        ),
        DivisionInfo(
            id = "sindh_div_mirpurkhas",
            en = "Mirpurkhas Division",
            sd = "ميرپورخاص ڊويزن",
            ur = "میرپورخاص ڈویژن",
            districts = listOf(
                // 10. Mirpurkhas
                DistrictInfo(
                    id = "sindh_mirpurkhas",
                    en = "Mirpurkhas",
                    sd = "ميرپورخاص",
                    ur = "میرپورخاص",
                    talukas = listOf(
                        TalukaInfo("mpk_mirpurkhas", "Mirpurkhas", "ميرپورخاص", "میرپورخاص"),
                        TalukaInfo("mpk_digri", "Digri", "ڊگھڙي", "ڈگری"),
                        TalukaInfo("mpk_kot_ghulam_muhammad", "Kot Ghulam Muhammad", "ڪوٽ غلام محمد", "کوٹ غلام محمد"),
                        TalukaInfo("mpk_jhuddo", "Jhuddo", "جھڏو", "جھڈو"),
                        TalukaInfo("mpk_sindhri", "Sindhri", "سنڌڙي", "سندھڑی"),
                        TalukaInfo("mpk_hussain_bux_mari", "Hussain Bux Mari", "حسين بخش مري", "حسین بخش مری"),
                        TalukaInfo("mpk_shujaabad", "Shujaabad", "شجاع آباد", "شجاع آباد")
                    )
                ),
                // 11. Umerkot
                DistrictInfo(
                    id = "sindh_umerkot",
                    en = "Umerkot",
                    sd = "عمرڪوٽ",
                    ur = "عمرکوٹ",
                    talukas = listOf(
                        TalukaInfo("umerkot_umerkot", "Umerkot", "عمرڪوٽ", "عمرکوٹ"),
                        TalukaInfo("umerkot_kunri", "Kunri", "ڪنري", "کنری"),
                        TalukaInfo("umerkot_samaro", "Samaro", "سامارو", "سامارو"),
                        TalukaInfo("umerkot_pithoro", "Pithoro", "پٿورو", "پتھورو")
                    )
                ),
                // 12. Tharparkar
                DistrictInfo(
                    id = "sindh_tharparkar",
                    en = "Tharparkar",
                    sd = "ٿرپارڪر",
                    ur = "تھرپارکر",
                    talukas = listOf(
                        TalukaInfo("thar_mithi", "Mithi", "مٺي", "مٹھی"),
                        TalukaInfo("thar_islamkot", "Islamkot", "اسلام ڪوٽ", "اسلام کوٹ"),
                        TalukaInfo("thar_chachro", "Chachro", "ڇاڇرو", "چھاچھرو"),
                        TalukaInfo("thar_diplo", "Diplo", "ڏيپلو", "ڈیپلو"),
                        TalukaInfo("thar_nagarparkar", "Nagarparkar", "ننگرپارڪر", "ننگرپارکر"),
                        TalukaInfo("thar_dahli", "Dahli", "ڏاھلي", "ڈاہلی"),
                        TalukaInfo("thar_kaloi", "Kaloi", "ڪلوئي", "کلوئی")
                    )
                )
            )
        ),
        DivisionInfo(
            id = "sindh_div_sba",
            en = "Shaheed Benazirabad Division",
            sd = "شھيد بينظير آباد ڊويزن",
            ur = "شہید بینظیر آباد ڈویژن",
            districts = listOf(
                // 13. Shaheed Benazirabad / Nawabshah
                DistrictInfo(
                    id = "sindh_shaheed_benazirabad",
                    en = "Shaheed Benazirabad / Nawabshah",
                    sd = "شھيد بينظير آباد / نوابشاھه",
                    ur = "شہید بینظیر آباد / نوابشاہ",
                    talukas = listOf(
                        TalukaInfo("sba_nawabshah", "Nawabshah", "نوابشاھه", "نوابشاہ"),
                        TalukaInfo("sba_sakrand", "Sakrand", "سڪرنڊ", "سکرنڈ"),
                        TalukaInfo("sba_qazi_ahmed", "Qazi Ahmed", "قاضي احمد", "قاضی احمد"),
                        TalukaInfo("sba_daur", "Daur", "دوڙ", "دوڑ")
                    )
                ),
                // 14. Sanghar
                DistrictInfo(
                    id = "sindh_sanghar",
                    en = "Sanghar",
                    sd = "سانگھڙ",
                    ur = "سانگھڑ",
                    talukas = listOf(
                        TalukaInfo("sanghar_sanghar", "Sanghar", "سانگھڙ", "سانگھڑ"),
                        TalukaInfo("sanghar_shahdadpur", "Shahdadpur", "شھدادپور", "شہدادپور"),
                        TalukaInfo("sanghar_sinjhoro", "Sinjhoro", "سنجھورو", "سنجھورو"),
                        TalukaInfo("sanghar_tando_adam", "Tando Adam", "ٽنڊو آدم", "ٹنڈو آدم"),
                        TalukaInfo("sanghar_khipro", "Khipro", "کپرو", "کھپرو"),
                        TalukaInfo("sanghar_jam_nawaz_ali", "Jam Nawaz Ali", "ڄام نواز علي", "جام نواز علی")
                    )
                ),
                // 15. Naushahro Feroze
                DistrictInfo(
                    id = "sindh_naushahro_feroze",
                    en = "Naushahro Feroze",
                    sd = "نوشھرو فيروز",
                    ur = "نوشہرو فیروز",
                    talukas = listOf(
                        TalukaInfo("nf_naushahro_feroze", "Naushahro Feroze", "نوشھرو فيروز", "نوشہرو فیروز"),
                        TalukaInfo("nf_moro", "Moro", "مورو", "مورو"),
                        TalukaInfo("nf_kandiaro", "Kandiaro", "ڪنڊيارو", "کنڈیارو"),
                        TalukaInfo("nf_bhiria", "Bhiria", "ڀريا", "بھریا"),
                        TalukaInfo("nf_mehrabpur", "Mehrabpur", "محرابپور", "محرابپور")
                    )
                )
            )
        ),
        DivisionInfo(
            id = "sindh_div_sukkur",
            en = "Sukkur Division",
            sd = "سکر ڊويزن",
            ur = "سکھر ڈویژن",
            districts = listOf(
                // 16. Sukkur
                DistrictInfo(
                    id = "sindh_sukkur",
                    en = "Sukkur",
                    sd = "سکر",
                    ur = "سکھر",
                    talukas = listOf(
                        TalukaInfo("sukkur_city", "Sukkur City", "سکر سٽي", "سکھر سٹی"),
                        TalukaInfo("sukkur_new", "New Sukkur", "نيو سکر", "نیو سکھر"),
                        TalukaInfo("sukkur_rohri", "Rohri", "روھڙي", "روہڑی"),
                        TalukaInfo("sukkur_pano_akil", "Pano Akil", "پنو عاقل", "پنو عاقل"),
                        TalukaInfo("sukkur_salehpat", "Salehpat", "صالح پٽ", "صالح پٹ")
                    )
                ),
                // 17. Khairpur
                DistrictInfo(
                    id = "sindh_khairpur",
                    en = "Khairpur",
                    sd = "خيرپور ميرس",
                    ur = "خیرپور میرس",
                    talukas = listOf(
                        TalukaInfo("khairpur_khairpur", "Khairpur", "خيرپور", "خیرپور"),
                        TalukaInfo("khairpur_gambat", "Gambat", "گمبٽ", "گمبٹ"),
                        TalukaInfo("khairpur_kingri", "Kingri", "ڪنگري", "کنگری"),
                        TalukaInfo("khairpur_kot_diji", "Kot Diji", "ڪوٽ ڏيجي", "کوٹ ڈیجی"),
                        TalukaInfo("khairpur_mirwah", "Mirwah / Thari Mirwah", "ٺري ميرواھه", "ٹھری میرواہ"),
                        TalukaInfo("khairpur_sobho_dero", "Sobho Dero", "سوڀوديرو", "صوبھودیرو"),
                        TalukaInfo("khairpur_faiz_ganj", "Faiz Ganj", "فيض گنج", "فیض گنج"),
                        TalukaInfo("khairpur_nara", "Nara", "نارو", "نارو")
                    )
                ),
                // 18. Ghotki
                DistrictInfo(
                    id = "sindh_ghotki",
                    en = "Ghotki",
                    sd = "گھوٽڪي",
                    ur = "گھوٹکی",
                    talukas = listOf(
                        TalukaInfo("ghotki_ghotki", "Ghotki", "گھوٽڪي", "گھوٹکی"),
                        TalukaInfo("ghotki_mirpur_mathelo", "Mirpur Mathelo", "ميرپور ماٿيلو", "میرپور ماتھیلو"),
                        TalukaInfo("ghotki_daharki", "Daharki", "ڏھرڪي", "ڈہرکی"),
                        TalukaInfo("ghotki_ubauro", "Ubauro", "اوٻاوڙو", "اوباؤڑو"),
                        TalukaInfo("ghotki_khangarh", "Khangarh / Khanpur Mahar", "خانپور مھر", "خانپور مہر")
                    )
                )
            )
        ),
        DivisionInfo(
            id = "sindh_div_larkana",
            en = "Larkana Division",
            sd = "لاڙڪاڻو ڊويزن",
            ur = "لاڑکانہ ڈویژن",
            districts = listOf(
                // 19. Larkana
                DistrictInfo(
                    id = "sindh_larkana",
                    en = "Larkana",
                    sd = "لاڙڪاڻو",
                    ur = "لاڑکانہ",
                    talukas = listOf(
                        TalukaInfo("larkana_larkana", "Larkana", "لاڙڪاڻو", "لاڑکانہ"),
                        TalukaInfo("larkana_ratodero", "Ratodero", "رتوديرو", "رتوڈیرو"),
                        TalukaInfo("larkana_dokri", "Dokri", "ڏوڪري", "ڈوکری"),
                        TalukaInfo("larkana_bakrani", "Bakrani", "باڪراڻي", "باکرانی")
                    )
                ),
                // 20. Qambar Shahdadkot
                DistrictInfo(
                    id = "sindh_qambar_shahdadkot",
                    en = "Qambar Shahdadkot",
                    sd = "قمبر شھدادڪوٽ",
                    ur = "قمبر شہدادکوٹ",
                    talukas = listOf(
                        TalukaInfo("qs_qambar", "Qambar", "قمبر", "قمبر"),
                        TalukaInfo("qs_shahdadkot", "Shahdadkot", "شھدادڪوٽ", "شہدادکوٹ"),
                        TalukaInfo("qs_warah", "Warah", "وارھه", "وارہ"),
                        TalukaInfo("qs_mirokhan", "Mirokhan", "ميروخان", "میروخان"),
                        TalukaInfo("qs_nasirabad", "Nasirabad", "نصيرآباد", "نصیرآباد"),
                        TalukaInfo("qs_qubo_saeed_khan", "Qubo Saeed Khan", "قبو سعيد خان", "قبو سعید خان"),
                        TalukaInfo("qs_sijawal_junejo", "Sijawal Junejo", "سجاول جوڻيجو", "سجاول جونیجو")
                    )
                ),
                // 21. Shikarpur
                DistrictInfo(
                    id = "sindh_shikarpur",
                    en = "Shikarpur",
                    sd = "شڪارپور",
                    ur = "شکارپور",
                    talukas = listOf(
                        TalukaInfo("shikarpur_shikarpur", "Shikarpur", "شڪارپور", "شکارپور"),
                        TalukaInfo("shikarpur_garhi_yasin", "Garhi Yasin", "ڳڙھي ياسين", "گڑھی یاسین"),
                        TalukaInfo("shikarpur_lakhi_ghulam_shah", "Lakhi Ghulam Shah", "لکي غلام شاھه", "لکھی غلام شاہ"),
                        TalukaInfo("shikarpur_khanpur", "Khanpur", "خانپور", "خانپور")
                    )
                ),
                // 22. Jacobabad
                DistrictInfo(
                    id = "sindh_jacobabad",
                    en = "Jacobabad",
                    sd = "جيڪب آباد",
                    ur = "جیکب آباد",
                    talukas = listOf(
                        TalukaInfo("jacobabad_jacobabad", "Jacobabad", "جيڪب آباد", "جیکب آباد"),
                        TalukaInfo("jacobabad_thul", "Thul", "ٺل", "ٹھل"),
                        TalukaInfo("jacobabad_garhi_khairo", "Garhi Khairo", "ڳڙھي خيرو", "گڑھی خیرو")
                    )
                ),
                // 23. Kashmore
                DistrictInfo(
                    id = "sindh_kashmore",
                    en = "Kashmore",
                    sd = "ڪشمور",
                    ur = "کشمور",
                    talukas = listOf(
                        TalukaInfo("kashmore_kandhkot", "Kandhkot", "ڪنڌڪوٽ", "کندھ کوٹ"),
                        TalukaInfo("kashmore_kashmore", "Kashmore", "ڪشمور", "کشمور"),
                        TalukaInfo("kashmore_tangwani", "Tangwani", "تنگواڻي", "تنگوانی")
                    )
                )
            )
        ),
        DivisionInfo(
            id = "sindh_div_karachi",
            en = "Karachi Division",
            sd = "ڪراچي ڊويزن",
            ur = "کراچی ڈویژن",
            districts = listOf(
                // 24. Karachi Central
                DistrictInfo(
                    id = "sindh_karachi_central",
                    en = "Karachi Central",
                    sd = "ڪراچي سينٽرل",
                    ur = "کراچی سینٹرل",
                    talukas = listOf(
                        TalukaInfo("kc_liaquatabad", "Liaquatabad", "لياقت آباد", "لیاقت آباد"),
                        TalukaInfo("kc_nazimabad", "Nazimabad", "ناظم آباد", "ناظم آباد"),
                        TalukaInfo("kc_north_nazimabad", "North Nazimabad", "نارٿ ناظم آباد", "نارتھ ناظم آباد"),
                        TalukaInfo("kc_gulberg", "Gulberg", "گلبرگ", "گلبرگ"),
                        TalukaInfo("kc_new_karachi", "New Karachi", "نيو ڪراچي", "نیو کراچی")
                    )
                ),
                // 25. Karachi East
                DistrictInfo(
                    id = "sindh_karachi_east",
                    en = "Karachi East",
                    sd = "ڪراچي اوڀر",
                    ur = "کراچی شرقی",
                    talukas = listOf(
                        TalukaInfo("ke_gulshan_e_iqbal", "Gulshan-e-Iqbal", "گلشن اقبال", "گلشن اقبال"),
                        TalukaInfo("ke_jamshed_quarters", "Jamshed Quarters", "جمشيد ڪوارٽرز", "جمشید کوارٹرز"),
                        TalukaInfo("ke_ferozabad", "Ferozabad", "فيروز آباد", "فیروز آباد"),
                        TalukaInfo("ke_gulistan_e_johar", "Gulistan-e-Johar", "گلستان جوھر", "گلستان جوہر"),
                        TalukaInfo("ke_sohrab_goth", "Safari Park / Sohrab Goth", "سھراب ڳوٺ", "سہراب گوٹھ")
                    )
                ),
                // 26. Karachi South
                DistrictInfo(
                    id = "sindh_karachi_south",
                    en = "Karachi South",
                    sd = "ڪراچي ڏکڻ",
                    ur = "کراچی جنوبی",
                    talukas = listOf(
                        TalukaInfo("ks_saddar", "Saddar", "صدر", "صدر"),
                        TalukaInfo("ks_lyari", "Lyari", "لياري", "لیاری"),
                        TalukaInfo("ks_civil_lines", "Civil Lines", "سول لائينز", "سول لائنز"),
                        TalukaInfo("ks_clifton", "Clifton", "ڪلفٽن", "کلفٹن"),
                        TalukaInfo("ks_garden", "Garden", "گارڊن", "گارڈن")
                    )
                ),
                // 27. Karachi West
                DistrictInfo(
                    id = "sindh_karachi_west",
                    en = "Karachi West",
                    sd = "ڪراچي اولھه",
                    ur = "کراچی غربی",
                    talukas = listOf(
                        TalukaInfo("kw_orangi", "Orangi", "اورنگي", "اورنگی"),
                        TalukaInfo("kw_mominabad", "Mominabad", "مومن آباد", "مومن آباد"),
                        TalukaInfo("kw_manghopir", "Manghopir", "منگھوپير", "منگھوپیر")
                    )
                ),
                // 28. Keamari
                DistrictInfo(
                    id = "sindh_keamari",
                    en = "Keamari",
                    sd = "ڪياماڙي",
                    ur = "کیماڑی",
                    talukas = listOf(
                        TalukaInfo("keamari_keamari", "Keamari", "ڪياماڙي", "کیماڑی"),
                        TalukaInfo("keamari_site", "SITE", "سائيٽ", "سائٹ"),
                        TalukaInfo("keamari_baldia", "Baldia", "بلديه", "بلدیہ"),
                        TalukaInfo("keamari_mauripur", "Mauripur", "ماڙيپور", "ماڑی پور")
                    )
                ),
                // 29. Korangi
                DistrictInfo(
                    id = "sindh_korangi",
                    en = "Korangi",
                    sd = "ڪورنگي",
                    ur = "کورنگی",
                    talukas = listOf(
                        TalukaInfo("korangi_korangi", "Korangi", "ڪورنگي", "کورنگی"),
                        TalukaInfo("korangi_landhi", "Landhi", "لانڍي", "لانڈھی"),
                        TalukaInfo("korangi_shah_faisal", "Shah Faisal", "شاھه فيصل", "شاہ فیصل"),
                        TalukaInfo("korangi_model_colony", "Model Colony", "ماڊل ڪالوني", "ماڈل کالونی")
                    )
                ),
                // 30. Malir
                DistrictInfo(
                    id = "sindh_malir",
                    en = "Malir",
                    sd = "ملير",
                    ur = "ملیر",
                    talukas = listOf(
                        TalukaInfo("malir_malir", "Malir", "ملير", "ملیر"),
                        TalukaInfo("malir_bin_qasim", "Bin Qasim", "بن قاسم", "بن قاسم"),
                        TalukaInfo("malir_gadap", "Gadap", "گڏاپ", "گڈاپ"),
                        TalukaInfo("malir_ibrahim_hyderi", "Ibrahim Hyderi", "ابراھيم حيدري", "ابراہیم حیدری"),
                        TalukaInfo("malir_murad_memon", "Murad Memon", "مراد ميمڻ", "مراد میمن"),
                        TalukaInfo("malir_shah_murad", "Shah Murad", "شاھه مراد", "شاہ مراد")
                    )
                )
            )
        )
    )
}
