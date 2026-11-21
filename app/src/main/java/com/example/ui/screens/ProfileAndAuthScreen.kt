package com.example.ui.screens

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Sms
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.auth.OtpChannel
import com.example.data.model.PakistanLocations
import com.example.data.model.UserRole
import com.example.data.model.WorkerProfileEntity
import com.example.i18n.AppLanguage
import com.example.i18n.LocalAppLanguage
import com.example.i18n.LocalAppStrings
import com.example.ui.KaamUiState
import com.example.ui.components.CascadingLocationBar
import com.example.ui.components.LanguageSelectorPill
import com.example.ui.components.VerificationBadgeChip
import com.example.ui.components.WorkerAvatar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ProfileAndAuthScreen(
    uiState: KaamUiState,
    onLanguageSelected: (AppLanguage) -> Unit,
    onRoleSelected: (UserRole) -> Unit,
    onToggleSamplePreview: (Boolean) -> Unit,
    onToggleDarkTheme: (Boolean) -> Unit,
    onOtpPhoneChange: (String) -> Unit,
    onOtpChannelSelect: (OtpChannel) -> Unit,
    onRequestOtp: () -> Unit,
    onOtpCodeChange: (String) -> Unit,
    onVerifyOtp: () -> Unit,
    onSaveCustomerProfile: (
        name: String,
        phone: String,
        provinceId: String,
        divisionId: String,
        districtId: String,
        talukaId: String,
        areaDetail: String
    ) -> Unit,
    onSaveWorkerProfile: (
        existingWorkerId: String?,
        name: String,
        phone: String,
        cnic: String,
        provinceId: String,
        divisionId: String,
        districtId: String,
        talukaId: String,
        areaDetail: String,
        categoryKeys: List<String>,
        experienceYears: Int,
        skills: String,
        basePricePkr: Int,
        profilePhotoUri: String,
        portfolioUris: List<String>,
        isAvailable: Boolean
    ) -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    val activeRole = uiState.preferences.activeRole

    val existingLocalWorker = uiState.allWorkers.firstOrNull { !it.worker.isDemoData }?.worker
    val existingCustomer = uiState.customers.firstOrNull { it.id == uiState.preferences.activeCustomerId }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                text = strings.authAndProfileTitle,
                style = MaterialTheme.typography.headlineMedium
            )
        }

        // Language + Role Switcher + App Toggles Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = strings.languageLabel, style = MaterialTheme.typography.titleSmall)
                        LanguageSelectorPill(selectedLanguage = language, onSelect = onLanguageSelected)
                    }

                    Text(text = strings.localPreviewModeTitle, style = MaterialTheme.typography.titleSmall)
                    FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        listOf(
                            UserRole.CUSTOMER to strings.roleCustomer,
                            UserRole.WORKER to strings.roleWorker,
                            UserRole.ADMIN to strings.roleAdmin
                        ).forEach { (role, label) ->
                            FilterChip(
                                selected = activeRole == role,
                                onClick = { onRoleSelected(role) },
                                label = { Text(label) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier.testTag("role_chip_${role.name.lowercase()}")
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = strings.showSampleDataToggle,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = uiState.preferences.showSamplePreviewData,
                            onCheckedChange = onToggleSamplePreview
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Dark Theme",
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.weight(1f)
                        )
                        Switch(
                            checked = uiState.preferences.darkTheme,
                            onCheckedChange = onToggleDarkTheme
                        )
                    }
                }
            }
        }

        // Phone + 6-Digit SMS/WhatsApp OTP Auth Card
        item {
            Card(
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PhoneAndroid,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(text = strings.phoneLoginTitle, style = MaterialTheme.typography.titleMedium)
                    }

                    Surface(
                        shape = RoundedCornerShape(14.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Security,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(18.dp)
                            )
                            Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(
                                    text = strings.otpBackendNoticeTitle,
                                    style = MaterialTheme.typography.labelLarge
                                )
                                Text(
                                    text = strings.otpBackendNoticeBody,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = uiState.otpState.phoneInput,
                        onValueChange = onOtpPhoneChange,
                        label = { Text(strings.phoneHint) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth().testTag("otp_phone_input")
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        FilterChip(
                            selected = uiState.otpState.selectedChannel == OtpChannel.SMS,
                            onClick = { onOtpChannelSelect(OtpChannel.SMS) },
                            label = { Text(strings.otpChannelSms) },
                            leadingIcon = { Icon(Icons.Filled.Sms, contentDescription = null, modifier = Modifier.size(16.dp)) }
                        )
                        FilterChip(
                            selected = uiState.otpState.selectedChannel == OtpChannel.WHATSAPP,
                            onClick = { onOtpChannelSelect(OtpChannel.WHATSAPP) },
                            label = { Text(strings.otpChannelWhatsapp) }
                        )
                    }

                    Button(
                        onClick = onRequestOtp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .minimumInteractiveComponentSize()
                            .testTag("request_otp_btn")
                    ) {
                        Text(strings.sendOtpButton)
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        OutlinedTextField(
                            value = uiState.otpState.otpCodeInput,
                            onValueChange = onOtpCodeChange,
                            label = { Text(strings.enterSixDigitOtp) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            modifier = Modifier.weight(1f).testTag("otp_code_input")
                        )
                        OutlinedButton(
                            onClick = onVerifyOtp,
                            modifier = Modifier
                                .minimumInteractiveComponentSize()
                                .testTag("verify_otp_btn")
                        ) {
                            Text(strings.verifyOtpButton)
                        }
                    }

                    uiState.otpState.statusNotice?.let { notice ->
                        Text(
                            text = notice,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }
        }

        // Customer Profile Form OR Worker Self-Registration / Management Form
        if (activeRole == UserRole.CUSTOMER) {
            item {
                CustomerProfileFormCard(
                    locationProvider = uiState.locationProvider,
                    initialName = existingCustomer?.fullName.orEmpty(),
                    initialProvinceId = existingCustomer?.provinceId ?: "sindh",
                    initialDivisionId = existingCustomer?.divisionId ?: "sindh_div_karachi",
                    initialDistrictId = existingCustomer?.districtId ?: "sindh_karachi_east",
                    initialTalukaId = existingCustomer?.talukaId ?: "ke_gulshan_e_iqbal",
                    initialArea = existingCustomer?.area.orEmpty(),
                    onSave = onSaveCustomerProfile
                )
            }
        } else {
            item {
                WorkerRegistrationFormCard(
                    uiState = uiState,
                    existingWorker = existingLocalWorker,
                    onSave = onSaveWorkerProfile
                )
            }
        }
    }
}

@Composable
private fun CustomerProfileFormCard(
    locationProvider: PakistanLocations,
    initialName: String,
    initialProvinceId: String,
    initialDivisionId: String,
    initialDistrictId: String,
    initialTalukaId: String,
    initialArea: String,
    onSave: (
        name: String,
        phone: String,
        provinceId: String,
        divisionId: String,
        districtId: String,
        talukaId: String,
        areaDetail: String
    ) -> Unit
) {
    val strings = LocalAppStrings.current
    var fullName by remember(initialName) { mutableStateOf(initialName) }
    var rawPhone by remember { mutableStateOf("03001234412") }
    var provinceId by remember(initialProvinceId) { mutableStateOf(initialProvinceId) }
    var divisionId by remember(initialDivisionId) { mutableStateOf(initialDivisionId) }
    var districtId by remember(initialDistrictId) { mutableStateOf(initialDistrictId) }
    var talukaId by remember(initialTalukaId) { mutableStateOf(initialTalukaId) }
    var areaDetail by remember(initialArea) { mutableStateOf(initialArea) }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = strings.customerProfileTitle, style = MaterialTheme.typography.titleMedium)

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text(strings.fullNameLabel) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("customer_name_input")
            )
            OutlinedTextField(
                value = rawPhone,
                onValueChange = { rawPhone = it },
                label = { Text(strings.phoneHint) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Text(text = strings.selectLocationTitle, style = MaterialTheme.typography.labelLarge)
            CascadingLocationBar(
                selectedProvinceId = provinceId,
                selectedDivisionId = divisionId,
                selectedDistrictId = districtId,
                selectedTalukaId = talukaId,
                allowAllOption = false,
                locationProvider = locationProvider,
                onLocationChanged = { prov, div, dist, tal ->
                    val resolved = locationProvider.resolveStrictCascade(prov, div, dist, tal)
                    provinceId = resolved.province.id
                    divisionId = resolved.division.id
                    districtId = resolved.district.id
                    talukaId = resolved.taluka.id
                }
            )

            OutlinedTextField(
                value = areaDetail,
                onValueChange = { areaDetail = it },
                label = { Text(strings.areaLabel) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { onSave(fullName, rawPhone, provinceId, divisionId, districtId, talukaId, areaDetail) },
                modifier = Modifier
                    .fillMaxWidth()
                    .minimumInteractiveComponentSize()
                    .testTag("save_customer_profile_btn")
            ) {
                Text(strings.saveCustomerProfile)
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun WorkerRegistrationFormCard(
    uiState: KaamUiState,
    existingWorker: WorkerProfileEntity?,
    onSave: (
        existingWorkerId: String?,
        name: String,
        phone: String,
        cnic: String,
        provinceId: String,
        divisionId: String,
        districtId: String,
        talukaId: String,
        areaDetail: String,
        categoryKeys: List<String>,
        experienceYears: Int,
        skills: String,
        basePricePkr: Int,
        profilePhotoUri: String,
        portfolioUris: List<String>,
        isAvailable: Boolean
    ) -> Unit
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current

    var fullName by remember(existingWorker?.id) { mutableStateOf(existingWorker?.fullName.orEmpty()) }
    var rawPhone by remember(existingWorker?.id) { mutableStateOf(existingWorker?.dialablePhone.orEmpty()) }
    var rawCnic by remember { mutableStateOf("") }
    var provinceId by remember(existingWorker?.id) { mutableStateOf(existingWorker?.provinceId ?: "sindh") }
    var divisionId by remember(existingWorker?.id) { mutableStateOf(existingWorker?.divisionId ?: "sindh_div_hyderabad") }
    var districtId by remember(existingWorker?.id) { mutableStateOf(existingWorker?.districtId ?: "sindh_dadu") }
    var talukaId by remember(existingWorker?.id) { mutableStateOf(existingWorker?.talukaId ?: "dadu_dadu") }
    var areaDetail by remember(existingWorker?.id) { mutableStateOf(existingWorker?.area.orEmpty()) }
    var experience by remember(existingWorker?.id) {
        mutableStateOf((existingWorker?.experienceYears ?: 3).toString())
    }
    var skills by remember(existingWorker?.id) { mutableStateOf(existingWorker?.skillsSummary.orEmpty()) }
    var basePrice by remember(existingWorker?.id) {
        mutableStateOf((existingWorker?.basePricePkr ?: 1000).toString())
    }
    var isAvailable by remember(existingWorker?.id) { mutableStateOf(existingWorker?.isAvailable ?: true) }
    var profilePhotoUri by remember(existingWorker?.id) { mutableStateOf(existingWorker?.profilePhotoUri.orEmpty()) }
    val portfolioUris = remember(existingWorker?.id) {
        mutableStateListOf<String>().apply {
            addAll(existingWorker?.portfolioUris.orEmpty())
        }
    }
    val selectedCategories = remember(existingWorker?.id) {
        mutableStateListOf<String>().apply {
            val existing = existingWorker?.categoryKeys.orEmpty()
            if (existing.isNotEmpty()) addAll(existing) else add("electrician")
        }
    }

    // Zero-permission Android Photo Picker
    val profilePhotoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) profilePhotoUri = uri.toString()
    }

    val portfolioPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(maxItems = 5)
    ) { uris ->
        if (uris.isNotEmpty()) {
            portfolioUris.clear()
            portfolioUris.addAll(uris.map { it.toString() })
        }
    }

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(20.dp))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (existingWorker == null) strings.workerRegistrationTitle else strings.editWorkerProfileTitle,
                    style = MaterialTheme.typography.titleMedium
                )
                if (existingWorker != null) {
                    VerificationBadgeChip(statusName = existingWorker.cnicVerificationStatus)
                }
            }

            // Profile Photo Picker
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                WorkerAvatar(photoUri = profilePhotoUri, name = fullName.ifBlank { "Worker" }, sizeDp = 64)
                OutlinedButton(
                    onClick = {
                        profilePhotoPicker.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                    modifier = Modifier.minimumInteractiveComponentSize()
                ) {
                    Icon(Icons.Filled.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(strings.pickProfilePhoto)
                }
            }

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text(strings.fullNameLabel) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("worker_name_input")
            )

            OutlinedTextField(
                value = rawPhone,
                onValueChange = { rawPhone = it },
                label = { Text(strings.phoneHint) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("worker_phone_input")
            )

            OutlinedTextField(
                value = rawCnic,
                onValueChange = { rawCnic = it.filter { c -> c.isDigit() || c == '-' }.take(15) },
                label = { Text(strings.cnicNumberLabel) },
                supportingText = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Lock, contentDescription = null, modifier = Modifier.size(12.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(strings.cnicPrivacyHint)
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("worker_cnic_input")
            )

            // 4-Level Cascading Location Selector (Province -> Division -> District -> Taluka/Tehsil)
            Text(text = strings.selectLocationTitle, style = MaterialTheme.typography.labelLarge)
            CascadingLocationBar(
                selectedProvinceId = provinceId,
                selectedDivisionId = divisionId,
                selectedDistrictId = districtId,
                selectedTalukaId = talukaId,
                allowAllOption = false,
                locationProvider = uiState.locationProvider,
                onLocationChanged = { prov, div, dist, tal ->
                    val resolved = uiState.locationProvider.resolveStrictCascade(prov, div, dist, tal)
                    provinceId = resolved.province.id
                    divisionId = resolved.division.id
                    districtId = resolved.district.id
                    talukaId = resolved.taluka.id
                }
            )

            OutlinedTextField(
                value = areaDetail,
                onValueChange = { areaDetail = it },
                label = { Text(strings.areaLabel) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth().testTag("worker_area_input")
            )

            Text(text = strings.selectCategoriesLabel, style = MaterialTheme.typography.labelLarge)
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                uiState.categories.filter { it.isEnabled }.forEach { cat ->
                    val selected = selectedCategories.contains(cat.key)
                    FilterChip(
                        selected = selected,
                        onClick = {
                            if (selected && selectedCategories.size > 1) {
                                selectedCategories.remove(cat.key)
                            } else if (!selected) {
                                selectedCategories.add(cat.key)
                            }
                        },
                        label = { Text(cat.localizedName(language)) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primary,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }

            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = experience,
                    onValueChange = { experience = it.filter { c -> c.isDigit() } },
                    label = { Text(strings.experienceYearsLabel) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = basePrice,
                    onValueChange = { basePrice = it.filter { c -> c.isDigit() } },
                    label = { Text(strings.basePriceLabel) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }

            OutlinedTextField(
                value = skills,
                onValueChange = { skills = it },
                label = { Text(strings.skillsInputLabel) },
                minLines = 2,
                modifier = Modifier.fillMaxWidth().testTag("worker_skills_input")
            )

            // Portfolio Picker
            OutlinedButton(
                onClick = {
                    portfolioPicker.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                modifier = Modifier.fillMaxWidth().minimumInteractiveComponentSize()
            ) {
                Icon(Icons.Filled.AddPhotoAlternate, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(6.dp))
                Text("${strings.addPortfolioPhotos} (${portfolioUris.size})")
            }

            if (portfolioUris.isNotEmpty()) {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(portfolioUris) { uri ->
                        AsyncImage(
                            model = uri,
                            contentDescription = strings.portfolioPhotos,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .size(84.dp)
                                .clip(RoundedCornerShape(12.dp))
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = strings.availabilityToggleLabel, style = MaterialTheme.typography.bodyMedium)
                Switch(checked = isAvailable, onCheckedChange = { isAvailable = it })
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    if (fullName.isNotBlank()) {
                        onSave(
                            existingWorker?.id,
                            fullName,
                            rawPhone.ifBlank { "03000000000" },
                            rawCnic,
                            provinceId,
                            divisionId,
                            districtId,
                            talukaId,
                            areaDetail,
                            selectedCategories.toList(),
                            experience.toIntOrNull() ?: 1,
                            skills,
                            basePrice.toIntOrNull() ?: 1000,
                            profilePhotoUri,
                            portfolioUris.toList(),
                            isAvailable
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .minimumInteractiveComponentSize()
                    .testTag("save_worker_profile_btn")
            ) {
                Text(strings.saveWorkerProfile)
            }
        }
    }
}
