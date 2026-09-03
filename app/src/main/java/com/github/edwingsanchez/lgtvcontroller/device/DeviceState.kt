package com.github.edwingsanchez.lgtvcontroller.device

import com.github.edwingsanchez.lgtvcontroller.domain.model.App
import com.github.edwingsanchez.lgtvcontroller.domain.model.Input

data class DeviceState(
    val displayName: String?,
    val status: DeviceStatus,
    val runningApp: String,
    val apps: List<App>,
    val inputs: List<Input>,
    val isKeyboardOpen: Boolean,
)