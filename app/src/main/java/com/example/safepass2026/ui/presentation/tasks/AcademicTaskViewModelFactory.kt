package com.example.safepass2026.ui.presentation.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.safepass2026.domain.repository.AcademicTaskRepository
import com.example.safepass2026.domain.usecase.AddTaskUseCase
import com.example.safepass2026.domain.usecase.GetTasksUseCase

class AcademicTaskViewModelFactory(
    private val getTasksUseCase: GetTasksUseCase,
    private val addTaskUseCase: AddTaskUseCase,
    private val repository: AcademicTaskRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AcademicTaskViewModel::class.java)) {
            return AcademicTaskViewModel(
                getTasksUseCase,
                addTaskUseCase,
                repository
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}