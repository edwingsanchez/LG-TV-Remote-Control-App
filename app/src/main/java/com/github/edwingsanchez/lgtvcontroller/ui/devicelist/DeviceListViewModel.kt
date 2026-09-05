package com.github.edwingsanchez.lgtvcontroller.ui.devicelist

import androidx.lifecycle.viewModelScope
import com.github.edwingsanchez.lgtvcontroller.DeviceManager
import com.github.edwingsanchez.lgtvcontroller.device.DeviceStatus
import com.github.edwingsanchez.lgtvcontroller.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceListViewModel @Inject constructor(
    private val deviceManager: DeviceManager,
) : BaseViewModel(deviceManager) {
    val uiState: StateFlow<DeviceListUiState> = deviceManager.devices.map { devices ->
        val deviceItems = devices.map { device ->
            DeviceItemData(
                displayName = if (device.displayName.isNullOrEmpty()) device.friendlyName else device.displayName!!,
                status = device.status,
                isPoweredOn = true, // TODO: Get actual power status if available from SDK
                connect = device::connect
            )
        }.sortedWith { device1, device2 ->
            when {
                !device1.isPoweredOn -> 1
                !device2.isPoweredOn -> -1
                device1.status.value == DeviceStatus.CONNECTED -> -1
                device2.status.value == DeviceStatus.CONNECTED -> 1
                else -> 0
            }
        }
        DeviceListUiState(devices = deviceItems)
    }.stateIn(viewModelScope, SharingStarted.Eagerly, DeviceListUiState())

    private val _navigationEvents = MutableSharedFlow<Unit>()
    val navigationEvents: SharedFlow<Unit> = _navigationEvents.asSharedFlow()

    init {
        viewModelScope.launch {
            deviceManager.connectedDevice.collect {
                if (it != null) {
                    _navigationEvents.emit(Unit)
                }
            }
        }
    }
}
