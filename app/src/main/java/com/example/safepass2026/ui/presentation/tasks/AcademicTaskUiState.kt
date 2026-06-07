package com.example.safepass2026.ui.presentation.tasks

import com.example.safepass2026.domain.model.AcademicTask

sealed class AcademicTaskUiState {
    object Loading : AcademicTaskUiState()
    data class Success(val tasks: List<AcademicTask>) : AcademicTaskUiState()
    data class Error(val message: String) : AcademicTaskUiState()
}

enum class ScreenType {
    LIST, CREATE
}