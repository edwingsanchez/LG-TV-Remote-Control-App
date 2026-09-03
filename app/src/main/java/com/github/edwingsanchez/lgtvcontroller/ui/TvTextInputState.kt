package com.github.edwingsanchez.lgtvcontroller.ui

data class TvTextInputState(
    val isKeyboardOpen: Boolean = false,
    val sendBackspace: () -> Unit = {},
    val sendEnter: () -> Unit = {},
    val sendText: (String) -> Unit = {},
)
