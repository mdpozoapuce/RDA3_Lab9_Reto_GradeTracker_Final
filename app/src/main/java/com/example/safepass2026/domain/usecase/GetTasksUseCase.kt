package com.example.safepass2026.domain.usecase

import com.example.safepass2026.domain.model.AcademicTask
import kotlinx.coroutines.flow.Flow
import com.example.safepass2026.domain.repository.AcademicTaskRepository

class GetTasksUseCase(private val repository: AcademicTaskRepository) {
    operator fun invoke(): Flow<List<AcademicTask>> {
        return repository.getTasksStream()
    }
}