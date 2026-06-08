package com.example.safepass2026.ui.presentation.grades

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safepass2026.domain.usecase.AddGradeUseCase
import com.example.safepass2026.domain.usecase.GetGradesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GradeViewModel(
    private val getGradesUseCase: GetGradesUseCase,
    private val addGradeUseCase: AddGradeUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<GradeUiState>(GradeUiState.Loading)
    val uiState: StateFlow<GradeUiState> = _uiState.asStateFlow()

    private val _currentScreen = MutableStateFlow(GradeScreenRoute.LIST)
    val currentScreen: StateFlow<GradeScreenRoute> = _currentScreen.asStateFlow()

    init {
        observeGrades()
    }

    private fun observeGrades() {
        viewModelScope.launch {
            try {
                getGradesUseCase().collect { grades ->
                    val average = if (grades.isEmpty()) 0.0 else grades.map { it.grade }.average()
                    _uiState.value = GradeUiState.Success(grades, average)
                }
            } catch (e: Exception) {
                _uiState.value = GradeUiState.Error("Error al cargar calificaciones: ${e.localizedMessage}")
            }
        }
    }

    fun navigateTo(screen: GradeScreenRoute) {
        _currentScreen.value = screen
    }

    fun addGrade(activityName: String, subject: String, gradeText: String) {
        viewModelScope.launch {
            try {
                val gradeValue = gradeText.toDoubleOrNull()
                    ?: throw IllegalArgumentException("La nota ingresada no es un número válido")
                addGradeUseCase(activityName, subject, gradeValue)
                _currentScreen.value = GradeScreenRoute.LIST
            } catch (e: IllegalArgumentException) {
                _uiState.value = GradeUiState.Error(e.message ?: "Dato inválido")
            } catch (e: Exception) {
                _uiState.value = GradeUiState.Error("Error al guardar: ${e.localizedMessage}")
            }
        }
    }

    fun clearError() {
        observeGrades()
    }
}
