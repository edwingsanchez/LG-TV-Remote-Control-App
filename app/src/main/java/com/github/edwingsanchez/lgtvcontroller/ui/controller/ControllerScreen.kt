package com.github.edwingsanchez.lgtvcontroller.ui.controller

import androidx.compose.foundation.background
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.draggable2D
import androidx.compose.foundation.gestures.rememberDraggable2DState
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.automirrored.rounded.Input
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.rounded.VolumeOff
import androidx.compose.material.icons.filled.ElectricalServices
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.KeyboardAlt
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Tv
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.integration.compose.placeholder
import com.bumptech.glide.load.model.GlideUrl
import com.github.edwingsanchez.lgtvcontroller.ControllerTopAppBar
import com.github.edwingsanchez.lgtvcontroller.R
import com.github.edwingsanchez.lgtvcontroller.R.string
import com.github.edwingsanchez.lgtvcontroller.TopAppBarAction
import com.github.edwingsanchez.lgtvcontroller.device.DeviceControllerButton
import com.github.edwingsanchez.lgtvcontroller.device.DeviceStatus
import com.github.edwingsanchez.lgtvcontroller.device.impl.PreviewDevice
import com.github.edwingsanchez.lgtvcontroller.domain.model.App
import com.github.edwingsanchez.lgtvcontroller.domain.model.Input
import com.github.edwingsanchez.lgtvcontroller.ui.ConnectedDeviceScaffold
import com.github.edwingsanchez.lgtvcontroller.ui.TvTextInputState
import com.github.edwingsanchez.lgtvcontroller.ui.icons.MyIconPack
import com.github.edwingsanchez.lgtvcontroller.ui.icons.myiconpack.TrackpadInput
import com.github.edwingsanchez.lgtvcontroller.ui.snackbar.Snackbar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

enum class ControllerTab(val title: String, val icon: ImageVector) {
    REMOTE("Remote", Icons.Rounded.Tv),
    APPS("Apps", Icons.Filled.Widgets),
    INPUTS("Inputs", Icons.Filled.ElectricalServices)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview()
@Composable
fun ControlPreview() {
    ControllerScreenPreview(isConnected = true)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ControlPreviewDisconnected() {
    ControllerScreenPreview(isConnected = false)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControllerScreenPreview(
    isConnected: Boolean,
) {
    val device = if (isConnected) PreviewDevice else null
    val controllerUiState = ControllerUiState(
        deviceName = device?.friendlyName,
        deviceStatus = device?.let { runBlocking { it.state.first().status } }
            ?: DeviceStatus.DISCONNECTED,
        hasCapability = { true },
        executeButton = {},
    )

    ControllerScreen(
        controllerUiState = controllerUiState,
        tvTextInputState = TvTextInputState(),
        errorFlow = emptyFlow<Snackbar>(),
        navigateUp = {},
        navigateToEditDevice = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControllerScreen(
    controllerViewModel: ControllerViewModel = hiltViewModel(),
    navigateUp: () -> Unit,
    navigateToEditDevice: (String) -> Unit,
) {
    val controllerUiState by controllerViewModel.uiState.collectAsState()
    val tvTextInputState by controllerViewModel.tvTextInputState.collectAsState()

    ControllerScreen(
        controllerUiState,
        tvTextInputState,
        controllerViewModel.errors,
        navigateUp,
        navigateToEditDevice,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ControllerScreen(
    controllerUiState: ControllerUiState,
    tvTextInputState: TvTextInputState,
    errorFlow: Flow<Snackbar>,
    navigateUp: () -> Unit,
    navigateToEditDevice: (String) -> Unit,
) {
    var selectedTab by remember { mutableIntStateOf(0) }

    ConnectedDeviceScaffold(
        errorFlow = errorFlow,
        textInputState = tvTextInputState,
        topBar = {
            ControllerTopAppBar(
                title = controllerUiState.deviceName ?: stringResource(string.controller_title),
                subtitle = buildAnnotatedString {
                    if (controllerUiState.deviceStatus == DeviceStatus.CONNECTED) {
                        withStyle(style = SpanStyle(color = colorResource(R.color.connected))) {
                            append("● ")
                        }
                    }
                    append(stringResource(controllerUiState.deviceStatus.nameResId))
                },
                navigateUp = navigateUp,
                actions = {
                    TopAppBarAction(
                        imageVector = Icons.Filled.Settings,
                        contentDescription = stringResource(string.edit_button),
                        enabled = controllerUiState.deviceID != null,
                    ) {
                        controllerUiState.deviceID?.also { navigateToEditDevice(it) }
                    }
                },
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF1E1E1E),
                contentColor = Color.White
            ) {
                ControllerTab.entries.forEachIndexed { index, tab ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = { Icon(tab.icon, contentDescription = tab.title) },
                        label = { Text(tab.title) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            selectedTextColor = Color.White,
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray,
                            indicatorColor = Color(0xFF37474F)
                        )
                    )
                }
            }
        }
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (ControllerTab.entries[selectedTab]) {
                ControllerTab.REMOTE -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(ControlsSpacing),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(start = 24.dp, end = 24.dp, top = 16.dp, bottom = 8.dp)
                    ) {
                        var trackpadEnabled by remember { mutableStateOf(false) }
                        Header(
                            deviceName = controllerUiState.deviceName,
                            deviceStatus = controllerUiState.deviceStatus,
                            trackpadEnabled = trackpadEnabled,
                            setTrackpadEnabled = { trackpadEnabled = it },
                            hasPowerCapability = controllerUiState.hasCapability(DeviceControllerButton.POWER),
                            powerOff = { controllerUiState.executeButton(DeviceControllerButton.POWER) },
                            navigateToDeviceList = navigateUp,
                        )

                        Controls(
                            modifier = Modifier.weight(1f),
                            trackpadEnabled = trackpadEnabled,
                            clickMouse = controllerUiState.clickMouse,
                            moveMouse = controllerUiState.moveMouse,
                            scroll = controllerUiState.scroll,
                            hasCapability = controllerUiState.hasCapability,
                            executeButton = controllerUiState.executeButton,
                        )
                    }
                }
                ControllerTab.APPS -> {
                    AppsList(
                        apps = controllerUiState.apps,
                        runningApp = controllerUiState.runningApp,
                        openApp = { controllerUiState.launchApp(it.id) }
                    )
                }
                ControllerTab.INPUTS -> {
                    InputsList(
                        inputs = controllerUiState.inputs,
                        runningApp = controllerUiState.runningApp,
                        switchInput = { controllerUiState.launchApp(it.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun Header(
    deviceName: String?,
    deviceStatus: DeviceStatus,
    trackpadEnabled: Boolean,
    setTrackpadEnabled: (Boolean) -> Unit,
    hasPowerCapability: Boolean,
    powerOff: () -> Unit,
    navigateToDeviceList: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        // Power Button - Modern Circle
        if (hasPowerCapability) {
            FilledIconButton(
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = Color(0xFFE53935).copy(alpha = 0.15f),
                    contentColor = Color(0xFFE53935)
                ),
                onClick = powerOff,
                modifier = Modifier.size(52.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_power_24),
                    contentDescription = stringResource(string.power_button),
                    modifier = Modifier.size(28.dp)
                )
            }
        } else {
            Spacer(modifier = Modifier.size(52.dp))
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .weight(1f)
                .clickable(onClick = navigateToDeviceList)
        ) {
            Text(
                text = deviceName ?: stringResource(string.controller_device),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (deviceName != null && deviceStatus == DeviceStatus.CONNECTED) Color(0xFF4CAF50) else Color.Gray)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = if (deviceName == null) stringResource(string.controller_disconnected) else stringResource(deviceStatus.nameResId),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }

        // Trackpad Toggle - Modern Circle
        FilledIconButton(
            colors = IconButtonDefaults.filledIconButtonColors(
                containerColor = if (trackpadEnabled) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
                contentColor = if (trackpadEnabled) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onSurfaceVariant
            ),
            onClick = { setTrackpadEnabled(!trackpadEnabled) },
            modifier = Modifier.size(52.dp)
        ) {
            Icon(
                imageVector = if (trackpadEnabled) Icons.Rounded.KeyboardAlt else MyIconPack.TrackpadInput,
                contentDescription = "Toggle Trackpad",
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

@Composable
fun ColumnScope.Controls(
    modifier: Modifier = Modifier,
    trackpadEnabled: Boolean,
    clickMouse: () -> Unit,
    moveMouse: (Double, Double) -> Unit,
    scroll: (Double, Double) -> Unit,
    hasCapability: (DeviceControllerButton) -> Boolean,
    executeButton: (DeviceControllerButton) -> Unit,
) {
    if (trackpadEnabled) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
                .draggable2D(
                    state = rememberDraggable2DState { delta ->
                        moveMouse(delta.x.toDouble(), delta.y.toDouble())
                    },
                )
                .clickable(onClick = clickMouse),
            shape = RoundedCornerShape(32.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)),
            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    "TRACKPAD",
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f),
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Black
                )
            }
        }
    } else {
        Column(
            modifier = modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Row 1: Rockers and Action buttons
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                VolumeControls(
                    hasCapability = hasCapability,
                    executeButton = executeButton,
                    modifier = Modifier.size(width = 64.dp, height = 170.dp)
                )

                Column(
                    modifier = Modifier.height(170.dp),
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CIconButton(
                        imageVector = Icons.AutoMirrored.Rounded.Input,
                        contentDescription = "Source",
                        enabled = hasCapability(DeviceControllerButton.SOURCE),
                        shape = CircleShape,
                        iconSize = 32.dp,
                        modifier = Modifier.size(52.dp),
                        onClick = { executeButton(DeviceControllerButton.SOURCE) }
                    )
                    CIconButton(
                        imageVector = Icons.AutoMirrored.Rounded.VolumeOff,
                        contentDescription = "Mute",
                        enabled = hasCapability(DeviceControllerButton.MUTE),
                        shape = CircleShape,
                        iconSize = 32.dp,
                        modifier = Modifier.size(52.dp),
                        onClick = { executeButton(DeviceControllerButton.MUTE) }
                    )
                }

                ChannelControls(
                    hasCapability = hasCapability,
                    executeButton = executeButton,
                    modifier = Modifier.size(width = 64.dp, height = 170.dp)
                )
            }

            // Row 2: Integrated D-Pad
            DPad(
                modifier = Modifier
                    .size(260.dp)
                    .padding(16.dp),
                hasCapability = hasCapability,
                executeButton = executeButton
            )

            // Row 3: Navigation Cluster
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val navBtnMod = Modifier.size(52.dp)
                CIconButton(
                    imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                    contentDescription = "Back",
                    enabled = hasCapability(DeviceControllerButton.BACK),
                    shape = CircleShape,
                    modifier = navBtnMod,
                    iconSize = 32.dp,
                    onClick = { executeButton(DeviceControllerButton.BACK) }
                )
                CIconButton(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "Home",
                    enabled = hasCapability(DeviceControllerButton.HOME),
                    shape = CircleShape,
                    modifier = navBtnMod,
                    iconSize = 32.dp,
                    onClick = { executeButton(DeviceControllerButton.HOME) }
                )
                CIconButton(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "Settings",
                    enabled = hasCapability(DeviceControllerButton.QMENU),
                    shape = CircleShape,
                    modifier = navBtnMod,
                    iconSize = 32.dp,
                    onClick = { executeButton(DeviceControllerButton.QMENU) }
                )
                CIconButton(
                    imageVector = Icons.AutoMirrored.Rounded.ExitToApp,
                    contentDescription = "Exit",
                    enabled = hasCapability(DeviceControllerButton.EXIT),
                    shape = CircleShape,
                    modifier = navBtnMod,
                    iconSize = 32.dp,
                    onClick = { executeButton(DeviceControllerButton.EXIT) }
                )
            }
        }
    }
}

@Composable
fun DPad(
    modifier: Modifier = Modifier,
    hasCapability: (DeviceControllerButton) -> Boolean,
    executeButton: (DeviceControllerButton) -> Unit,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        // Main D-Pad Ring
        Surface(
            modifier = Modifier.fillMaxSize(),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
            border = androidx.compose.foundation.BorderStroke(2.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
        ) {}

        // Center OK button
        Surface(
            modifier = Modifier
                .fillMaxSize(0.4f)
                .clickable(
                    enabled = hasCapability(DeviceControllerButton.OK),
                    onClick = { executeButton(DeviceControllerButton.OK) }
                ),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            shadowElevation = 4.dp
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    "OK",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        }

        // Arrow Buttons
        val arrowSize = 48.dp
        val padding = 8.dp

        IconButton(
            onClick = { executeButton(DeviceControllerButton.UP) },
            enabled = hasCapability(DeviceControllerButton.UP),
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = padding)
                .size(arrowSize)
        ) {
            Icon(Icons.Rounded.KeyboardArrowUp, "Up", modifier = Modifier.size(32.dp))
        }

        IconButton(
            onClick = { executeButton(DeviceControllerButton.DOWN) },
            enabled = hasCapability(DeviceControllerButton.DOWN),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = padding)
                .size(arrowSize)
        ) {
            Icon(Icons.Rounded.KeyboardArrowDown, "Down", modifier = Modifier.size(32.dp))
        }

        IconButton(
            onClick = { executeButton(DeviceControllerButton.LEFT) },
            enabled = hasCapability(DeviceControllerButton.LEFT),
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = padding)
                .size(arrowSize)
        ) {
            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowLeft, "Left", modifier = Modifier.size(32.dp))
        }

        IconButton(
            onClick = { executeButton(DeviceControllerButton.RIGHT) },
            enabled = hasCapability(DeviceControllerButton.RIGHT),
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = padding)
                .size(arrowSize)
        ) {
            Icon(Icons.AutoMirrored.Rounded.KeyboardArrowRight, "Right", modifier = Modifier.size(32.dp))
        }
    }
}

@Composable
fun RowScope.ChannelControls(
    hasCapability: (DeviceControllerButton) -> Boolean,
    executeButton: (DeviceControllerButton) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEnabled = hasCapability(DeviceControllerButton.CHANNEL_UP)
    VerticalControls(
        centerText = "CH",
        modifier = modifier,
        enabled = isEnabled,
        topButton = {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable(enabled = isEnabled) { executeButton(DeviceControllerButton.CHANNEL_UP) },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.KeyboardArrowUp, "Ch Up", modifier = Modifier.size(28.dp))
            }
        },
        bottomButton = {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable(enabled = isEnabled) { executeButton(DeviceControllerButton.CHANNEL_DOWN) },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.KeyboardArrowDown, "Ch Down", modifier = Modifier.size(28.dp))
            }
        }
    )
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AppsList(
    apps: List<App>,
    runningApp: String,
    openApp: (App) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        apps.chunked(3).forEach { rowApps ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                rowApps.forEach { appInfo ->
                    AppItem(
                        appInfo = appInfo,
                        isRunning = appInfo.id == runningApp,
                        modifier = Modifier.weight(1f),
                        onClick = { openApp(appInfo) }
                    )
                }
                // Add spacers if the row is not full
                repeat(3 - rowApps.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AppItem(
    appInfo: App,
    isRunning: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
            .background(if (isRunning) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface)
            .padding(12.dp)
    ) {
        GlideImage(
            model = CustomGlideUrl(appInfo.icon, appInfo.name),
            contentDescription = appInfo.name,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .clip(RoundedCornerShape(12.dp)),
            loading = placeholder {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            },
            failure = placeholder {
                Icon(Icons.Filled.Widgets, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(48.dp))
            }
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = appInfo.name,
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun InputsList(
    inputs: List<Input>,
    runningApp: String,
    switchInput: (Input) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        inputs.forEach { input ->
            InputItem(
                input = input,
                isRunning = input.id == runningApp,
                onClick = { switchInput(input) }
            )
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun InputItem(
    input: Input,
    isRunning: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isRunning) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            GlideImage(
                model = CustomGlideUrl(input.icon, input.name),
                contentDescription = input.name,
                modifier = Modifier.size(48.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSurface),
                failure = placeholder {
                    Icon(Icons.Filled.ElectricalServices, contentDescription = null, tint = Color.White)
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = buildAnnotatedString {
                    if (input.connected) {
                        withStyle(style = SpanStyle(color = Color(0xFF4CAF50))) {
                            append("● ")
                        }
                    }
                    append(input.name)
                },
                color = MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            if (isRunning) {
                Text(
                    text = "Active",
                    color = Color(0xFF4CAF50),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Composable
fun RowScope.VolumeControls(
    hasCapability: (DeviceControllerButton) -> Boolean,
    executeButton: (DeviceControllerButton) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isEnabled = hasCapability(DeviceControllerButton.VOLUME_UP)
    VerticalControls(
        centerText = "VOL",
        modifier = modifier,
        enabled = isEnabled,
        topButton = {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable(enabled = isEnabled) { executeButton(DeviceControllerButton.VOLUME_UP) },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.KeyboardArrowUp, "Vol Up", modifier = Modifier.size(28.dp))
            }
        },
        bottomButton = {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .clickable(enabled = isEnabled) { executeButton(DeviceControllerButton.VOLUME_DOWN) },
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Rounded.KeyboardArrowDown, "Vol Down", modifier = Modifier.size(28.dp))
            }
        }
    )
}

class CustomGlideUrl(
    url: String,
    val cacheName: String,
) : GlideUrl(url.ifEmpty { "Not Found" }) {
    override fun getCacheKey(): String {
        return cacheName
    }
}

