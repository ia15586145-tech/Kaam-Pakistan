package com.example.ui.screens

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.GpsFixed
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.PinDrop
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.data.model.ChatConversationEntity
import com.example.data.model.ChatMessageEntity
import com.example.data.model.ChatMessageType
import com.example.data.model.ServiceCategoryEntity
import com.example.data.model.UserRole
import com.example.i18n.LocalAppLanguage
import com.example.i18n.LocalAppStrings
import com.example.ui.KaamUiState
import com.example.ui.components.SamplePreviewChip
import com.example.ui.components.WorkerAvatar
import com.example.ui.components.launchDialer
import com.example.ui.components.launchMapsPin
import com.example.ui.components.launchWhatsApp
import com.example.ui.theme.PakistanGreenDark
import com.example.ui.theme.PakistanGreenDeep
import com.example.ui.theme.PakistanGreenLight
import com.example.ui.theme.PureWhite
import com.example.ui.theme.VerifiedEmerald
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private enum class InboxFilter {
    ALL,
    UNREAD,
    WITH_LOCATION
}

@Composable
fun MessagesScreen(
    uiState: KaamUiState,
    onOpenConversation: (String?) -> Unit,
    onSendText: (conversationId: String, body: String) -> Unit,
    onShareAddress: (conversationId: String, street: String, landmark: String, cityArea: String, note: String) -> Unit,
    onShareGpsPin: (conversationId: String, lat: Double, lng: Double, cityArea: String, note: String) -> Unit,
    onFeedback: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val activeConversation = uiState.conversations.firstOrNull { it.id == uiState.selectedConversationId }

    if (activeConversation != null) {
        val threadMessages = uiState.chatMessages.filter { it.conversationId == activeConversation.id }
        val worker = uiState.allWorkers.firstOrNull { it.worker.id == activeConversation.workerId }?.worker
        val defaultArea = activeConversation.locationLabel.ifBlank {
            uiState.customers.firstOrNull { it.id == uiState.preferences.activeCustomerId }
                ?.let { "${it.area}, ${it.city}" }.orEmpty()
        }

        ConversationThreadView(
            conversation = activeConversation,
            messages = threadMessages,
            activeRole = uiState.preferences.activeRole,
            workerPhone = worker?.dialablePhone.orEmpty(),
            workerPhoto = worker?.profilePhotoUri.orEmpty(),
            defaultCityArea = defaultArea,
            onBack = { onOpenConversation(null) },
            onSendText = { body -> onSendText(activeConversation.id, body) },
            onShareAddress = { street, landmark, area, note ->
                onShareAddress(activeConversation.id, street, landmark, area, note)
            },
            onShareGpsPin = { lat, lng, area, note ->
                onShareGpsPin(activeConversation.id, lat, lng, area, note)
            },
            onFeedback = onFeedback,
            modifier = modifier
        )
    } else {
        MessagesInboxView(
            uiState = uiState,
            onSelectConversation = { convId -> onOpenConversation(convId) },
            modifier = modifier
        )
    }
}

@Composable
private fun MessagesInboxView(
    uiState: KaamUiState,
    onSelectConversation: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var filter by rememberSaveable { mutableStateOf(InboxFilter.ALL) }

    val filteredConversations = remember(uiState.conversations, searchQuery, filter, language) {
        val q = searchQuery.trim().lowercase()
        uiState.conversations.filter { conv ->
            val matchesFilter = when (filter) {
                InboxFilter.ALL -> true
                InboxFilter.UNREAD -> conv.unreadCount > 0
                InboxFilter.WITH_LOCATION -> conv.hasSharedAddress || conv.hasSharedGpsPin
            }
            val catName = uiState.categories.firstOrNull { it.key == conv.categoryKey }
                ?.localizedName(language)?.lowercase().orEmpty()
            val matchesSearch = q.isBlank() ||
                conv.workerName.lowercase().contains(q) ||
                conv.customerName.lowercase().contains(q) ||
                conv.locationLabel.lowercase().contains(q) ||
                conv.lastMessagePreview.lowercase().contains(q) ||
                catName.contains(q)
            matchesFilter && matchesSearch
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .testTag("messages_inbox_list"),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // Header Banner
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(PakistanGreenDeep, PakistanGreenDark)
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 18.dp)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = strings.messagesTitle,
                                style = MaterialTheme.typography.headlineMedium,
                                color = PureWhite
                            )
                            Text(
                                text = strings.messagesSubtitle,
                                style = MaterialTheme.typography.bodySmall,
                                color = PureWhite.copy(alpha = 0.85f)
                            )
                        }
                        if (uiState.unreadMessagesCount > 0) {
                            Surface(
                                shape = RoundedCornerShape(50),
                                color = PakistanGreenLight
                            ) {
                                Text(
                                    text = "${uiState.unreadMessagesCount} ${strings.unreadBadgeLabel}",
                                    style = MaterialTheme.typography.labelMedium,
                                    color = PureWhite,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                                )
                            }
                        }
                    }

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = {
                            Text(
                                text = strings.searchChatsHint,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                contentDescription = strings.searchChatsHint,
                                tint = MaterialTheme.colorScheme.primary
                            )
                        },
                        trailingIcon = {
                            if (searchQuery.isNotEmpty()) {
                                IconButton(
                                    onClick = { searchQuery = "" },
                                    modifier = Modifier.minimumInteractiveComponentSize()
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Clear,
                                        contentDescription = strings.cancel
                                    )
                                }
                            }
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedBorderColor = PakistanGreenLight,
                            unfocusedBorderColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("messages_search_input")
                    )
                }
            }
        }

        // Filter Chips
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = filter == InboxFilter.ALL,
                    onClick = { filter = InboxFilter.ALL },
                    label = { Text(strings.allConversationsFilter) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("inbox_filter_all")
                )
                FilterChip(
                    selected = filter == InboxFilter.UNREAD,
                    onClick = { filter = InboxFilter.UNREAD },
                    label = {
                        val suffix = if (uiState.unreadMessagesCount > 0) " (${uiState.unreadMessagesCount})" else ""
                        Text("${strings.unreadFilter}$suffix")
                    },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("inbox_filter_unread")
                )
                FilterChip(
                    selected = filter == InboxFilter.WITH_LOCATION,
                    onClick = { filter = InboxFilter.WITH_LOCATION },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.PinDrop,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                    },
                    label = { Text(strings.withLocationFilter) },
                    colors = FilterChipDefaults.filterChipColors(
                        selectedContainerColor = MaterialTheme.colorScheme.primaryContainer
                    ),
                    modifier = Modifier.testTag("inbox_filter_location")
                )
            }
        }

        if (filteredConversations.isEmpty()) {
            item {
                EmptyWorkersView(
                    title = strings.noMessagesTitle,
                    subtitle = strings.noMessagesSubtitle,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        } else {
            items(filteredConversations, key = { it.id }) { conv ->
                val workerPhoto = uiState.allWorkers
                    .firstOrNull { it.worker.id == conv.workerId }
                    ?.worker?.profilePhotoUri.orEmpty()
                val category = uiState.categories.firstOrNull { it.key == conv.categoryKey }
                ConversationInboxCard(
                    conversation = conv,
                    activeRole = uiState.preferences.activeRole,
                    category = category,
                    workerPhotoUri = workerPhoto,
                    onClick = { onSelectConversation(conv.id) },
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun ConversationInboxCard(
    conversation: ChatConversationEntity,
    activeRole: UserRole,
    category: ServiceCategoryEntity?,
    workerPhotoUri: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val strings = LocalAppStrings.current
    val language = LocalAppLanguage.current
    val counterpartName = if (activeRole == UserRole.WORKER) {
        conversation.customerName
    } else {
        conversation.workerName
    }
    val categoryName = category?.localizedName(language) ?: conversation.categoryKey

    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (conversation.unreadCount > 0) {
                MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
            .fillMaxWidth()
            .border(
                width = if (conversation.unreadCount > 0) 1.5.dp else 1.dp,
                color = if (conversation.unreadCount > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                shape = RoundedCornerShape(20.dp)
            )
            .clip(RoundedCornerShape(20.dp))
            .clickable(onClick = onClick)
            .testTag("conv_card_${conversation.id}")
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                WorkerAvatar(
                    photoUri = if (activeRole == UserRole.WORKER) "" else workerPhotoUri,
                    name = counterpartName,
                    sizeDp = 52
                )

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = counterpartName,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = if (conversation.unreadCount > 0) FontWeight.Bold else FontWeight.SemiBold,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier.weight(1f, fill = false)
                            )
                            if (conversation.isDemoData) {
                                SamplePreviewChip()
                            }
                        }
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = formatShortTime(conversation.lastMessageAt),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "$categoryName • ${conversation.locationLabel}",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = conversation.lastMessagePreview,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.weight(1f)
                        )
                        if (conversation.unreadCount > 0) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.primary),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = conversation.unreadCount.toString(),
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            if (conversation.hasSharedAddress || conversation.hasSharedGpsPin) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    if (conversation.hasSharedAddress) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.primaryContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Home,
                                    contentDescription = strings.sharedAddressTitle,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = strings.shareHomeAddress,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            }
                        }
                    }
                    if (conversation.hasSharedGpsPin) {
                        Surface(
                            shape = RoundedCornerShape(50),
                            color = MaterialTheme.colorScheme.tertiaryContainer
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.PinDrop,
                                    contentDescription = strings.sharedGpsPinTitle,
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer,
                                    modifier = Modifier.size(13.dp)
                                )
                                Text(
                                    text = strings.shareGpsPin,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConversationThreadView(
    conversation: ChatConversationEntity,
    messages: List<ChatMessageEntity>,
    activeRole: UserRole,
    workerPhone: String,
    workerPhoto: String,
    defaultCityArea: String,
    onBack: () -> Unit,
    onSendText: (String) -> Unit,
    onShareAddress: (street: String, landmark: String, area: String, note: String) -> Unit,
    onShareGpsPin: (lat: Double, lng: Double, area: String, note: String) -> Unit,
    onFeedback: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBack)

    val strings = LocalAppStrings.current
    val context = LocalContext.current
    val listState = rememberLazyListState()
    var messageInput by rememberSaveable { mutableStateOf("") }
    var showAddressDialog by remember { mutableStateOf(false) }
    var showGpsDialog by remember { mutableStateOf(false) }

    val counterpartName = if (activeRole == UserRole.WORKER) {
        conversation.customerName
    } else {
        conversation.workerName
    }

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    WorkerAvatar(
                        photoUri = if (activeRole == UserRole.WORKER) "" else workerPhoto,
                        name = counterpartName,
                        sizeDp = 38
                    )
                    Column {
                        Text(
                            text = counterpartName,
                            style = MaterialTheme.typography.titleMedium,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = conversation.locationLabel,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .minimumInteractiveComponentSize()
                        .testTag("chat_back_btn")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = strings.backToInbox
                    )
                }
            },
            actions = {
                if (workerPhone.isNotBlank()) {
                    IconButton(
                        onClick = {
                            launchDialer(context, workerPhone) {
                                onFeedback(strings.dialerUnavailable)
                            }
                        },
                        modifier = Modifier.minimumInteractiveComponentSize()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Call,
                            contentDescription = strings.callNow,
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    IconButton(
                        onClick = {
                            launchWhatsApp(context, workerPhone, conversation.workerName) {
                                onFeedback(strings.whatsappUnavailable)
                            }
                        },
                        modifier = Modifier.minimumInteractiveComponentSize()
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Chat,
                            contentDescription = strings.whatsapp,
                            tint = VerifiedEmerald
                        )
                    }
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        )

        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background)
                .testTag("chat_messages_list"),
            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                val isOwnMessage = when (activeRole) {
                    UserRole.WORKER -> msg.senderRole == UserRole.WORKER.name
                    else -> msg.senderRole != UserRole.WORKER.name
                }
                ChatMessageBubble(
                    message = msg,
                    isOwnMessage = isOwnMessage,
                    onOpenMap = { lat, lng, label ->
                        launchMapsPin(context, lat, lng, label) {
                            onFeedback(strings.mapsUnavailable)
                        }
                    }
                )
            }
        }

        // Composer + Quick Location Share Strip
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 3.dp,
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.5f))
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { showAddressDialog = true },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("share_address_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Home,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = strings.shareHomeAddress,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    FilledTonalButton(
                        onClick = { showGpsDialog = true },
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
                        modifier = Modifier
                            .weight(1f)
                            .minimumInteractiveComponentSize()
                            .testTag("share_gps_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.PinDrop,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = strings.shareGpsPin,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = messageInput,
                        onValueChange = { messageInput = it },
                        placeholder = {
                            Text(
                                text = strings.typeMessageHint,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        },
                        singleLine = true,
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("chat_message_input")
                    )
                    Button(
                        onClick = {
                            if (messageInput.isNotBlank()) {
                                onSendText(messageInput)
                                messageInput = ""
                            }
                        },
                        enabled = messageInput.isNotBlank(),
                        shape = CircleShape,
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier
                            .size(48.dp)
                            .testTag("chat_send_btn")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = strings.send
                        )
                    }
                }
            }
        }
    }

    if (showAddressDialog) {
        ShareHomeAddressDialog(
            defaultArea = defaultCityArea,
            onDismiss = { showAddressDialog = false },
            onShare = { street, landmark, area, note ->
                onShareAddress(street, landmark, area, note)
                showAddressDialog = false
            }
        )
    }

    if (showGpsDialog) {
        ShareGpsPinDialog(
            defaultArea = defaultCityArea,
            onDismiss = { showGpsDialog = false },
            onShare = { lat, lng, area, note ->
                onShareGpsPin(lat, lng, area, note)
                showGpsDialog = false
            }
        )
    }
}

@Composable
private fun ChatMessageBubble(
    message: ChatMessageEntity,
    isOwnMessage: Boolean,
    onOpenMap: (lat: Double, lng: Double, label: String) -> Unit
) {
    val strings = LocalAppStrings.current
    val msgType = runCatching { ChatMessageType.valueOf(message.messageType) }
        .getOrDefault(ChatMessageType.TEXT)

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start
    ) {
        Column(
            horizontalAlignment = if (isOwnMessage) Alignment.End else Alignment.Start,
            modifier = Modifier.widthIn(max = 310.dp)
        ) {
            when (msgType) {
                ChatMessageType.HOME_ADDRESS -> {
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(18.dp))
                            .testTag("chat_address_card_${message.id}")
                    ) {
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = MaterialTheme.colorScheme.primaryContainer
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Home,
                                        contentDescription = strings.sharedAddressTitle,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier
                                            .padding(6.dp)
                                            .size(16.dp)
                                    )
                                }
                                Text(
                                    text = strings.sharedAddressTitle,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            if (message.addressStreet.isNotBlank()) {
                                Text(
                                    text = message.addressStreet,
                                    style = MaterialTheme.typography.titleSmall,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                            if (message.addressLandmark.isNotBlank()) {
                                Text(
                                    text = "${strings.landmarkLabel}: ${message.addressLandmark}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                            if (message.addressCityArea.isNotBlank()) {
                                Text(
                                    text = message.addressCityArea,
                                    style = MaterialTheme.typography.labelMedium,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                            if (message.body.isNotBlank()) {
                                Text(
                                    text = message.body,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                ChatMessageType.GPS_PIN -> {
                    val lat = message.latitude ?: 0.0
                    val lng = message.longitude ?: 0.0
                    Card(
                        shape = RoundedCornerShape(18.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.5.dp, PakistanGreenLight, RoundedCornerShape(18.dp))
                            .testTag("chat_gps_card_${message.id}")
                    ) {
                        Column {
                            // Stylized Mini Map Pin Header
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(84.dp)
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                val gridColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.45f)
                                val pulseColor = PakistanGreenLight.copy(alpha = 0.25f)
                                Canvas(modifier = Modifier.fillMaxSize()) {
                                    val step = 28.dp.toPx()
                                    var x = step
                                    while (x < size.width) {
                                        drawLine(gridColor, Offset(x, 0f), Offset(x, size.height), strokeWidth = 1f)
                                        x += step
                                    }
                                    var y = step
                                    while (y < size.height) {
                                        drawLine(gridColor, Offset(0f, y), Offset(size.width, y), strokeWidth = 1f)
                                        y += step
                                    }
                                    drawCircle(
                                        color = pulseColor,
                                        radius = 28.dp.toPx(),
                                        center = Offset(size.width / 2f, size.height / 2f)
                                    )
                                }
                                Surface(
                                    shape = CircleShape,
                                    color = PakistanGreenDark,
                                    shadowElevation = 4.dp
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.LocationOn,
                                        contentDescription = strings.sharedGpsPinTitle,
                                        tint = PureWhite,
                                        modifier = Modifier
                                            .padding(8.dp)
                                            .size(24.dp)
                                    )
                                }
                            }

                            Column(
                                modifier = Modifier.padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = strings.sharedGpsPinTitle,
                                    style = MaterialTheme.typography.labelLarge,
                                    color = MaterialTheme.colorScheme.primary
                                )
                                if (message.body.isNotBlank()) {
                                    Text(
                                        text = message.body,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                                Text(
                                    text = "${"%.4f".format(lat)}, ${"%.4f".format(lng)}" +
                                        if (message.addressCityArea.isNotBlank()) " • ${message.addressCityArea}" else "",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Button(
                                    onClick = { onOpenMap(lat, lng, message.body) },
                                    colors = ButtonDefaults.buttonColors(containerColor = PakistanGreenDark),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .minimumInteractiveComponentSize()
                                        .testTag("open_maps_pin_btn_${message.id}")
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Map,
                                        contentDescription = null,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(strings.openInMaps)
                                }
                            }
                        }
                    }
                }

                ChatMessageType.TEXT -> {
                    Surface(
                        shape = RoundedCornerShape(
                            topStart = 18.dp,
                            topEnd = 18.dp,
                            bottomStart = if (isOwnMessage) 18.dp else 4.dp,
                            bottomEnd = if (isOwnMessage) 4.dp else 18.dp
                        ),
                        color = if (isOwnMessage) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.surface
                        },
                        tonalElevation = if (isOwnMessage) 0.dp else 2.dp,
                        modifier = if (isOwnMessage) {
                            Modifier
                        } else {
                            Modifier.border(
                                1.dp,
                                MaterialTheme.colorScheme.outline.copy(alpha = 0.6f),
                                RoundedCornerShape(topStart = 18.dp, topEnd = 18.dp, bottomStart = 4.dp, bottomEnd = 18.dp)
                            )
                        }
                    ) {
                        Text(
                            text = message.body,
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isOwnMessage) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            },
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(3.dp))
            Text(
                text = "${message.senderName} • ${formatShortTime(message.createdAt)}",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun ShareHomeAddressDialog(
    defaultArea: String,
    onDismiss: () -> Unit,
    onShare: (street: String, landmark: String, area: String, note: String) -> Unit
) {
    val strings = LocalAppStrings.current
    var street by remember { mutableStateOf("") }
    var landmark by remember { mutableStateOf("") }
    var area by remember { mutableStateOf(defaultArea) }
    var note by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(strings.shareHomeAddress) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = street,
                    onValueChange = { street = it },
                    label = { Text(strings.streetAddressLabel) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = landmark,
                    onValueChange = { landmark = it },
                    label = { Text(strings.landmarkLabel) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = area,
                    onValueChange = { area = it },
                    label = { Text(strings.areaAddress) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text(strings.gpsNoteLabel) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onShare(street, landmark, area, note) },
                enabled = street.isNotBlank() || area.isNotBlank()
            ) {
                Text(strings.send)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(strings.cancel)
            }
        }
    )
}

@Composable
private fun ShareGpsPinDialog(
    defaultArea: String,
    onDismiss: () -> Unit,
    onShare: (lat: Double, lng: Double, area: String, note: String) -> Unit
) {
    val strings = LocalAppStrings.current
    val context = LocalContext.current
    var latText by remember { mutableStateOf("") }
    var lngText by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var statusNote by remember { mutableStateOf<String?>(null) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { results ->
        val granted = results[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            results[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        if (granted) {
            val coords = readBestLastKnownCoordinates(context)
            if (coords != null) {
                latText = "%.5f".format(Locale.US, coords.first)
                lngText = "%.5f".format(Locale.US, coords.second)
                statusNote = null
            } else {
                statusNote = strings.gpsUnavailable
            }
        } else {
            statusNote = strings.gpsPermissionDenied
        }
    }

    val parsedLat = latText.toDoubleOrNull()
    val parsedLng = lngText.toDoubleOrNull()
    val validCoords = parsedLat != null && parsedLng != null &&
        parsedLat in -90.0..90.0 && parsedLng in -180.0..180.0

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(strings.shareGpsPin) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                FilledTonalButton(
                    onClick = {
                        val hasFine = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                        val hasCoarse = ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                        if (hasFine || hasCoarse) {
                            val coords = readBestLastKnownCoordinates(context)
                            if (coords != null) {
                                latText = "%.5f".format(Locale.US, coords.first)
                                lngText = "%.5f".format(Locale.US, coords.second)
                                statusNote = null
                            } else {
                                statusNote = strings.gpsUnavailable
                            }
                        } else {
                            permissionLauncher.launch(
                                arrayOf(
                                    Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.ACCESS_COARSE_LOCATION
                                )
                            )
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Filled.GpsFixed,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(strings.useCurrentGps)
                }

                if (statusNote != null) {
                    Text(
                        text = statusNote.orEmpty(),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.error
                    )
                }

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(
                        value = latText,
                        onValueChange = { latText = it },
                        label = { Text(strings.latitudeLabel) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedTextField(
                        value = lngText,
                        onValueChange = { lngText = it },
                        label = { Text(strings.longitudeLabel) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true,
                        modifier = Modifier.weight(1f)
                    )
                }

                OutlinedTextField(
                    value = note,
                    onValueChange = { note = it },
                    label = { Text(strings.gpsNoteLabel) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    if (validCoords) {
                        onShare(parsedLat, parsedLng, defaultArea, note)
                    }
                },
                enabled = validCoords
            ) {
                Text(strings.send)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(strings.cancel)
            }
        }
    )
}

@SuppressLint("MissingPermission")
private fun readBestLastKnownCoordinates(context: Context): Pair<Double, Double>? {
    val manager = context.getSystemService(Context.LOCATION_SERVICE) as? LocationManager ?: return null
    val providers = listOf(
        LocationManager.GPS_PROVIDER,
        LocationManager.NETWORK_PROVIDER,
        LocationManager.PASSIVE_PROVIDER
    )
    val best = providers
        .mapNotNull { runCatching { manager.getLastKnownLocation(it) }.getOrNull() }
        .maxByOrNull { it.time }
    return best?.let { it.latitude to it.longitude }
}

private fun formatShortTime(epochMillis: Long): String {
    val formatter = SimpleDateFormat("h:mm a", Locale.getDefault())
    return formatter.format(Date(epochMillis))
}
