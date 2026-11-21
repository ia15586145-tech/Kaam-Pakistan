package com.example.ui

import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Assignment
import androidx.compose.material.icons.automirrored.outlined.Assignment
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AdminPanelSettings
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.i18n.LocalAppLanguage
import com.example.i18n.LocalAppStrings
import com.example.i18n.stringsFor
import com.example.ui.screens.AdminPanelScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MessagesScreen
import com.example.ui.screens.ProfileAndAuthScreen
import com.example.ui.screens.RequestsScreen
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.WorkerDetailScreen
import com.example.ui.theme.AlertRed
import com.example.ui.theme.KaamPakistanTheme
import com.example.ui.theme.PureWhite
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

object KaamRoutes {
    const val HOME = "home"
    const val REQUESTS = "requests"
    const val PROFILE = "profile"
    const val ADMIN = "admin"
    const val MESSAGES = "messages"
    const val WORKER_DETAIL = "worker_detail/{workerId}"

    fun workerDetail(workerId: String) = "worker_detail/$workerId"
}

private data class NavTabItem(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val testTag: String,
    val badgeCount: Int = 0
)

@Composable
fun KaamPakistanApp(
    viewModel: KaamViewModel = viewModel(),
    locationViewModel: LocationViewModel = viewModel(),
    showSplashOnLaunch: Boolean = true
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val locationState by locationViewModel.uiState.collectAsStateWithLifecycle()
    val language = uiState.preferences.language
    val strings = remember(language) { stringsFor(language) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isSplashVisible by remember { mutableStateOf(showSplashOnLaunch) }

    LaunchedEffect(showSplashOnLaunch) {
        if (showSplashOnLaunch) {
            delay(2500L)
            isSplashVisible = false
        }
    }

    LaunchedEffect(uiState.preferences.showSamplePreviewData) {
        locationViewModel.setPreviewMode(uiState.preferences.showSamplePreviewData)
    }

    LaunchedEffect(Unit) {
        viewModel.events.collect { msg ->
            snackbarHostState.showSnackbar(msg)
        }
    }

    KaamPakistanTheme(darkTheme = uiState.preferences.darkTheme) {
        CompositionLocalProvider(
            LocalAppLanguage provides language,
            LocalAppStrings provides strings,
            LocalLayoutDirection provides language.layoutDirection
        ) {
            Crossfade(
                targetState = isSplashVisible,
                animationSpec = tween(durationMillis = 450),
                label = "splash_to_home_transition"
            ) { showingSplash ->
                if (showingSplash) {
                    SplashScreen()
                } else {
                    val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            val tabs = remember(strings, uiState.unreadMessagesCount) {
                listOf(
                    NavTabItem(KaamRoutes.HOME, strings.navHome, Icons.Filled.Home, Icons.Outlined.Home, "nav_home"),
                    NavTabItem(
                        KaamRoutes.REQUESTS,
                        strings.navRequests,
                        Icons.AutoMirrored.Filled.Assignment,
                        Icons.AutoMirrored.Outlined.Assignment,
                        "nav_requests"
                    ),
                    NavTabItem(KaamRoutes.PROFILE, strings.navProfile, Icons.Filled.Person, Icons.Outlined.Person, "nav_profile"),
                    NavTabItem(KaamRoutes.ADMIN, strings.navAdmin, Icons.Filled.AdminPanelSettings, Icons.Outlined.AdminPanelSettings, "nav_admin"),
                    NavTabItem(
                        KaamRoutes.MESSAGES,
                        strings.navMessages,
                        Icons.Filled.ChatBubble,
                        Icons.Outlined.ChatBubbleOutline,
                        "nav_messages",
                        badgeCount = uiState.unreadMessagesCount
                    )
                )
            }

            val showTopLevelNav = currentRoute in tabs.map { it.route }

            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val useNavRail = maxWidth >= 600.dp
                val navLabelSize = if (maxWidth < 400.dp) 10.sp else 11.sp

                Scaffold(
                    snackbarHost = { SnackbarHost(snackbarHostState) },
                    contentWindowInsets = WindowInsets.statusBars,
                    bottomBar = {
                        if (showTopLevelNav && !useNavRail) {
                            androidx.compose.foundation.layout.Column {
                                HorizontalDivider(
                                    thickness = 1.dp,
                                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                                )
                                NavigationBar(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    tonalElevation = 2.dp,
                                    windowInsets = WindowInsets.navigationBars
                                ) {
                                    tabs.forEach { tab ->
                                        val selected = currentRoute == tab.route
                                        NavigationBarItem(
                                            selected = selected,
                                            alwaysShowLabel = true,
                                            onClick = {
                                                if (currentRoute != tab.route) {
                                                    navController.navigate(tab.route) {
                                                        popUpTo(KaamRoutes.HOME) { saveState = true }
                                                        launchSingleTop = true
                                                        restoreState = true
                                                    }
                                                }
                                            },
                                            icon = {
                                                NavTabIcon(
                                                    icon = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                                    contentDescription = tab.label,
                                                    badgeCount = tab.badgeCount
                                                )
                                            },
                                            label = {
                                                Text(
                                                    text = tab.label,
                                                    style = MaterialTheme.typography.labelSmall.copy(
                                                        fontSize = navLabelSize,
                                                        lineHeight = 13.sp,
                                                        letterSpacing = 0.sp
                                                    ),
                                                    maxLines = 1,
                                                    softWrap = false,
                                                    textAlign = TextAlign.Center
                                                )
                                            },
                                            colors = NavigationBarItemDefaults.colors(
                                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                                indicatorColor = MaterialTheme.colorScheme.primaryContainer
                                            ),
                                            modifier = Modifier.testTag(tab.testTag)
                                        )
                                    }
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Row(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        if (showTopLevelNav && useNavRail) {
                            NavigationRail(
                                containerColor = MaterialTheme.colorScheme.surface,
                                modifier = Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                            ) {
                                tabs.forEach { tab ->
                                    val selected = currentRoute == tab.route
                                    NavigationRailItem(
                                        selected = selected,
                                        onClick = {
                                            if (currentRoute != tab.route) {
                                                navController.navigate(tab.route) {
                                                    popUpTo(KaamRoutes.HOME) { saveState = true }
                                                    launchSingleTop = true
                                                    restoreState = true
                                                }
                                            }
                                        },
                                        icon = {
                                            NavTabIcon(
                                                icon = if (selected) tab.selectedIcon else tab.unselectedIcon,
                                                contentDescription = tab.label,
                                                badgeCount = tab.badgeCount
                                            )
                                        },
                                        label = {
                                            Text(
                                                text = tab.label,
                                                maxLines = 1,
                                                softWrap = false
                                            )
                                        },
                                        modifier = Modifier.testTag(tab.testTag)
                                    )
                                }
                            }
                        }

                        Box(modifier = Modifier.weight(1f).fillMaxSize()) {
                            NavHost(
                                navController = navController,
                                startDestination = KaamRoutes.HOME
                            ) {
                                composable(KaamRoutes.HOME) {
                                    HomeScreen(
                                        uiState = uiState.copy(
                                            locationProvider = locationState.locationProvider,
                                            provinces = locationState.provinces
                                        ),
                                        onLanguageSelected = viewModel::setLanguage,
                                        onSearchQueryChange = { query ->
                                            locationViewModel.updateSearchQuery(query)
                                            viewModel.updateSearchQuery(query)
                                        },
                                        onLocationFilterSelected = { prov, div, dist, tal ->
                                            locationViewModel.selectLocation(prov, div, dist, tal)
                                            viewModel.selectLocationFilter(prov, div, dist, tal)
                                        },
                                        onCategorySelected = { categoryKey ->
                                            locationViewModel.selectCategory(categoryKey)
                                            viewModel.selectCategory(categoryKey)
                                        },
                                        onClearAllFilters = {
                                            locationViewModel.clearFilters()
                                            viewModel.clearAllFilters()
                                        },
                                        onToggleSamplePreview = { show ->
                                            locationViewModel.setPreviewMode(show)
                                            viewModel.setShowSamplePreviewData(show)
                                        },
                                        onToggleFavorite = viewModel::toggleFavoriteWorker,
                                        onToggleFavoritesOnly = viewModel::setFavoritesOnlyFilter,
                                        onWorkerClick = { workerId ->
                                            navController.navigate(KaamRoutes.workerDetail(workerId))
                                        }
                                    )
                                }

                                composable(KaamRoutes.REQUESTS) {
                                    RequestsScreen(
                                        uiState = uiState,
                                        onUpdateStatus = viewModel::updateRequestStatus,
                                        onToggleAvailability = viewModel::toggleWorkerAvailability
                                    )
                                }

                                composable(KaamRoutes.PROFILE) {
                                    ProfileAndAuthScreen(
                                        uiState = uiState,
                                        onLanguageSelected = viewModel::setLanguage,
                                        onRoleSelected = viewModel::setActiveRole,
                                        onToggleSamplePreview = viewModel::setShowSamplePreviewData,
                                        onToggleDarkTheme = viewModel::setDarkTheme,
                                        onOtpPhoneChange = viewModel::updateOtpPhone,
                                        onOtpChannelSelect = viewModel::selectOtpChannel,
                                        onRequestOtp = viewModel::requestSixDigitOtp,
                                        onOtpCodeChange = viewModel::updateOtpCode,
                                        onVerifyOtp = viewModel::verifySixDigitOtp,
                                        onSaveCustomerProfile = { name, phone, provId, divId, distId, talId, area ->
                                            viewModel.saveCustomerProfile(
                                                fullName = name,
                                                rawPhone = phone,
                                                provinceId = provId,
                                                divisionId = divId,
                                                districtId = distId,
                                                talukaId = talId,
                                                areaDetail = area,
                                                successMessage = strings.feedbackSaved
                                            )
                                        },
                                        onSaveWorkerProfile = { existingId, name, phone, cnic, provId, divId, distId, talId, area, cats, exp, skills, price, photo, portfolio, avail ->
                                            viewModel.saveWorkerProfile(
                                                existingWorkerId = existingId,
                                                fullName = name,
                                                rawPhone = phone,
                                                rawCnic = cnic,
                                                provinceId = provId,
                                                divisionId = divId,
                                                districtId = distId,
                                                talukaId = talId,
                                                areaDetail = area,
                                                categoryKeys = cats,
                                                experienceYears = exp,
                                                skillsSummary = skills,
                                                basePricePkr = price,
                                                profilePhotoUri = photo,
                                                portfolioUris = portfolio,
                                                isAvailable = avail,
                                                successMessage = strings.feedbackSaved
                                            )
                                        }
                                    )
                                }

                                composable(KaamRoutes.ADMIN) {
                                    AdminPanelScreen(
                                        uiState = uiState,
                                        onApproveWorker = viewModel::adminApproveWorker,
                                        onRejectWorker = viewModel::adminRejectWorker,
                                        onToggleFeatured = viewModel::adminToggleFeatured,
                                        onToggleCategory = viewModel::adminToggleCategory,
                                        onAddCategory = { en, ur, sd ->
                                            viewModel.adminAddCategory(en, ur, sd, strings.feedbackSaved)
                                        },
                                        onUpdateReportStatus = viewModel::adminUpdateReportStatus
                                    )
                                }

                                composable(KaamRoutes.MESSAGES) {
                                    MessagesScreen(
                                        uiState = uiState,
                                        onOpenConversation = viewModel::openConversation,
                                        onSendText = viewModel::sendChatTextMessage,
                                        onShareAddress = viewModel::shareHomeAddressInChat,
                                        onShareGpsPin = viewModel::shareGpsPinInChat,
                                        onFeedback = { msg ->
                                            scope.launch { snackbarHostState.showSnackbar(msg) }
                                        }
                                    )
                                }

                                composable(
                                    route = KaamRoutes.WORKER_DETAIL,
                                    arguments = listOf(navArgument("workerId") { type = NavType.StringType })
                                ) { backStackEntry ->
                                    val workerId = backStackEntry.arguments?.getString("workerId").orEmpty()
                                    val workerWithStats = uiState.allWorkers.firstOrNull { it.worker.id == workerId }
                                    if (workerWithStats != null) {
                                        val workerReviews = uiState.reviews.filter { it.workerId == workerId }
                                        WorkerDetailScreen(
                                            workerWithStats = workerWithStats,
                                            categories = uiState.categories,
                                            reviews = workerReviews,
                                            onBack = { navController.popBackStack() },
                                            onSubmitRequest = { worker, catKey, area, desc, price, time ->
                                                viewModel.submitServiceRequest(
                                                    worker = worker,
                                                    categoryKey = catKey,
                                                    area = area,
                                                    description = desc,
                                                    offeredPricePkr = price,
                                                    preferredTime = time,
                                                    successMessage = strings.feedbackRequestSent
                                                )
                                            },
                                            onSubmitReview = { wId, rating, comment ->
                                                viewModel.submitReview(
                                                    workerId = wId,
                                                    rating = rating,
                                                    comment = comment,
                                                    successMessage = strings.feedbackReviewPosted
                                                )
                                            },
                                            onSubmitReport = { worker, reason, details ->
                                                viewModel.submitReport(
                                                    worker = worker,
                                                    reason = reason,
                                                    details = details,
                                                    successMessage = strings.feedbackReportSubmitted
                                                )
                                            },
                                            onOpenChat = { worker ->
                                                viewModel.startOrOpenChatWithWorker(worker) {
                                                    navController.navigate(KaamRoutes.MESSAGES) {
                                                        popUpTo(KaamRoutes.HOME) { saveState = true }
                                                        launchSingleTop = true
                                                    }
                                                }
                                            },
                                            onFeedback = { msg ->
                                                scope.launch { snackbarHostState.showSnackbar(msg) }
                                            }
                                        )
                                    } else {
                                        LaunchedEffect(Unit) { navController.popBackStack() }
                                    }
                                }
                            }
                        }
                    }
                }
            }
                }
            }
        }
    }
}

@Composable
private fun NavTabIcon(
    icon: ImageVector,
    contentDescription: String,
    badgeCount: Int
) {
    if (badgeCount > 0) {
        BadgedBox(
            badge = {
                Badge(
                    containerColor = AlertRed,
                    contentColor = PureWhite,
                    modifier = Modifier.testTag("messages_unread_badge")
                ) {
                    Text(
                        text = if (badgeCount > 99) "99+" else badgeCount.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp)
                    )
                }
            }
        ) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription
            )
        }
    } else {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription
        )
    }
}
