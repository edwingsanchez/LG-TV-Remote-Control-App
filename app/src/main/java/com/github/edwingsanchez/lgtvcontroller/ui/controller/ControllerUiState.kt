package com.github.edwingsanchez.lgtvcontroller.ui.controller

import com.github.edwingsanchez.lgtvcontroller.device.DeviceControllerButton
import com.github.edwingsanchez.lgtvcontroller.device.DeviceStatus

data class ControllerUiState(
    val deviceName: String? = null,
    val deviceStatus: DeviceStatus = DeviceStatus.DISCONNECTED,
    val clickMouse: () -> Unit = {},
    val moveMouse: (Double, Double) -> Unit = { _, _ -> },
    val scroll: (Double, Double) -> Unit = { _, _ -> },
    val hasCapability: (DeviceControllerButton) -> Boolean = { false },
    val executeButton: (DeviceControllerButton) -> Unit = {},
)