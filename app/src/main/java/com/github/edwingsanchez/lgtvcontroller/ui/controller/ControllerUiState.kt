package com.github.edwingsanchez.lgtvcontroller.ui.controller

import com.github.edwingsanchez.lgtvcontroller.device.DeviceControllerButton
import com.github.edwingsanchez.lgtvcontroller.device.DeviceStatus
import com.github.edwingsanchez.lgtvcontroller.domain.model.App
import com.github.edwingsanchez.lgtvcontroller.domain.model.Input

data class ControllerUiState(
    val deviceID: String? = null,
    val deviceName: String? = null,
    val deviceStatus: DeviceStatus = DeviceStatus.DISCONNECTED,
    val apps: List<App> = emptyList(),
    val inputs: List<Input> = emptyList(),
    val runningApp: String = "",
    val clickMouse: () -> Unit = {},
    val moveMouse: (Double, Double) -> Unit = { _, _ -> },
    val scroll: (Double, Double) -> Unit = { _, _ -> },
    val hasCapability: (DeviceControllerButton) -> Boolean = { false },
    val executeButton: (DeviceControllerButton) -> Unit = {},
    val launchApp: (String) -> Unit = {},
)
