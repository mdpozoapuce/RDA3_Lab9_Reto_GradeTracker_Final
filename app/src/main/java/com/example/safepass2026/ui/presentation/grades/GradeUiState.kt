package com.example.safepass2026.ui.presentation.grades

import com.example.safepass2026.domain.model.AcademicGrade

sealed class GradeUiState {
    object Loading : GradeUiState()
    data class Success(
        val grades: List<AcademicGrade>,
        val average: Double
    ) : GradeUiState()
    data class Error(val message: String) : GradeUiState()
}
