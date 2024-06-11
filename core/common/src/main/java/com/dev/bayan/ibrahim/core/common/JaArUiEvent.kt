package com.dev.bayan.ibrahim.core.common

interface JaArUiEvent<UiState> {
    data object OnRefresh

    fun onApplyEvent(uiState: UiState): UiState
}